package control.listeners.button;

import view.activities.Home;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import control.Globale;
import control.asynctasks.NearbyStops;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom OnClickListener for the nearest stop button
 * @see OnClickListener
 * @author Antoine Sauray
 * @version 0.1
 */
public class NearestStopOnClickListener implements OnClickListener {
	
	private Home home;
	
	public NearestStopOnClickListener(Home home){
		this.home=home;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(Globale.engine.getLigneCourante() != null && Globale.engine.getLocation() != null){
		new NearbyStops(home).execute(); 
		}
		else if(Globale.engine.getLigneCourante() == null){
			Toast.makeText((Home) v.getContext(),
					"Veuillez selectionner une ligne",
					Toast.LENGTH_SHORT).show();
		}
		else if(Globale.engine.getLocation() == null){
			Toast.makeText((Home) v.getContext(),
					"Ce service nécessite la géolocalisation de votre téléphone",
					Toast.LENGTH_SHORT).show();
		}
	}

}
