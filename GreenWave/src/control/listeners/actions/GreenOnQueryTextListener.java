package control.listeners.actions;

import android.text.TextUtils;
import android.widget.ListView;
import android.widget.SearchView.OnQueryTextListener;
/**
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 *
 */
public class GreenOnQueryTextListener implements OnQueryTextListener{

	private ListView lv;
	
	public GreenOnQueryTextListener(ListView lv){
		this.lv=lv;
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		// this is your adapter that will be filtered
	      if (TextUtils.isEmpty(newText))
	      {
	            lv.clearTextFilter();
	        }
	      else
	      {
	            lv.setFilterText(newText.toString());
	        }
	         
	      
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
