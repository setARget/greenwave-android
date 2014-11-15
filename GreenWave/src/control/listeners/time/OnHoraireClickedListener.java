package control.listeners.time;

import control.Globale;
import view.activities.FBLogin;
import view.activities.Home;
import view.custom.HoraireDetail;
import datas.Horaire;
import datas.utility.NetworkUtil;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
			if(Globale.engine.getUtilisateur().getIdFacebook()!=null){
				d = new HoraireDetail(c, h);
				d.show();
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(c);
    			builder.setTitle("Informations détaillées");
    			builder.setMessage("Pour accéder à ces informations, une connexion à votre compte Facebook est requise.");
			   	//alertDialog.setIcon(R.drawable.tick);
			 // Setting OK Button
    			builder.setPositiveButton("Se connecter", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog closed
		                	Intent intent = new Intent(c, FBLogin.class);
		            		c.startActivity(intent);
		                }
		        });
    			builder.setNegativeButton("Continuer", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                // Write your code here to execute after dialog closed
	                }
	        });
			   	builder.show();
			}

		}
		else{
			Toast.makeText(c,
					"Le détail des horaires n'est pas accessible hors-ligne",
					Toast.LENGTH_LONG).show();
		}
	}


}
