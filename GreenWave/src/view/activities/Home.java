package view.activities;


import java.util.ArrayList;

import view.custom.DrawerItem;
import view.custom.GreenDrawerAdapter;
import view.fragments.CustomMapFragment;
import view.fragments.LineFragment;
import view.fragments.StopFragment;
import view.fragments.TwitterFragment;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.item.DrawerItemClickListener;
import control.listeners.location.KiceoLocationListener;
import control.listeners.navigation.KiceoFragmentPagerAdapter;
import control.listeners.navigation.KiceoTabListener;
import control.listeners.navigation.SimpleOnChangePageListener;
import control.services.TimeService;
import datas.utility.TravelHelper;


/**
 * The main activity of the program. Home hosts the fragments of the applications.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.2.1
 */
public class Home extends FragmentActivity implements Globale{
	
	private ActionBar actionBar;
	
	private LineFragment line;
	private StopFragment stop;
	private CustomMapFragment map;
	private TwitterFragment twitter;
	
	private Tab lineTab;
	private Tab stopTab;
	private Tab mapTab;
	private Tab twitterTab;
	
	private ViewPager viewPager;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	 
	private final String PREFS_NAME="launch";

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); 	
        MapsInitializer.initialize(getApplicationContext());
        drawerSetup();
        locationSetup();
        initInterface();
        attachReactions();
        
		viewPager.setCurrentItem(Globale.engine.getDefaultFragment());	// Selection du bon fragment
        Log.d("Kiceo - Vannes Agglo", "L'application a démarrée");
        
        startService(new Intent(this, TimeService.class));
    }
    
    private void drawerSetup(){

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        ArrayList<DrawerItem> dataList = new ArrayList<DrawerItem>();
        dataList.add(new DrawerItem("", Globale.engine.getEntreprise().getImage()));
        dataList.add(new DrawerItem("A propos", R.drawable.ic_action_about));
        dataList.add(new DrawerItem("Réglages", R.drawable.ic_action_settings));
        dataList.add(new DrawerItem("Aide", R.drawable.ic_action_help));
       
        // Set the adapter for the list view
        GreenDrawerAdapter adapter = new GreenDrawerAdapter(this, R.layout.green_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener(this));
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_navigation_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle("Ctrl");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle("Options");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }
    
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
 
         // call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
        // then it has handled the app icon touch event
 
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void locationSetup(){
		 LocationManager locationManager = (LocationManager) this
	                .getSystemService(Context.LOCATION_SERVICE);
	        
	        Globale.engine.setLocationManager(locationManager);
	        Criteria criteria = new Criteria();
	        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

	     // Listen for a better provider becoming available.
	        String provider = TravelHelper.getBestProvider(locationManager);
	        if (provider != null){
	        	KiceoLocationListener kll = new KiceoLocationListener(this);
	        	  locationManager.requestLocationUpdates(provider, 0, 0, 
	        			  kll, getMainLooper());
	        	  locationManager.requestSingleUpdate(provider, kll, this.getMainLooper());
	        	  Globale.engine.setProvider(provider);
	        	  Globale.engine.setLocation(locationManager.getLastKnownLocation(provider));      	  
	        }
    }
    /**
     * Initializes the graphical interface of the activity
     */
	private void initInterface() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
        line  = new LineFragment();
        stop = new StopFragment();
        map = new CustomMapFragment();
        twitter = new TwitterFragment();
           
        lineTab = actionBar.newTab();
        lineTab.setIcon(R.drawable.bus_line);
        lineTab.setTag("LIGNE");
        lineTab.setTabListener(new KiceoTabListener(line, this, viewPager)); //'this' because your activity implements a TabListener
        
        stopTab = actionBar.newTab();
        stopTab.setIcon(R.drawable.bus_stop);
        stopTab.setTag("ARRET");
        stopTab.setTabListener(new KiceoTabListener(stop, this, viewPager)); //'this' because your activity implements a TabListener
           
        mapTab = actionBar.newTab();
        mapTab.setIcon(R.drawable.map);
        mapTab.setTag("CARTE");
        mapTab.setTabListener(new KiceoTabListener(map, this, viewPager)); //'this' because your activity implements a TabListener

        twitterTab = actionBar.newTab();
        twitterTab.setIcon(R.drawable.twitter);
        twitterTab.setTag("ACTUALITE");
        twitterTab.setTabListener(new KiceoTabListener(twitter, this, viewPager)); //'this' because your activity implements a TabListener
        
        actionBar.addTab(twitterTab);
        actionBar.addTab(lineTab);
        actionBar.addTab(stopTab);
        actionBar.addTab(mapTab);

        //viewPager.setCurrentItem(3);
        
       // new DatabaseConnection().execute();
	}
	
	/**
	 * Sets the reactions of the control elements
	 */
	private void attachReactions() {
		viewPager.setAdapter(new KiceoFragmentPagerAdapter(getSupportFragmentManager())); 
		viewPager.setOnPageChangeListener(new SimpleOnChangePageListener(actionBar));
	}
	
	/**
	 * Accessor : Returns the view pager of the activity
	 * @see ViewPager
	 * @return ViewPager
	 */
	public ViewPager getViewPager(){return viewPager;}
	
	
	/**
	 * Accessor : Returns the fragments which displays the stop selection
	 * @see Fragment
	 * @return StopFragment
	 */
	public StopFragment getArret(){return stop;}
	
	/**
	 * Accessor : Returns the fragments which displays the map
	 * @see Fragment
	 * @return Carte
	 */
	public CustomMapFragment getCarte(){return map;}
	
	/**
	 * Only for testing
	 * @return datas.KiceoDatas
	 */
	public datas.KiceoDatas getDatas(){return Globale.engine;}
	
	private boolean isFirstLaunch() {
	    // Restore preferences
	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    boolean isFirstLaunch = settings.getBoolean("isFirstLaunch", true);
	    Log.i(".isFirstLaunch", "sharedPreferences ");
	    return isFirstLaunch;
	}
	
}
