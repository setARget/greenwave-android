package control.listeners.item;

import view.activities.Home;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class DrawerItemClickListener implements OnItemClickListener {

	private Home home;
	
	public DrawerItemClickListener(Home home){
		this.home=home;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		selectItem(position);

	}
	
	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		Log.d("Position", position+"");
		Intent intent=null;
		switch(position){
		
			case 0:
				intent = new Intent(home, view.activities.FBLogin.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				home.startActivity(intent);
			break;
			case 1:
				// Selectionner mon réseau
				intent = new Intent(home, view.activities.SelectionReseau.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				home.startActivity(intent);
				
			break;
			case 2:
				// A Propos
				
			break;
			case 3:
				// Réglages
				intent = new Intent(home, view.activities.Preferences.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				home.startActivity(intent);
				
			break;
			case 4:
				// Aide
				intent = new Intent(home, view.activities.Help.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				home.startActivity(intent);
				
				
			break;
			case 5:
				// A Propos		
			break;
			
		}
	}


}
