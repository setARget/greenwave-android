package view.activities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.LoginActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.MapsInitializer;
import com.wavon.greenwave.R;

import control.Globale;
import datas.Reseau;
import datas.Utilisateur;
import datas.db.external.didier.DownloadFullReseau;
import datas.db.external.didier.GetUtilisateur;
import datas.db.external.didier.GetVersion;
import datas.db.external.didier.InsertUtilisateur;
import datas.db.internal.JuniorDAO;
import datas.db.internal.SetupReseau;
import datas.reseau.Lorient;
import datas.utility.NetworkUtil;

/**
 * SplashScreen is the splashscreen activity. It shows up when the application is started to perform operations in the background.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class SplashScreen extends Activity {
	 
	 // Show the Splash Screen for 3secs(3000ms)
	long START_UP_DELAY = 1500L;
	private boolean isResumed = false;;
	 
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
		private UiLifecycleHelper lifecycleHelper;
			
	  
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  
	  lifecycleHelper = new UiLifecycleHelper(this, callback);
      lifecycleHelper.onCreate(savedInstanceState);
      
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
	  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	                                  WindowManager.LayoutParams.FLAG_FULLSCREEN);

	  setContentView(R.layout.splash);
	  
	  LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
	  authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
        
        if(this.ensureOpenSession()){
        	authButton.setVisibility(View.GONE);
        }

	  Log.d("oncreate", "oncreate");
	 }
	 
	 private boolean ensureOpenSession() {
	        if (Session.getActiveSession() == null ||
	                !Session.getActiveSession().isOpened()) {
	            return false;
	        }
	        return true;
	    }
	 
		private void onSessionStateChange(Session session, SessionState state, Exception exception) {
			Log.d("onSessionStateChange", "onSessionStateChange");
			if (state.isOpened()) {
		        Log.i("", "Logged in...");
		    } else if (state.isClosed()) {
		        Log.i("", "Logged out...");
		    }

		    if (session != null && session.isOpened()) {
		        // Get the user's data.
		        makeMeRequest(session);
		    }
		}
		
	 	private void makeMeRequest(final Session session) {
		    // Make an API call to get user data and define a 
		    // new callback to handle the response.
	 		final SetupReseau t = new SetupReseau(this);
	 		t.start();
		    Request request = Request.newMeRequest(session, 
		            new Request.GraphUserCallback() {
		        @Override
		        public void onCompleted(GraphUser user, Response response) {
		            // If the response is successful
		        	Log.d("onCompleted", "onCompleted");
		            if (session == Session.getActiveSession()) {
		            	Log.d("getActiveSession", "getActiveSession");
		                if (user != null) {
		                	Globale.engine.setUtilisateur(new Utilisateur(user.getId(), user.getLastName(), user.getFirstName()));
		                	GetUtilisateur t2 = new GetUtilisateur(user.getId());
		                	t2.start();
		                	try {
								t2.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		                	Globale.engine.setUtilisateur(t2.getUtilisateur());
		                }
		            }
		            if (response.getError() != null) {
		                // Handle errors, will do so later.
		            	Log.d(response.getError().getErrorMessage(), "error");
		            }
		            try {
						t.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    });
		    request.executeAsync();
		} 
	 	
	 	@Override
	    public void onResume() {
	        super.onResume();
	        isResumed = true;
	        Session session = Session.getActiveSession();
	        if (session != null && (session.isOpened() || session.isClosed())) {
	            onSessionStateChange(session, session.getState(), null);
	        }
	        lifecycleHelper.onResume();
	    }

	    @Override
	    public void onPause() {
	        super.onPause();
	        lifecycleHelper.onPause();
	        isResumed = false;
	    }

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        lifecycleHelper.onActivityResult(requestCode, resultCode, data);
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        lifecycleHelper.onDestroy();
	    }

	    @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        lifecycleHelper.onSaveInstanceState(outState);
	    }

}
