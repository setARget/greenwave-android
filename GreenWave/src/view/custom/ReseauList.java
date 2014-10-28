package view.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import view.activities.Home;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.wavon.greenwave.R;

import datas.Reseau;
import datas.Reseau;
import db.external.didier.GetReseaux;

/**
 * Provides a list of bus lines
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class ReseauList extends ArrayAdapter<Reseau>{

	private final Context context;
	private ArrayList<Reseau> originalList;
	private ArrayList<Reseau> items;
	
	private ListFilter filter;
	private final Object mLock = new Object();

	public ReseauList(Context context, ArrayList<Reseau> array) {
		super(context, R.layout.list_reseau, array);
		this.context = context;
		originalList = new ArrayList<Reseau>();
		items=array;
		Collections.sort(items);
		cloneItems(array);
	}
	
	protected void cloneItems(ArrayList<Reseau> items) {
        for (Iterator<Reseau> iterator = items.iterator(); iterator.hasNext();) {
        	Reseau r = (Reseau) iterator.next();
            originalList.add(r);
        }
    }
	
	@SuppressLint("InflateParams") @Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_reseau, null);
        }
		Reseau l = (Reseau)(items.get(position));
		
		
		
		TextView nb = (TextView) view.findViewById(R.id.numero);
		nb.setText(l.getNom());
		nb.setTextColor(Color.WHITE);
		//nb.setBackgroundColor(home.getResources().getColor(l.getColor()));

		//
		
		return view;
	}
	
	public Filter getFilter() {
        if (filter == null) {
            filter = new ListFilter();
        }
        return filter;
    }   
	
	private class ListFilter extends Filter {
        @SuppressLint("DefaultLocale") protected FilterResults performFiltering(CharSequence prefix) {
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
                    final ArrayList<Reseau> filteredItems = new ArrayList<Reseau>();
                    // Local to here so we're not changing actual array
                    final ArrayList<Reseau> localItems = new ArrayList<Reseau>();
                    localItems.addAll(originalList);
                    final int count = localItems.size();

                    for (int i = 0; i < count; i++) {
                        final Reseau item = localItems.get(i);
                        final String itemName = item.getNom().toLowerCase();

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
                final ArrayList<Reseau> localItems = (ArrayList<Reseau>) results.values;
                notifyDataSetChanged();
                clear();
                //Add the items back in
                for (Iterator<Reseau> iterator = localItems.iterator(); iterator
                        .hasNext();) {
                    Reseau r = iterator.next();
                    add(r);
                }
            }//end synchronized
        }
    }
}