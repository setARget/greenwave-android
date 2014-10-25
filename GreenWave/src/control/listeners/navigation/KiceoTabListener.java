package control.listeners.navigation;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom TabListener for the Kiceo Application
 * Sets the right tab selected by the user
 * @author Antoine Sauray
 * @version 0.1
 */
public class KiceoTabListener implements TabListener {
	private ViewPager vp;
	
	public KiceoTabListener(Fragment fragment, Activity a, ViewPager vp) {
		this.vp=vp;
	}
	
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        vp.setCurrentItem(tab.getPosition()); 
    }
	
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		//ft.remove(fragment);
	}
	
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// nothing done here
	}
}
