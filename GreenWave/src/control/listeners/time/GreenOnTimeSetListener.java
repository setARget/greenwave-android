package control.listeners.time;

import view.activities.Home;
import control.Globale;
import datas.Horaire;
import datas.db.external.didier.InsertHoraire;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.Log;
import android.widget.TimePicker;
import android.widget.Toast;

public class GreenOnTimeSetListener implements OnTimeSetListener {

	private Context c;
	private int ligne, arret, direction;
	private String calendrier;
	
	public GreenOnTimeSetListener(Context c, int ligne, int arret, int direction, String calendrier){
		this.c=c;
		this.ligne=ligne;
		this.arret=arret;
		this.direction=direction;
		this.calendrier=calendrier;
	}
	
	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		Log.d("OnTimeSetListener", "new Time Set");
		int userID = Globale.engine.getUtilisateur().getId();
		if(userID != -1){
			new InsertHoraire(c, new Horaire(hourOfDay, minute,userID), ligne, arret, direction, calendrier).execute();
		}
		else{
			Toast.makeText(c,
					"Erreur : problème ID",
					Toast.LENGTH_LONG).show();
		}
	}

}
