package control.listeners.navigation;

import android.app.ActionBar;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * © Copyright 2014 Antoine Sauray
 * Custom OnPageChangeListener for the Kiceo application
 * Sets the right tab chosen by the user using swap moves
 * @author Antoine Sauray
 * @version 0.1
 */
public class SimpleOnChangePageListener implements OnPageChangeListener{
	
	private ActionBar actionBar;
	
	public SimpleOnChangePageListener(ActionBar actionBar){
		this.actionBar=actionBar;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}
    @Override
    public void onPageSelected(int position) {
    	actionBar.setSelectedNavigationItem(position);    
    }
}
