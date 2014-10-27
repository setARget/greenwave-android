package db.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * @author Alexis Robin
 * @version 0.2
 */
public abstract class DAOBase {
	
	  // ---------- ATTRIBUTES
	
	  protected final static int VERSION = 1;
	  protected final static String NOM = "ctrl_test2.db";
	  protected SQLiteDatabase db;
	  protected DatabaseHandler handler;
	  
	  
	  // ---------- CONSTRUCTOR
	  
	  public DAOBase(Context context) {
	    this.handler = new DatabaseHandler(context, NOM, null, VERSION);
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
}