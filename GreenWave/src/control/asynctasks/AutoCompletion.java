package control.asynctasks;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import view.custom.RouteList;
import control.Globale;
import control.listeners.item.LineRouteClickListener;
import datas.Place;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AutoCompletion extends AsyncTask<String, Cursor, Cursor>{

	
	private static final String LOG_TAG = "ExampleApp";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyA_vH2XKyfNpGBc5ZzrvVW54-ShDVMesKk";
	
	@Override
	protected Cursor doInBackground(String... params) {
		// TODO Auto-generated method stub
		return autocomplete(params[0]);
	}
	
	private Cursor autocomplete(String input) {
	    ArrayList<String> resultList = null;
	    MatrixCursor c = new MatrixCursor(new String[]{"_id","description", "placeID"});
	    
	    HttpURLConnection conn = null;
	    StringBuilder jsonResults = new StringBuilder();
	    try {
	        StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
	        sb.append("?key=" + API_KEY);
	        sb.append("&components=country:fr");
	        sb.append("&language=fr");
	        sb.append("&types=address");
	        sb.append("&input=" + URLEncoder.encode(input, "utf8"));

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
	        Log.e(LOG_TAG, "Error processing Places API URL", e);
	        return c;
	    } catch (IOException e) {
	        Log.e(LOG_TAG, "Error connecting to Places API", e);
	        return c;
	    } finally {
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }

	    try {
	        // Create a JSON object hierarchy from the results
	        JSONObject jsonObj = new JSONObject(jsonResults.toString());
	        JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

	        
	        // Extract the Place descriptions from the results
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	        	 JSONObject placeObject = predsJsonArray.getJSONObject(i);
	            String description = placeObject.getString("description");              
	            String placeID = placeObject.getString("place_id");   
	            c.addRow(new String[]{"1", description, placeID});
	            Globale.engine.getPlacesMap().put(description, new Place(description, placeID, null));
	    		Log.d("placeID", "placeID="+placeID);
	        }
	    } catch (JSONException e) {
	        Log.e(LOG_TAG, "Cannot process JSON results", e);
	    }

	    return c;
	}
}

