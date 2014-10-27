package datas.utility;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import view.activities.Home;
import view.activities.Route;
import view.custom.DirectionDialog;
import view.custom.LineList;
import view.custom.RouteList;

import control.Globale;
import control.listeners.item.LineClickListener;
import control.listeners.item.LineRouteClickListener;
import datas.Arret;
import datas.Ligne;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class CalculerItineraire extends AsyncTask <Float,String, ArrayList<String> > {

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
	protected ArrayList<String> doInBackground(Float... params) {
		ArrayList<String> ret = new ArrayList<String>();
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
	protected void onPostExecute(ArrayList<String> result) {
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

	private ArrayList<String> chercheLignes(ArrayList<Arret> list1, ArrayList<Arret> list2){
		
		Iterator<Arret> it1 = list1.iterator();
		
		ArrayList<String> lignesPossibles = new ArrayList<String>();
		ArrayList<String> lignes = new ArrayList<String>();
		
		while(it1.hasNext()){
			// Pour chaque arret
			Iterator<String> itLignes = it1.next().getLignesDesservant().iterator();
			while(itLignes.hasNext()){
				// On ajoute les lignes possibles si elles ne sont pas déjà présentes
				String l = itLignes.next();
				if(!lignesPossibles.contains(l)){
					lignesPossibles.add(l);
					Log.d("Ligne Possible", l);
				}
			}
		}
		
		lignes = getLignes(lignesPossibles, list2);

		return lignes;
	}
	
	private ArrayList<String> getLignes(ArrayList<String> lignesPossibles, ArrayList<Arret> arretsDestination){
		ArrayList<String> ret = new ArrayList<String>();
		Iterator<Arret> it = arretsDestination.iterator();
		
		while(it.hasNext()){
			Arret a = it.next();
			Iterator<String> itLignes = a.getLignesDesservant().iterator();
			while(itLignes.hasNext()){
				// On ajoute les lignes possibles si elles ne sont pas déjà présentes
				String l = itLignes.next();
				if(lignesPossibles.contains(l) && !ret.contains(l)){
					ret.add(l);
					Log.d("Ligne Compatible", l);
				}
			}
		}
		
		return ret;
	}
}
