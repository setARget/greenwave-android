package view.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.wavon.greenwave.R;

public class Preferences extends PreferenceActivity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 getFragmentManager().beginTransaction().replace(android.R.id.content, new InnerPreferenceFragment()).commit();
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

    	}
    	
    }



}
