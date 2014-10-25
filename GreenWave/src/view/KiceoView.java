package view;

import java.util.Hashtable;
import view.activities.*;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import control.KiceoControl;

import datas.KiceoDatas;

/**
 *
 *  @author A. ROBIN
 *  @version 0.1
 */
public class KiceoView{
    
    // ---------- Attributes

    /** intent object */
    private Intent intent;
    
    private ViewPager vp;

    /** the list of all the states of the application */
    private Hashtable<String, Activity> activityList;


    // ---------- Constructor

    /**
     * Construction of the MEF IHM.
     *
     * @param anEngine link to the MEF datas
     */
    public KiceoView ( KiceoDatas theEngine ) {
        activityList = new Hashtable<String, Activity>();
    } // ------------------------------------------------------------- MefView()
    
    // ---------- Methods

    // ------ Accessors
    /** 
     *  Get the state which links to the speciefied identifier
     *
     * @param identifier The key of the state wishes
     * @return the states wishes
     */
    public Activity getActivity(String identifier){
        return activityList.get(identifier);
    } // ----------------------------------------------------------- getState()


    /**
    * Set the current State, and change the main JPanel
    *
    * @param identifier The State's key of the new State
    */
    public void setCurrentActivity(String identifier, View v){
    	intent = new Intent(v.getContext(), getActivity(identifier).getClass());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		v.getContext().startActivity(intent);
		Log.d("Ctrl","New activity : "+getActivity(identifier).toString());
    } // ------------------------------------------------------------- setCurrentState()


    // ----- Other Methods

    /**
    * Initializes all the states of the application and starts the first one
    */
    public void init(KiceoControl ctrlControl){
        Home home = new Home();     
        activityList.put("HOME", home);
    } // ------------------------------------------------------------ init()
    
    public void setViewPager(ViewPager vp){this.vp = vp;}
    
    public ViewPager getViewPager(){return vp;}
}