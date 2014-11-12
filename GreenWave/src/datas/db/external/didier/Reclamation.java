package datas.db.external.didier;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class Reclamation extends Thread{

	private int idAuteurReclamation;
	private int idHoraire;
	
	public Reclamation(int idHoraire, int idAuteurReclamation){
		this.idAuteurReclamation=idAuteurReclamation;
		this.idHoraire=idHoraire;
	}
	
	@Override
	public void run(){

		final String BASE_URL = "http://sauray.me/green_wave/reclamation.php?";

		 		HttpURLConnection conn = null;
			    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        	sb.append("idAuteurReclamation=" + idAuteurReclamation);
		        	sb.append("&idHoraire=" + idHoraire);
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
