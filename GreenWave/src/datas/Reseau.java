package datas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import datas.database.arret.ArretsDAO;
import datas.database.ligne.LignesDAO;

import android.content.Context;
import android.util.Log;

/**
 * © Copyright 2014 Antoine Sauray
 * Entreprise is a bus company class
 * @author Antoine Sauray
 * @version 0.1
 */
public abstract class Reseau {

	protected String nom;
	
	protected TreeMap<String, Ligne> lignes;
	protected HashMap<String, Arret> arrets;
	
	private final String twitterTimeline;
	
	public Reseau(String nom, String twitterTimeline){
		this.nom=nom;
		this.twitterTimeline=twitterTimeline;
		lignes = new TreeMap<String, Ligne>();
		arrets = new HashMap<String, Arret>();
	}
	
	protected void synchronize(Context c){
		
		Log.d("envoi: ", ""+ lignes.size());
		LignesDAO lDao = new LignesDAO(c);
		lDao.open();
		lDao.synchronisation(lignes);
        lDao.close();
		
		ArretsDAO aDao = new ArretsDAO(c);
		aDao.open();
		aDao.synchronisation(arrets);
        aDao.close();

	}
	
	protected void associer(){
		Iterator<Arret> it = arrets.values().iterator();
		while(it.hasNext()){
			Arret a = it.next();
			Iterator<String> itLignes = a.getLignesDesservant().iterator();
			while(itLignes.hasNext()){
				String idLigne = itLignes.next();
				if(lignes.get(idLigne)!=null){	// Sécurité à retirer plus tard
					lignes.get(idLigne).getArrets().put(a.toString(), a);
					Log.d("Association", idLigne+" - "+a.toString());
				}
			}
		}
	}
	
	protected void charger(Context c){
		chargerArrets(c);
		chargerLignes(c);
		associer();
		synchronize(c);
	}
	
	protected abstract void chargerArrets(Context c);
	
	protected abstract void chargerLignes(Context c);
	
	public TreeMap<String, Ligne> getLignes(){
		return lignes;
	}
	
	public HashMap<String, Arret> getArrets(){
		return arrets;
	}
	
	public abstract int getImage();
	
	public String getTwitterTimeline(){return this.twitterTimeline;}
	
	public String toString(){
		return nom;
	}
}
