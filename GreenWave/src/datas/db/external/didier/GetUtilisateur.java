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

import view.activities.Home;
import view.activities.SplashScreen;

import control.Globale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import datas.Utilisateur;
import datas.db.internal.JuniorDAO;

public class GetUtilisateur extends Thread{
	
	private Utilisateur u;
	private String idFacebook, pseudo, password;
	private Context c;
	
	public GetUtilisateur(String idFacebook){
		this.idFacebook=idFacebook;
		this.password=null;
	}
	
	public GetUtilisateur(Context c, String pseudo, String password){
		this.c=c;
		this.idFacebook=null;
		this.pseudo=pseudo;
		this.password=password;
	}
	
	@Override
	public void run(){
		Log.d("GetUtilisateur", "GetUtilisateur");
		Integer ret = null;
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/utilisateur.php?";
		HttpURLConnection conn = null;
		StringBuilder sb = new StringBuilder(BASE_URL);
		
		if(this.password!=null && pseudo != null){
			
    		sb.append("pseudo=" + this.pseudo);
    		sb.append("&password=" + this.password);
   		 try {
		        URL url = new URL(sb.toString());
		        conn = (HttpURLConnection) url.openConnection();
		        InputStreamReader in = new InputStreamReader(conn.getInputStream());

		        BufferedReader jsonReader = new BufferedReader(in);
				String lineIn;
				while ((lineIn = jsonReader.readLine()) != null) {
				    jsonResult.append(lineIn);
				}
			
				JSONObject jsonObj = new JSONObject(jsonResult.toString());
		        
      		JSONArray jsonMainNode = jsonObj.optJSONArray("utilisateur");
      		JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
      		
      			int id = jsonChildNode.optInt("id");
      			String pseudo = jsonChildNode.optString("pseudo");	
	        	String nom = jsonChildNode.optString("nom");	
	        	String prenom = jsonChildNode.optString("prenom");	
	        	int quota = jsonChildNode.optInt("quota");
	        	int nbHorairesAjoutees = jsonChildNode.optInt("nbHorairesAjoutees");
	        	int nbErreurs = jsonChildNode.optInt("nbErreurs");
	        	int nbApprobations = jsonChildNode.optInt("nbApprobations");
	        	int statut = jsonChildNode.optInt("statut");
	        	Globale.engine.setUtilisateur(new Utilisateur(id, nom, prenom, quota, nbHorairesAjoutees, nbErreurs, nbApprobations, statut));
	        	Log.d("", "Utilisateur trouvé");
	        	Toast.makeText(c,
						"Connexion réussie",
						Toast.LENGTH_LONG).show();
	        	if(statut!=Utilisateur.BANNI){
		        	Intent intent = new Intent(c, SplashScreen.class);
			    	c.startActivity(intent);
	        	}
     			
		    } catch (MalformedURLException e) {

		    } 
		    catch (JSONException e) {
		    	Toast.makeText(c,
						"Erreur connexion",
						Toast.LENGTH_LONG).show();

		    }catch (IOException e) {
		    } finally {
		        if (conn != null) {
		            conn.disconnect();
		        }
		    }
 	
    	}
    	else if(this.idFacebook!=null){
    		sb.append("idFacebook="+this.idFacebook);
    		 try {
   
 		        URL url = new URL(sb.toString());
 		        conn = (HttpURLConnection) url.openConnection();
 		        InputStreamReader in = new InputStreamReader(conn.getInputStream());

 		        BufferedReader jsonReader = new BufferedReader(in);
 				String lineIn;
 				while ((lineIn = jsonReader.readLine()) != null) {
 				    jsonResult.append(lineIn);
 				}
 			
 				JSONObject jsonObj = new JSONObject(jsonResult.toString());
 		        
         		JSONArray jsonMainNode = jsonObj.optJSONArray("utilisateur");
         		JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
         		
         			int id = jsonChildNode.optInt("id");
         			String idFacebook = jsonChildNode.optString("idFacebook");
 	        		String nom = jsonChildNode.optString("nom");	
 	        		String prenom = jsonChildNode.optString("prenom");	
 	        		int quota = jsonChildNode.optInt("quota");
 	        		int nbHorairesAjoutees = jsonChildNode.optInt("nbHorairesAjoutees");
 	        		int nbErreurs = jsonChildNode.optInt("nbErreurs");
 	        		int nbApprobations = jsonChildNode.optInt("nbApprobations");
 	        		int statut = jsonChildNode.optInt("statut");
 	        		Globale.engine.setUtilisateur(new Utilisateur(id, idFacebook, nom, prenom, quota, nbHorairesAjoutees, nbErreurs, nbApprobations, statut));
 	        		Log.d("", "Utilisateur trouvé");
        			
 		    } catch (MalformedURLException e) {

 		    } 
 		    catch (JSONException e) {
 		    	u = new Utilisateur(idFacebook, Globale.engine.getUtilisateur().getNom(), Globale.engine.getUtilisateur().getPrenom(), 3);
     			insererUtilisateur(Globale.engine.getUtilisateur().getIdFacebook(), Globale.engine.getUtilisateur().getNom(), Globale.engine.getUtilisateur().getPrenom());
     			Globale.engine.setUtilisateur(u);
     			Log.d("", "Utilisateur ajouté");

 		    }catch (IOException e) {
 		    } finally {
 		        if (conn != null) {
 		            conn.disconnect();
 		        }
 		    }
    	}
 	   
	}
	
	public void insererUtilisateur(String idFacebook, String nom, String prenom){
		final String BASE_URL = "http://sauray.me/green_wave/insertutilisateur.php?";

 		HttpURLConnection conn = null;
	    try {
        StringBuilder sb = new StringBuilder(BASE_URL);
        	sb.append("idFacebook=" + idFacebook);
        	sb.append("&nom=" + nom);
        	sb.append("&prenom=" + prenom);
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
	
}
