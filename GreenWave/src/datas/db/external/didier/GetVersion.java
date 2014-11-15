package datas.db.external.didier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import view.activities.Home;
import view.activities.SelectionReseau;
import view.activities.SplashScreen;
import view.custom.LineList;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.item.LineClickListener;
import control.listeners.item.ReseauOnClickListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import datas.Arret;
import datas.Ligne;
import datas.Reseau;
import datas.db.internal.JuniorDAO;
import datas.utility.NetworkUtil;

public class GetVersion extends AsyncTask<Void, String, Integer> {
	
	private Reseau reseau;
	private Activity a;

	public GetVersion(Activity a){
		this.a=a;
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		JuniorDAO dao = new JuniorDAO(a);
	 	dao.open();
	 	ArrayList<Reseau> reseaux = dao.findReseaux();
		this.reseau = reseaux.get(0);
	 	dao.close();
	 	int ret = -1;
		    if(reseaux.size()!=0) {
		    	String reseauStr = PreferenceManager.getDefaultSharedPreferences(this.a).getString("pref_reseau","Aucun réseau");
		    	Log.d(reseauStr, "reseauStr");
		    	Iterator<Reseau> it = reseaux.iterator();
		    	while(it.hasNext()){
		    		Reseau rNew = it.next();
		    		if(rNew.toString().equals(reseauStr)){
		    			Log.d(rNew.toString(), "rNew");
		    			reseau=rNew;
		    		}
		    	}
		        //Success! Do what you want
		    	if(NetworkUtil.isConnected(a)){
		    		ret = this.getVersion(reseau);
		    	}
		    	else{
		    		ret = reseau.getVersion();
		    	}
		    }	  	  
		return ret;
	   
    }
	
	public int getVersion(Reseau r){
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/reseau.php?";
		int version = r.getVersion();
 
		 HttpURLConnection conn = null;
		    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        	sb.append("reseau=" + r.getIdBdd());
		   
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
        		JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
        		
        		version = jsonChildNode.optInt("version");	
        		Log.d(r.toString(), "Nouveau réseau découvert");
        		
        			
		    } catch (MalformedURLException e) {

		    } 
		    catch (JSONException e) {

		    }catch (IOException e) {
		    } finally {
		        if (conn != null) {
		            conn.disconnect();
		        }
		    }
		   return version;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
	    super.onPostExecute(result);
	    if(result != reseau.getVersion()){
	    	new DownloadFullReseau(a, reseau, result).execute();
	    }
	    else{
			Globale.engine.setReseau(reseau, a);
			Intent home = new Intent(a, Home.class);
	  	    a.startActivity(home);
	  	    a.finish();
	    }
	}
	    
		
}


