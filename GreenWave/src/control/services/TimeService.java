package control.services;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.wavon.greenwave.R;

import view.activities.Home;

import datas.Arret;
import datas.utility.CSVReader;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

/**
 * © Copyright 2014 Antoine Sauray
 * Background service which checks for the last buses times
 * When the last bus of a favorite line is coming soon, It sends a notification to the user.
 * @author Antoine Sauray
 * @version 0.1
 */
public class TimeService extends Service {

		private NotificationManager mNotificationManager;
		public final int lastBusID=0;
		private CSVReader csvReader;
		private String path;
		private Context c;

	    // This is the object that receives interactions from clients.  See
	    // RemoteService for a more complete example.
		private final IBinder mBinder = new LocalBinder();
	  
	  
	// Unique Identification Number for the Notification.
	    // We use it on Notification start, and to cancel it.
	    private int NOTIFICATION = R.string.lastBus;

	    /**
	     * Class for clients to access.  Because we know this service always
	     * runs in the same process as its clients, we don't need to deal with
	     * IPC.
	     */
	    public class LocalBinder extends Binder {
	    	TimeService getService() {
	            return TimeService.this;
	        }
	    }

	    @Override
	    public void onCreate() {
	    	mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    	csvReader = null;
	    	c = this.getApplicationContext();
	    	path = c.getApplicationInfo().dataDir+"/";
	    }

	    @Override
	    public int onStartCommand(Intent intent, int flags, int startId) {
	        Log.i("LocalService", "Received start id " + startId + ": " + intent);
	        
	        this.getArrets();
	     // TODO Auto-generated method stub   
	        Intent i = new Intent();  

	        PendingIntent pi = PendingIntent.getService(getBaseContext(), 0, i, 0);          
	        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);  
	        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()  
	                + (2000 * 1000), pi);  
	        Toast.makeText(this, "Service rescheduled", Toast.LENGTH_SHORT).show();     
	        System.out.println("Service rescheduled");  
	        stopSelf(startId);
	        // We want this service to continue running until it is explicitly
	        // stopped, so return sticky.
	        return START_STICKY;
	    }

	    @Override  
	    public void onDestroy() {  
	        // TODO Auto-generated method stub  
	        super.onDestroy();  
	        mNotificationManager.cancel(NOTIFICATION);
	    }  

	    @Override
	    public IBinder onBind(Intent intent) {
	        return mBinder;
	    }
	      
	    private void getArrets(){
	    	/*
	    	Iterator<Arret> it = Globale.engine.getEntreprise().getArretsFavoris().values().iterator();
	    	while(it.hasNext()){
	    		Arret a = it.next();
	    		String[] tab = loadCSV(a);

	    		if(tab[0]!=null){
	    						
	    		}
	    		if(tab[1]!=null){
					
	    		}
	    	}*/
	    }
	    
	    private void showNotification(Context c, String ligne, String arret, String horaire){
			
			
			//HH converts hour in 24 hours format (0-23), day calculation
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date d2=null;	
			try {
				d2 = format.parse(horaire);
				long difference = System.currentTimeMillis() - d2.getTime();
				
				String hms = getDifference(difference);
				
				NotificationCompat.Builder mBuilder =
				        new NotificationCompat.Builder(c)
				        .setSmallIcon(R.drawable.greenwav_ico)
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
				mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);

				// mId allows you to update the notification later on.
				mNotificationManager.notify(lastBusID, mBuilder.build()); 
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	    public String[] loadCSV(Arret arret){	  
			/*
	    	String[] ret = new String[2];
	    	
			int[] indiceCol = new int[2];
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
								showNotification(c, ligne.toString(), arret.toString(), horairesRetour[horairesRetour.length-1]);
								ret[0]=horairesAller[horairesAller.length-1];
								i++;
							}
							else if(listeArrets[i].equals( arret.toString()+"_"+ligne.getDirection2())){
								trouve++;
								horairesRetour = getHoraires(csvReader, i);
								Log.d("Dernière horaire Retour:", horairesRetour[horairesRetour.length-1]);
								showNotification(c, ligne.toString(), arret.toString(), horairesRetour[horairesRetour.length-1]);
								ret[1]=horairesRetour[horairesRetour.length-1];
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
			return null;

		}

		public String[] getHoraires(CSVReader csvReader, int col) throws IOException{
			return csvReader.readColumn(col);
		}
		
		public String getDifference(long difference){
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
