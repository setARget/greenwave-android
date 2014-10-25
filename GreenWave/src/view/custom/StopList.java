package view.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import view.activities.Home;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.wavon.greenwave.R;

import control.Globale;
import datas.Arret;
import datas.Ligne;

/**
 * Provides a list of bus stops
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class StopList extends ArrayAdapter<Arret> {

	private final Context context;
	private ArrayList<Arret> originalList;
	private ArrayList<Arret> items;
	
	private ListFilter filter;
	private final Object mLock = new Object();
	
	public StopList(Context context, ArrayList<Arret> array, Home home) {
		super(context, R.layout.list_arret, array);
		this.context = context;
		items=array;
		Collections.sort(items);
		originalList = new ArrayList<Arret>();
		cloneItems(items);
	}
	
	protected void cloneItems(ArrayList<Arret> items) {
        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
        	Arret a = (Arret) iterator.next();
            originalList.add(a);
        }
    }
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_arret, null);
        }
		Arret a = (Arret)(items.get(position));
		
		String info;
		TextView nom = (TextView) view.findViewById(R.id.nom);
		info=a.toString();
		

		if(Globale.engine.getLocation()!=null){
			int distance = (int)Globale.engine.getLocation().distanceTo(a.getLocation());
			a.setDistance(distance);
			info+=" ("+distance+"m)";
		}
		else{
			info+=" ("+a.getVille()+")";
		}
		nom.setText(info);
		
		if(a.getFavorite()==1){
			view.findViewById(R.id.star).setVisibility(View.VISIBLE);
		}
		else{
			view.findViewById(R.id.star).setVisibility(View.INVISIBLE);
		}

		return view;
	}
	
	public Filter getFilter() {
        if (filter == null) {
            filter = new ListFilter();
        }
        return filter;
    }   
	
	private class ListFilter extends Filter {
        protected FilterResults performFiltering(CharSequence prefix) {
            // Initiate our results object
            FilterResults results = new FilterResults();

            // No prefix is sent to filter by so we're going to send back the original array
            if (prefix == null || prefix.length() == 0) {
                synchronized (mLock) {
                    results.values = originalList;
                    results.count = originalList.size();
                }
            } else {
                synchronized(mLock) {
                        // Compare lower case strings
                    String prefixString = prefix.toString().toLowerCase();
                    final ArrayList<Arret> filteredItems = new ArrayList<Arret>();
                    // Local to here so we're not changing actual array
                    final ArrayList<Arret> localItems = new ArrayList<Arret>();
                    localItems.addAll(originalList);
                    final int count = localItems.size();

                    for (int i = 0; i < count; i++) {
                        final Arret item = localItems.get(i);
                        final String itemName = item.toString().toLowerCase();

                        // First match against the whole, non-splitted value
                        if (itemName.startsWith(prefixString)) {
                            filteredItems.add(item);
                        } else {} /* This is option and taken from the source of ArrayAdapter
                            final String[] words = itemName.split(" ");
                            final int wordCount = words.length;

                            for (int k = 0; k < wordCount; k++) {
                                if (words[k].startsWith(prefixString)) {
                                    newItems.add(item);
                                    break;
                                }
                            }
                        } */
                    }

                    // Set and return
                    results.values = filteredItems;
                    results.count = filteredItems.size();
                }//end synchronized
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence prefix, FilterResults results) {
            //noinspection unchecked
            synchronized(mLock) {
                final ArrayList<Ligne> localItems = (ArrayList<Ligne>) results.values;
                notifyDataSetChanged();
                clear();
                //Add the items back in
                for (Iterator iterator = localItems.iterator(); iterator
                        .hasNext();) {
                    Arret gi = (Arret) iterator.next();
                    add(gi);
                }
            }//end synchronized
        }
    }
}
