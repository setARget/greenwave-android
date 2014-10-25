package control.listeners.navigation;

import view.fragments.CustomMapFragment;
import view.fragments.LineFragment;
import view.fragments.StopFragment;
import view.fragments.TwitterFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom FragmentPagerAdapter for the Kiceo application
 * Returns the right fragment selected by the user
 * @author Antoine Sauray
 * @version 0.1
 */
public class KiceoFragmentPagerAdapter extends FragmentPagerAdapter{
	
	private final int PAGE_COUNT = 4;

        public KiceoFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
        }


        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {  	
        	
        	switch (position) {
            case 0:
                // Top Rated fragment activity
            	return new TwitterFragment();
            case 1:
                // Games fragment activity
            	return new LineFragment();
            case 2:
                // Movies fragment activity
            	return new StopFragment();
            case 3:
                // Movies fragment activity
                return new CustomMapFragment();
            }
     
            return null;
        }
 }


