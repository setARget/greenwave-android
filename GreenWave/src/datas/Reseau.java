package datas;

import java.util.HashMap;
import java.util.Iterator;

import com.google.android.gms.maps.model.LatLng;

import android.util.Log;

/**
 * © Copyright 2014 Antoine Sauray
 * Entreprise is a bus company class
 * @author Antoine Sauray, Alexis Robin
 * @version 0.2
 */
public class Reseau implements Comparable<Reseau>{

	protected String nom;
	private int idBdd , version;
	private String image;
	private LatLng position;
	
	protected HashMap<String, Ligne> lignes;
	protected HashMap<String, Arret> arrets;
	
	private final String twitterTimeline;
	
	public Reseau(int id, String nom, String twitterTimeline, String img, int version){
		this.nom=nom;
		this.idBdd=id;
		this.image=img;
		this.twitterTimeline=twitterTimeline;
		this.version = version;
		lignes = new HashMap<String, Ligne>();
		arrets = new HashMap<String, Arret>();
		position = new LatLng(47.7481, -3.36455);
	}
	
	public Reseau(int id, double latitude, double longitude, String nom, String twitterTimeline, String img, int version){
		this.nom=nom;
		this.idBdd=id;
		this.image=img;
		this.twitterTimeline=twitterTimeline;
		this.version = version;
		lignes = new HashMap<String, Ligne>();
		arrets = new HashMap<String, Arret>();
		position = new LatLng(latitude, longitude);
	}
	
	public Reseau(String nom, String twitterTimeline){
		this.nom=nom;
		this.twitterTimeline=twitterTimeline;
		lignes = new HashMap<String, Ligne>();
		arrets = new HashMap<String, Arret>();
	}
	
	public HashMap<String, Ligne> getLignes(){
		return lignes;
	}
	
	public HashMap<String, Arret> getArrets(){
		return arrets;
	}
	
	public String getImage(){
		return this.image;	// a modifier : Image est l'url qui donne vers l'image
	}
	
	public String getTwitterTimeline(){return this.twitterTimeline;}
	
	public String toString(){
		return nom;
	}
	public int getIdBdd(){
		return idBdd;
	}
	
	public String getNom(){
		return this.nom;
	}
	
	public int getVersion(){
		return this.version;
	}
	
	public void setLignes(HashMap<String, Ligne> lignes){
		this.lignes = lignes;
	}
	
	public void setArrets(HashMap<String, Arret> arrets){
		this.arrets = arrets;
	}
	
	public void setVersion(int version){
		this.version=version;
	}
	
	public void setPosition(LatLng position){
		this.position=position;
	}
	
	public void setPosition(double latitude, double longitude){
		this.position= new LatLng(latitude, longitude);
	}
	
	public LatLng getPosition(){
		return this.position;
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
