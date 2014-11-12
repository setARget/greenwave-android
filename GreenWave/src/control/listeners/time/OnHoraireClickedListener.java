package control.listeners.time;

import view.activities.Home;
import view.custom.HoraireDetail;
import datas.Horaire;
import datas.utility.NetworkUtil;
import android.app.Dialog;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class OnHoraireClickedListener implements OnClickListener{

	private Horaire h;
	private HoraireDetail d;
	private Context c;
	
	public OnHoraireClickedListener(Context c, Horaire h){
		this.h=h;
		this.c=c;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(NetworkUtil.isConnected(c)){
			d = new HoraireDetail(c, h);
			d.show();
		}
		else{
			Toast.makeText(c,
					"Le détail des horaires n'est pas accessible hors-ligne",
					Toast.LENGTH_LONG).show();
		}
	}


}
