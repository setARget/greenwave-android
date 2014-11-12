package datas.db.external.didier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import control.Globale;

import view.activities.Home;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import datas.Horaire;
import datas.Reseau;

public class InsertHoraire extends AsyncTask<Void, String, Boolean> {
	
	private Context c;
	private Horaire h;
	private int idLigne, idArret, direction;
	private String calendrier;
	
	public InsertHoraire(Context c, Horaire h, int ligne, int arret, int direction, String calendrier){
		this.c=c;
		this.h=h;
		this.idLigne=ligne;
		this.idArret=arret;
		this.direction=direction;
		this.calendrier=calendrier;
	}


	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		int idAuteur = Globale.engine.getUtilisateur().getId();
		boolean resultat=false;
		if(idAuteur != -1){
		final String BASE_URL = "http://sauray.me/green_wave/inserthoraire.php?";

		 		HttpURLConnection conn = null;
			    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        	sb.append("horaire=" + h.toString());
		        	sb.append("&ligne=" + idLigne);
		        	sb.append("&arret=" + idArret);
		        	sb.append("&direction=" + direction);
		        	sb.append("&calendrier=" + calendrier);
		        	sb.append("&idAuteur=" + idAuteur);
		        	URL url = new URL(sb.toString());
		        	url.openStream();     			
		        	Log.d("insertion", "insertion");
			    } catch (MalformedURLException e) {

			    } 
			    catch (IOException e) {
			    } finally {
			        if (conn != null) {
			            conn.disconnect();
			        }
			    }
		}
		else{
			Toast.makeText(c,
					"Erreur id",
					Toast.LENGTH_LONG).show();
		}
		        return resultat;
	}

	
	

	@Override
	protected void onPostExecute(Boolean result) {
	    super.onPostExecute(result);
	    Globale.engine.getUtilisateur().decrementQuota();
	}
}
    