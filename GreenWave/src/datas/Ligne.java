package datas;

import java.util.HashMap;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

/**
 * Ligne is a bus line class
 * It has stops and belongs to a certain company
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray & Alexis Robin
 * @version 0.2
 */
public class Ligne implements Comparable<Ligne>{
	
	// ----------- ATTRIBUTES
	
	private static int nbLignes = 0;
	private HashMap<String, Arret> arrets;
	
	private int nbVues, prefRang, couleur;
	private int id;
	private boolean favorite;
	private String nom, direction1, direction2, numero;
	private BitmapDescriptor markerColor;
	
    
 // ----------- CONSTRUCTORS
    
	
	public Ligne(int id, String numero, String direction1, String direction2, int couleur){
		this.id=id;
		this.numero=numero;
		this.nom="Ligne "+numero;
		this.direction1=direction1;
		this.direction2=direction2;
		this.couleur=couleur;
		BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		
		favorite=false;
		nbLignes++;
		arrets = new HashMap<String, Arret>();
	}
	
	public Ligne(String numero, String aller, String retour, BitmapDescriptor markerColor, int color){
		this.id=nbLignes;
		this.numero=numero;
		this.direction1=aller;
		this.direction2=retour;
		this.markerColor=markerColor;
		this.couleur=color;
		nom="Ligne "+numero;
		favorite=false;
		nbLignes++;
		Log.d("Nouvelle Ligne", this.toString());
		arrets = new HashMap<String, Arret>();		
	}
	
	public Ligne(int id, String numero, String aller, String retour, BitmapDescriptor markerColor, int color){
		this.id=nbLignes;
		this.id=id;
		this.numero=numero;
		this.direction1=aller;
		this.direction2=retour;
		this.markerColor=markerColor;
		this.couleur=color;
		nom="Ligne "+numero;
		favorite=false;
		nbLignes++;
		Log.d("Nouvelle Ligne", this.toString());
		arrets = new HashMap<String, Arret>();		
	}
	
	// ---------- METHODS
	// ----- ACCESSORS
	
	public static int getNbLignes(){return nbLignes;}
	
	public String getNom(){return nom;}
	
	public String getDirection1(){return direction1;}
	
	public String getDirection2(){return direction2;}
	
	public String getNumero(){return numero;}
	
	public int getNbVues(){return nbVues;}
	
	public int getPrefRang(){return prefRang;}
	
	public int getColor(){return couleur;}
	
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
	
	public void setID(int id){this.id = id;}
	
	public void setNom(String nom){this.nom = nom;}
	
	public void setNbVues(int nb){this.nbVues = nb;}
	
	public void setPrefRang(int rang){this.prefRang = rang;}
	
	public void setNumero( String numero){this.numero = numero;}
	
	public void setAller(String aller){this.direction1 = aller;}
	
	public void setRetour(String retour){this.direction2 = retour;}
	
	public void setColor(int color){this.couleur = color;}
	
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
		return nom;
	}
	
	public int getID(){
		return id;
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
