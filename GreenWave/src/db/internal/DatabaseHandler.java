package db.internal;


import datas.Arret;
import datas.Ligne;

import android.content.ContentValues;
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
public class DatabaseHandler extends SQLiteOpenHelper {
		 
		// ----------- ATTRIBUTES
	
		// ----- TABLE LIGNES
		private static final String LIGNES_TABLE_NAME = "Lignes";
		private static final String LIGNES_ID = "ID";
		private static final String LIGNES_NBVUES = "NbVues";
		private static final String LIGNES_PREFRANG = "PrefRang";
		private static final String LIGNES_FAVORIS = "Favorite";
		private static final String LIGNES_TABLE_CREATE = "CREATE TABLE " + LIGNES_TABLE_NAME + " ("
		+ LIGNES_ID + " INTEGER PRIMARY KEY, " + LIGNES_NBVUES + " INTEGER, " + LIGNES_PREFRANG 
		+ " INTEGER, " + LIGNES_FAVORIS + " INTEGER);";
		private static final String LIGNES_TABLE_DROP = "DROP TABLE IF EXISTS " + LIGNES_TABLE_NAME + ";";
		
		
		// ----- TABLE ARRETS
		
		private static final String ARRETS_TABLE_NAME = "Arrets";
		private static final String ARRETS_ID = "ID";
		private static final String ARRETS_NBVUES = "NbVues";
		private static final String ARRETS_PREFRANG = "PrefRang";
		private static final String ARRETS_FAVORIS = "Favorite";
		private static final String ARRETS_TABLE_CREATE = "CREATE TABLE " + ARRETS_TABLE_NAME + " ("
				+ ARRETS_ID + " INTEGER PRIMARY KEY, " + ARRETS_NBVUES + " INTEGER, " 
				+ ARRETS_PREFRANG + " INTEGER, " + ARRETS_FAVORIS + " INTEGER);";
		private static final String ARRETS_TABLE_DROP = "DROP TABLE IF EXISTS " + ARRETS_TABLE_NAME + ";";
		
	 
		
		// ---------- CONSTRUCTOR
		
		public DatabaseHandler(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
	 
		
		// ---------- METHODS
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(LIGNES_TABLE_CREATE);
			db.execSQL(ARRETS_TABLE_CREATE);
			initializeTables(db);
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(LIGNES_TABLE_DROP);
			db.execSQL(ARRETS_TABLE_DROP);
			onCreate(db);
		}
		
		public void initializeTables(SQLiteDatabase db){
			Log.d("nbLa: ", "" + Ligne.getNbLignes());
			
			for (int i = 0; i < Ligne.getNbLignes(); i++) {
				ContentValues values = new ContentValues();
				values.put(LIGNES_ID, i);
				values.put(LIGNES_NBVUES, 0);
				values.put(LIGNES_PREFRANG, 0);
				values.put(LIGNES_FAVORIS, 0);
				db.insert(LIGNES_TABLE_NAME, null, values);
				Log.d("nb: ", "" + i);
			}

			for (int j = 0; j <Arret.getNbArrets(); j++) {
				ContentValues values = new ContentValues();
				values.put(ARRETS_ID, j);
				values.put(ARRETS_NBVUES, 0);
				values.put(ARRETS_PREFRANG, 0);
				values.put(ARRETS_FAVORIS, 0);
				db.insert(ARRETS_TABLE_NAME, null, values);
			}
		}
}
