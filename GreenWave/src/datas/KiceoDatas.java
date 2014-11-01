package datas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.Marker;

import datas.reseau.Lorient;
import datas.reseau.Vannes;
import datas.utility.TravelHelper;

/**
* Class KiceoDatas
* © Copyright 2014 Antoine Sauray
* @author Antoine Sauray & Quentin Pitalier
* @version 0.1
*/
public class KiceoDatas{
	
	

    // ---------- Attributes  
	/** the name of the application */
    private String label;
    
    /** The current Company selected*/
    private Reseau reseau;
    
    /** Contains the companies available in the application*/
    private TreeMap<String, Reseau> listeReseaux;

    /** The current Stop selected*/
    private Arret arretCourant;   
    
    /**The current Line selected*/
    private Ligne ligneCourante;
    
	/**	La liste des arrets actuellements desservis par la ligne. Par defaut, il contient tous les arrets car aucune ligne n'est selectionnée*/
    private ArrayList<Arret> listeArretsDesservis;
    
    private ArrayList<Arret> arretsFavorisDesservis;
    
    /**Contains the list of markers identified by their String key*/
    private TreeMap<String, Marker> hash;
    
    private Marker[] placeMarkers;
    private HashMap<String, Place> placesMap;
    
    private boolean updateLigne, updateArret;	//triche ! Permet au fragment carte de savoir si il doit mettre à jour la carte
    						// Il est impossible pour le listener de toucher à ses elements graphiques
    private int defaultFragment;
    private Location location;
	private LocationManager locationManager;
	private String provider;
	
	private String placeToSearch;
	
	//
	private TravelHelper travelHelper;
	
	private Utilisateur user;

    // ---------- Constructor

	/**
	 * Initialise the datas and the application
	 *
	 */
    public KiceoDatas(){
    	this.label = new String ( "Green Wav'" );
    	listeReseaux = new TreeMap<String, Reseau>();
    	
    	hash  = new TreeMap<String, Marker>();

    	listeArretsDesservis = new ArrayList<Arret>();
    	arretsFavorisDesservis = new ArrayList<Arret>();
    	
    	placesMap = new HashMap<String, Place>();
    	
    	travelHelper = new TravelHelper();
    	
    	
    	defaultFragment=0;
    	user=new Utilisateur();
    }
    /**
     * Modifie l'entreprise choisie et charge les données
     * @param e 
     */
    
    public Reseau getReseau(){
    	return reseau;
    }
    
	public Reseau getReseau(String id){
		return listeReseaux.get(id);
	}
    
    public TravelHelper getTravelHelper(){
    	return travelHelper;
    }
    public TreeMap<String, Reseau> getListeEntreprises(){return listeReseaux;}
    
    public void setReseau(Reseau e, Context c){
    	Log.d("Nouvelle entreprise", e.toString());
    	reseau = e;
    }
    
    public ArrayList<Arret> getListeArretsDesservis(){
    	return listeArretsDesservis;
    }
    
    public ArrayList<Arret> getArretsFavorisDesservis(){
    	return this.arretsFavorisDesservis;
    }
    
    public TreeMap<String, Marker> getHash(){return hash;}
    
    public Arret getArretCourant(){return arretCourant;}
    
    public void setArretCourant(Arret nouvelArretCourant){arretCourant = nouvelArretCourant;}
    
    public Ligne getLigneCourante(){return ligneCourante;}
    
    public void setLigneCourante(Ligne nouvelleLigneCourante){ligneCourante = nouvelleLigneCourante;}
    
    /**
 	 * Get the text of the application.
     *
     * @return the text of the application
     */
    public String getLabel() {

        return label;        
    } // ------------------------------------------------------------ getLabel()
	
	public boolean getUpdateLigne(){return updateLigne;}
	
	public void setUpdateLigne(boolean update){this.updateLigne=update;}
	
	public boolean getUpdateArret(){return updateArret;}
	
	public void setUpdateArret(boolean update){this.updateArret=update;}
	
	public void setLocation(Location l){
		location=l;
	}
	public int getDefaultFragment(){return defaultFragment;}
	
	public void setDefaultFragment(int num){this.defaultFragment=num;}
	
	public Location getLocation(){return this.location;}
	
	public String getProvider(){return provider;}
	
	public void setProvider(String provider){this.provider=provider;}
	
	public LocationManager getLocationManager(){return this.locationManager;}
	
	public void setLocationManager(LocationManager locationManager){this.locationManager=locationManager;}
	
	public String getPlaceToSearch(){return placeToSearch;}
	
	public Marker getDestination(){
		return travelHelper.getDestination();
	}
	public void setDestination(Marker destination){
		travelHelper.setDestination(destination);
	}
	
	public Marker[] getPlacesMarkers(){
		return this.placeMarkers;
	}
	public void setPlacesMarkers(Marker[] markers){
		this.placeMarkers=markers;
	}
	
	public HashMap<String, Place> getPlacesMap(){
		return placesMap;
	}
	
	public Utilisateur getUtilisateur(){
		return user;
	}
	
	public void setUtilisateur(Utilisateur user){
		this.user=user;
	}
  
    
}