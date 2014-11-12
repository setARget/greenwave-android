package view.activities;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import datas.Utilisateur;
import datas.db.external.didier.GetUtilisateur;
import datas.db.external.didier.GetVersion;
import datas.db.internal.JuniorDAO;

public class FirstLaunch extends FragmentActivity{
	
	private ViewPager vp;
	private PagerAdapter mPagerAdapter;
	private static final int NUM_PAGES = 3;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_firstlaunch);
		this.vp = (ViewPager) this.findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		vp.setAdapter(mPagerAdapter);
	}
 	public void finishTutorial(){
        this.finish();
 	}
	
	public static class First extends Fragment {

        
        @Override
        public void onAttach(Activity activity) {               
                super.onAttach(activity);               
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_first, container, false);
        }
	}
	
	public static class Second extends Fragment {

        
        @Override
        public void onAttach(Activity activity) {               
                super.onAttach(activity);               
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                return inflater.inflate(R.layout.fragment_second, container, false);
        }
	}
	
	public static class Third extends Fragment implements OnClickListener {

		private View v;
		private Button button;
		private Session.StatusCallback callback = new Session.StatusCallback() {
		    @Override
		    public void call(Session session, SessionState state, Exception exception) {
		        onSessionStateChange(session, state, exception);
		    }
		};
			private UiLifecycleHelper uiHelper;
        @Override
        public void onAttach(Activity activity) {               
                super.onAttach(activity);               
        }
        
   	 	@Override
   	 	public void onCreate(Bundle savedInstanceState) {
   	 		super.onCreate(savedInstanceState);
   	 		uiHelper = new UiLifecycleHelper(this.getActivity(), callback);
   	 		uiHelper.onCreate(savedInstanceState);
   	 	}
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
        	
        		this.v = inflater.inflate(R.layout.fragment_third, container, false);
        		LoginButton authButton = (LoginButton) v.findViewById(R.id.authButton);
        		authButton.setFragment(this);
        		authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));
        		
        		button = (Button) v.findViewById(R.id.noLogin);
        		button.setOnClickListener(this);
                return v;
        }
        private void onSessionStateChange(Session session, SessionState state, Exception exception) {
            if (state.isOpened()) {
            	 makeMeRequest(session);
            } else if (state.isClosed()) {
                //Log.i(TAG, "Logged out...");
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
			    this.getActivity().getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean(SplashScreen.PREFS_FB, true).commit();
		 		JuniorDAO dao = new JuniorDAO(v.getContext());
		 		dao.open();
		 		if(dao.findReseaux().size()!=0){
		 			new GetVersion(this.getActivity()).execute();
		 		}
		 		else{
		 			Intent home = new Intent(v.getContext(), SelectionReseau.class);
			  	    startActivity(home);
			  	    FirstLaunch f = (FirstLaunch) this.getActivity();
			  	    f.finishTutorial();
			  	   
		 		}
		    
		} 
	 	
	 	@Override
	 	public void onResume() {
	 	    super.onResume();
	 	    uiHelper.onResume();
	 	}

	 	@Override
	 	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	 	    super.onActivityResult(requestCode, resultCode, data);
	 	    uiHelper.onActivityResult(requestCode, resultCode, data);
	 	}

	 	@Override
	 	public void onPause() {
	 	    super.onPause();
	 	    uiHelper.onPause();
	 	}

	 	@Override
	 	public void onDestroy() {
	 	    super.onDestroy();
	 	    uiHelper.onDestroy();
	 	}

	 	@Override
	 	public void onSaveInstanceState(Bundle outState) {
	 	    super.onSaveInstanceState(outState);
	 	    uiHelper.onSaveInstanceState(outState);
	 	}

	 	private void setFbLogin(boolean fbLogin){
	 		 	SharedPreferences pref = this.getActivity().getSharedPreferences(SplashScreen.PREFS_NAME, 0);
			    pref.edit().putBoolean(SplashScreen.PREFS_FB, fbLogin);
	 	}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			this.setFbLogin(false);
			Intent home = new Intent(v.getContext(), SelectionReseau.class);
	  	    startActivity(home);
	  	    FirstLaunch f = (FirstLaunch) this.getActivity();
	  	    f.finishTutorial();
		}

	 	
	 
	}
	
	/**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	if(position==0){
        		return new First();
        	}
        	else if(position==1){
        		return new Second();
        	}
        	else if(position==2){
        		return new Third();
        	}
        	else{
        		return new First();
        	}

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
