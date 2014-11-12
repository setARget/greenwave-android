package control.listeners.item;

import view.activities.Home;
import control.Globale;
import android.content.Context;
import android.content.Intent;
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
		JuniorDAO dao = new JuniorDAO(c);
		dao.open();
		if(dao.findReseaux().toString().contains(r.toString())){
			// Le réseau est déjà téléchargé
			dao.close();
			Globale.engine.setReseau(r, c);
			Intent home = new Intent(c, Home.class);
	  	    c.startActivity(home);
		}
		else{
			dao.close();
			new DownloadFullReseau(c, r).execute();
		}

	}

}
