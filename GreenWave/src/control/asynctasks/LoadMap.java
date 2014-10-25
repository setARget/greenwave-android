package control.asynctasks;

import view.activities.Home;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.wavon.greenwave.R;

import control.Globale;
import control.KiceoControl;

public class LoadMap extends AsyncTask<GoogleMap,GoogleMap,GoogleMap>{

	private Home home;
	
	public LoadMap(Home home){
		this.home=home;
	}
	
	@Override
	protected GoogleMap doInBackground(GoogleMap... params) {
		// TODO Auto-generated method stub
		Looper.prepare();
		params[0] = (((com.google.android.gms.maps.SupportMapFragment)home.getSupportFragmentManager().findFragmentById(R.id.map)).getMap());	
		return params[0];
	}
	
	@Override
	protected void onPostExecute(GoogleMap result) {
	    super.onPostExecute(result);
	    attachReactions(result);
	    result.setMyLocationEnabled(true);
	    result.setTrafficEnabled(true);
	    result.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(47.748122, -3.364546), 14.0f) );
	}
   
	 private void attachReactions(GoogleMap gMap){
		 KiceoControl kc = new KiceoControl(home);
		 gMap.setOnMarkerClickListener(kc);
		 gMap.setOnInfoWindowClickListener(kc);
		 gMap.setOnMapLongClickListener(kc); 
	 }	
	
	

}
