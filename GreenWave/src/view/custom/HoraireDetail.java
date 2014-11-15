package view.custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wavon.greenwave.R;

import control.Globale;

import datas.Horaire;
import datas.Utilisateur;
import datas.db.external.didier.Reclamation;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class HoraireDetail extends Dialog implements Runnable, android.view.View.OnClickListener{
	
	private Context c;
	private Horaire h;
	private Utilisateur auteur;
	private TextView horaire, auteurText, ratio, statut, nbErreurs, nbHoraires;
	private ImageView smiley,reclamation, approbation;
	private com.facebook.widget.ProfilePictureView image;
	private ProgressBar loading;
	private RatingBar bar;
	

	public HoraireDetail(Context c, Horaire h) {
		super(c);
		// TODO Auto-generated constructor stub
		setContentView(R.layout.dialog_detailhoraire);
		this.c=c;
		this.h=h;
		initInterface();
		attachReactions();
		
		Thread t = new Thread(this);
		t.start();
		
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loading.setVisibility(View.GONE);
		if(auteur.getIdFacebook()!=null){
			image.setProfileId(auteur.getIdFacebook());
		}
		ratio.setText((auteur.getRatio())+"% des horaires valides");
		float ratio = auteur.getRatio();
		bar.setRating(ratio*0.05f);
		if(ratio<50){
			//smiley.setImageResource(R.drawable.no_confiance);
		}
		else{
			//smiley.setImageResource(R.drawable.confiance);
		}
		horaire.setText(h.toString());
		auteurText.setText(auteur.getPrenom());	
		nbHoraires.setText(auteur.getNbHoraires()+" horaires renseignées");
		nbErreurs.setText(auteur.getNbErreurs()+" erreurs");
		switch(auteur.getStatut()){
		
			case Utilisateur.NOUVEAU:
				statut.setText("Nouveau venu");
			break;
			
			case Utilisateur.HABITUE:
				statut.setText("Habitué");
			break;
			
			case Utilisateur.MEMBRE:
				statut.setText("Membre du Staff");
			break;
			
			case Utilisateur.EXPERT:
				statut.setText("Expert du réseau");
			break;
			
			case Utilisateur.ADMINISTRATEUR:
				statut.setText("Administrateur");
			break;
			
			case Utilisateur.BANNI:
				statut.setText("Banni");
			break;
			
			case Utilisateur.NOMAJ:
				statut.setText("Privé de Mise à jour");
			break;
		
		}
	}
	
	private void initInterface(){
		this.setTitle(Globale.engine.getArretCourant().toString());
		horaire = (TextView) this.findViewById(R.id.horaire);
		auteurText = (TextView) this.findViewById(R.id.auteur);
		statut = (TextView) this.findViewById(R.id.statut);
		nbHoraires = (TextView) this.findViewById(R.id.nbHoraires);
		nbErreurs = (TextView) this.findViewById(R.id.nbErreurs);
		reclamation = (ImageView) this.findViewById(R.id.no_approve);
		approbation = (ImageView) this.findViewById(R.id.approve);
		loading = (ProgressBar) this.findViewById(R.id.loadingDetails);
		image = (com.facebook.widget.ProfilePictureView) this.findViewById(R.id.userimageFB);
		ratio = (TextView) this.findViewById(R.id.ratio);
		bar = (RatingBar) this.findViewById(R.id.rating);
		loading.setVisibility(View.VISIBLE);
		
		bar.setActivated(false);
		bar.setEnabled(false);
	}
	
	private void attachReactions(){
		reclamation.setOnClickListener(this);
		approbation.setOnClickListener(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		StringBuilder jsonResult = new StringBuilder();
		final String BASE_URL = "http://sauray.me/green_wave/utilisateur.php?";

 
		 HttpURLConnection conn = null;
		    try {
		        StringBuilder sb = new StringBuilder(BASE_URL);
		        	sb.append("id=" + h.getIdAuteur());
		   
		        URL url = new URL(sb.toString());
		        conn = (HttpURLConnection) url.openConnection();
		        InputStreamReader in = new InputStreamReader(conn.getInputStream());

		        BufferedReader jsonReader = new BufferedReader(in);
				String lineIn;
				while ((lineIn = jsonReader.readLine()) != null) {
				    jsonResult.append(lineIn);
				}
			
				JSONObject jsonObj = new JSONObject(jsonResult.toString());
		        
        		JSONArray jsonMainNode = jsonObj.optJSONArray("utilisateur");
        		JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);

        		String idFacebook = jsonChildNode.optString("idFacebook");
        		String nom = jsonChildNode.optString("nom");
        		String prenom = jsonChildNode.optString("prenom");	
        		int quota = jsonChildNode.optInt("quota");
        		int nbHorairesAjoutees = jsonChildNode.optInt("nbHorairesAjoutees");
        		int nbErreurs = jsonChildNode.optInt("nbErreurs");
        		int nbApprobations = jsonChildNode.optInt("nbApprobations");
        		int statut = jsonChildNode.optInt("statut");
        		Log.d(nbHorairesAjoutees+"", "nbHorairesAjoutees");
        		Log.d(nbErreurs+"", "nbErreurs");
        		auteur = new Utilisateur(h.getIdAuteur(), idFacebook, nom, prenom, quota, nbHorairesAjoutees, nbErreurs, nbApprobations, statut);      		
        			
		    } catch (MalformedURLException e) {

		    } 
		    catch (JSONException e) {

		    }catch (IOException e) {
		    } finally {
		        if (conn != null) {
		            conn.disconnect();
		        }
		    }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==reclamation){
			if(Globale.engine.getUtilisateur().getQuota()>0){
				Reclamation t = new Reclamation(h.getId(), h.getIdAuteur());
				t.start();
				try {
					t.join();
					AlertDialog.Builder builder = new AlertDialog.Builder(c);
					builder.setTitle("Horaire signalée");
					builder.setMessage("Merci de nous aider à rendre notre contenu plus fiable !\n Vous pouvez encore signaler ou ajouter "+Globale.engine.getUtilisateur().getQuota()+" horaires aujourd'hui :)");
					builder.setNeutralButton("Ok", null);
				   	builder.show();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				else{
					AlertDialog.Builder builder = new AlertDialog.Builder(c);
					builder.setTitle("Quota atteint");
					builder.setMessage("Merci pour votre aide !\n Cependant il semble que vous ayez atteint le nombre maximum de modifications et d'ajout horaires aujourd'hui :)");
					builder.setNeutralButton("Ok", null);
				   	builder.show();
				}
			}
		
		else{
			AlertDialog.Builder builder = new AlertDialog.Builder(c);
			builder.setTitle("Quota atteint");
			builder.setMessage("Merci pour votre aide !\n En nous signalants les horaires valides vous aidez à rendre notre contenu plus fiable :)");
			builder.setNeutralButton("Ok", null);
		   	builder.show();
		}
	}

}
