package control;

import view.activities.Home;
import view.activities.Horaire;
import view.activities.Route;
import view.fragments.CustomMapFragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import datas.Arret;
import datas.Ligne;

/**
 *	© Copyright 2014 Antoine Sauray
 *  @author Antoine Sauray & Quentin Pitalier
 *  @version 0.1
 */
public class KiceoControl implements Globale, OnInfoWindowClickListener, OnMarkerClickListener, OnMapLongClickListener{

        private Home a;
        // ---------- Constructor
        

    	/**
     	 * Initialize the datas and ihm of MEF application.
     	 */
    	public KiceoControl (Home a) {
            //(Globale.view).init(this);
            this.a=a;
    	} // ------------------------------------------------------------- MefCtrl()

    	
        // ---------- Methods
		public void sendSms(String dest, String message, View v){
        	try {
				SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(dest, null, message, null, null);
				Toast.makeText((Home) v.getContext(), "SMS Sent!",
						Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				Toast.makeText((Home) v.getContext(),
						"SMS faild, please try again later!",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}

		@Override
		public void onInfoWindowClick(Marker marker) {
			// TODO Auto-generated method stub
			int i = 0;
			boolean isPlace=false;

			if(!isPlace){
			Ligne l = Globale.engine.getLigneCourante();
				if(l!=null){
					Arret arret = l.getArrets().get(marker.getTitle());
					//if(arret == null){arret = Globale.engine.getEntreprise().getArretsFavoris().get(marker.getTitle());}
					if(arret != null && !Globale.engine.getPlacesMap().containsKey(marker.getTitle())){
						// On a un arret de selectionné
						Globale.engine.setArretCourant(arret);
						//TabbedDialog td = new TabbedDialog(a, marker.getTitle());
						//td.show();
						Intent intent = new Intent(a, Horaire.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						a.startActivity(intent);
					}
				}
			}
			else{
				// Un lieu a été selectionné
				Globale.engine.setDestination(marker);
				Intent intent = new Intent(a, Route.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				a.startActivity(intent);
			}
		}
		
		public static int getConnectivityStatus(Context context) {
	        ConnectivityManager cm = (ConnectivityManager) context
	                .getSystemService(Context.CONNECTIVITY_SERVICE);
	 
	        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
	        if (null != activeNetwork) {
	            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
	            	//WIFI
	                return 1;
	             
	            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
	            	//MOBILE
	                return 2;
	        }
	        //Not connected
	        return 0;
	    }


		@Override
		public boolean onMarkerClick(Marker marker) {
			// TODO Auto-generated method stub
			/*
			int i = 0;
			boolean isPlace=false;
			// Si c'est un "place" alors on ne modifiera pas le snippet
			while(i<Globale.engine.getPlacesMarkers().length && isPlace==false){
				if(marker.getTitle()==Globale.engine.getPlacesMarkers()[i].getTitle()){
					isPlace=true;
				}
				i++;
			}
			
			if(Globale.engine.getLocation()!=null && isPlace==false){
				LatLng l = marker.getPosition();
				Location loc = new Location("arret");
				loc.setLatitude(l.latitude);
				loc.setLongitude(l.longitude);
				//marker.setSnippet("Distance : "+(int)Globale.engine.getLocation().distanceTo(loc)+"m");
			}
			else{
				marker.setSnippet(Globale.engine.getEntreprise().getArrets().get(marker.getTitle()).getVille());
			}
		*/
			return false;
		}
		
		 @Override
		 public void onMapLongClick(LatLng point) {
			 // TODO Auto-generated method stub
			 CustomMapFragment.addDestinationMarker(point);
			 Globale.engine.getDestination().showInfoWindow();
			 if(Globale.engine.getLocation()!=null){
				 LatLng depart = new LatLng(Globale.engine.getLocation().getLatitude(), Globale.engine.getLocation().getLongitude());
				 CustomMapFragment.drawPolyline(depart, point);
			 }
		 }
		
}
