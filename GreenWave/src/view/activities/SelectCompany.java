package view.activities;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.wavon.greenwave.R;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class SelectCompany extends FragmentActivity{

	private Location destination;
	private ListView list;
	private RelativeLayout layout;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        this.setTitle("Selection du réseau");
        initInterface();
        attachReactions();
        Log.d("New Activity", "Selecting Company");
    }
    
    /**
     * Initializes the graphical interface of the activity
     */
	private void initInterface() {
		list = (ListView) this.findViewById(R.id.list);
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
	
	public ListView getList(){
		return list;
	}
	
}
