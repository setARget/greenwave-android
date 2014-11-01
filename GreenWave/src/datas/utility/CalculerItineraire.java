package datas.utility;

import java.util.ArrayList;
import java.util.Iterator;

import view.activities.Route;
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

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class CalculerItineraire extends AsyncTask <Float,String, ArrayList<Ligne> > {

	private Location currentLocation, destination;
	private Context c;
	private Route route;
	
	public CalculerItineraire(Context c, Route route, Location destination){
		currentLocation = Globale.engine.getLocation();
		this.destination = destination;
		this.c=c;
		this.route=route;
		route.getLayout().setVisibility(View.VISIBLE);
	}
	
	@Override
	protected ArrayList<Ligne> doInBackground(Float... params) {
		ArrayList<Ligne> ret = new ArrayList<Ligne>();
		if(currentLocation != null){
			int nb=0;
			float distance=100;
			ArrayList<Arret> arretsProcheUtilisateur = null;
			ArrayList<Arret> arretsProcheDestination = null;
			
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
			Iterator<Arret> itProcheUtilisateur = arretsProcheUtilisateur.iterator();
			
			while(itProcheUtilisateur.hasNext()){
				Arret a1 = itProcheUtilisateur.next();
				Iterator<Arret> itProcheDestination = arretsProcheDestination.iterator();
				while(itProcheDestination.hasNext()){
					Arret a2 = itProcheDestination.next();
					ret.addAll(chercheLignes(a1, a2));
				}
			}
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
	    Log.d("Result size", result.size()+"");
	    RouteList listAdapter = new RouteList(c, result);
		route.getList().setAdapter(listAdapter);
		route.getList().refreshDrawableState();
		route.getList().setOnItemClickListener(new LineRouteClickListener(route, c));
		route.getLayout().setVisibility(View.GONE);
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

	private ArrayList<Ligne> chercheLignes(Arret a1, Arret a2){
		
		ArrayList<Ligne> ret = new ArrayList<Ligne>();
		Iterator<Ligne> it = Globale.engine.getReseau().getLignes().values().iterator();
		while(it.hasNext()){
			Ligne l = it.next();
			if(l.getArrets().containsValue(a1) && l.getArrets().containsValue(a2)){
				ret.add(l);
			}
		}
		
		return ret;
	}
}
