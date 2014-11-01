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

public class GetReseaux extends AsyncTask<Void, String, ArrayList<Reseau>> {
	
	private int idReseau;
	private RelativeLayout layout;
	private TextView operation;
	private ListView lv_offline, lv_online;
	private Activity a;
	ArrayList<Reseau> offline = new ArrayList<Reseau>();

	public GetReseaux(Activity a, ListView lv_offline, ListView lv_online, RelativeLayout layout){
		idReseau=-1;
		this.layout=layout;
		this.lv_online = lv_online;
		this.a=a;
		layout.setVisibility(View.VISIBLE);
		lv_online.setVisibility(View.GONE);
		operation = (TextView) a.findViewById(R.id.operation);
		offline = new ArrayList<Reseau>();
		getLocalReseau(lv_offline);
	}
	
	public GetReseaux(Activity a, ListView lv_offline, ListView lv_online, RelativeLayout layout, Reseau r){
		idReseau = r.getIdBdd();
		this.layout=layout;
		this.lv_offline = lv_offline;
		this.lv_online = lv_online;
		this.a=a;
		layout.setVisibility(View.VISIBLE);
		lv_online.setVisibility(View.GONE);
		operation = (TextView) a.findViewById(R.id.operation);
		offline = new ArrayList<Reseau>();
		getLocalReseau(lv_offline);
	}
	
	@Override
	protected ArrayList<Reseau> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		ArrayList<Reseau> ret = new ArrayList<Reseau>();
		getOnlineReseau(ret);
		return ret;
   
    }

	private void getLocalReseau(ListView lv_offline){

		JuniorDAO dao = new JuniorDAO(a);
		dao.open();
		Iterator<Reseau> it = dao.findReseaux().iterator();
		while(it.hasNext()){
			Reseau r = it.next();
			offline.add(r);
		}
		ArrayAdapter<Reseau> adapter = new ArrayAdapter<Reseau>(a, R.layout.list_reseau, R.id.nom, offline);
		lv_offline.setAdapter(adapter);
	    lv_offline.setOnItemClickListener(new ReseauOnClickListener(a));
	    lv_offline.setVisibility(View.VISIBLE);
	    dao.close();
	}
	
	private void getOnlineReseau(ArrayList<Reseau> ret){
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/reseau.php?";

 
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
        			String twitter = jsonChildNode.optString("twitter");
        			String image = jsonChildNode.optString("image");
        			int version = jsonChildNode.optInt("version");
        			Reseau reseau = new Reseau(id, nom, twitter, image, version);

        			if(!(offline.toString().contains(reseau.toString()))){
        				ret.add(reseau);
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
	}
	
	@Override
	protected void onPostExecute(ArrayList<Reseau> result) {
	    super.onPostExecute(result);
	    ArrayAdapter<Reseau> adapter = new ArrayAdapter<Reseau>(a, R.layout.list_reseau, R.id.nom, result);
	    lv_online.setAdapter(adapter);
	    lv_online.setOnItemClickListener(new ReseauOnClickListener(a));
	    layout.setVisibility(View.GONE);
	    lv_online.setVisibility(View.VISIBLE);
	}
	    
		
}


