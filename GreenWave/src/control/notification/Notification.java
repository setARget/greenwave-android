package control.notification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import com.wavon.greenwave.R;

import view.activities.Home;

import control.Globale;
import datas.Arret;
import datas.Ligne;
import datas.utility.CSVReader;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * © Copyright 2014 Antoine Sauray
 * Provides notifications for the Kiceo application
 * Shows notifications in the notification bar when the last bus of a favorite user line is coming soon
 * @author Antoine Sauray
 * @version 0.1
 */
public class Notification {
	
	public static int lastBusID=0;


	public static void lastBus(Context c, String ligne, String arret, String horaire){
	
		
		//HH converts hour in 24 hours format (0-23), day calculation
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		
		Date d2=null;


		
		
		try {
			d2 = format.parse(horaire);
			long difference = System.currentTimeMillis() - d2.getTime();
			
			String hms = getDifference(difference);
			
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(c)
			        .setSmallIcon(R.drawable.ic_launcher)
			        .setContentTitle("Dernier bus dans "+hms)
			        .setContentText(ligne+" : "+ arret+ " ("+horaire+")");
			// Creates an explicit intent for an Activity in your app
			Intent resultIntent = new Intent(c, Home.class);

			// The stack builder object will contain an artificial back stack for the
			// started Activity.
			// This ensures that navigating backward from the Activity leads out of
			// your application to the Home screen.
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(c);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(Home.class);
			// Adds the Intent that starts the Activity to the top of the stack
			stackBuilder.addNextIntent(resultIntent);
			PendingIntent resultPendingIntent =
			        stackBuilder.getPendingIntent(
			            0,
			            PendingIntent.FLAG_UPDATE_CURRENT
			        );
			mBuilder.setContentIntent(resultPendingIntent);
			NotificationManager mNotificationManager =
			    (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
			// mId allows you to update the notification later on.
			mNotificationManager.notify(lastBusID, mBuilder.build()); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void loadCSV(Context c, Arret arret){
		/*
		String ret;
		
		CSVReader csvReader = null;
		int[] indiceCol = new int[2];
		String path = c.getApplicationInfo().dataDir+"/";
		Log.d("path", path);
		try {

			int trouve=0;
			int i=0;
			
			String[] horairesAller = null, horairesRetour = null;

			arret.getLignesDesservant();
			Iterator<String> it = arret.getLignesDesservant().iterator();
			while(it.hasNext()){
				Ligne ligne = Globale.engine.getReseau().getLignes().get(it.next());	//attention ! changer lignes en lignesfavorites
				// on récupère la ligne. Si elle n'est pas un favoris alors elle n'est pas dans l'arraylist et elle est donc null
				if(ligne != null){
					csvReader = new CSVReader(new BufferedReader(new FileReader(path+ligne.getNumero()+".csv")));
					Log.d("CSV OK","CSV OUVERT");
					String [] listeArrets = csvReader.readNext();
					Log.d("Arret à trouver", arret.toString()+"_"+ligne.getDirection1());
					Log.d("Arret à trouver", arret.toString()+"_"+ligne.getDirection2());
					
					while(trouve != 2 && i<listeArrets.length){
						Log.d("Arret["+i+"]", listeArrets[i]+"");
						if(listeArrets[i].equals( arret.toString()+"_"+ligne.getDirection1())){
							trouve++;					
							horairesAller = getHoraires(csvReader, i);				
							Log.d("Dernière horaire aller:", horairesAller[horairesAller.length-1]);
							i++;
						}
						else if(listeArrets[i].equals( arret.toString()+"_"+ligne.getDirection2())){
							trouve++;
							horairesRetour = getHoraires(csvReader, i);
							Log.d("Dernière horaire Retour:", horairesRetour[horairesRetour.length-1]);
							lastBus(c, ligne.toString(), arret.toString(), horairesRetour[horairesRetour.length-1]);
							i++;
						}
						else{
							i++;
						}
					}
				}
				Log.d("Nombre d'arrets trouvés : ", trouve+"");
				Log.d("Aller : ", indiceCol[0]+"");
				Log.d("Retour : ", indiceCol[1]+"");

			}

	
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d("CSV ERROR",e.getMessage());
		}
		*/
	}

	public static String[] getHoraires(CSVReader csvReader, int col) throws IOException{
		return csvReader.readColumn(col);
	}
	
	public static String getDifference(long difference){
		String ret = "";

        System.out.println("difference : " + difference);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = difference / daysInMilli;
        difference = difference % daysInMilli;

        long elapsedHours = difference / hoursInMilli;
        difference = difference % hoursInMilli;

        long elapsedMinutes = difference / minutesInMilli;
        difference = difference % minutesInMilli;

        long elapsedSeconds = difference / secondsInMilli;

        if(elapsedHours!=0){
        	ret+=elapsedHours+":";
        }
        ret+=elapsedMinutes;
        return ret;


    }
}