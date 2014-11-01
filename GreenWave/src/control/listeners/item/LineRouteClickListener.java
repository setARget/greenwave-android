package control.listeners.item;

import view.activities.Home;
import view.activities.Route;
import control.Globale;
import datas.Ligne;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom OnItemClickListener for the line list selection
 * @author Antoine Sauray
 * @version 0.1
 */
public class LineRouteClickListener implements Globale, OnItemClickListener, Runnable{

	private String s;
	private Route route;
	private Context c;
	private Ligne l;
	
	public LineRouteClickListener(Route route, Context c){
		this.c=c;
		this.route=route;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
			s = (String) parent.getItemAtPosition(position);
			l = Globale.engine.getReseau().getLignes().get(s);
			if(l==null){
				//l = Globale.engine.getEntreprise().getLignesFavorites().get(s);
			}
			//map.getMap().clear();
			
			Globale.engine.setLigneCourante(l);
			Globale.engine.setUpdateLigne(true);
			Globale.engine.setDefaultFragment(3);
			this.run();
			Intent intent = new Intent(route, Home.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			route.startActivity(intent);
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
		/*
		Iterator<Arret> itFav = Globale.engine.getEntreprise().getArretsFavoris().values().iterator();	
		Iterator<Arret> it = Globale.engine.getEntreprise().getArrets().values().iterator();
		
		ArrayList<Arret> arretsDesservis = Globale.engine.getListeArretsDesservis();
		ArrayList<Arret> favorisDesservis = Globale.engine.getArretsFavorisDesservis();
		
		arretsDesservis.clear();
		favorisDesservis.clear();
		
		// On remplit les arrets favoris
		while(itFav.hasNext()){
			Arret a = itFav.next();
			if(a.getLignesDesservant().contains(l.toString())){
				favorisDesservis.add(a);
				Log.d("Favoris desservis", a.toString());
			}
		}
		
		// On remplit les autres
		while(it.hasNext()){
			Arret a = it.next();
			if(a.getLignesDesservant().contains(l.toString())){
				arretsDesservis.add(a);
				Log.d("Arret desservis", a.toString());
			}
		}
		 */
	}


}
