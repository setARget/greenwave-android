package view.activities;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;

import com.wavon.greenwave.R;

import datas.Reseau;
import datas.db.internal.JuniorDAO;

public class Preferences extends PreferenceActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 getFragmentManager().beginTransaction().replace(android.R.id.content, new InnerPreferenceFragment()).commit();
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getActionBar().setDisplayHomeAsUpEnabled(true);
 	   return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){	// actions on "previous" button
        	startActivity(new Intent(this, Home.class));
        }
        return super.onOptionsItemSelected(item);
    }
	
	public static class InnerPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener{
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference connectionPref = findPreference("pref_map_type");
            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(getPreferenceScreen().getSharedPreferences().getString("pref_map_type", "Normale"));
            JuniorDAO dao = new JuniorDAO(this.getActivity());
            dao.open();
            ArrayList<Reseau> reseaux = dao.findReseaux();
            ListPreference listPref = (ListPreference)findPreference("pref_reseau");
            listPref.setSummary(getPreferenceScreen().getSharedPreferences().getString("pref_reseau", "Aucun réseau"));
            ArrayList<String> listId = new ArrayList<String>();
            ArrayList<String> listNom = new ArrayList<String>();
            Iterator<Reseau> it = reseaux.iterator();
            while(it.hasNext()){
            	Reseau r = it.next();
            	listId.add(r.getIdBdd()+"");
            	listNom.add(r.toString());
            }
            CharSequence[] csNom = listNom.toArray(new CharSequence[listNom.size()]);
            CharSequence[] csId = listId.toArray(new CharSequence[listId.size()]);
            listPref.setEntries(csNom);
            listPref.setEntryValues(csNom);
        }
        
        
        
        @Override
		public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
		public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }

    	@Override
    	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
    			String key) {
    		// TODO Auto-generated method stub
    		 if (key.equals("pref_map_type")) {
    	            Preference connectionPref = findPreference(key);
    	            // Set summary to be the user-description for the selected value
    	            connectionPref.setSummary(sharedPreferences.getString(key, "Normale"));
    	        }
    		 else if (key.equals("pref_reseau")) {
    			 Preference connectionPref = (Preference) findPreference(key);
 	            // Set summary to be the user-description for the selected value
 	            connectionPref.setSummary(sharedPreferences.getString(key, "Aucun réseau"));
 	        }

    	}
    	
    }



}
