package datas;

import android.util.Log;

public class Utilisateur {
	
	private String idFacebook, nom, prenom;
	private int id, quota, nbHorairesAjoutees, nbErreurs, nbApprobations, statut;
	
	public final static int BANNI=-1, NOMAJ=0, NOUVEAU=1, HABITUE=2, EXPERT=3, MEMBRE = 4,ADMINISTRATEUR=10;

	public Utilisateur(){
		this.id=-1;
		this.idFacebook=null;	
		this.nom=null;
		this.prenom=null;
		this.quota=1;
		this.nbHorairesAjoutees=0;
		this.nbErreurs=0;
		this.nbApprobations=0;
		this.statut=1;
	}
	
	public Utilisateur(int id){
		this.id=id;
		this.nom=null;
		this.prenom=null;
		this.quota=1;
		this.nbHorairesAjoutees=0;
		this.nbErreurs=0;
		this.nbApprobations=0;
		this.statut=1;
	}
	
	public Utilisateur(String idFacebook, String nom, String prenom){
		this.id=-1;
		this.idFacebook=idFacebook;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=1;
		this.nbHorairesAjoutees=0;
		this.nbErreurs=0;
		this.nbApprobations=0;
		this.statut=1;
	}
	
	public Utilisateur(int id, String idFacebook, String nom, String prenom){
		this.id=id;
		this.idFacebook=idFacebook;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=1;
		this.nbHorairesAjoutees=0;
		this.nbErreurs=0;
		this.nbApprobations=0;
		this.statut=1;
	}
	
	public Utilisateur(int id, String nom, String prenom, int quota){
		this.id=id;
		this.idFacebook=null;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=quota;
		this.nbHorairesAjoutees=0;
		this.nbErreurs=0;
		this.nbApprobations=0;
		this.statut=1;
	}
	
	public Utilisateur(String idFacebook, String nom, String prenom, int quota){
		this.id=-1;
		this.idFacebook=idFacebook;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=quota;
		this.nbHorairesAjoutees=0;
		this.nbErreurs=0;
		this.nbApprobations=0;
		this.statut=1;
	}
	
	public Utilisateur(int id, String nom, String prenom, int quota, int nbHorairesAjoutees, int nbErreurs, int nbApprobations, int statut){
		this.id=id;
		this.idFacebook=null;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=quota;
		this.nbHorairesAjoutees=nbHorairesAjoutees;
		this.nbErreurs=nbErreurs;
		this.nbApprobations=nbApprobations;
		this.statut=statut;
	}
	
	public Utilisateur(String idFacebook, String nom, String prenom, int quota, int nbHorairesAjoutees, int nbErreurs, int nbApprobations, int statut){
		this.id=-1;
		this.idFacebook=idFacebook;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=quota;
		this.nbHorairesAjoutees=nbHorairesAjoutees;
		this.nbErreurs=nbErreurs;
		this.nbApprobations=nbApprobations;
		this.statut=statut;
	}
	
	public Utilisateur(int id, String idFacebook, String nom, String prenom, int quota, int nbHorairesAjoutees, int nbErreurs, int nbApprobations, int statut){
		this.id=id;
		this.idFacebook=idFacebook;
		this.nom=nom;
		this.prenom=prenom;
		this.quota=quota;
		this.nbHorairesAjoutees=nbHorairesAjoutees;
		this.nbErreurs=nbErreurs;
		this.nbApprobations=nbApprobations;
		this.statut=statut;
	}
	
	public int getId(){
		return id;
	}
	
	public String getIdFacebook(){
		return idFacebook;
	}
	
	public String getNom(){
		return nom;
	}
	
	public String getPrenom(){
		return prenom;
	}
	
	public int getQuota(){
		return quota;
	}
	
	public int getNbErreurs(){
		return nbErreurs;
	}
	
	public int getNbHoraires(){
		return nbHorairesAjoutees;
	}
	
	public int getStatut(){
		return statut;
	}
	
	public void decrementQuota(){
		this.quota=quota-1;
	}
	
	public int getRatio(){
		if(nbErreurs==0){
			return 100;
		}
		else if(nbHorairesAjoutees==0){
			return 0;
		}
		else{
			return (int) (((float)(nbErreurs*100)/(nbHorairesAjoutees*100))*100);
		}	
	}
	
}
