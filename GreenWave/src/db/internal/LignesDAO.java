package db.internal;

import java.util.HashMap;
import java.util.Map;

import datas.Ligne;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author Alexis Robin
 * @version 0.2
 *
 */

public class LignesDAO extends DAOBase{

	// ---------- ATTRIBUTES
	
	private static final String LIGNES_TABLE_NAME = "Lignes";
	private static final String LIGNES_ID = "ID";
	private static final String LIGNES_NBVUES = "NbVues";
	private static final String LIGNES_PREFRANG = "PrefRang";
	private static final String LIGNES_FAVORIS = "Favorite";
	
	
	// ---------- CONSTRUCTOR
	
	public LignesDAO(Context context) {
		super(context);
		
	}
	
	
	// ---------- METHODS
	
	public long add(Ligne ligne){
		if(ligne !=  null){
			ContentValues values = new ContentValues();
			values.put(LIGNES_ID, ligne.getID());
			values.put(LIGNES_NBVUES, ligne.getNbVues());
			values.put(LIGNES_PREFRANG, ligne.getPrefRang());
			values.put(LIGNES_FAVORIS, ligne.getFavorite());
			return db.insert(LIGNES_TABLE_NAME, null, values);
		}
		else{
			return 0;
		}
	} // ------------------------------------------------------------ add(Ligne)
	
	public void synchronisation(HashMap<String, Ligne> tree){
		for (Map.Entry<String, Ligne> entry : tree.entrySet()) {
			if(entry.getValue() !=  null){
				Cursor c = db.query(LIGNES_TABLE_NAME, new String[] {LIGNES_ID, LIGNES_NBVUES, LIGNES_PREFRANG, LIGNES_FAVORIS}, LIGNES_ID + " LIKE \"" + entry.getValue().getID() +"\"", null, null, null, null);
				c.moveToFirst();
				entry.getValue().setNbVues(c.getInt(1));
				entry.getValue().setPrefRang(c.getInt(2));
				entry.getValue().setFavorite(c.getInt(3));
				c.close();	
			}
	    }
	}
	
	public void save(HashMap<String, Ligne> tree){
		ContentValues values = null;
		for (Map.Entry<String, Ligne> entry : tree.entrySet()) {
			if(entry.getValue() !=  null){
				values = new ContentValues();
				values.put(LIGNES_NBVUES, entry.getValue().getNbVues());
				values.put(LIGNES_PREFRANG, entry.getValue().getPrefRang());
				values.put(LIGNES_FAVORIS, entry.getValue().getFavorite());
				db.update(LIGNES_TABLE_NAME, values, LIGNES_ID + " = " + entry.getValue().getID(), null);
			}
		}
	}
}
