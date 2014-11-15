package datas.utility;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import view.activities.Route;
import view.custom.LineList;
import view.custom.RouteList;

import control.Globale;
import control.listeners.item.LineRouteClickListener;
import datas.Arret;
import datas.Ligne;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class CalculerItineraire extends AsyncTask <Float,String, ArrayList<Ligne> > {

	private Location currentLocation, destination;
	private Context c;
	private RelativeLayout layout;
	private ListView list;
	
	public CalculerItineraire(Context c, RelativeLayout layout, ListView list, Location destination){
		currentLocation = Globale.engine.getLocation();
		this.destination = destination;
		this.c=c;
		this.layout=layout;
		this.list=list;
		layout.setVisibility(View.VISIBLE);
	}
	
	@Override
	protected ArrayList<Ligne> doInBackground(Float... params) {
		ArrayList<Ligne> ret = new ArrayList<Ligne>();
		if(currentLocation != null){
			int nb=0;
			float distance=100;
			ArrayList<Arret> arretsProcheUtilisateur = null;
			ArrayList<Arret> arretsProcheDestination = null;
			
			arretsProcheUtilisateur = getArretsProcheUtilisateur(params[0]);
			arretsProcheDestination = getArretsProcheDestination(params[1]);		
			/*
			while(distance < params[0]){
				arretsProcheUtilisateur = getArretsProcheUtilisateur(distance);			
				nb = arretsProcheUtilisateur.size();
				Log.d("Nombre d'arrets à moins de "+distance+"m de la position", nb+"");
				distance=distance+100;
			}
			
			nb=0;
			distance=100;
			
			while(distance < params[1]){
				arretsProcheDestination = getArretsProcheDestination(distance);		
				nb = arretsProcheDestination.size();
				Log.d("Nombre d'arrets à moins de "+distance+"m de la destination", nb+"");
				distance=distance+100;
			}
			*/
			ret = chercheLignes(arretsProcheUtilisateur, arretsProcheDestination);
			
		}
		else{
			Log.d("Nombre d'arrets", "Erreur location");
		}
		
		return ret;
	}

	@Override
	protected void onProgressUpdate(String... progress) {
			 
	}
	
	@Override
	protected void onPostExecute(ArrayList<Ligne> result) {
	    super.onPostExecute(result);
	    Log.d(result.size()+"", "Result size");
	    LineList listAdapter = new LineList(c, result);
		list.setAdapter(listAdapter);
		list.refreshDrawableState();
		layout.setVisibility(View.INVISIBLE);
	}
	
	private ArrayList<Arret> getArretsProcheUtilisateur(float distance){
		
		ArrayList<Arret> ret = new ArrayList<Arret>();
		
		Iterator<Arret> it = Globale.engine.getReseau().getArrets().values().iterator();
		while(it.hasNext()){
			Arret a = it.next();
			float distanceTo = currentLocation.distanceTo(a.getLocation());
			if(distanceTo<distance)
				ret.add(a);
			Log.d(a.toString(), "Nouvel arret trouvé à une distance inférieure à "+distance+"m");
		}
		return ret;
	}
	
	private ArrayList<Arret> getArretsProcheDestination(float distance){
		
		ArrayList<Arret> ret = new ArrayList<Arret>();
		
		Iterator<Arret> it = Globale.engine.getReseau().getArrets().values().iterator();
		while(it.hasNext()){
			Arret a = it.next();
			float distanceTo = destination.distanceTo(a.getLocation());
			if(distanceTo<distance)
				ret.add(a);
		}
		return ret;
	}
	
	private ArrayList<Ligne> chercheLignes(ArrayList<Arret> a1, ArrayList<Arret> a2){

		a1.retainAll(a2);
		ArrayList<Ligne> ret = new ArrayList<Ligne>();
		Iterator<Arret> it = a1.iterator();
		while(it.hasNext()){
			Arret a = it.next();
			Iterator<Ligne> itLignes = Globale.engine.getReseau().getLignes().values().iterator();
			while(itLignes.hasNext()){
				Ligne l = itLignes.next();
				if(l.getArrets().containsKey(a.toString()) && !ret.contains(l)){
					ret.add(l);
				}
			}
		}
		
		return ret;
	}
}
