package datas.db.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import control.Globale;

import datas.Arret;
import datas.Horaire;
import datas.Ligne;
import datas.Reseau;

/**
 * 
 * @author Alexis Robin
 * @version 0.2
 */
public class JuniorDAO {
		
		  // ---------- ATTRIBUTES
		
		  protected final static int VERSION = 1;
		  protected final static String NOM = "greenwave8.db";
		  protected SQLiteDatabase db;
		  protected Junior handler;
		  
		  // ---------- TABLES
		  // ----- reseau
			
		  public static final String RESEAU = "reseau";
		  public static final String RESEAU_ID = "id";
		  public static final String RESEAU_NOM = "nom";
		  public static final String RESEAU_TWITTER = "twitter";
		  public static final String RESEAU_IMAGE = "image";
		  public static final String RESEAU_VERSION = "version";
		  
		  // ----- ligne
		  
		  public static final String LIGNE = "ligne";
		  public static final String LIGNE_ID = "id";
		  public static final String LIGNE_NOM = "nom";
		  public static final String LIGNE_DIRECTION1 = "direction1";
		  public static final String LIGNE_DIRECTION2 = "direction2";
		  public static final String LIGNE_COULEUR = "couleur";
		  public static final String LIGNE_RESEAU = "reseau";
		  public static final String LIGNE_FAVORIS = "favoris";

		  
		  // ----- arret
		  
		  public static final String ARRET = "arret";
		  public static final String ARRET_ID = "id";
		  public static final String ARRET_NOM = "nom";
		  public static final String ARRET_LATITUDE = "latitude";
		  public static final String ARRET_LONGITUDE = "longitude";
		  public static final String ARRET_RESEAU = "reseau";
		  public static final String ARRET_FAVORIS = "favoris";
		  
		  // ----- appartient
		  
		  public static final String APPARTIENT = "appartient";
		  public static final String APPARTIENT_LIGNE = "ligne";
		  public static final String APPARTIENT_ARRET = "arret";
		  
		  // ----- horaire
		  
		  public static final String HORAIRE = "horaire";
		  public static final String HORAIRE_ID = "id";
		  public static final String HORAIRE_HORAIRE = "horaire";
		  public static final String HORAIRE_CALENDRIER = "calendrier";
		  public static final String HORAIRE_ARRET = "arret";
		  public static final String HORAIRE_LIGNE = "ligne";
		  public static final String HORAIRE_DIRECTION = "direction";
		  
		  
		  // ---------- CONSTRUCTOR
		  
		  public JuniorDAO(Context context) {
			    this.handler = new Junior(context, NOM, null, VERSION);
		  }
		    
		  
		  // ---------- METHODS
		  
		  public SQLiteDatabase open() {
		    db = handler.getWritableDatabase();
		    return db;
		  } // ---------------------------------------------------------- open()
		    
		  public void close() {
		    db.close();
		  } // ---------------------------------------------------------- close()
		    
		  public SQLiteDatabase getDb() {
		    return db;
		  } // ---------------------------------------------------------- getDb()
		  
		  
		  // ----- RESEAU
		  
		  public long insertReseau(Reseau reseau){
			  long ret = 0;
			  HashMap<String, Ligne> lignes = null;
			  HashMap<String, Arret> arrets = null;
			  
			  // On cherche si le réseau a déjà été intégré, si oui on le supprime avant de le re-rentré dans la base
			  Cursor c = db.query(RESEAU, new String[] {RESEAU_ID},  RESEAU_ID + " LIKE '" + reseau.getIdBdd() + "'" , null, null, null, null);
			  
			  if(c != null && c.moveToFirst()){
				  this.removeReseau(reseau.getIdBdd());
			  }
			  
			  if(reseau !=  null){  
				  
				    // On rentre le reseau dans la bdd
					ContentValues values = new ContentValues();
					values.put(RESEAU_ID, reseau.getIdBdd());
					values.put(RESEAU_NOM, reseau.getNom());
					values.put(RESEAU_TWITTER, reseau.getTwitterTimeline());
					values.put(RESEAU_IMAGE, reseau.getImage());
					values.put(RESEAU_VERSION, reseau.getVersion());
					ret = db.insert(RESEAU, null, values);
					
					// On récupère la liste des lignes et des arrets pour les rentrer dans la bdd
				  	arrets = reseau.getArrets();
			        Iterator<Arret> itArret = arrets.values().iterator();
			        while(itArret.hasNext()){
			        	insertArret(itArret.next());
			        }
				  
				  
				  	lignes = reseau.getLignes();
				    Iterator<Ligne> itLigne = lignes.values().iterator();
			        while(itLigne.hasNext()){
			        	insertLigne(itLigne.next());
			        }
			  }
			  
			  Log.d(reseau.getNom(), "JUNIOR : Nouveau réseau");
			  return ret;
		  }
		  
		  public ArrayList<Reseau> findReseaux(){
			  ArrayList<Reseau> ret = new ArrayList<Reseau>();
			  Reseau tmp = null;
			  int tmpId, tmpVersion;
			  String tmpNom, tmpTwitter, tmpImage;
			  
			  Cursor c = db.query(RESEAU, new String[] {RESEAU_ID, RESEAU_NOM, RESEAU_TWITTER, RESEAU_IMAGE, RESEAU_VERSION}, null, null, null, null, null);
				  
			  while(c.moveToNext()){
				  tmpId = c.getInt(0);
				  tmpNom = c.getString(1);
				  tmpTwitter = c.getString(2);
				  tmpImage = c.getString(3);
				  tmpVersion = c.getInt(4);
				  tmp = new Reseau(tmpId, tmpNom, tmpTwitter, tmpImage, tmpVersion);
				  
				  //On cherche les lignes et les arrets correspondants au réseau
				  tmp.setArrets(findArrets(tmpId));
				  tmp.setLignes(findLignes(tmpId));
				  
				  ret.add(tmp);
			  }
			  
			  Log.d("", "JUNIOR : FIND Reseau");
			  c.close();
			  
			  return ret;
		  }
		  
		  private int removeReseau(int id) {
			  
			  Ligne tmpLigne = null;
			  HashMap<String, Ligne> lignes = this.findLignes(id);
			  Iterator<Ligne> itLigne = lignes.values().iterator();
		      while(itLigne.hasNext()){
		    	  tmpLigne = itLigne.next();
		    	  this.removeAssociation(tmpLigne.getIdBdd());
		    	  this.removeHoraireLigne(tmpLigne.getIdBdd());
		      }  
		      this.removeArret(id);
		      this.removeLigne(id);
			  
		      Log.d(""+ id, "JUNIOR : suprresion réseau");
		      
			  return db.delete(RESEAU, RESEAU_ID + " = " + id, null);
		  }
		  
		  // ----- LIGNE
		  
		  public long insertLigne(Ligne ligne){
			  long ret = 0;
			  if(ligne !=  null){
					
				  	// On insère la ligne en elle-même
				  
				  	ContentValues values = new ContentValues();
					values.put(LIGNE_ID, ligne.getIdBdd());
					values.put(LIGNE_NOM, ligne.getNumero());
					values.put(LIGNE_DIRECTION1, ligne.getDirection1());
					values.put(LIGNE_DIRECTION2, ligne.getDirection2());
					values.put(LIGNE_COULEUR, ligne.getColor());
					values.put(LIGNE_RESEAU, ligne.getReseau());
					ret = db.insert(LIGNE, null, values);
					
					// On insère les associations ligne-arret
					insertAssociation(ligne);
					
					Log.d(ligne.getNom(), "JUNIOR : Nouvelle ligne");
					
			  }
			  return ret;
		  }
		  
		  public HashMap<String, Ligne> findLignes(int reseau){
			  HashMap<String, Ligne> ret = new HashMap<String, Ligne>();
			  Ligne tmp = null;
			  int tmpId, tmpFavoris;
			  String tmpNom, tmpDirection1, tmpDirection2, tmpCouleur;
			  
			  if(reseau != 0){
				  Cursor c = db.query(LIGNE, new String[] {LIGNE_ID, LIGNE_NOM, LIGNE_DIRECTION1, LIGNE_DIRECTION2, LIGNE_COULEUR, LIGNE_FAVORIS}, LIGNE_RESEAU + " LIKE '" + reseau + "'", null, null, null, null);
				  
				  while(c.moveToNext()){
					  tmpId = c.getInt(0);
					  tmpNom = c.getString(1);
					  tmpDirection1 = c.getString(2);
					  tmpDirection2 = c.getString(3);
					  tmpCouleur = c.getString(4);
					  tmpFavoris = c.getInt(5);
					  tmp = new Ligne(tmpId, tmpNom, tmpDirection1, tmpDirection2, tmpCouleur, reseau, tmpFavoris);
					  tmp.setArrets(findAssociateArrets(tmp));
					  ret.put(tmpNom, tmp);
				  }
				  c.close();
			  }
			  
			  Log.d("", "JUNIOR : FIND ligne");
			  
			  return ret;
		  }
		  
		  public void setLigneFavoris(int idLigne){
			  if(idLigne != 0){
				  ContentValues values = new ContentValues();
				  values.put(LIGNE_FAVORIS,1);
				  db.update(LIGNE, values, LIGNE_ID + " LIKE " + idLigne, null);
			  }
		  }
		  
		  public void setLigneNotFavoris(int idLigne){
			  if(idLigne != 0){
				  ContentValues values = new ContentValues();
				  values.put(LIGNE_FAVORIS,0);
				  db.update(LIGNE, values, LIGNE_ID + " LIKE " + idLigne, null);
			  }
		  }
		  
		  public int removeLigne(int idReseau) {
			  return db.delete(LIGNE, LIGNE_RESEAU + " = " + idReseau, null);
		  }
		  

		  // ----- ARRET
		  
		  public long insertArret(Arret arret){
			  long ret = 0;
			  if(arret !=  null){
					ContentValues values = new ContentValues();
					values.put(ARRET_ID, arret.getIdBdd());
					values.put(ARRET_NOM, arret.getNom());
					values.put(ARRET_LATITUDE, arret.getLatLng().latitude);
					values.put(ARRET_LONGITUDE, arret.getLatLng().longitude);
					values.put(ARRET_RESEAU, arret.getReseau());
					ret = db.insert(ARRET, null, values);
			  }
			  
			  Log.d(arret.getNom() + ", etat = " + ret + ", id = " + arret.getIdBdd(), "JUNIOR : Nouvelle arret");
			  return ret;
		  }
		  
		  public HashMap<String, Arret> findArrets(int reseau){
			  HashMap<String, Arret> ret = new HashMap<String, Arret>();
			  Arret tmp = null;
			  int tmpId, tmpFavoris;
			  String tmpNom;
			  LatLng tmpLatLng;
			  
			  if(reseau != 0){
				  Cursor c = db.query(ARRET, new String[] {ARRET_ID, ARRET_NOM, ARRET_LATITUDE, ARRET_LONGITUDE, ARRET_FAVORIS}, ARRET_RESEAU + " LIKE '" + reseau + "'", null, null, null, null);
				  
				  while(c.moveToNext()){
					  tmpId = c.getInt(0);
					  tmpNom = c.getString(1);
					  tmpLatLng = new LatLng(c.getFloat(2), c.getFloat(3));
					  tmpFavoris = c.getInt(4);
					  tmp = new Arret(tmpId, tmpNom, tmpLatLng, reseau, tmpFavoris);
					  ret.put(tmpNom, tmp);
				  }
				  c.close();
			  }
			  
			  return ret;
		  }
		  
		  
		  public void setArretFavoris(int idArret){
			  if(idArret != 0){
				  ContentValues values = new ContentValues();
				  values.put(ARRET_FAVORIS,1);
				  db.update(ARRET, values, ARRET_ID + " LIKE " + idArret, null);
			  }
		  }
		  
		  public void setArretNotFavoris(int idArret){
			  if(idArret != 0){
				  ContentValues values = new ContentValues();
				  values.put(ARRET_FAVORIS,0);
				  db.update(ARRET, values, ARRET_ID + " LIKE " + idArret, null);
			  }
		  }
		  
		  public int removeArret(int idReseau) {		  
			  return db.delete(ARRET, ARRET_RESEAU + " = " + idReseau, null);
		  }
		  
		  // ----- APPARTIENT
		  
		  public long insertAssociation(Ligne ligne){
			  long ret = 0;
			  if(ligne !=  null){		  
				  	ArrayList<Arret> arrets = new ArrayList<Arret>(ligne.getArrets().values());
				  	Iterator<Arret> itArret = arrets.iterator();
				  	Arret tmpArret = null;
				  	while(itArret.hasNext()){
				  		tmpArret = itArret.next();
				  		ContentValues values = new ContentValues();
						values.put(APPARTIENT_LIGNE, ligne.getIdBdd());
						values.put(APPARTIENT_ARRET, tmpArret.getIdBdd());
						ret = db.insert(APPARTIENT, null, values);
						
						Log.d("ligne" + ligne.getNom() + ", arret " + tmpArret.getNom(), "JUNIOR : Nouvelle association");
				  	}
			  }
			  return ret;
		  }
		  
		  public HashMap<String, Arret> findAssociateArrets(Ligne ligne){
			  HashMap<String, Arret> ret = new HashMap<String, Arret>();
			  Arret tmp = null;
			  int tmpId, tmpReseau, tmpFavoris;
			  String tmpNom;
			  LatLng tmpLatLng;
			  
			  Log.d(ligne.getNom(), "JUNIOR : Récupération des associations");
			  
			  if(ligne != null){
				  
				  // On récupère les id des arrets associés à la ligne
				  Cursor c1 = db.query(APPARTIENT, new String[] {APPARTIENT_ARRET}, APPARTIENT_LIGNE+ " LIKE '" + ligne.getIdBdd() + "'", null, null, null, null);
				  while(c1.moveToNext()){
					  Log.d(""+c1.getInt(0), "JUNIOR : Nouvelle association trouvé pour ligne " + ligne.getNom());
				  
					  	// On récupère un à un chaque arret
					  	Cursor c2 = db.query(ARRET, new String[] {ARRET_ID, ARRET_NOM, ARRET_LATITUDE, ARRET_LONGITUDE, ARRET_RESEAU, ARRET_FAVORIS}, ARRET_ID + " LIKE '" + c1.getInt(0) + "'", null, null, null, null);
				  
				  		while(c2.moveToNext()){
					  		tmpId = c2.getInt(0);
					  		tmpNom = c2.getString(1);
					  		tmpLatLng = new LatLng(c2.getFloat(2), c2.getFloat(3));
					  		tmpReseau = c2.getInt(4);
					  		tmpFavoris = c2.getInt(5);
					 		tmp = new Arret(tmpId, tmpNom, tmpLatLng, tmpReseau, tmpFavoris);
					  		ret.put(tmpNom, tmp);
					  		
					  		Log.d(tmpNom, "JUNIOR : Nouvelle association faite pour ligne " + ligne.getNom());
				  		}
				  		c2.close();
				  }
				  c1.close();
			  }
			  
			  return ret;
		  }
		  
		  public int removeAssociation(int idLigne) {		  
			  return db.delete(APPARTIENT, APPARTIENT_LIGNE + " = " + idLigne, null);
		  }
		  
		  
		  // ----- HORAIRE
		  
		  public long insertHoraires(ArrayList<Horaire> horaires){
			  long ret = 0;
			  Horaire tmpHoraire = null;
			  if(!(horaires.isEmpty())){
				  	Iterator<Horaire> it = horaires.iterator();
				   while(it.hasNext()){
					   	tmpHoraire = it.next();
						ContentValues values = new ContentValues();
						values.put(HORAIRE_ID, tmpHoraire.getId());
						values.put(HORAIRE_HORAIRE, tmpHoraire.toString());
						values.put(HORAIRE_CALENDRIER, Horaire.getDayOfWeek());
						values.put(HORAIRE_ARRET, Globale.engine.getArretCourant().getIdBdd());
						values.put(HORAIRE_LIGNE, Globale.engine.getLigneCourante().getIdBdd());
						values.put(HORAIRE_DIRECTION, tmpHoraire.getDirection());
						ret = db.insert(HORAIRE, null, values);
				   }
			  }
			  return ret;
		  }
		  
		  

		  public ArrayList<Horaire> findHoraire(int arret, int ligne, int direction, String calendrier){
			  ArrayList<Horaire> ret = new ArrayList<Horaire>();
			  
			  if(arret != 0 && ligne != 0 && direction != 0 && calendrier != ""){
				  Cursor c = db.query(HORAIRE, new String[] {HORAIRE_ID, HORAIRE_HORAIRE}, HORAIRE_ARRET + " LIKE '" + arret + "' AND " + HORAIRE_LIGNE + " LIKE '" + ligne + "' AND " + HORAIRE_DIRECTION + " LIKE '" + direction + "' AND " + HORAIRE_CALENDRIER + " LIKE '" + calendrier+"'", null, null, null, null);
				  
				  while(c.moveToNext()){
					  ret.add(new Horaire(c.getInt(0), c.getString(1)));
				  }
				  c.close();
			  }
			  return ret;
		  }
		  
		  public int removeHoraireLigne(int idLigne) {		  
			  return db.delete(HORAIRE, HORAIRE_LIGNE + " = " + idLigne, null);
		  }

}