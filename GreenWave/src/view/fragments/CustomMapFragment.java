package view.fragments;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import view.activities.Home;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.wavon.greenwave.R;

import control.Globale;
import control.KiceoControl;
import datas.Arret;
import datas.Ligne;
import datas.db.external.google.GetPlaces;

/**
 * MapFragment is a Fragment Object which displays a map.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.2.1
 */
public class CustomMapFragment extends SupportMapFragment{

	 private static GoogleMap gMap;
	 
	 private ArrayAdapter<Ligne> adapter2;
	 private ArrayAdapter<Arret> adapter3;
	 
	 private static TreeMap<String, Marker> markerHash;
	 private MarkerOptions[] places;
	 private static HashMap<String, Polyline> polyHash;
	 private final int MAX_PLACES = 20;
	 
	 private View v;
	 private Home home;	// Current activity
	 SharedPreferences sharedPref;

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		 super.onCreateView(inflater, container, savedInstanceState);
		 Log.d("OnCreateView Map", "Started");
		 v = inflater.inflate(R.layout.fragment_map, container, false);
		 
	     home = (Home) this.getActivity();
			 gMap = (((com.google.android.gms.maps.SupportMapFragment)this.getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap());
	      
			 gMap.setMyLocationEnabled(true);
			 gMap.setTrafficEnabled(true);
			 gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.748122, -3.364546), 14.0f) );
		    
		 sharedPref = PreferenceManager.getDefaultSharedPreferences(home);

 
	      initInterface();
	      attachReactions();
	      setHasOptionsMenu(true);
	      Log.d("OnCreateView Map", "Finished");
	      
		  return v;
	 }
	 
	 @Override
	 public void onDestroyView() {
	     super.onDestroyView();
	     try{
	         getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.map)).commit();
	     }
	     catch(Exception e){
	    	 
	     }
	     finally{
	    	 
	     }
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 item.getItemId();
       
		 // handle item selection
		 switch (item.getItemId()) {
		 default: 	         
		 }

		 return super.onOptionsItemSelected(item);
	 }
   
	 @Override
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	 
		  inflater.inflate(R.menu.map_menu, menu);
		   	
		  MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		  SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );
		  SearchView search = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
	
		  	int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		  	TextView textView = (TextView) search.findViewById(id);
		  	textView.setTextColor(Color.WHITE);
		  	textView.setHintTextColor(Color.WHITE);
		  
		  search.setSearchableInfo(searchManager.getSearchableInfo(home.getComponentName()));
		  control.listeners.search.OnFocusChangeListener listener = new control.listeners.search.OnFocusChangeListener(search, home);
		  search.setOnQueryTextFocusChangeListener(listener);
		  search.setOnQueryTextListener(listener);
		  search.setOnSuggestionListener(listener);
		    
		  super.onCreateOptionsMenu(menu, inflater);
	 }
	 
	 @Override
	 public void onResume() {
		 super.onResume();	
		 if (!getUserVisibleHint())
		    {
		        return;
		    }
		 
		 String pref_map_type = sharedPref.getString("pref_map_type", "Normale");
		 
		 if(pref_map_type.equals("Normale")){
			 gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		 }
		 else if(pref_map_type.equals("Hybride")){
			 gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		 }
		 else{
			 gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		 }
		 
		 	Log.d("OnResume()", "Carte");
		 	if(Globale.engine.getLigneCourante() != null){
		 		addMarkers();
		 	}
			 if(Globale.engine.getUpdateLigne()==true){	// si une mise à jour de la ligne est demandée		
				gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.748122, -3.364546), 14.0f) );
	            Globale.engine.setUpdateLigne(false);
			 }
			 
			 if (Globale.engine.getUpdateArret()==true){	// si une mise à jour de l'arret est demandée
					Marker marker = markerHash.get(Globale.engine.getArretCourant().toString());
				 if(Globale.engine.getLocation()!=null){
						LatLng l = marker.getPosition();
						Location loc = new Location("arret");
						loc.setLatitude(l.latitude);
						loc.setLongitude(l.longitude);
						marker.setSnippet("Distance : "+(int)Globale.engine.getLocation().distanceTo(loc)+"m");
					}
				 else{
					 //marker.setSnippet(Globale.engine.getArretCourant().getVille());
				 }
					
				gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Globale.engine.getArretCourant().getLatLng(), 14.0f) );
				marker.showInfoWindow(); 
				Globale.engine.setUpdateArret(false);
			 }
				
			 
			 if (Globale.engine.getLocation() != null ){
			 	if(Globale.engine.getUpdateLigne()!=true){
			 			 updateMarkerDistance();
			 	}
			 	
			 	boolean pref_gPlaces = sharedPref.getBoolean("pref_gplaces", true);
				 
				 if(pref_gPlaces){
				 
				Location position = Globale.engine.getLocation();
					
				String latVal=String.valueOf(position.getLatitude());
				String lngVal=String.valueOf(position.getLongitude());
				String url;
					try {
					        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
					        +URLEncoder.encode(latVal, "UTF-8")
					        +","
					        +URLEncoder.encode(lngVal, "UTF-8")
					        +"&radius="
					        +URLEncoder.encode("10000", "UTF-8")
					        +"&sensor="
					        +URLEncoder.encode("true", "UTF-8")
					        +"&types="
					        +URLEncoder.encode("bar", "UTF-8")
					        +"&key="
					        +URLEncoder.encode("AIzaSyA_vH2XKyfNpGBc5ZzrvVW54-ShDVMesKk", "UTF-8");
					  		new GetPlaces(position, Globale.engine.getPlacesMarkers(), places, gMap).execute(url);
					  		//+URLEncoder.encode("food|bar|church|museum|art_gallery", "UTF-8")
					 } catch (UnsupportedEncodingException e) {
					        // TODO Auto-generated catch block
					        e.printStackTrace();
						}
				 }
			 }
			 if(Globale.engine.getDestination() != null){
				 addDestinationMarker(Globale.engine.getDestination().getPosition());	  
			 }
	    	//setHasOptionsMenu(true);
   }

	 @Override
	 public void onPause() {
	     super.onPause();
	 }
	 
	 @Override
	 public void setUserVisibleHint(boolean visible)
	 {
		 super.setUserVisibleHint(visible);
		 if (visible && isResumed()){
           //Only manually call onResume if fragment is already visible
           //Otherwise allow natural fragment lifecycle to call onResume
           onResume();
		 }
	 }
   
	 private void initInterface(){
		 markerHash = Globale.engine.getHash();
		 polyHash = new HashMap<String, Polyline>();
		 Globale.engine.setPlacesMarkers(new Marker[MAX_PLACES]);
	 }
   
	 private void attachReactions(){
		 KiceoControl kc = new KiceoControl(home);
		 gMap.setOnMarkerClickListener(kc);
		 gMap.setOnInfoWindowClickListener(kc);
		 gMap.setOnMapLongClickListener(kc); 
	 }	
	
	 public ArrayAdapter<Ligne> getLigneAdapter(){return adapter2;}
	
	 public ArrayAdapter<Arret> getArretAdapter(){return adapter3;}
	 
	 public TreeMap<String, Marker> getMarkers(){return CustomMapFragment.markerHash;}
	
	 public String toString(){
		 return Globale.engine.getReseau().toString();
	 }
	
	 private void updateMarkerDistance(){
		 Iterator<Marker> it = new ArrayList<Marker>(markerHash.values()).iterator();
		 Location currentLocation = Globale.engine.getLocation();
		 while(it.hasNext()){
			 Marker m = it.next();
			 Location loc = new Location("marker");
			 loc.setLatitude(m.getPosition().latitude);
			 loc.setLongitude(m.getPosition().longitude);
			 m.setSnippet("Distance : "+(int)currentLocation.distanceTo(loc)+"m");
		 }
	 }
	
	 public static void addDestinationMarker(LatLng point){
		 if(Globale.engine.getDestination() != null){
			 Globale.engine.getDestination().remove();
		 }
		 if(Globale.engine.getLocation() != null){
			 // on calcule la ligne qui sera la plus efficace
			 //new CalculLignePertinente(home, markerHash, gMap, point, destination).execute();
			 Globale.engine.setDestination(gMap.addMarker(new MarkerOptions()
	           	.position(point)
	           	.title("Destination")
	           	.snippet("Clickez pour obtenir l'itinéraire")
	            .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)))); 
		}
		else{
			Globale.engine.setDestination(gMap.addMarker(new MarkerOptions()
	           	.position(point)
	           	.title("Destination")
	           	.snippet("Géolocalisation non activée")
	           	.icon(BitmapDescriptorFactory.fromResource(R.drawable.destination)))); 
		}
		 gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(point, 14.0f));
	 }
	 
	 public static void drawPolyline(LatLng depart, LatLng arrivee){
		 Polyline poly =  polyHash.get("itineraire");
		 if(poly!=null){poly.remove();}
		 polyHash.put("itineraire", gMap.addPolyline(new PolylineOptions()
		    .add(depart, arrivee)
		    .width(8)
		    .color(Color.CYAN)));
	 }

	 private static void addMarkers(){
		 Iterator<Arret> it = Globale.engine.getLigneCourante().getArrets().values().iterator();
			BitmapDescriptor defaultColor=Globale.engine.getLigneCourante().getMarkerColor();
			gMap.clear();
			markerHash.clear();

			if(Globale.engine.getLocation() != null){
				Log.d("AddMarkers", "Location activée");
				Location currentLocation = Globale.engine.getLocation();
				// Les arrets basiques
				while(it.hasNext()){
					
					Arret a = (Arret) it.next();

					markerHash.put(a.toString(), gMap.addMarker(new MarkerOptions()
				        	.position(a.getLatLng())
				        	.alpha(0.7f)
				        	.title(a.toString())     	
				        	.icon(defaultColor)));	
				}
			}
			else{
				Log.d("AddMarkers", "Location désactivée");
				while(it.hasNext()){
					
					Arret a = (Arret) it.next();
					markerHash.put(a.toString(), gMap.addMarker(new MarkerOptions()
				        	.position(a.getLatLng())
				        	.alpha(0.7f)
				        	.title(a.toString())
				        	.icon(defaultColor)));	
				}
			}

	 }




	 
}
