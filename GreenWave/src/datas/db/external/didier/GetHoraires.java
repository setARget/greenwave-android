package datas.db.external.didier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.wavon.greenwave.R;

import control.listeners.time.OnHoraireClickedListener;
import datas.Horaire;
import datas.db.internal.JuniorDAO;
import datas.utility.NetworkUtil;

public class GetHoraires extends AsyncTask<Void, String, ArrayList<Horaire>> {
	
	private Context c;
	private int idArret, idLigne, direction;
	private String calendrier;
	private ArrayList<TableRow> tableRowArray;
	private TableLayout table;
	private RelativeLayout loading;
	
	public GetHoraires(Context c, int idLigne, int idArret, int direction, String calendrier, TableLayout table, RelativeLayout loading){
		this.idLigne=idLigne;
		this.c=c;
		this.idArret=idArret;
		this.direction=direction;
		this.calendrier=calendrier;
		this.table=table;
		this.loading=loading;
		table.removeAllViews();
		tableRowArray = new ArrayList<TableRow>();
		loading.setVisibility(View.VISIBLE);
	}


	@Override
	protected ArrayList<Horaire> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		ArrayList<Horaire> horaires = new ArrayList<Horaire>();
		if(NetworkUtil.isConnected(c)){
			Integer ret = null;

			StringBuilder jsonResult = new StringBuilder();
			final String BASE_URL = "http://sauray.me/green_wave/horaire.php?";

	 
			 HttpURLConnection conn = null;
			    try {
			        StringBuilder sb = new StringBuilder(BASE_URL);
			        	sb.append("ligne=" + idLigne);
			        	sb.append("&arret=" + idArret);
			        	sb.append("&direction=" + direction);
			        	sb.append("&calendrier=" + calendrier);
			   
			        URL url = new URL(sb.toString());
			        conn = (HttpURLConnection) url.openConnection();
			        InputStreamReader in = new InputStreamReader(conn.getInputStream());

			        BufferedReader jsonReader = new BufferedReader(in);
					String lineIn;
					while ((lineIn = jsonReader.readLine()) != null) {
					    jsonResult.append(lineIn);
					}
				
					JSONObject jsonObj = new JSONObject(jsonResult.toString());
			        
	        		JSONArray jsonMainNode = jsonObj.optJSONArray("horaire");
	  		
	        		for (int i = 0; i < jsonMainNode.length(); i++) {
	            		JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
	            		String horaire = jsonChildNode.optString("horaire");
	            		int idAuteur = jsonChildNode.optInt("idAuteur");
	            		int id = jsonChildNode.optInt("id");
	            		horaires.add(new Horaire(id, horaire, idAuteur, direction));
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
			    Collections.sort(horaires);
		}
		else{
			JuniorDAO dao = new JuniorDAO(c);
			dao.open();
			horaires = dao.findHoraire(idArret, idLigne, direction, calendrier);
		}
	

		return horaires;
	
	}

	private void process(ArrayList<Horaire>  horaires){
		publishProgress("Chargement des horaires");
		if(horaires.size()!=0){
			Horaire horaire = horaires.get(0);
			int h= horaire.hour;
			int indexParcours=0;
	 		for (int i = 0; i < 16; i++) {
		        TableRow tableRow = new TableRow(c);
		        
		        tableRow.setOnClickListener( new OnClickListener() {
		            @Override
		            public void onClick( View v ) {
		                //Do Stuff
		            }
		        } );
		        int j=0;
		        boolean notEnd=true;
		        while(notEnd==true && indexParcours < horaires.size() ){
		        	TextView textView = new TextView(c);
		        		
		        		Horaire tmp = horaires.get(indexParcours);
		        		String[] tab = tmp.toString().split(":");
			        	Log.d(indexParcours+"", tab[0]+":"+tab[1]);
		        		int currentHour = Integer.parseInt(tab[0]);
		        		if(currentHour==h){
		        			textView.setText(tmp.toString());
			        		indexParcours++;
		        		}
		        		else{
			        		notEnd=false;
			        		h++;
		        		}
		
		            textView.setPadding(20, 20, 20, 20);
	        		textView.setTextColor(c.getResources().getColor(R.color.text));	
	        		textView.setPaintFlags(textView.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
	        		textView.setTextIsSelectable(true);
		            tableRow.addView(textView);
		            textView.setOnClickListener(new OnHoraireClickedListener(c, tmp));
		            j++;
		        }
		        if(tableRowArray.size()%2==0){
		        	tableRow.setBackgroundColor(c.getResources().getColor(R.color.bleubg));
		        }
		        tableRowArray.add(tableRow);
	 			}
		}
		else{
			Log.d("", "Oups ! Pas d'horaire sur cet arret. Pourquoi ne pas en ajouter ?");
		}
		
	}
	
	@Override
	protected void onProgressUpdate(String... progress) {
		//operation.setText(progress[0]);
	}
	@Override
	protected void onPostExecute(ArrayList<Horaire> result) {
	    super.onPostExecute(result);
	    JuniorDAO dao = new JuniorDAO(c);
	    dao.open();
	    dao.insertHoraires(result);
	    process(result);
		Iterator<TableRow> it = tableRowArray.iterator();
		while(it.hasNext()){
			table.addView((View) it.next());
		}
		table.refreshDrawableState();
		
	    Log.e("UpdateTextProgress", "Finished");
	    loading.setVisibility(View.GONE);
	}
}
    