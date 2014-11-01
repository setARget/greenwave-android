package datas;

import java.util.HashMap;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;


/**
 * Ligne is a bus line class
 * It has stops and belongs to a certain company
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray & Alexis Robin
 * @version 0.2
 */
public class Ligne implements Comparable<Ligne>{
	
	// ----------- ATTRIBUTES
	
	private int idBdd, reseau;
	private boolean favorite;
	private String direction1, direction2, numero, color;
	private BitmapDescriptor markerColor;
	private HashMap<String, Arret> arrets;
	
    
 // ----------- CONSTRUCTORS
    
	
	public Ligne(int idBdd, String numero, String direction1, String direction2, String color, int reseau){
		this.idBdd=idBdd;
		this.numero=numero;
		this.direction1=direction1;
		this.direction2=direction2;
		this.color=color;
		this.reseau = reseau;
		favorite=false;
		Log.d("Nouvelle Ligne", this.toString());
		arrets = new HashMap<String, Arret>();		
	}
	
	// ---------- METHODS
	// ----- ACCESSORS
	
	public int getIdBdd(){return idBdd;}
	
	public String getNom(){return ("Ligne " + this.numero);}
	
	public String getDirection1(){return direction1;}
	
	public String getDirection2(){return direction2;}
	
	public String getNumero(){return numero;}
	
	public String getColor(){return color;}
	
	public int getReseau(){return reseau;}
	
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
	
	public HashMap<String, Arret> getArrets(){return arrets;}
	
	public BitmapDescriptor getMarkerColor(){return markerColor;}
	
	public void setIdBdd(int idBdd){this.idBdd = idBdd;}
	
	public void setNumero( String numero){this.numero = numero;}
	
	public void setDirection1(String direction1){this.direction1 = direction1;}
	
	public void setDirection2(String direction2){this.direction2 = direction2;}
	
	public void setColor(String color){this.color = color;}
	
	public void setArrets(HashMap<String, Arret> arrets){
		this.arrets = arrets;
	}
	
	public void setFavorite(int favorisInt){
		if(favorisInt == 0){
			this.favorite = false;
		}
		else{
			this.favorite = true;
		}
	}
	
	// ----- OTHER METHODS
	
	public String toString(){
		return ("Ligne " + this.numero);
	}

	@Override
	public int compareTo(Ligne another) {
		// TODO Auto-generated method stub
		int ret=-1;
		
		if(this.favorite && !another.favorite){
			ret=-1;
		}
		
		else if(another.favorite && !this.favorite){
			ret=1;
		}
		else{
			if(Integer.parseInt(numero) > Integer.parseInt(another.numero)){
				ret=1;
			}
			else if(Integer.parseInt(numero)==Integer.parseInt(another.numero)){
				ret=0;
			}
		}
		
		return ret;
	}
}
