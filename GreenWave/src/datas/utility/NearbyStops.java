package datas.utility;

import java.util.Iterator;

import view.activities.Home;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import control.Globale;

import datas.Arret;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class NearbyStops extends AsyncTask<Void,String,Void> {
	   

	private ProgressDialog pd;
	private Home home;
	public NearbyStops(Home home){
		this.home=home;
		pd = new ProgressDialog(home);
		pd.setTitle("Chargement");
		pd.setMessage("Calcul de l'arret le plus proche en cours");
		pd.show();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		
		String id = Globale.engine.getLigneCourante().toString();
		Iterator<Arret> it = Globale.engine.getLigneCourante().getArrets().values().iterator();
		//Iterator<Arret> itFav = Globale.engine.getEntreprise().getArretsFavoris().values().iterator();
		  Location currentLocation = Globale.engine.getLocation();
		  float distance=10000000;
		  Arret nearest=null;
		  while(it.hasNext()){
			  Arret nouvelArret = it.next();
			  if(currentLocation.distanceTo(nouvelArret.getLocation()) < distance && nouvelArret.getLignesDesservant().contains(id)){
				  distance = currentLocation.distanceTo(nouvelArret.getLocation());
				  nearest=nouvelArret;
			  }
		  }
		  /*
		  while(itFav.hasNext()){
			  Arret nouvelArret = itFav.next();
			  if(currentLocation.distanceTo(nouvelArret.getLocation()) < distance && nouvelArret.getLignesDesservant().contains(id)){
				  distance = currentLocation.distanceTo(nouvelArret.getLocation());
				  nearest=nouvelArret;
			  }
		  }
		  */
		  Log.d("Arret le plus proche", nearest.toString());
		 Globale.engine.setUpdateArret(true);
		 Globale.engine.setArretCourant(nearest);
	    return null;
	}
	
	@Override
	protected void onProgressUpdate(String... progress) {
			 
	}
	
	@Override
	protected void onPostExecute(Void result) {
	    super.onPostExecute(result);
	    Log.e("UpdateTextProgress", "Finished");
	    pd.dismiss();
		home.getViewPager().setCurrentItem(3);
	}
}
