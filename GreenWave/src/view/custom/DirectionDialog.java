package view.custom;

import view.activities.Home;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.wavon.greenwave.R;

/**
 * Shows the user the direction to take to reach the destination
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */

public class DirectionDialog extends Dialog{

	private Location destination;
	private Home home;
	
	public DirectionDialog(Context context, Home home, String destinationName, LatLng latlng) {
		super(context);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.activity_route);
		setTitle("Itinéraire vers "+destinationName);
		
		this.home=home;
		
		destination = new Location(destinationName);
		destination.setLatitude(latlng.latitude);
		destination.setLongitude(latlng.longitude);
		
		initInterface();
		attachReactions();	
		prepareRoute();
       
       //new CalculerItineraire().execute();
	}

	private void attachReactions() {
		// TODO Auto-generated method stub
		
	}

	private void initInterface() {
		// TODO Auto-generated method stub
		
	}
	
	private void prepareRoute(){
		 //new CalculerItineraire(this.getContext(), home, destination, this).execute(1000f, 1000f);
	}
	
	public RelativeLayout getLayout(){
		return (RelativeLayout) this.findViewById(R.id.loadingPanel);
	}
	
	public ListView getList(){
		return (ListView) this.findViewById(R.id.list);
	}

}
