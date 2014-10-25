package datas.utility;

import java.util.Iterator;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import control.Globale;

import datas.Arret;
import datas.Ligne;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class TravelHelper {
	
	private Marker destination;
	

public int[] getDistanceTrajet(LatLng position){
		
		Location destination = new Location("Destination");
		destination.setLatitude(position.latitude);
		destination.setLongitude(position.longitude);
		

		String id = Globale.engine.getLigneCourante().toString();
		Iterator<Arret> it =Globale.engine.getListeArretsDesservis().iterator();
		 float distanceMarche=10000000;
		 Arret nearest=null;
		 while(it.hasNext()){
			  Arret nouvelArret = it.next();
			  if(destination.distanceTo(nouvelArret.getLocation()) < distanceMarche && nouvelArret.getLignesDesservant().contains(id)){
				  distanceMarche = destination.distanceTo(nouvelArret.getLocation());
				  nearest=nouvelArret;
			  }
		  }
		 Iterator<Arret> itFav = Globale.engine.getArretsFavorisDesservis().iterator();
		 while(itFav.hasNext()){
			 Arret fav = itFav.next();
			 if(destination.distanceTo(fav.getLocation()) < distanceMarche && fav.getLignesDesservant().contains(id)){
				  distanceMarche = destination.distanceTo(fav.getLocation());
				  nearest=fav;
			  }
		 }
		 // nearest est l'arret desservis le plus proche de la destination sur la ligne
		 
		  Arret nearestMe=null;
		  float distanceMe=10000000;
		  Iterator<Arret> itMe = Globale.engine.getListeArretsDesservis().iterator();
		  
		  Location location = Globale.engine.getLocation();
		  while(itMe.hasNext()){
			  Arret nouvelArret = itMe.next();
			  if(location.distanceTo(nouvelArret.getLocation()) < distanceMe && nouvelArret.getLignesDesservant().contains(id)){
				  distanceMe = location.distanceTo(nouvelArret.getLocation());
				  nearestMe=nouvelArret;
			  }
		  }
		  Iterator<Arret> itFavMe = Globale.engine.getArretsFavorisDesservis().iterator();
		  while(itFavMe.hasNext()){
			  Arret nouvelArret = itFavMe.next();
			  if(location.distanceTo(nouvelArret.getLocation()) < distanceMe && nouvelArret.getLignesDesservant().contains(id)){
				  distanceMe = location.distanceTo(nouvelArret.getLocation());
				  nearestMe=nouvelArret;
			  }
		  }
		 
		  distanceMarche+=distanceMe;
		 float distanceBus = location.distanceTo(nearest.getLocation());
		 
		int[] ret = new int[2];
		ret[0]=(int)distanceBus; 
		ret[1]=(int)distanceMarche;
		return ret;		
	}
	
	public int[] getTempsTrajet(int[] distancesTrajet){
		int[] ret = new int[2];
		double coef = 1.4;
		
		int distanceBus = distancesTrajet[0];
		int distanceMarche = distancesTrajet[1];
		
		double t1 = (distanceBus*coef)/11.1;
		double t2 = (distanceMarche*coef)/1.38;
		
		ret[0]=(int)(t1);
		ret[1]=(int)(t2);
		
		return ret;
	}
	
	public Marker getDestination(){
		return destination;
	}
	public void setDestination(Marker destination){
		this.destination=destination;
	}
	
	public static String getBestProvider(LocationManager locationManager){
		
		String ret=null;
		
		boolean isGPSEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// getting network status
		boolean isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if(!isGPSEnabled && !isNetworkEnabled){
			Log.d("No Provider", "Geolocalisaton unavailable");
		}
		else{
			if (isGPSEnabled && Globale.engine.getLocationManager().getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
				ret=LocationManager.GPS_PROVIDER;
			}
			else if(isNetworkEnabled && Globale.engine.getLocationManager().getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null){
				ret=LocationManager.NETWORK_PROVIDER;				
			}
		}
		return ret;
	}
}
