package datas.reseau;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.wavon.greenwave.R;

import datas.Arret;
import datas.Ligne;
import datas.Reseau;

/**
 * Kiceo bus company
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray & Alexis Robin
 * @version 0.2
 */
public class Lorient extends Reseau{

	public Lorient() {
		super("Ctrl", "<a class=\"twitter-timeline\"  href=\"https://twitter.com/BibusBrest\"  data-widget-id=\"499488710736359424\"></a>"+
			    "<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.async=true;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>");
	}
	
	protected void chargerLignes(Context c){

		Ligne ligne22 =  new Ligne("22", "Ploemeur Les Pins", "Lanester Grande Lande", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED), R.color.rouge);
		Ligne ligne1 = new Ligne("1", "1A", "1B", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE), R.color.orange);
		Ligne ligne2 = new Ligne("2", "Lorient Kerulvé", "Lorient Gare Maritime", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW), R.color.jaune);
		Ligne ligne21 = new Ligne("21", "Lanester Jardin du Blavet", "Lorient Port de Pêche", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW), R.color.jaune);
		Ligne ligne30 = new Ligne("30", "Ploemeur Fort-Bloqué", "Caudan", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN), R.color.vert);
		Ligne ligne31 = new Ligne("31", "Cleguer La Croix-Rouge", "Ploemeur Le Courégant", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA), R.color.violet);
		Ligne ligne42 = new Ligne("42", "Languidic Kerogonan", "Lorient Le Ter", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN), R.color.vert);
		Ligne ligne51 = new Ligne("51", "Guidel Z.A. Pen Mané", "Ploemeur CRF Kerpape", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE), R.color.marron);
		Ligne ligne52 = new Ligne("52", "Queven Bel Air", "Larmor-Plage Kerderff", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE), R.color.bleu);
		
		lignes.put(ligne1.getNom(), ligne1);
		lignes.put(ligne2.getNom(), ligne2);
		lignes.put(ligne21.getNom(), ligne21);
		lignes.put(ligne22.getNom(), ligne22);
		lignes.put(ligne30.getNom(), ligne30);
		lignes.put(ligne31.getNom(), ligne31);
		lignes.put(ligne42.getNom(), ligne42);
		lignes.put(ligne51.getNom(), ligne51);
		lignes.put(ligne52.getNom(), ligne52);

	}
	
	protected void chargerArrets(Context c){
		ploemeur();
		lorient();
		lanester();
	}

	
	private void ploemeur(){
		Arret lesPins = new Arret(new LatLng(47.735250, -3.438258), "Les Pins", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret lesCeltes = new Arret(new LatLng(47.736785, -3.436329), "Les Celtes", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 30d")));
		Arret lesPommiers = new Arret(new LatLng(47.735407, -3.432553), "Les Pommiers", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30d")));
		Arret leParc = new Arret(new LatLng(47.732412, -3.432738), "Le Parc", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30d")));
		Arret placeDeBretagne = new Arret(new LatLng(47.732443, -3.428808), "Place de Bretagne", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30d", "Ligne 31")));
		Arret tyNehue = new Arret(new LatLng(47.735207, -3.427295), "Ty Néhué", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret leCoupanec = new Arret(new LatLng(47.737808, -3.431722), "Le Coupannec", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 30")));
		Arret eglise = new Arret(new LatLng(47.736924, -3.428647), "Eglise", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret stPierre = new Arret(new LatLng(47.739683, -3.426169), "Saint-Pierre", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30")));
		Arret stMathurin = new Arret(new LatLng(47.739418, -3.412244), "Saint-Mathurin", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 31")));
		Arret kergourgant = new Arret(new LatLng(47.739269, -3.423347), "Kergourgant", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30")));
		Arret kerjoel = new Arret(new LatLng(47.738678, -3.419099), "Kerjoel", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 31")));
		Arret kerabus = new Arret(new LatLng(47.744170, -3.399183), "Kerabus", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 31")));
		Arret kerdiret = new Arret(new LatLng(47.745357, -3.395487), "Kerdiret", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 31")));
		Arret kerlederne = new Arret(new LatLng(47.741811, -3.404783), "Kerlederne", "Ploemeur", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 31")));
 
		arrets.put(lesPins.toString(), lesPins);
    	arrets.put(lesCeltes.toString(), lesCeltes);
    	arrets.put(lesPommiers.toString(), lesPommiers);
    	arrets.put(placeDeBretagne.toString(), placeDeBretagne);
    	arrets.put(leParc.toString(), leParc);
    	arrets.put(tyNehue.toString(), tyNehue);
    	arrets.put(kergourgant.toString(), kergourgant);
    	arrets.put(kerjoel.toString(), kerjoel);
    	arrets.put(leCoupanec.toString(), leCoupanec);
    	arrets.put(eglise.toString(), eglise);
    	arrets.put(stPierre.toString(), stPierre);
    	arrets.put(kerabus.toString(), kerabus);
    	arrets.put(kerdiret.toString(), kerdiret);
    	arrets.put(kerlederne.toString(), kerlederne);
    	arrets.put(stMathurin.toString(), stMathurin);
	}
	
	private void lorient(){
		Arret alsaceLorraine = new Arret(new LatLng(47.750004, -3.361961), "Alsace-Lorraine", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22", "Ligne 1")));
		Arret bayard = new Arret(new LatLng(47.745351, -3.370308), "Bayard", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22")));
		Arret coursDeChazelles = new Arret(new LatLng(47.752298, -3.361419), "Cours de Chazelles", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 32")));
		Arret faouedic = new Arret(new LatLng(47.748049, -3.364595), "Faouedic", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22", "Ligne 1")));
		Arret garedEchange = new Arret(new LatLng(47.755349, -3.363442), "Gare d'Echange", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22", "Ligne 1")));
		Arret guillemot = new Arret(new LatLng(47.744855, -3.389850), "Guillemot", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 32")));
		Arret lycee = new Arret(new LatLng(47.743374, -3.382173), "Lycée", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22", "Ligne 1")));
		Arret merville = new Arret(new LatLng(47.745146, -3.374325), "Merville", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22")));
		Arret poteau = new Arret(new LatLng(47.745070, -3.376948), "Poteau", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22", "Ligne 1")));
		Arret restauU = new Arret(new LatLng(47.743299, -3.388836), "Restaurant Universitaire", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 1A", "Ligne 1B", "Ligne 21", "Ligne 22", "Ligne 30", "Ligne 40", "Ligne 42", "Ligne 31", "Ligne 60")));
		Arret securiteSociale = new Arret(new LatLng(47.746437, -3.366569), "Sécurité Sociale", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 30", "Ligne 51", "Ligne 54", "Ligne 42", "Ligne 52", "Ligne 40", "Ligne 21", "Ligne 31", "Ligne 22", "Ligne 1")));
		Arret stMaude = new Arret(new LatLng(47.746868, -3.392200), "Saint-Maudé", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 22", "Ligne 30", "Ligne 31", "Ligne 60")));
		Arret universite = new Arret(new LatLng(47.742519, -3.384775), "Université", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 22", "Ligne 30", "Ligne 40", "Ligne 42", "Ligne 31", "Ligne 60")));
		Arret guieyesse = new Arret (new LatLng(47.759110, -3.365584), "Paul Guieysse", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret verdun = new Arret(new LatLng(47.759961, -3.365885), "Verdun", "Lorient", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 21", "Ligne 22", "Ligne 31", "Ligne 1D", "Ligne 31d")));
		
		
		arrets.put(alsaceLorraine.toString(), alsaceLorraine);
		arrets.put(bayard.toString(), bayard);
		arrets.put(coursDeChazelles.toString(), coursDeChazelles);
		arrets.put(faouedic.toString(), faouedic);
		arrets.put(garedEchange.toString(), garedEchange);
		arrets.put(guillemot.toString(), guillemot);
		arrets.put(lycee.toString(), lycee);
		arrets.put(merville.toString(), merville);
		arrets.put(poteau.toString(), poteau);
		arrets.put(restauU.toString(), restauU);
		arrets.put(securiteSociale.toString(), securiteSociale);
		arrets.put(stMaude.toString(), stMaude);
		arrets.put(universite.toString(), universite);
		arrets.put(guieyesse.toString(), guieyesse);
		arrets.put(verdun.toString(), verdun);
	}

	private void lanester(){
		Arret anse = new Arret(new LatLng(47.763177, -3.361786), "Anse", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 21", "Ligne 22", "Ligne 31", "Ligne 31d")));
		Arret kerdavid = new Arret(new LatLng(47.765182, -3.358567), "Kerdavid", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 21", "Ligne 22", "Ligne 31", "Ligne 31d")));
		Arret squareNoury = new Arret(new LatLng(47.767072, -3.355478), "Square Noury", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 21", "Ligne 22", "Ligne 31", "Ligne 31d")));
		Arret vilar = new Arret(new LatLng(47.764576, -3.351165), "Jean Villar", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 21", "Ligne 22")));
		Arret hotelDeVille = new Arret(new LatLng(47.762279, -3.348171), "Hotel de Ville", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret leCorpont = new Arret(new LatLng(47.762956, -3.346186), "Le Corpont", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 40", "Ligne 42", "Ligne 42d")));
		Arret lesHalles = new Arret(new LatLng(47.765579, -3.345256), "Les Halles", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 40", "Ligne 42", "Ligne 42d")));
		Arret petitLanester = new Arret(new LatLng(47.768022, -3.344955), "Petit Lanester", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 30", "Ligne 40", "Ligne 42", "Ligne 42d")));
		Arret devillers = new Arret(new LatLng(47.767805, -3.342048), "Kesler Devillers", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22", "Ligne 42", "Ligne 42d")));
		Arret pontDuBonhomme = new Arret(new LatLng(47.766781, -3.337515), "Avenue du Pont du Bonhomme", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret lannGazec = new Arret(new LatLng(47.766352, -3.334419), "Lann Gazec", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret vieuxChenes = new Arret(new LatLng(47.765003, -3.329369), "Vieux Chênes", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret kerfrehour = new Arret(new LatLng(47.761581, -3.331276), "Kerfréhour", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret leCoutaller = new Arret(new LatLng(47.759816, -3.330995), "Le Coutaller", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 21", "Ligne 22")));
		Arret plessis = new Arret(new LatLng(47.758894, -3.336775), "Les Quatres Chemins du Plessis", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 21", "Ligne 22")));
		Arret stJoseph = new Arret(new LatLng(47.757454, -3.337082), "Saint-Joseph", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret toulhouet = new Arret (new LatLng(47.755395, -3.335162), "Toulhouët", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 20", "Ligne 20d", "Ligne 22")));
		Arret stade = new Arret (new LatLng(47.753277, -3.333929), "Stade A et l Le Bail", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
		Arret grandeLande = new Arret (new LatLng(47.750929, -3.333585), "Grande Lande", "Lanester", new ArrayList<String>(Arrays.asList("Ligne 22")));
	
		arrets.put(anse.toString(), anse);
		arrets.put(kerdavid.toString(), kerdavid);
		arrets.put(squareNoury.toString(), squareNoury);
		arrets.put(vilar.toString(), vilar);
		arrets.put(hotelDeVille.toString(), hotelDeVille);
		arrets.put(leCorpont.toString(), leCorpont);
		arrets.put(lesHalles.toString(), lesHalles);
		arrets.put(petitLanester.toString(), petitLanester);
		arrets.put(devillers.toString(), devillers);
		arrets.put(pontDuBonhomme.toString(), pontDuBonhomme);
		arrets.put(lannGazec.toString(), lannGazec);
		arrets.put(vieuxChenes.toString(), vieuxChenes);
		arrets.put(kerfrehour.toString(), kerfrehour);
		arrets.put(leCoutaller.toString(), leCoutaller);
		arrets.put(plessis.toString(), plessis);
		arrets.put(stJoseph.toString(), stJoseph);
		arrets.put(toulhouet.toString(), toulhouet);
		arrets.put(stade.toString(), stade);
		arrets.put(grandeLande.toString(), grandeLande);
		
		
	}

	public String toString(){
		return "LORIENT";
	}

	@Override
	public int getImage() {
		// TODO Auto-generated method stub
		return R.drawable.ctrl_img;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
}
