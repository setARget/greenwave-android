package datas.db.external.didier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import view.activities.Home;

import com.google.android.gms.maps.model.LatLng;

import control.Globale;

import datas.Arret;
import datas.Ligne;
import datas.Reseau;
import datas.db.internal.JuniorDAO;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * 
 * @author Antoine Sauray
 * Downloads a full "reseau" and adds it to the internal database
 * Keeps a track of the version downloaded
 */
public class DownloadFullReseau extends AsyncTask<Void, String, HashMap<String, Arret>> {

	private Reseau reseau;
	private HashMap<String, Ligne> lignes;
	private ProgressDialog pd;
	private Context c;
	
	public DownloadFullReseau(Context c, Reseau reseau){
		this.reseau=reseau;
		this.c=c;
	}
	
	@Override
	protected void onPreExecute() {
	    super.onPreExecute();
		pd = new ProgressDialog(c);
		pd.setTitle("Téléchargement du Réseau de Bus de "+reseau.toString());
		pd.show();
	}
	
	@Override
	protected HashMap<String, Arret> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		HashMap<String, Arret> ret = new HashMap<String, Arret>();
		getLignes();	// On récupère toutes les lignes du réseau
		getAllArrets();	// On récupère tous les arrets du réseau
		 
		Iterator<Ligne> it = lignes.values().iterator();	// On assigne à chaque ligne ses arrets
		while(it.hasNext()){
			Ligne l = it.next();
			getArrets(l);
			this.publishProgress("Téléchargement des arrets de la ligne "+l.toString());
		}

		return ret;
   
    }
	
	private void getAllArrets(){
		
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/arret.php?";
		HashMap<String, Arret> ret = new HashMap<String, Arret>();
 
		 HttpURLConnection conn = null;
		try {
	        StringBuilder sb = new StringBuilder(BASE_URL);
	        	sb.append("reseau=" + reseau.getIdBdd());
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
    			String nom = jsonChildNode.optString("nom");
    			double lat = jsonChildNode.optDouble("latitude");
    			double lng = jsonChildNode.optDouble("longitude");
    			int reseau = jsonChildNode.optInt("reseau");
    			Arret a = new Arret(id, nom, new LatLng(lat, lng), reseau);
    			ret.put(nom, a);
    			this.publishProgress("Téléchargement des arrets du réseau de "+this.reseau.toString());
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
		
		reseau.setArrets(ret);
	}

	private void getArrets(Ligne l){
		
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/arret.php?";
		HashMap<String, Arret> ret = new HashMap<String, Arret>();
		HttpURLConnection conn = null;
		 
	    try {
	        StringBuilder sb = new StringBuilder(BASE_URL);
	        sb.append("ligne=" + l.getIdBdd());
	        
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
   			String nom = jsonChildNode.optString("nom");
   			double lat = jsonChildNode.optDouble("latitude");
   			double lng = jsonChildNode.optDouble("longitude");
   			int reseau = jsonChildNode.optInt("reseau");
   			Arret a = new Arret(id, nom, new LatLng(lat, lng), reseau);
   			ret.put(nom, a);
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
	    l.setArrets(ret);
	}
	
	private void getLignes(){
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/ligne.php?";
		HashMap<String, Ligne> ret = new HashMap<String, Ligne>();
		 HttpURLConnection conn = null;
		    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        if(reseau.getIdBdd() !=-1){
		        	sb.append("reseau=" + reseau.getIdBdd()+"&");
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
		        
        		JSONArray jsonMainNode = jsonObj.optJSONArray("ligne");

        		for (int i = 0; i < jsonMainNode.length(); i++) {
        			JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
        			int id = jsonChildNode.optInt("id");
        			String nom = jsonChildNode.optString("nom");
        			String direction1 = jsonChildNode.optString("direction1");
        			String direction2 = jsonChildNode.optString("direction2");
        			String couleur = jsonChildNode.optString("couleur");
        			Ligne l = new Ligne(id, nom, direction1, direction2, couleur, 1);
        			ret.put(nom, l);
        			this.publishProgress("Téléchargement de la "+l.toString());
        			Log.d(l.toString(), "Nouvelle ligne appartenant au reseau "+reseau.getIdBdd()+" découverte");
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
		    reseau.setLignes(ret);
			this.lignes=ret;

	}
	@Override
	protected void onProgressUpdate(String... progress) {
		pd.setMessage(progress[0]);
	}
	
	@Override
	protected void onPostExecute(HashMap<String, Arret> result) {
	    super.onPostExecute(result);
		pd.dismiss();
		JuniorDAO dao = new JuniorDAO(c);
		dao.open();
		dao.insertReseau(reseau);
		Globale.engine.setReseau(reseau, c);
		Intent home = new Intent(c, Home.class);
  	    c.startActivity(home);
	}
		
}


