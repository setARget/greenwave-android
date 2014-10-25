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
public class Ligne implements Comparable{
	
	// ----------- ATTRIBUTES
	
	private static int nbLignes = 0;
	private HashMap<String, Arret> arrets;
	
	private int idBdd, nbVues, prefRang, color;
	private boolean favorite;
	private String nom, aller, retour, numero;
	private BitmapDescriptor markerColor;
	
    
 // ----------- CONSTRUCTORS
    
	
	public Ligne(String numero, String aller, String retour, BitmapDescriptor markerColor, int color){
		this.idBdd=nbLignes;
		this.numero=numero;
		this.aller=aller;
		this.retour=retour;
		this.markerColor=markerColor;
		this.color=color;
		nom="Ligne "+numero;
		favorite=false;
		nbLignes++;
		Log.d("Nouvelle Ligne", this.toString());
		arrets = new HashMap<String, Arret>();		
	}
	
	// ---------- METHODS
	// ----- ACCESSORS
	
	public static int getNbLignes(){return nbLignes;}
	
	public int getIdBdd(){return idBdd;}
	
	public String getNom(){return nom;}
	
	public String getAller(){return aller;}
	
	public String getRetour(){return retour;}
	
	public String getNumero(){return numero;}
	
	public int getNbVues(){return nbVues;}
	
	public int getPrefRang(){return prefRang;}
	
	public int getColor(){return color;}
	
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
	
	public void setNom(String nom){this.nom = nom;}
	
	public void setNbVues(int nb){this.nbVues = nb;}
	
	public void setPrefRang(int rang){this.prefRang = rang;}
	
	public void setNumero( String numero){this.numero = numero;}
	
	public void setAller(String aller){this.aller = aller;}
	
	public void setRetour(String retour){this.retour = retour;}
	
	public void setColor(int color){this.color = color;}
	
	public void setFavorite(int favorisInt){
		if(favorisInt == 0){
			this.favorite = false;
		}
		else{
			this.favorite = true;
		}
	}
	
	@Override
	public int compareTo(Object another) {
		// TODO Auto-generated method stub
		Ligne other = (Ligne) another;
		int ret=-1;
		
		if(this.favorite && !other.favorite){
			ret=-1;
		}
		
		else if(other.favorite && !this.favorite){
			ret=1;
		}
		else{
			if(Integer.parseInt(numero) > Integer.parseInt(other.numero)){
				ret=1;
			}
			else if(Integer.parseInt(numero)==Integer.parseInt(other.numero)){
				ret=0;
			}
		}
		
		return ret;
	}
	
	
	// ----- OTHER METHODS
	
	public String toString(){
		return nom;
	}
}
