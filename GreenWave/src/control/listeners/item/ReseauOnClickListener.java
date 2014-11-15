package control.listeners.item;

import java.util.Iterator;

import view.activities.Home;
import control.Globale;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import datas.Reseau;
import datas.db.external.didier.DownloadFullReseau;
import datas.db.internal.JuniorDAO;

public class ReseauOnClickListener implements OnItemClickListener{

	private Context c;
	
	public ReseauOnClickListener(Context c){
		this.c=c;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Reseau r = (Reseau) parent.getItemAtPosition(position);
		Log.d("R�seau local", "Reseau click�");
		JuniorDAO dao = new JuniorDAO(c);
		dao.open();
		Iterator<Reseau> it = dao.findReseaux().iterator();
		dao.close();
		boolean foundReseauLocally=false;
		while(it.hasNext() && foundReseauLocally==false){
			Reseau reseau = it.next();
			if(reseau.getIdBdd()==r.getIdBdd()){
				foundReseauLocally=true;
				Log.d("R�seau local", r.toString()+" a �t� trouv� localement");
			}
		}
		if(foundReseauLocally==true){
			// Le r�seau est d�j� t�l�charg�

			Globale.engine.setReseau(r, c);
			Intent home = new Intent(c, Home.class);
	  	    c.startActivity(home);
		}
		else{
			new DownloadFullReseau(c, r).execute();
		}

	}

}
