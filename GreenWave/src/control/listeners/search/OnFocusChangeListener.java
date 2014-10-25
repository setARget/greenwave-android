package control.listeners.search;

import java.util.concurrent.ExecutionException;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.wavon.greenwave.R;

import control.Globale;
import control.asynctasks.AutoCompletion;
import control.asynctasks.GetSpecificPlace;
import datas.Ligne;
import datas.Place;

import view.activities.Home;
import view.fragments.CustomMapFragment;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Toast;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SearchView.OnSuggestionListener;
import android.widget.TextView;

public class OnFocusChangeListener implements android.view.View.OnFocusChangeListener, OnQueryTextListener, OnSuggestionListener{

	private Home home;
	private android.widget.SearchView search;
	private CursorAdapter c;
	private TextView text;
	private Cursor cursor;
	
	public OnFocusChangeListener(android.widget.SearchView search, Home home){
		this.home=home;
		this.search=search;
		c = new CursorAdapter(home, null, 0){


			@Override
			public void bindView(View view, Context context, Cursor cursor) {
				// TODO Auto-generated method stub
				
					    TextView name = (TextView)view.findViewById(R.id.text);               
					    name.setText(cursor.getString(1));
			}

			@Override
			public View newView(Context context, Cursor cursor,
					ViewGroup parent) {
				LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			    View retView = inflater.inflate(R.layout.item,parent,false);
			    return retView;
			}
			
		};
		search.setSuggestionsAdapter(c);
	}
	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		//
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		try {
			cursor=new AutoCompletion().execute(newText).get();
			c.changeCursor(cursor);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean onSuggestionSelect(int position) {
		// TODO Auto-generated method stub
		cursor.moveToPosition(position);
		Place p = Globale.engine.getPlacesMap().get(cursor.getString(1));
		new GetSpecificPlace(home).execute(p);
		return false;
	}

	@Override
	public boolean onSuggestionClick(int position) {
		// TODO Auto-generated method stub
		cursor.moveToPosition(position);
		Place p = Globale.engine.getPlacesMap().get(cursor.getString(1));
		new GetSpecificPlace(home).execute(p);
		return false;
	}


}
