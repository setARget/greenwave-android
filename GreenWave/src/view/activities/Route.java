package view.activities;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.wavon.greenwave.R;

import control.Globale;
import datas.utility.CalculerItineraire;


/**
 * The main activity of the program. Home hosts the fragments of the applications.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.2.1
 */
public class Route extends FragmentActivity implements Globale{
	
	private Location destination;
	private ListView list;
	private RelativeLayout layout;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initInterface();
        attachReactions();
		prepareRoute();
        Log.d("Kiceo - Vannes Agglo", "L'application a démarrée");
    }
    
    /**
     * Initializes the graphical interface of the activity
     */
	private void initInterface() {
		list = (ListView) this.findViewById(R.id.list);
		layout = (RelativeLayout) this.findViewById(R.id.loadingPanel);
		LatLng ll = Globale.engine.getDestination().getPosition();
		destination = new Location("destination");
		destination.setLatitude(ll.latitude);
		destination.setLongitude(ll.longitude);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getActionBar().setDisplayHomeAsUpEnabled(true);
 	   return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){	// actions on "previous" button
        	startActivity(new Intent(this, Home.class));
        }
        return super.onOptionsItemSelected(item);
    }
	
	/**
	 * Sets the reactions of the control elements
	 */
	private void attachReactions() {
		
	}
	
	private void prepareRoute(){
		 new CalculerItineraire(this, this, destination).execute(1000f, 1000f);
	}
	
	public RelativeLayout getLayout(){
		return layout;
	}
	
	public ListView getList(){
		return list;
	}
	
}
