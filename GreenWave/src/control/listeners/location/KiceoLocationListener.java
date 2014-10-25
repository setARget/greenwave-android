package control.listeners.location;

import view.activities.Home;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.widget.Toast;
import control.Globale;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom LocationListener for the Kiceo application
 * Every time the location changes, it is updated with this class
 * @author Antoine Sauray
 */
public class KiceoLocationListener implements LocationListener{

	private Home home;
	
	public KiceoLocationListener(Home home){
		this.home=home;
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Globale.engine.setLocation(location);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		switch( status ) {
	    case LocationProvider.AVAILABLE:
	    	Globale.engine.getLocationManager().requestLocationUpdates(provider,0,0, this);
			Globale.engine.setProvider(provider); 
	        break;
	    case LocationProvider.OUT_OF_SERVICE:
	        break;
	    case LocationProvider.TEMPORARILY_UNAVAILABLE:
	        break;
	    }
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(home,
				"Géolocalisation activée",
				Toast.LENGTH_SHORT).show();
		Globale.engine.getLocationManager().requestLocationUpdates(provider,0,0, this);
		Globale.engine.setProvider(provider); 
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		Toast.makeText(home,
				"Géolocalisation désactivée",
				Toast.LENGTH_SHORT).show();
	}
}
