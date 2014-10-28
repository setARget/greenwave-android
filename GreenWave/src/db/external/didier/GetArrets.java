package db.external.didier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import datas.Arret;
import datas.Ligne;
import datas.Reseau;

import android.os.AsyncTask;
import android.util.Log;

public class GetArrets extends AsyncTask<Void, String, ArrayList<Arret>> {

	int idReseau, idLigne;
	
	public GetArrets(){
		this.idReseau=-1;
		this.idLigne=-1;
	}
	
	public GetArrets(Reseau reseau){
		this.idReseau=reseau.getID();
		this.idLigne=-1;
	}
	
	public GetArrets(Reseau reseau, Ligne ligne){
		this.idReseau=reseau.getID();
		this.idLigne=ligne.getID();
	}
	
	@Override
	protected ArrayList<Arret> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/arret.php?";
		ArrayList<Arret> ret = new ArrayList<Arret>();
 
		 HttpURLConnection conn = null;
		    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        if(idReseau!=-1){
		        	sb.append("reseau=" + idReseau+"&");
		        }
		        if(idLigne!=-1){
		        	sb.append("ligne=" + idLigne);
		        }
		        URL url = new URL(sb.toString());
		        conn = (HttpURLConnection) url.openConnection();
		        InputStreamReader in = new InputStreamReader(conn.getInputStream());

		        BufferedReader jsonReader = new BufferedReader(in);
				String lineIn;
				while ((lineIn = jsonReader.readLine()) != null) {
				    jsonResult.append(lineIn);
				}
			
				JSONObject jsonObj = new JSONObject(jsonResult.toString());
		        
        		JSONArray jsonMainNode = jsonObj.optJSONArray("arret");

        		for (int i = 0; i < jsonMainNode.length(); i++) {
        			JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
        			int id = jsonChildNode.optInt("id");
        			String name = jsonChildNode.optString("nom");
        			double lat = jsonChildNode.optDouble("latitude");
        			double lng = jsonChildNode.optDouble("longitude");
        			int reseau = jsonChildNode.optInt("reseau");
        			Arret a = new Arret(new LatLng(lat, lng), name, null, null);
        			ret.add(a);
        			Log.d(a.toString(), "Nouvel arret trouvé");
        		}
        			
		    } catch (MalformedURLException e) {

		    } 
		    catch (JSONException e) {

		    }catch (IOException e) {
		    } finally {
		        if (conn != null) {
		            conn.disconnect();
		        }
		    }

		return null;
   
    }
	
	private StringBuilder inputStreamToString(InputStream is) {    
		String rLine = "";    
		StringBuilder answer = new StringBuilder();    
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));      
		try {     
			while ((rLine = rd.readLine()) != null) 
			{      answer.append(rLine);     }    
			}      catch (IOException e) {     
				// e.printStackTrace();        
				}    
		return answer;   
	}
		
}


