package view.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.wavon.greenwave.R;

import datas.db.external.didier.GetReseaux;
import datas.utility.NetworkUtil;

/**
 * � Copyright 2014 Antoine Sauray
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
        this.setTitle("Selection du r�seau");
        initInterface();
        attachReactions();
        Log.d("New Activity", "Selecting Company");
		new GetReseaux(this, listOffline, layout).execute();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	//getActionBar().setDisplayHomeAsUpEnabled(true);
 	   return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Initializes the graphical interface of the activity
     */
	private void initInterface() {
		//listOffline = (ListView) this.findViewById(R.id.list_reseau_offline);
		listOffline = (ListView) this.findViewById(R.id.list_reseau_offline);
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
