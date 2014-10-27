package view.custom;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wavon.greenwave.R;

import control.Globale;
import datas.Ligne;

/**
 * Provides a list of bus lines
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray
 * @version 0.1
 */
public class RouteList extends ArrayAdapter<String> {

	private final Context context;
	private final ArrayList<String> e;

	public RouteList(Context context, ArrayList<String> array) {
		super(context, R.layout.list_itineraire, array);
		this.context = context;
		e=array;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.list_itineraire, null);
        }
		String s = (String)(e.get(position));
		Log.d("Ligne s", s);
		Ligne l = Globale.engine.getReseau().getLignes().get(s);
		if(l==null){
		//	l = Globale.engine.getEntreprise().getLignesFavorites().get(s);
		}
		TextView nb = (TextView) view.findViewById(R.id.numero);
		nb.setText(l.getNumero());
		nb.setTextColor(Color.WHITE);
		nb.setBackgroundColor(context.getResources().getColor(l.getColor()));

		//
		
		TextView ligne = (TextView) view.findViewById(R.id.ligne);
		ligne.setText(l.toString());
		return view;
	}
}