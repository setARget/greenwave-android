package view.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.wavon.greenwave.R;

import control.Globale;
import datas.KiceoDatas;
import datas.Utilisateur;
import datas.db.external.didier.GetUtilisateur;
import datas.db.external.didier.GetVersion;
import datas.db.internal.JuniorDAO;

/**
 * SplashScreen is the splashscreen activity. It shows up when the application is started to perform operations in the background.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class SplashScreen extends Activity{
	 
	 // Show the Splash Screen for 3secs(3000ms)
	long START_UP_DELAY = 1500L;
	private boolean isResumed = false;
	public static final String PREFS_NAME="gwpref";
	public static final String PREFS_FB="fblogin";
	private final String PREFS_FL="firstLaunch";
	 
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
	  
	  ImageView img = (ImageView)findViewById(R.id.animation_vague);
	  img.setBackgroundResource(R.drawable.wave_anim);
	  AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();
	  frameAnimation.start();
	 
	  
	  Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isfirstrun", true);
      if (isFirstRun) {
    	  getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isfirstrun", false).commit();
    	  Intent intent = new Intent(SplashScreen.this, FirstLaunch.class);
	  	    startActivity(intent);
	  	    this.finishSplashSreen();
      }
      else if(!getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean(PREFS_FB, false)){
    	  JuniorDAO dao = new JuniorDAO(this);
	 		dao.open();
	 		if(dao.findReseaux().size()!=0){
	 			dao.close();
	 			new GetVersion(this).execute();
	 		}
	 		else{
	 			Intent home = new Intent(SplashScreen.this, SelectionReseau.class);
		  	    startActivity(home);
		  	    this.finishSplashSreen();
		  	   
	 		}
      }
	  Log.d("oncreate", "oncreate");
	 }
	 
	 public static boolean ensureOpenSession() {
	        if (Session.getActiveSession() == null ||
	                !Session.getActiveSession().isOpened()) {
	            return false;
	        }
	        return true;
	    }
	 
		private void onSessionStateChange(Session session, SessionState state, Exception exception) {
			Log.d("onSessionStateChange", "onSessionStateChange");
		    if (session != null && session.isOpened()) {
		        // Get the user's data.
		        makeMeRequest(session);
		    }
		    else{
            	SharedPreferences settings = getSharedPreferences(KiceoDatas.PREFS_NAME, 0);
            	settings.edit().putBoolean("fb_logged", false);
		    }
		}
		
	 	private void makeMeRequest(final Session session) {
		    // Make an API call to get user data and define a 
		    // new callback to handle the response.
		    Request request = Request.newMeRequest(session, 
		            new Request.GraphUserCallback() {
		        @Override
		        public void onCompleted(GraphUser user, Response response) {
		            // If the response is successful
		        	Log.d("onCompleted", "onCompleted");
		            if (session == Session.getActiveSession()) {
		            	Log.d("getActiveSession", "getActiveSession");
		                if (user != null) {		                	
		                	Utilisateur u = new Utilisateur(user.getId(), user.getLastName(), user.getFirstName());
		                	Globale.engine.setUtilisateur(u);
		                	SharedPreferences settings = getSharedPreferences(KiceoDatas.PREFS_NAME, 0);
		                	settings.edit().putBoolean("fb_logged", true);
		                	GetUtilisateur t2 = new GetUtilisateur(user.getId());
		                	t2.start();
		                }
		                else{
		                	Log.d("user error", "user error");
		                }
		            }
		            if (response.getError() != null) {
		                // Handle errors, will do so later.
		            	Log.d(response.getError().getErrorMessage(), "error");
		            }
		        }
		    });
			    request.executeAsync();
			    
		 		JuniorDAO dao = new JuniorDAO(this);
		 		dao.open();
		 		if(dao.findReseaux().size()!=0){
		 			new GetVersion(this).execute();
		 		}
		 		else{
		 			Intent home = new Intent(SplashScreen.this, SelectionReseau.class);
			  	    startActivity(home);
			  	    this.finishSplashSreen();
			  	   
		 		}
		    
		} 
	 	
	 	
	 	public void finishSplashSreen(){
	        this.finish();
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
