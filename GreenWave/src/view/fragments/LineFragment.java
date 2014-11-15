package view.fragments;

import java.util.ArrayList;
import java.util.Collections;

import view.activities.Home;
import view.custom.LineList;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SearchViewCompat;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.actions.GreenOnQueryTextListener;
import control.listeners.item.LineClickListener;
import datas.Ligne;
import datas.db.internal.JuniorDAO;

/**
 * LineFragment is a Fragment Object which shows up a list where you can select a bus line.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class LineFragment extends Fragment{
	
	private View v;
	private ListView list;
	private LineList listAdapter;
	private Home home;	// Current activity

	public static LineFragment  newInstance(String chaine) {
		LineFragment  fragment = new LineFragment ();
	    Bundle args = new Bundle();
	    args.putString("LIGNE", chaine);
	    fragment.setArguments(args);
	    return fragment;
	}
	
	@Override
	public void onActivityCreated(Bundle savedState) {
	    super.onActivityCreated(savedState);
		registerForContextMenu(list);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_ligne, container, false); 
		home = (Home) this.getActivity();
		initInterface();
		attachReactions();
    	setHasOptionsMenu(true);
		return v;
	}
	 
	@Override
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	 
  	   	inflater.inflate(R.menu.line_menu, menu);
  	   	
  	  MenuItem searchMenuItem = menu.findItem(R.id.action_search);
  	  
  	  SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );
  	  SearchView search = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
  	  SearchViewCompat.setInputType(search, InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);


  	search.setSearchableInfo(searchManager.getSearchableInfo(home.getComponentName()));
  	//search.setSubmitButtonEnabled(true);


  	int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
  	TextView textView = (TextView) search.findViewById(id);
  	textView.setTextColor(Color.WHITE);
  	textView.setHintTextColor(Color.WHITE);
  	
  	search.setOnQueryTextListener(new GreenOnQueryTextListener(list));
  	    
  	super.onCreateOptionsMenu(menu, inflater);
	 }
	@Override
	public void onResume() {
		super.onResume();
		Log.d("OnResume()", "Ligne");
	}
	   
	@Override
	public void setUserVisibleHint(boolean visible){
		super.setUserVisibleHint(visible);
		if (visible && isResumed()){
			//Only manually call onResume if fragment is already visible
			//Otherwise allow natural fragment lifecycle to call onResume
			onResume();
		}
	}
	
	/**
	 * Initializes the graphical interface of the fragment.
	 */
	private void initInterface(){
		list = (ListView) v.findViewById(R.id.list);
		list.setTextFilterEnabled(true);
	}
	
	/**
	 * Sets the reactions of the control elements
	 */
	private void attachReactions(){
		ArrayList<Ligne> lignes = new ArrayList<Ligne>(Globale.engine.getReseau().getLignes().values());
		listAdapter = new LineList(getActivity(), lignes);
		list.setAdapter(listAdapter);
		list.setOnItemClickListener(new LineClickListener(home));
	}
	
	/**
	 * Accessor 
	 * @return ListView the list which contains the bus lines.
	 */
	public ListView getList(){return list;}
	
	/**
	 * @return String the String which describes the Object
	 */
	public String toString(){
		return "Ligne";
	}
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
				super.onCreateContextMenu(menu, v, menuInfo);
	    
			    menu.setHeaderTitle("Gestion des Favoris");
			    AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
				Ligne l = (Ligne) list.getItemAtPosition(info.position);
				if(!l.getFavorite())
				menu.add("Ajouter "+l.toString()+" aux favoris");
				else
					menu.add("Retirer "+l.toString()+" des favoris");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (getUserVisibleHint()) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
	            .getMenuInfo();
	    final Ligne l = (Ligne) list.getItemAtPosition(info.position);
	    ArrayList<Ligne> lignes = new ArrayList<Ligne>(Globale.engine.getReseau().getLignes().values());
	    if(!l.getFavorite()){
		    l.setFavorite(1);
		    Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					JuniorDAO dao = new JuniorDAO(home);
					dao.open();
					dao.setLigneFavoris(l.getIdBdd());
		    		dao.close();
				}
		    	
		    });
		    t.start();
		    
		}
		else{
			l.setFavorite(0);
		    Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					JuniorDAO dao = new JuniorDAO(home);
					dao.open();
					dao.setLigneNotFavoris(l.getIdBdd());
		    		dao.close();
				}
		    	
		    });
		    t.start();
		    
		}
	    Collections.sort(lignes);
	    listAdapter = new LineList(v.getContext(), lignes);
	    list.setAdapter(listAdapter);
	    list.invalidate();

		
	    return true;
	}
		
		return false;
	}
	
}
