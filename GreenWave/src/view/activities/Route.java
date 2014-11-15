package view.activities;


import view.activities.FirstLaunch.First;
import view.activities.FirstLaunch.Second;
import view.activities.FirstLaunch.Third;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.item.LineRouteClickListener;
import datas.utility.CalculerItineraire;


/**
 * The main activity of the program. Home hosts the fragments of the applications.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.2.1
 */
public class Route extends FragmentActivity implements Globale{

	private ViewPager vp;
	private PagerAdapter mPagerAdapter;
	private static final int NUM_PAGES = 2;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        Log.d("Kiceo - Vannes Agglo", "L'application a démarrée");
        this.vp = (ViewPager) this.findViewById(R.id.pager_route);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		vp.setAdapter(mPagerAdapter);
    }
    

	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getActionBar().setDisplayHomeAsUpEnabled(true);
 	   return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){	// actions on "previous" button
        	this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

	
public static class First extends Fragment implements OnItemClickListener {

		private Location destination;
		private ListView list;
		private RelativeLayout layout;
	
        @Override
        public void onAttach(Activity activity) {               
                super.onAttach(activity);               
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                        Bundle savedInstanceState) {
                View v = inflater.inflate(R.layout.fragment_route_left, container, false);
        		list = (ListView) v.findViewById(R.id.list);
        		list.setOnItemClickListener(this);
        		layout = (RelativeLayout) v.findViewById(R.id.loadingPanel);
        		LatLng ll = Globale.engine.getDestination().getPosition();
        		destination = new Location("destination");
        		destination.setLatitude(ll.latitude);
        		destination.setLongitude(ll.longitude);
        		this.prepareRoute();
        		return v;
        }
    	
    	private void prepareRoute(){
   		 new CalculerItineraire(this.getActivity(), layout, list, destination).execute(100000f, 100000f);
    	}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			((Route) this.getActivity()).vp.setCurrentItem(1);
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
                return inflater.inflate(R.layout.fragment_route_right, container, false);
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
