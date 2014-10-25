package control;

import datas.*;
import view.*;

/**
 *
 *  @author A. ROBIN
 *  @version 0.1
 */
public interface Globale{

	/** The main engine of the application */
	public static final KiceoDatas engine = new KiceoDatas();
	
	/** The Main view of the application */
	public static final KiceoView view = new KiceoView(engine);
}