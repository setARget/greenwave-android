package control.listeners.item;

import java.util.ArrayList;
import java.util.Iterator;

import view.activities.Home;
import control.Globale;
import datas.Arret;
import datas.Ligne;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom OnItemClickListener for the line list selection
 * @author Antoine Sauray
 * @version 0.1
 */
public class LineClickListener implements Globale, OnItemClickListener, Runnable{

	private Home home;
	private Ligne l;
	
	public LineClickListener(Home home){
		this.home=home;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
			l = (Ligne) parent.getItemAtPosition(position);
				
			Globale.engine.setLigneCourante(l);
			//Globale.engine.setArretCourant(Globale.engine.getLigneCourante().getArrets().get("Saint-Mathurin"));
			Globale.engine.setUpdateLigne(true);
			this.run();
			home.getViewPager().setCurrentItem(2);	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		/*
		ArrayList<Arret> listDesservis = Globale.engine.getListeArretsDesservis();
		// list va contenir les arrets à afficher dans le fragment suivant et sur la carte
		listDesservis.clear();
	    
		// On itère sur les arrets de la carte
	    Iterator<Arret> itr = Globale.engine.getEntreprise().getArrets().values().iterator();


	    while(itr.hasNext()){	// Tant qu'il reste des arrets
		    	Arret a = itr.next();	
		    	if(a.getLignesDesservant().contains(l.toString())){	// Si l'arret est desservis
	    	    	listDesservis.add(a);  		// On l'ajoute
		    	}
	    }
	    */
		//Iterator<Arret> itFav = Globale.engine.getEntreprise().getArretsFavoris().values().iterator();
	}

}
