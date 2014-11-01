package datas.db.external.google;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wavon.greenwave.R;

import control.Globale;
import datas.Place;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

/**
 * @author Antoine Sauray
 * @version 0.1
 */
public class GetPlaces extends AsyncTask<String, String, JSONObject> {

	 private Marker[] placeMarkers;
	 private MarkerOptions[] places;
	 private GoogleMap map;
	
	public GetPlaces(Location location, Marker[] placeMarkers, MarkerOptions[] places, GoogleMap map){
		this.placeMarkers=placeMarkers;
		this.places=places;
		this.map=map;
		Log.d("GetPlaces", "Update");
	}
	
	@Override
	protected JSONObject doInBackground(String... placesURL) {
		// TODO Auto-generated method stub
		
		JSONObject ret = null;
		
		StringBuilder placesBuilder = new StringBuilder();
		for (String placeSearchURL : placesURL) {
			//execute search
			HttpClient placesClient = new DefaultHttpClient();
			try {
			    //try to fetch the data
				HttpGet placesGet = new HttpGet(placeSearchURL);
				HttpResponse placesResponse = placesClient.execute(placesGet);
				StatusLine placeSearchStatus = placesResponse.getStatusLine();
				
				if (placeSearchStatus.getStatusCode() == 200) {
					//we have an OK response
					HttpEntity placesEntity = placesResponse.getEntity();
					InputStream placesContent = placesEntity.getContent();
					InputStreamReader placesInput = new InputStreamReader(placesContent);
					
					BufferedReader placesReader = new BufferedReader(placesInput);
					String lineIn;
					while ((lineIn = placesReader.readLine()) != null) {
					    placesBuilder.append(lineIn);
					}
				}
				ret = new JSONObject(placesBuilder.toString());
			}
			catch(Exception e){
			    e.printStackTrace();
			}
		}
		Log.d("DoInBackground", placesBuilder.toString());
		return ret;
	}
	
	@Override
	protected void onPostExecute(JSONObject resultObject) {
		super.onPostExecute(resultObject);

		
		if(placeMarkers!=null){
		    for(int pm=0; pm<placeMarkers.length; pm++){
		        if(placeMarkers[pm]!=null)
		            placeMarkers[pm].remove();
		    }
		}

		try {
			JSONArray placesArray = resultObject.getJSONArray("results");
			places = new MarkerOptions[placesArray.length()];
			
			//loop through places
			boolean missingValue=false;
			LatLng placeLL=null;
			String placeName="";
			String vicinity="";
			int currIcon = (int) BitmapDescriptorFactory.HUE_YELLOW;
			
			for (int p=0; p<placesArray.length(); p++) {
			    //parse each place
				try{
				    //attempt to retrieve place data values
					missingValue=false;
					JSONObject placeObject = placesArray.getJSONObject(p);
					JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");
					placeLL = new LatLng(
						    Double.valueOf(loc.getString("lat")),
						    Double.valueOf(loc.getString("lng")));
					JSONArray types = placeObject.getJSONArray("types");
					
					for(int t=0; t<types.length(); t++){
					    //what type is it
						String thisType=types.get(t).toString();
						Log.d("Type", thisType);
						if(thisType.contains("bar")){
						    currIcon = R.drawable.bar;
						    break;
						}						
						else if(thisType.contains("food") || thisType.contains("meal_takeaway")){
						    currIcon = R.drawable.restaurant;
						    break;
						}
						else if(thisType.contains("store")){
						    currIcon = R.drawable.store;
						    break;
						}
					}
					vicinity = placeObject.getString("vicinity");
					placeName = placeObject.getString("name");
				}
				catch(JSONException jse){
				    missingValue=true;
				    jse.printStackTrace();
				}
				
				if(missingValue)    places[p]=null;
				else
					Globale.engine.getPlacesMap().put(placeName, new Place(placeName, placeLL));
				    places[p]=new MarkerOptions()
				    .position(placeLL)
				    .title(placeName)
				    .snippet(vicinity)
				    .icon(BitmapDescriptorFactory.fromResource(currIcon));
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		
		if(places!=null && placeMarkers!=null){
		    for(int p=0; p<places.length && p<placeMarkers.length; p++){
		        //will be null if a value was missing
		        if(places[p]!=null)
		            placeMarkers[p]=map.addMarker(places[p]);
		        Log.d("New Place", places[p].toString());
		    }
		}
	}

}
