package datas;

import java.util.ArrayList;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * © Copyright 2014 Antoine Sauray
 * Arret is a bus stop class
 * @author Antoine Sauray & Alexis Robin
 * @version 0.2
 */
public class Arret implements Comparable<Arret>{
	
	// ----------- ATTRIBUTES
	
	private int idBdd, reseau;
	private String nom;
	private LatLng latLng;
	private Location location;
	private int distance;
	//private ArrayList<String> lignesDesservant;
	private boolean favorite;
	
	
	// ----------- CONSTRUTORS
	
	public Arret(int id, String nom, LatLng latLng, int reseau){
		this.idBdd = id;
		//this.lignesDesservant = lignesDesservant; 
		this.latLng=latLng;
		this.nom = nom;
		this.distance=-1;
		this.reseau=reseau;
		this.location=new Location(nom);
		this.location.setLatitude(latLng.latitude);
		this.location.setLongitude(latLng.longitude);
		favorite=false;
	}
	
	// ----------- METHODS
	
	public int getIdBdd(){return idBdd;}
	
	public LatLng getLatLng(){return latLng;}
	
	public Location getLocation(){return location;}
	
	//public ArrayList<String> getLignesDesservant(){return lignesDesservant;}
	
	public int getReseau(){return reseau;}
	
	public String getNom(){return nom;}
	
	public int getFavorite(){
		int ret;
		
		if(favorite == false){
			ret = 0;
		}
		else{
			ret = 1;
		}
		
		return ret;
	}
	
	public void setFavorite(int favorisInt){
		if(favorisInt == 0){
			this.favorite = false;
		}
		else{
			this.favorite = true;
		}
	}	
	
	public void setDistance(int distance){
		this.distance=distance;
	}
	
	public int getDistance(){
		return this.distance;
	}
	
	public String toString(){
		return nom;
	}

	@Override
	public int compareTo(Arret another) {
		// TODO Auto-generated method stub
		int ret=-1;
		
		if(this.favorite && !another.favorite){
			ret=-1;
		}
		
		else if(another.favorite && !this.favorite){
			ret=1;
		}
		else{
			if(this.nom.charAt(0) > another.nom.charAt(0)){
				ret=1;
			}
			else if(this.nom.charAt(0)==another.nom.charAt(0)){
				ret=0;
			}
		}
		
		return ret;
	}
}
