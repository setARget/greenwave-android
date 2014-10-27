package datas;

import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import db.internal.ArretsDAO;
import db.internal.LignesDAO;

import android.content.Context;
import android.util.Log;

/**
 * © Copyright 2014 Antoine Sauray
 * Entreprise is a bus company class
 * @author Antoine Sauray
 * @version 0.1
 */
public class Reseau implements Comparable<Reseau>{

	protected String nom;
	private int id;
	private String image;
	
	protected HashMap<String, Ligne> lignes;
	protected HashMap<String, Arret> arrets;
	
	private final String twitterTimeline;
	
	public Reseau(int id, String nom, String twitterTimeline, String img){
		this.nom=nom;
		this.id=id;
		this.image=img;
		this.twitterTimeline=twitterTimeline;
		lignes = new HashMap<String, Ligne>();
		arrets = new HashMap<String, Arret>();
	}
	
	public Reseau(String nom, String twitterTimeline){
		this.nom=nom;
		this.twitterTimeline=twitterTimeline;
		lignes = new HashMap<String, Ligne>();
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
	
	protected void chargerArrets(Context c){
		// Requete vers BDD Interne en utilisant le nom ou l'id du réseau
	}
	
	protected void chargerLignes(Context c){
		// Requete vers BDD Interne en utilisant le nom ou l'id du réseau
	}
	
	public HashMap<String, Ligne> getLignes(){
		return lignes;
	}
	
	public HashMap<String, Arret> getArrets(){
		return arrets;
	}
	
	public int getImage(){
		return 0;
	}
	
	public String getTwitterTimeline(){return this.twitterTimeline;}
	
	public String toString(){
		return nom;
	}
	public int getID(){
		return id;
	}
	
	public String getNom(){
		return this.nom;
	}

	@Override
	public int compareTo(Reseau another) {
		// TODO Auto-generated method stub
		int ret=-1;

		if(this.nom.charAt(0) > another.nom.charAt(0)){
			ret=1;
		}
		else if(this.nom.charAt(0)==another.nom.charAt(0)){
			ret=0;
		}
			
		return ret;
	}
}
