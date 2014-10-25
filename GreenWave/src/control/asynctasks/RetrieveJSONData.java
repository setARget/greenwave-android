package control.asynctasks;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class RetrieveJSONData extends AsyncTask<String, String, JSONObject>{

	@Override
	protected JSONObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public static final String strURL = "http://virtual-projects.eu/x.php";
	 
    private void getServerData(String returnString) {
        InputStream is = null;
        String result = "";
        // Envoyer la requête au script PHP.
        // Script PHP : $sql=mysql_query("select * from tblVille where Nom_ville like '".$_REQUEST['ville']."%'");
        // $_REQUEST['ville'] sera remplacé par L dans notre exemple.
        // Ce qui veut dire que la requête enverra les villes commençant par la lettre L
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        	        nameValuePairs.add(new BasicNameValuePair("ville","L"));
        // Envoie de la commande http
        try{
        HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(strURL);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
 
        }catch(Exception e){
        Log.e("log_tag", "Error in http connection " + e.toString());
        }
 
        // Convertion de la requête en string
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
        }
        is.close();
        result=sb.toString();
        }catch(Exception e){
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        // Parse les données JSON
        parseJSONData(result);
    }

    private void parseJSONData(String result){
    	String returnString="";
    	try{
            JSONArray jArray = new JSONArray(result);
        for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);
            // Affichage ID_ville et Nom_ville dans le LogCat
                Log.i("log_tag","ID_ville: "+json_data.getInt("ID_ville")+
                        ", Nom_ville: "+json_data.getString("Nom_ville")
                );
                // Résultats de la requête
                returnString += "\n\t" + jArray.getJSONObject(i);
            }
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
    }
    }
}


