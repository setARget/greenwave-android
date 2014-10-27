package db.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import datas.Arret;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author Alexis Robin
 * @version 0.2
 */
public class ArretsDAO extends DAOBase{

	// ---------- ATTRIBUTES
	
	private static final String ARRETS_TABLE_NAME = "Arrets";
	private static final String ARRETS_ID = "ID";
	private static final String ARRETS_NBVUES = "NbVues";
	private static final String ARRETS_PREFRANG = "PrefRang";
	private static final String ARRETS_FAVORIS = "Favorite";
	
	
	
	// ---------- CONSTRUCTOR
	
	public ArretsDAO(Context context) {
		super(context);
	}
	
	
	// ---------- METHODS
	
	public long add(Arret arret){
		if(arret !=  null){
			ContentValues values = new ContentValues();
			values.put(ARRETS_ID, arret.getIdBdd());
			values.put(ARRETS_NBVUES, arret.getNbVues());
			values.put(ARRETS_PREFRANG, arret.getPrefRang());
			values.put(ARRETS_FAVORIS, arret.getFavorite());
			return db.insert(ARRETS_TABLE_NAME, null, values);
		}
		else{
			return 0;
		}
	} // ------------------------------------------------------------ add(Ligne)
	
	public void synchronisation(HashMap<String, Arret> tree){
		for (Map.Entry<String, Arret> entry : tree.entrySet()) {
			if(entry.getValue() !=  null){
				Cursor c = db.query(ARRETS_TABLE_NAME, new String[] {ARRETS_ID, ARRETS_NBVUES, ARRETS_PREFRANG, ARRETS_FAVORIS}, ARRETS_ID + " LIKE \"" + entry.getValue().getIdBdd() +"\"", null, null, null, null);
				c.moveToFirst();
				entry.getValue().setNbVues(c.getInt(1));
				entry.getValue().setPrefRang(c.getInt(2));
				entry.getValue().setFavorite(c.getInt(3));
				c.close();	
			}
	    }
	}
	
	public void save(HashMap<String, Arret> tree){
		ContentValues values = null;
		for (Map.Entry<String, Arret> entry : tree.entrySet()) {
			if(entry.getValue() !=  null){
				values = new ContentValues();
				values.put(ARRETS_NBVUES, entry.getValue().getNbVues());
				values.put(ARRETS_PREFRANG, entry.getValue().getPrefRang());
				values.put(ARRETS_FAVORIS, entry.getValue().getFavorite());
				db.update(ARRETS_TABLE_NAME, values, ARRETS_ID + " = " + entry.getValue().getIdBdd(), null);
			}
		}
	}
	
}
