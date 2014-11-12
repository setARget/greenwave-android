package datas.db.internal;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;




import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * 
 * @author Alexis Robin
 * @version 0.2
 */
public class Junior extends SQLiteOpenHelper {

	
  // ---------- BASE DE DONNES
	
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
  

  // ---------- TABLES CREATION
  private static final String RESEAU_CREATE = "CREATE TABLE " + RESEAU + "("
		  + RESEAU_ID + " INTEGER PRIMARY KEY, "
		  + RESEAU_NOM + " TEXT NOT NULL DEFAULT 'unknown',"
		  + RESEAU_TWITTER + " TEXT DEFAULT NULL," 
		  + RESEAU_IMAGE + " TEXT DEFAULT NULL,"
		  + RESEAU_VERSION + " INTEGER NOT NULL"
		  + ");";
  
  private static final String LIGNE_CREATE = "CREATE TABLE " + LIGNE + "("
		  + LIGNE_ID + " INTEGER PRIMARY KEY, "
		  + LIGNE_NOM + " TEXT NOT NULL DEFAULT 'unknown',"
		  + LIGNE_DIRECTION1 + " TEXT NOT NULL,"
		  + LIGNE_DIRECTION2 + " TEXT NOT NULL,"
		  + LIGNE_COULEUR + " INTEGER DEFAULT 0, "
		  + LIGNE_RESEAU + " INTEGER NOT NULL CONSTRAINT fkligne_reseau REFERENCES "+ RESEAU +" (id), "
		  + LIGNE_FAVORIS + " INTEGER DEFAULT 0"
		  + ");";
  
  private static final String ARRET_CREATE = "CREATE TABLE " + ARRET + "("
		  + ARRET_ID + " INTEGER PRIMARY KEY, "
		  + ARRET_NOM + " TEXT NOT NULL DEFAULT 'unknown',"
		  + ARRET_LATITUDE + " REAL NOT NULL,"
		  + ARRET_LONGITUDE + " REAL NOT NULL,"
		  + ARRET_RESEAU + " INTEGER NOT NULL CONSTRAINT fkarret_reseau REFERENCES "+ RESEAU +" (id), "
		  + ARRET_FAVORIS + " INTEGER DEFAULT 0"
		  + ");";
  
  private static final String APPARTIENT_CREATE = "CREATE TABLE " + APPARTIENT + "("
		  + APPARTIENT_LIGNE + " INTEGER NOT NULL CONSTRAINT fkappartient_ligne REFERENCES "+ LIGNE +" (id),"
		  + APPARTIENT_ARRET + " INTEGER NOT NULL CONSTRAINT fkappartient_arret REFERENCES "+ ARRET +" (id),"
		  + "PRIMARY KEY (" + APPARTIENT_LIGNE + "," + APPARTIENT_ARRET + ")"
		  + ");";
  
  private static final String HORAIRE_CREATE = "CREATE TABLE " + HORAIRE + "("
		  + HORAIRE_ID + " INTEGER PRIMARY KEY, "
		  + HORAIRE_HORAIRE + " TEXT NOT NULL,"
		  + HORAIRE_CALENDRIER + " TEXT NOT NULL,"
		  + HORAIRE_ARRET + " INTEGER NOT NULL CONSTRAINT fkhoraire_arret REFERENCES "+ ARRET +" (id),"
		  + HORAIRE_LIGNE + " INTEGER NOT NULL CONSTRAINT fkhoraire_ligne REFERENCES "+ LIGNE +" (id),"
		  + HORAIRE_DIRECTION + " INTEGER NOT NULL"
		  + ");";
  
  

  // ---------- CONSTRUCTOR

  public Junior(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

  
  // ---------- METHODS
  
  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(RESEAU_CREATE);
    database.execSQL(LIGNE_CREATE);
    database.execSQL(ARRET_CREATE);
    database.execSQL(APPARTIENT_CREATE);
    database.execSQL(HORAIRE_CREATE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    Log.w(Junior.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + RESEAU);
    db.execSQL("DROP TABLE IF EXISTS " + LIGNE);
    db.execSQL("DROP TABLE IF EXISTS " + ARRET);
    db.execSQL("DROP TABLE IF EXISTS " + APPARTIENT);
    db.execSQL("DROP TABLE IF EXISTS " + HORAIRE);
    onCreate(db);
  }

} 