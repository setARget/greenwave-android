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

import com.wavon.greenwave.R;

import db.external.didier.GetReseaux;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class SelectionReseau extends FragmentActivity{

	private ListView listOffline, listOnline;
	private RelativeLayout layout;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reseau);
        this.setTitle("Selection du réseau");
        initInterface();
        attachReactions();
        Log.d("New Activity", "Selecting Company");
		new GetReseaux(this, listOnline, layout).execute();
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
     * Initializes the graphical interface of the activity
     */
	private void initInterface() {
		//listOffline = (ListView) this.findViewById(R.id.list_reseau_offline);
		listOnline = (ListView) this.findViewById(R.id.list_reseau_online);
		layout = (RelativeLayout) this.findViewById(R.id.loadingPanel);
	}
	
	/**
	 * Sets the reactions of the control elements
	 */
	private void attachReactions() {
		
	}
	
	public RelativeLayout getLayout(){
		return layout;
	}
	
	public ListView getListOnline(){
		return listOnline;
	}
	public ListView getListOffline(){
		return listOffline;
	}
	
}
