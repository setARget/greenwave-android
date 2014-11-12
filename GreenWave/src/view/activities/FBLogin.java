package view.activities;

import java.util.Arrays;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.wavon.greenwave.R;

import control.Globale;
import datas.KiceoDatas;
import datas.Utilisateur;
import datas.db.external.didier.GetUtilisateur;

public class FBLogin extends FragmentActivity{
	
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

		  setContentView(R.layout.fragment_third);
		  
		  LoginButton authButton = (LoginButton) findViewById(R.id.authButton);
		  authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));

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
			                	try {
									t2.join();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
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
			    this.getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean(SplashScreen.PREFS_FB, true).commit();
			    Intent home = new Intent(FBLogin.this, Home.class);
		  	    startActivity(home);
		  	    this.finish();
			} 
		 	
		 	
		 	@Override
		    public void onResume() {
		        super.onResume();
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
