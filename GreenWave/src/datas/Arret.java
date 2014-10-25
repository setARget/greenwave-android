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
public class Arret implements Comparable{
	
	// ----------- ATTRIBUTES
	
	private static int nbArrets = 0;
	
	private int idBdd, nbVues, prefRang;
	private String ville, nom;
	private LatLng latLng;
	private Location location;
	private int distance;
	private ArrayList<String> lignesDesservant;
	private boolean favorite;
	
	
	// ----------- CONSTRUTORS
	
	public Arret(LatLng latLng, String nom, String ville, ArrayList<String> lignesDesservant){
		this.idBdd = nbArrets;
		this.lignesDesservant = lignesDesservant; 
		this.latLng=latLng;
		this.nom = nom;
		this.distance=-1;
		this.nbVues = 0;
		this.prefRang = 0;
		this.ville=ville;
		this.location=new Location(nom);
		this.location.setLatitude(latLng.latitude);
		this.location.setLongitude(latLng.longitude);
		favorite=false;
		nbArrets++;
	}
	
	// ----------- METHODS
	
	public static int getNbArrets(){return nbArrets;}
	
	public int getIdBdd(){return idBdd;}
	
	public LatLng getLatLng(){return latLng;}
	
	public Location getLocation(){return location;}
	
	public ArrayList<String> getLignesDesservant(){return lignesDesservant;}
	
	public String getVille(){return ville;}
	
	public String getNom(){return nom;}
	
	public int getNbVues(){return nbVues;}
	
	public int getPrefRang(){return prefRang;}
	
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
	
	public void setNbVues(int nb){this.nbVues = nb;}
	
	public void setPrefRang(int rang){this.prefRang = rang;}
	
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
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		Arret other = (Arret) another;
		int ret=-1;
		
		if(this.favorite && !other.favorite){
			ret=-1;
		}
		
		else if(other.favorite && !this.favorite){
			ret=1;
		}
		else{
			if(this.nom.charAt(0) > other.nom.charAt(0)){
				ret=1;
			}
			else if(this.nom.charAt(0)==other.nom.charAt(0)){
				ret=0;
			}
		}
		
		return ret;
	}
}
