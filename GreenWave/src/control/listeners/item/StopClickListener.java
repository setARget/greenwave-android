package control.listeners.item;

import view.activities.Home;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import control.Globale;
import datas.Arret;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom OnItemClickListener for the stop list selection
 * @author Antoine Sauray
 * @version 0.1
 */
public class StopClickListener implements Globale, OnItemClickListener {

	private Home home;
	
	public StopClickListener(Home home){
		this.home=home;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
			Arret a = (Arret) parent.getItemAtPosition(position);
			Globale.engine.setUpdateArret(true);
			Globale.engine.setArretCourant(a);
			home.getViewPager().setCurrentItem(3);
		
	}

}
