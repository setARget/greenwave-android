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

import view.custom.LineList;
import view.custom.ReseauList;

import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.item.LineClickListener;
import control.listeners.item.ReseauOnClickListener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import datas.Arret;
import datas.Ligne;
import datas.Reseau;
import datas.db.internal.JuniorDAO;

public class GetReseaux extends AsyncTask<Void, Reseau, ArrayList<Reseau>> {
	
	private int idReseau;
	private RelativeLayout layout;
	private TextView operation;
	private ListView lv_offline;
	private Activity a;
	ArrayList<Reseau> reseaux = new ArrayList<Reseau>();

	public GetReseaux(Activity a, ListView lv_offline, RelativeLayout layout){
		idReseau=-1;
		this.layout=layout;
		this.a=a;
		layout.setVisibility(View.VISIBLE);
		//lv_online.setVisibility(View.GONE);
		operation = (TextView) a.findViewById(R.id.operation);
		reseaux = new ArrayList<Reseau>();
		this.lv_offline=lv_offline;
		getLocalReseau(lv_offline);
	}
	
	public GetReseaux(Activity a, ListView lv_offline, RelativeLayout layout, Reseau r){
		idReseau = r.getIdBdd();
		this.layout=layout;
		this.lv_offline = lv_offline;
		this.a=a;
		//layout.setVisibility(View.VISIBLE);
		//lv_online.setVisibility(View.INVISIBLE);
		operation = (TextView) a.findViewById(R.id.operation);
		reseaux = new ArrayList<Reseau>();
		getLocalReseau(lv_offline);
	}
	
	@Override
	protected ArrayList<Reseau> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/reseau.php?";
		ArrayList<Reseau> ret = new ArrayList<Reseau>();
 
		 HttpURLConnection conn = null;
		    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        if(idReseau!=-1){
		        	sb.append("reseau=" + idReseau);
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
        			double latitude = jsonChildNode.optDouble("latitude");
        			double longitude = jsonChildNode.optDouble("longitude");
        			String twitter = jsonChildNode.optString("twitter");
        			String image = jsonChildNode.optString("image");
        			int version = jsonChildNode.optInt("version");
        			
        			Log.d("Position", "longitude : "+longitude+ "- latitude : "+latitude);
        			Reseau reseau = new Reseau(id, latitude, longitude, nom, twitter, image, version);

        			if(!(reseaux.toString().contains(reseau.toString()))){
        				ret.add(reseau);
        				this.publishProgress(reseau);
        			}
        			Log.d(reseau.toString(), "Nouveau réseau découvert");
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
		return ret;
    }

	private ArrayList<Reseau> getLocalReseau(ListView lv_offline){

		JuniorDAO dao = new JuniorDAO(a);
		dao.open();
		ArrayList<Reseau> ret = dao.findReseaux();
		Iterator<Reseau> it = ret.iterator();
		while(it.hasNext()){
			Reseau r = it.next();
			reseaux.add(r);
		}
		ReseauList adapter = new ReseauList(a, reseaux);
		lv_offline.setAdapter(adapter);
	    lv_offline.setOnItemClickListener(new ReseauOnClickListener(a));
	    lv_offline.setVisibility(View.VISIBLE);
	    dao.close();
	    
	    return ret;
	}
	
	@Override
	protected void onProgressUpdate(Reseau... progress) {
		reseaux.add(progress[0]);
		ReseauList adapter = new ReseauList(a, reseaux);
	    lv_offline.setAdapter(adapter);
	    lv_offline.invalidate();
	    layout.setVisibility(View.INVISIBLE);
	}
	
}


