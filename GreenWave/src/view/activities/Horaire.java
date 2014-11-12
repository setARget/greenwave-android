package view.activities;

import view.custom.AddHoraire;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.wavon.greenwave.R;

import control.Globale;
import control.listeners.time.GreenOnTimeSetListener;
import datas.KiceoDatas;
import datas.Utilisateur;
import datas.db.external.didier.GetHoraires;
import datas.utility.NetworkUtil;

public class Horaire extends Activity implements OnCheckedChangeListener{

	private TableLayout table;
	private RelativeLayout r;
	private RadioButton sens1;
	private RadioButton sens2;
	private RadioGroup group;
	private MenuItem addHoraire;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_info);
	    initInterface();
	    attachReactions();	
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.horaire_menu, menu);
        addHoraire = (MenuItem) menu.findItem(R.id.addhoraire);
        if(!sens1.isSelected() && !sens2.isSelected()){
            addHoraire.setVisible(false);
        }

    	getActionBar().setDisplayHomeAsUpEnabled(true);
 	   return true;
    }
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	switch (item.getItemId()) {
        case R.id.home:
        	this.finish();
            return true;
        case R.id.addhoraire:
        	SharedPreferences settings = getSharedPreferences(KiceoDatas.PREFS_NAME, 0);
        	boolean fb_logged = settings.getBoolean("fb_logged", false);
        	if(SplashScreen.ensureOpenSession()){
	        	if((sens1.isChecked() || sens2.isChecked()) && NetworkUtil.isConnected(this)){
	        		if(Globale.engine.getUtilisateur().getId() != -1){
	        		
		        		if(Globale.engine.getUtilisateur().getQuota()>0){
		        			if(Globale.engine.getUtilisateur().getStatut()>=Utilisateur.NOUVEAU){
		        				int idLigne = Globale.engine.getLigneCourante().getIdBdd();
		        	        	int idArret = Globale.engine.getArretCourant().getIdBdd();
		        	        	int direction = 1;
		        	        	if(sens2.isChecked()){direction=2;}
		        	            new AddHoraire(this, 0, new GreenOnTimeSetListener(this, idLigne, idArret, direction, datas.Horaire.getDayOfWeek()), 0, 0, true).show();
		        			}
		        			else if(Globale.engine.getUtilisateur().getStatut()== Utilisateur.NOMAJ){
		            			AlertDialog.Builder builder = new AlertDialog.Builder(this);
		            			builder.setTitle("Restriction");
		            			builder.setMessage("Suite à de fausses informations que vous nous avez transmises votre ajout d'horaire à été suspendu pour un temps indéterminé");
		    				   	//alertDialog.setIcon(R.drawable.tick);
		    				 // Setting OK Button
		            			builder.setPositiveButton("Envoyer une réclamation", new DialogInterface.OnClickListener() {
		    			                public void onClick(DialogInterface dialog, int which) {
		    			                // Write your code here to execute after dialog closed
		    			                	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);     
		    			                	emailIntent.setType("plain/text");     
		    			                	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"antoine@sauray.me"});     
		    			                	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Réclamation bloquage horaire"); 
		    			                	String text = "nom : "+Globale.engine.getUtilisateur().getNom()+"\n";
		    			                	text += "prénom : "+Globale.engine.getUtilisateur().getPrenom()+"\n";
		    			                	text+="id : "+Globale.engine.getUtilisateur().getId()+"\n";
		    			                	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);     
		    			                	startActivity(Intent.createChooser(emailIntent, "Envoyer..."));
		    			                }
		    			        });
		            			builder.setNegativeButton("Continuer à utiliser l'application", new DialogInterface.OnClickListener() {
		    		                public void onClick(DialogInterface dialog, int which) {
		    		                // Write your code here to execute after dialog closed
		    		                }
		    		        });
		    				   	builder.show();
		                	}
		                	else if(Globale.engine.getUtilisateur().getStatut()== Utilisateur.BANNI){
		                		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		            			builder.setTitle("Restriction");
		            			builder.setMessage("Suite à de fausses informations que vous nous avez transmises nous avons bloqué les mises à jour que vous pouvez recevoir de notre réseau");
		    				   	//alertDialog.setIcon(R.drawable.tick);
		    				 // Setting OK Button
		            			builder.setPositiveButton("Envoyer une réclamation", new DialogInterface.OnClickListener() {
		    			                public void onClick(DialogInterface dialog, int which) {
		    			                // Write your code here to execute after dialog closed
		    			                	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);     
		    			                	emailIntent.setType("plain/text");     
		    			                	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"antoine@sauray.me"});     
		    			                	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Réclamation bannissement"); 
		    			                	String text = "nom : "+Globale.engine.getUtilisateur().getNom()+"\n";
		    			                	text += "prénom : "+Globale.engine.getUtilisateur().getPrenom()+"\n";
		    			                	text+="id : "+Globale.engine.getUtilisateur().getId()+"\n";
		    			                	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);     
		    			                	startActivity(Intent.createChooser(emailIntent, "Envoyer..."));
		    			                }
		    			        });
		            			builder.setNegativeButton("Continuer à utiliser l'application", new DialogInterface.OnClickListener() {
		    		                public void onClick(DialogInterface dialog, int which) {
		    		                // Write your code here to execute after dialog closed
		    		                
		    		                }
		    		        });
		
		    				   	builder.show();
		                	}
		        			
		        		}
		        		else{
		        			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	            			builder.setTitle("Quota atteint");
	            			builder.setMessage("Vous avez atteint le quota d'horaires que vous pouvez ajouter pour aujourd'hui. Cette limite nous permet de controler le contenu mis en ligne par les utilisateurs et proposer une meilleure qualité pour tous.");
	    				   	//alertDialog.setIcon(R.drawable.tick);
	    				 // Setting OK Button
	            			builder.setPositiveButton("Envoyer une réclamation", new DialogInterface.OnClickListener() {
	    			                public void onClick(DialogInterface dialog, int which) {
	    			                // Write your code here to execute after dialog closed
	    			                	
	    			                }
	    			        });
	            			builder.setNegativeButton("Continuer à utiliser l'application", new DialogInterface.OnClickListener() {
	    		                public void onClick(DialogInterface dialog, int which) {
	    		                // Write your code here to execute after dialog closed
	    		                }
	    		        });
	    				   	builder.show();
		        		}
	        		}
        		}
	        	else if (!NetworkUtil.isConnected(this)){
	        		Toast.makeText(this,
	    					"Vous n'êtes pas connecté",
	    					Toast.LENGTH_LONG).show();
	        	}
        		else{
        			Toast.makeText(this,
        					"Vous avez dépassé votre quota",
        					Toast.LENGTH_LONG).show();
        		}
        		

        		
	        	
        	}
        	else{
        		Intent intent = new Intent(Horaire.this, FBLogin.class);
    	    	startActivity(intent);
        	}
        default:
            return super.onOptionsItemSelected(item);
    }

    }
	
	public void updateTimes(int direction){
		//text.replace("Je vais à ", "");
 		table.removeAllViews();
 		new GetHoraires(this, Globale.engine.getLigneCourante().getIdBdd(), Globale.engine.getArretCourant().getIdBdd(), direction, datas.Horaire.getDayOfWeek(), table, r).execute();
	}
	
	private void initInterface(){
 	
			//Cursor c = Globale.engine.getCurrentArretSelected().getHoraires(Globale.engine.getCurrentLigneSelected());
			table = (TableLayout) findViewById(R.id.table);
			
			r = (RelativeLayout) findViewById(R.id.loadingPanel);
			
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) r.getLayoutParams();
			params.addRule(RelativeLayout.BELOW, R.id.group);

			CharSequence s1 = Globale.engine.getLigneCourante().getDirection1();
			CharSequence s2 = Globale.engine.getLigneCourante().getDirection2();
			
			sens1 = (RadioButton) findViewById(R.id.sens1);
			sens1.setText("Je vais à "+s1);
			sens2 = (RadioButton) findViewById(R.id.sens2);
			sens2.setText("Je vais à "+s2);
			group = (RadioGroup) findViewById(R.id.group);
			
	}
	
	private void attachReactions(){
		group.setOnCheckedChangeListener(this); 
	}


	 public  void  onCheckedChanged(RadioGroup group, 
             int  checkedId) { 
		 if(Globale.engine.getUtilisateur().getStatut()!=Utilisateur.BANNI && Globale.engine.getUtilisateur().getStatut()!=Utilisateur.NOMAJ){
			 if(addHoraire!=null){
				 addHoraire.setVisible(true);
			 }
	     	int id = group.getCheckedRadioButtonId();
	     	if(id == R.id.sens1){
	     		// 1er cas donc premier sens
	     		this.updateTimes(1);
	     	}
	     	else if (id == R.id.sens2){
	     		// 2eme cas c'est donc le deuxième sens
	     		this.updateTimes(2);        	
	     	}
		 }
		 else if(Globale.engine.getUtilisateur().getStatut()== Utilisateur.BANNI){
     		AlertDialog.Builder builder = new AlertDialog.Builder(this);
 			builder.setTitle("Restriction");
 			builder.setMessage("Suite à de fausses informations que vous nous avez transmises nous avons bloqué les mises à jour que vous pouvez recevoir de notre réseau");
			   	//alertDialog.setIcon(R.drawable.tick);
			 // Setting OK Button
 			builder.setPositiveButton("Envoyer une réclamation", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int which) {
		                // Write your code here to execute after dialog closed
		                	final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);     
		                	emailIntent.setType("plain/text");     
		                	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"antoine@sauray.me"});     
		                	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Réclamation bannissement"); 
		                	String text = "nom : "+Globale.engine.getUtilisateur().getNom()+"\n";
		                	text += "prénom : "+Globale.engine.getUtilisateur().getPrenom()+"\n";
		                	text+="id : "+Globale.engine.getUtilisateur().getId()+"\n";
		                	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);     
		                	startActivity(Intent.createChooser(emailIntent, "Envoyer..."));
		                }
		        });
 			builder.setNegativeButton("Continuer à utiliser l'application", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                // Write your code here to execute after dialog closed
	                
	                }
	        });

			   	builder.show();
     	}	
		else{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Quota atteint");
			builder.setMessage("Vous avez atteint le quota d'horaires que vous pouvez ajouter pour aujourd'hui. Cette limite nous permet de controler le contenu mis en ligne par les utilisateurs et proposer une meilleure qualité pour tous.");
		   	//alertDialog.setIcon(R.drawable.tick);
		 // Setting OK Button
			builder.setPositiveButton("Envoyer une réclamation", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                // Write your code here to execute after dialog closed
	                	
	                }
	        });
			builder.setNegativeButton("Continuer à utiliser l'application", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int which) {
             // Write your code here to execute after dialog closed
             }
     });
		   	builder.show();
		}
     } 
	
}

