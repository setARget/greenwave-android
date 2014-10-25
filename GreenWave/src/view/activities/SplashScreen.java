package view.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.MapsInitializer;
import com.wavon.greenwave.R;

import control.Globale;

/**
 * SplashScreen is the splashscreen activity. It shows up when the application is started to perform operations in the background.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class SplashScreen extends Activity {
	 
	 // Show the Splash Screen for 3secs(3000ms)
	 long START_UP_DELAY = 1500L;
	  
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
	  setContentView(R.layout.splash);
	  int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
	    if(status == ConnectionResult.SUCCESS) {
	        //Success! Do what you want
	    	setup();
	  	   
	  	  // Add delay so that Splash Screen is displayed for 3secs
	  	  new Handler().postDelayed(new Runnable() {
	  	   @Override
	  	   public void run() {
	  	 
	  	    Intent loginPage = new Intent(SplashScreen.this, Home.class);
	  	    startActivity(loginPage);
	  	   }
	  	  }, START_UP_DELAY);
	  	  
	    }else if(status == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED){
	    	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.gms")));
	    }
	    else{
	    	
	    }
      
	 }
	  
	 // Override the onPause method to kill this Activity, so that pressing the back button doesn't bring up this screen.
	 @Override
	 protected void onPause() {
	  // TODO Auto-generated method stub
	  super.onPause();
	  this.finish();
	 }
	 
	 /**
	  * Sets up the localisation and the current company selected.
	  */
	 private void setup(){
		 	MapsInitializer.initialize(getApplicationContext());
			//Globale.engine.setEntreprise(Globale.engine.getEntreprise("CTRL"), this);	// Choix de la companie de transport
		 	Globale.engine.setEntreprise(Globale.engine.getEntreprise("CTRL"), this);
	 }
}
