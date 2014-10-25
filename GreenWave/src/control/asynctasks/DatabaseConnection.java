package control.asynctasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */

public class DatabaseConnection extends AsyncTask<String,String,JSONObject>{
 
    ArrayList<HashMap<String, String>> productsList;
 
    // url to get all products list
    private static String url_arrets = "http://api.androidhive.info/android_connect/get_all_products.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_PID = "pid";
    private static final String TAG_NAME = "name";
    
	@Override
	protected JSONObject doInBackground(String... arretsUrl) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		
		String dayOfWeek=null;
		
		switch (day) {
	    case Calendar.SUNDAY:
	        dayOfWeek="Dimanche";
	    break;
	    case Calendar.SATURDAY:
	        dayOfWeek="Samedi";
	    break;
	    default:
	    	dayOfWeek="Semaine";
	    break;
	}
		Log.d("Jour de la semaine", dayOfWeek);

		JSONObject ret = null;
		
		StringBuilder stopBuilder = new StringBuilder();
		for (String stopSearchURL : arretsUrl) {
			//execute search
			HttpClient stopClient = new DefaultHttpClient();
			try {
			    //try to fetch the data
				HttpGet stopGet = new HttpGet(stopSearchURL);
				HttpResponse stopResponse = stopClient.execute(stopGet);
				StatusLine stopSearchStatus = stopResponse.getStatusLine();
				
				if (stopSearchStatus.getStatusCode() == 200) {
					//we have an OK response
					HttpEntity stopEntity = stopResponse.getEntity();
					InputStream stopContent = stopEntity.getContent();
					InputStreamReader stopInput = new InputStreamReader(stopContent);
					
					BufferedReader stopReader = new BufferedReader(stopInput);
					String lineIn;
					while ((lineIn = stopReader.readLine()) != null) {
					    stopBuilder.append(lineIn);
					}
				}
				ret = new JSONObject(stopBuilder.toString());
			}
			catch(Exception e){
			    e.printStackTrace();
			}
		}
		Log.d("DoInBackground", stopBuilder.toString());
		
		return ret;
	}
	
	@Override
	protected void onPostExecute(JSONObject resultObject) {
		super.onPostExecute(resultObject);

		try {
			JSONArray placesArray = resultObject.getJSONArray("results");
			boolean missingValue=false;
			
			for (int p=0; p<placesArray.length(); p++) {
			    //parse each place
				try{
				    //attempt to retrieve place data values
					missingValue=false;
					JSONObject placeObject = placesArray.getJSONObject(p);
					JSONObject loc = placeObject.getJSONObject("nom").getJSONObject("location");
					JSONArray horaires = placeObject.getJSONArray("horaire");
					
					for(int t=0; t<horaires.length(); t++){
					    //what type is it
						String thisType=horaires.get(t).toString();
					}
					//vicinity = placeObject.getString("vicinity");
					//placeName = placeObject.getString("name");
				}
				catch(JSONException jse){
				    missingValue=true;
				    jse.printStackTrace();
				}
			}
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	}


}
