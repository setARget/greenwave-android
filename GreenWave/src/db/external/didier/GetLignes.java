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

import android.os.AsyncTask;
import android.util.Log;
import datas.Arret;
import datas.Ligne;
import datas.Reseau;

public class GetLignes extends AsyncTask<Void, String, ArrayList<Arret>> {
	
	private int idReseau, idLigne;
	
	public GetLignes(){
		idReseau=-1;
		idLigne=-1;
	}
	
	public GetLignes(Reseau r){
		idReseau = r.getID();
		idLigne=-1;
	}
	
	public GetLignes(Reseau r, Ligne l){
		idReseau = r.getID();
		idLigne = l.getID();
	}
	
	@Override
	protected ArrayList<Arret> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/ligne.php?";
		ArrayList<Ligne> ret = new ArrayList<Ligne>();
 
		 HttpURLConnection conn = null;
		    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        if(idReseau !=-1){
		        	sb.append("reseau=" + idReseau+"&");
		        }
		        if(idLigne !=-1){
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
		        
        		JSONArray jsonMainNode = jsonObj.optJSONArray("reseau");

        		for (int i = 0; i < jsonMainNode.length(); i++) {
        			JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
        			int id = jsonChildNode.optInt("id");
        			String nom = jsonChildNode.optString("nom");
        			String direction1 = jsonChildNode.optString("direction1");
        			String direction2 = jsonChildNode.optString("direction2");
        			int couleur = jsonChildNode.optInt("couleur");
        			Ligne l = new Ligne(id, nom, direction1, direction2, couleur);
        			ret.add(l);
        			Log.d(l.toString(), "Nouvelle ligne appartenant au reseau "+idReseau+" découverte");
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



