package view.fragments;

import java.util.ArrayList;
import java.util.Collections;

import view.activities.Home;
import view.custom.StopList;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.actions.GreenOnQueryTextListener;
import control.listeners.button.NearestStopOnClickListener;
import control.listeners.item.StopClickListener;
import datas.Arret;
import datas.Ligne;
import datas.database.arret.ArretsDAO;

/**
 * StopFragment is a Fragment which displays a list of bus stops.
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class StopFragment extends Fragment{

private View v;

private ListView list;
private StopList arretAdapter;

private ImageButton nearest;

private Home home;	// Current activity

	public static StopFragment newInstance(String chaine) {
		StopFragment fragment = new StopFragment();
	    Bundle args = new Bundle();
	    args.putString("ARRET", chaine);
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
		v = inflater.inflate(R.layout.fragment_arret, container, false); 
		home = (Home) this.getActivity();
    	setHasOptionsMenu(true);
		initInterface();
		attachReactions();
		return v;
	 }

  @Override
   	public void onResume() {
	  super.onResume();
		 
	  	Log.d("OnResume()", "Arret");
		 
		Ligne l = Globale.engine.getLigneCourante();
		// affichage de la ligne en haut
		if(l != null){
			
			v.findViewById(R.id.pasDeLigne).setVisibility(View.INVISIBLE);
			
			TextView nb = (TextView) v.findViewById(R.id.numero);
			nb.setText(" "+l.getNumero()+" ");
			nb.setTextColor(Color.WHITE);
			nb.setBackgroundColor(home.getResources().getColor(l.getColor()));
			
			TextView sens1 = (TextView) v.findViewById(R.id.sens1);
			sens1.setText(l.getAller());
			
			TextView sens2 = (TextView) v.findViewById(R.id.sens2);
			sens2.setText(l.getRetour());
			
			if(Globale.engine.getLocation() != null){
				v.findViewById(R.id.nearest).setVisibility(View.VISIBLE);
			}
			else{
				v.findViewById(R.id.nearest).setVisibility(View.GONE);
			}
			
			if(l.getFavorite()==1){
				v.findViewById(R.id.star).setVisibility(View.VISIBLE);
			}
			else{
				v.findViewById(R.id.star).setVisibility(View.GONE);
			}
			
			arretAdapter = new StopList(getActivity(), new ArrayList<Arret>(Globale.engine.getLigneCourante().getArrets().values()), home);
			list.setAdapter(arretAdapter);
			list.setOnItemClickListener(new StopClickListener(home));
			nearest.setOnClickListener(new NearestStopOnClickListener(home));
			
		    list.invalidate();
		}
   }
  
	 @Override
	 public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {	 
		  inflater.inflate(R.menu.stop_menu, menu);
		   	
		  MenuItem searchMenuItem = menu.findItem(R.id.action_search);
		  SearchManager searchManager = (SearchManager) getActivity().getSystemService( Context.SEARCH_SERVICE );
		  SearchView search = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
	
		  search.setSearchableInfo(searchManager.getSearchableInfo(home.getComponentName()));

		  	int id = search.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		  	TextView textView = (TextView) search.findViewById(id);
		  	textView.setTextColor(Color.WHITE);
		  	textView.setHintTextColor(Color.WHITE);
		  
		  search.setOnQueryTextListener(new GreenOnQueryTextListener(list));
		    
		  super.onCreateOptionsMenu(menu, inflater);
	 }
   
   @Override
   	public void setUserVisibleHint(boolean visible)
   {
       super.setUserVisibleHint(visible);
       if (visible && isResumed())
       {
           //Only manually call onResume if fragment is already visible
           //Otherwise allow natural fragment lifecycle to call onResume
           onResume();
       }
   }
	
   	private void initInterface(){
		list = (ListView) v.findViewById(R.id.listArret);
		list.setTextFilterEnabled(true);
		nearest = (ImageButton) v.findViewById(R.id.nearest);
	}

	private void attachReactions(){
		
	}
	
	public ListView getList(){return list;}

	public String toString(){
		return "Arrets";
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {
				super.onCreateContextMenu(menu, v, menuInfo);
			    AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
				Arret a = (Arret) list.getItemAtPosition(info.position);
			    menu.setHeaderTitle(a.toString());
				if(a.getFavorite()==0)
				menu.add("Ajouter aux favoris");
				else
					menu.add("Retirer des favoris");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (getUserVisibleHint()) {
	        // context menu logic
			  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
			            .getMenuInfo();
			    Arret a = (Arret) list.getItemAtPosition(info.position);
	    
			    ArrayList<Arret> arrets = new ArrayList<Arret>(Globale.engine.getLigneCourante().getArrets().values());
			    if(a.getFavorite()==0){
					    a.setFavorite(1);            
			    }
			    else{
			    	a.setFavorite(0);
			    }
				Collections.sort(arrets);
					    

				arretAdapter = new StopList(v.getContext(), arrets, home);
				//arretAdapter.addAll(arrets);
				list.removeAllViewsInLayout();
				list.setAdapter(arretAdapter);
			    list.invalidate();
			    
			    Thread t = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						ArretsDAO dao = new ArretsDAO(home);
			    		dao.open();
			    		dao.save( Globale.engine.getLigneCourante().getArrets());
			    		//dao.save(Globale.engine.getEntreprise().getArretsFavoris());
			    		dao.close();
					}
			    	
			    });
			    t.start();


	        return true;
	    }
	    return false;
	  
	}
}