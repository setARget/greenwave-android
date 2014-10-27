package db.external.google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import view.activities.Home;
import view.fragments.CustomMapFragment;

import com.google.android.gms.maps.model.LatLng;
import control.Globale;
import datas.Place;

import android.os.AsyncTask;

/**
 * @author Antoine Sauray
 * @version 0.1
 */
public class GetDetails extends AsyncTask<Place, Place, Place> {

	private Home home;
	
	public GetDetails(Home home){
		this.home=home;
	}
	
	@Override
	protected Place doInBackground(Place... place) {
		// TODO Auto-generated method stub
		final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/details/json?";

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
		    StringBuilder sb = new StringBuilder(PLACES_API_BASE);
		    sb.append("&key=AIzaSyA_vH2XKyfNpGBc5ZzrvVW54-ShDVMesKk");
		    sb.append("&placeid=" + URLEncoder.encode(place[0].getID(), "utf8"));

		    URL url = new URL(sb.toString());
		    conn = (HttpURLConnection) url.openConnection();
		    InputStreamReader in = new InputStreamReader(conn.getInputStream());

		    // Load the results into a StringBuilder
		    int read;
		    char[] buff = new char[1024];
		    while ((read = in.read(buff)) != -1) {
		        jsonResults.append(buff, 0, read);
		    }
		} catch (MalformedURLException e) {
		    return null;
		} catch (IOException e) {
		    return null;
		} finally {
		    if (conn != null) {
		        conn.disconnect();
		    }
		}

			try {
			    // Create a JSON object hierarchy from the results
			    JSONObject jsonObj = new JSONObject(jsonResults.toString()).getJSONObject("result");
			    //jsonObj.getString("name");
			    JSONObject loc = jsonObj.getJSONObject("geometry").getJSONObject("location");
				LatLng placeLL = new LatLng(
					    Double.valueOf(loc.getString("lat")),
					    Double.valueOf(loc.getString("lng")));
				place[0].setLatLng(placeLL);
			} catch (JSONException e) {
		}
			return place[0];
	}
	@Override
	protected void onPostExecute(Place result) {
	    super.onPostExecute(result);
		 CustomMapFragment.addDestinationMarker(result.getLatLng());
		 if(Globale.engine.getLocation()!=null){
			 LatLng depart = new LatLng(Globale.engine.getLocation().getLatitude(), Globale.engine.getLocation().getLongitude());
			 CustomMapFragment.drawPolyline(depart, result.getLatLng());
		 }
		 Globale.engine.getDestination().showInfoWindow();
	}



}
