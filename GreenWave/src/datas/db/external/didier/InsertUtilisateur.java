package datas.db.external.didier;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import datas.Utilisateur;
import android.os.AsyncTask;
import android.util.Log;

public class InsertUtilisateur extends AsyncTask<Void, String, Boolean> {
	
	private String id, idFacebook, pseudo, password, prenom, nom;
	
	public InsertUtilisateur(Utilisateur u){
		if(u.getIdFacebook()!=null){
			this.idFacebook=idFacebook;
		}
		this.prenom=u.getPrenom();
		this.nom=u.getNom();
		this.password=null;
	}
	
	public InsertUtilisateur(String pseudo, String password){
		this.prenom=null;
		this.nom=null;
		this.pseudo=pseudo;
		this.password=password;
	}


	@Override
	protected Boolean doInBackground(Void... params) {
		// TODO Auto-generated method stub
		boolean resultat=false;

		final String BASE_URL = "http://sauray.me/green_wave/insertutilisateur.php?";

		 		HttpURLConnection conn = null;
			    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        if(idFacebook!=null){
		        	sb.append("idFacebook=" + idFacebook);
		        }
		        else if (password!=null && pseudo!=null){
		        	sb.append("login=" + pseudo);
		        	sb.append("&password=" + password);
		        }
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
		
		        return resultat;
	}

	
	

	@Override
	protected void onPostExecute(Boolean result) {
	    super.onPostExecute(result);
	}
}
    