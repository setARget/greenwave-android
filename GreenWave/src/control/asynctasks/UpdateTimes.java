package control.asynctasks;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.util.ByteArrayBuffer;

import com.wavon.greenwave.R;

import control.Globale;
import control.KiceoControl;

import datas.utility.CSVReader;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class UpdateTimes extends AsyncTask<String,String,Void> {
   
	private TableLayout table;
	private RadioGroup group;
	private RelativeLayout loading;
	
	private TextView operation;
	
	private Context c;
	private Dialog d;
	
	private ArrayList<TableRow> tableRowArray;

	private String [] horaires;
	
	public UpdateTimes(TableLayout table, RadioGroup group, RelativeLayout r, Dialog d){
		this.table=table;
		this.group=group;
		this.c=d.getContext();
		this.d=d;
		this.loading=r;
		tableRowArray = new ArrayList<TableRow>();
		loading.setVisibility(View.VISIBLE);
		
		operation = (TextView) d.findViewById(R.id.operation);
	}

	@Override
	protected Void doInBackground(String... params) {
		
		loadCSV(params[0]);
	    return null;
	}
	
	@Override
	protected void onProgressUpdate(String... progress) {
		operation.setText(progress[0]);
	}
	
	@Override
	protected void onPostExecute(Void result) {
	    super.onPostExecute(result);
	    
		Iterator<TableRow> it = tableRowArray.iterator();
		while(it.hasNext()){
			table.addView((View) it.next());
		}
		table.refreshDrawableState();
		
	    Log.e("UpdateTextProgress", "Finished");
	    loading.setVisibility(View.GONE);
	}

	private void downloadCSV(String sens){
		if(KiceoControl.getConnectivityStatus(c) != 0){
			publishProgress("Téléchargement des horaires depuis le serveur");
            try {
            		String filename=Globale.engine.getLigneCourante().getNumero()+".csv";
            		String path = c.getApplicationInfo().dataDir+"/";
                    URL url = new URL("http://wavon.craym.eu/data/ctrl/" + filename); //you can write here any link
                    File file = new File(path+filename);

                    long startTime = System.currentTimeMillis();
                    Log.d("ImageManager", "download begining");
                    Log.d("ImageManager", "download url:" + url);
                    Log.d("ImageManager", "downloaded file name:" + filename);
                    /* Open a connection to that URL. */
                    URLConnection ucon = url.openConnection();

                    /*
                     * Define InputStreams to read from the URLConnection.
                     */
                    InputStream is = ucon.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);

                    /*
                     * Read bytes to the Buffer until there is nothing more to read(-1).
                     */
                    ByteArrayBuffer baf = new ByteArrayBuffer(50);
                    int current = 0;
                    while ((current = bis.read()) != -1) {
                            baf.append((byte) current);
                    }

                    /* Convert the Bytes read to a String. */
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(baf.toByteArray());
                    fos.close();
                    Log.d("ImageManager", "download ready in"
                                    + ((System.currentTimeMillis() - startTime) / 1000)
                                    + " sec");
        			loadCSV(sens);

            } catch (IOException e) {
                    Log.d("File Download", "Error: " + e);
            }
		}

    
	}

	private void process(){
		publishProgress("Chargement des horaires");
		int h = 6;
		int indexParcours=0;
		 
 		for (int i = 0; i < 16; i++) {
	        TableRow tableRow = new TableRow(c);
	        boolean notEnd=true;
	        int j=0;
	        while(notEnd==true && indexParcours < horaires.length && horaires[indexParcours].length() != 0){
	        	TextView textView = new TextView(c);
	        	/*
	        	if(j==0){
	        		textView.setText(h+"h");
	        	}
	        	else{
	        		*/
	        		
	        		String tmp = horaires[indexParcours];
	        		String[] tab = tmp.split(":");
		        	Log.d(indexParcours+"", tab[0]+":"+tab[1]);
	        		int currentHour = Integer.parseInt(tab[0]);
	        		if(currentHour==h){
	        			textView.setText(tmp);
		        		indexParcours++;
	        		}
	        		else{
		        		notEnd=false;
		        		h++;
	        		}

	        	//}     	
	            textView.setPadding(20, 20, 20, 20);
        		textView.setTextColor(c.getResources().getColor(R.color.textSecondaire));	
	            tableRow.addView(textView);
	            j++;
	        }
	        if(tableRowArray.size()%2==0){
	        	tableRow.setBackgroundColor(c.getResources().getColor(R.color.bleubg));
	        }
	        tableRowArray.add(tableRow);
 			}
	}

	private void loadCSV(String sens){
		CSVReader csvReader = null;
		String path = c.getApplicationInfo().dataDir+"/";
		new File(path+Globale.engine.getLigneCourante().getNumero()).delete();
		Log.d("path", path);
		try {
			csvReader = new CSVReader(new BufferedReader(new FileReader(path+Globale.engine.getLigneCourante().getNumero()+".csv")));
			Log.d("CSV OK","CSV OUVERT");
			boolean trouve=false;
			int i=0;
			String [] listeArrets = csvReader.readNext();

			Log.d("Arret à trouver", Globale.engine.getArretCourant().toString()+"_"+sens);
			while(trouve==false && i<listeArrets.length){
				Log.d("Arret["+i+"]", listeArrets[i]+"");
				if(listeArrets[i].equals( Globale.engine.getArretCourant().toString()+"_"+sens)){
					trouve=true;
				}
				else{
					i++;
				}
			}
			if(trouve == true){
				Log.d("Arret trouvé", "Colonne"+i);
				horaires = csvReader.readColumn(i);
				Log.d("Horaires : ", horaires.length+" horaires trouvées");
				process();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d("CSV ERROR","Fichier non Trouvé en Local");
			downloadCSV(sens);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("CSV ERROR",e.getMessage());
		}	
	}
}
