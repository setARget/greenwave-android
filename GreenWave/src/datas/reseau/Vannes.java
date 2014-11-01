package datas.reseau;

import java.util.ArrayList;
import java.util.Arrays;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.wavon.greenwave.R;

import datas.Arret;
import datas.Reseau;
import datas.Ligne;

/**
 * Kiceo bus company
 * © Copyright 2014 Antoine Sauray
 * @author Antoine Sauray & Alexis Robin
 * @version 0.2
 */
public class Vannes extends Reseau{

	public Vannes() {
		super("Ctrl", "<a class=\"twitter-timeline\"  href=\"https://twitter.com/BibusBrest\"  data-widget-id=\"499488710736359424\"></a>"+
			    "<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.async=true;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>");
	}
	
	protected void chargerLignes(Context c){
		/*
		Ligne ligne1 =  new Ligne("1", "Ténénio", "Cassard", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED), R.color.rouge);
		lignes.put(ligne1.getNom(), ligne1);
		
		Ligne ligne2 =  new Ligne("2", "Fourchêne - Madeleie - République", "PIBS 2 - Petit Tohannic", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE), R.color.bleu);
		lignes.put(ligne2.getNom(), ligne2);
		
		Ligne ligne3 =  new Ligne("3", "Poulfanc - Delestraint - République", "Sq. Morbihan - Conleau", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN), R.color.vert);
		lignes.put(ligne3.getNom(), ligne3);
		
		Ligne ligne4 =  new Ligne("4", "Le Poteau - St-Avé Mairie - Gare SNCF", "République - Arradon Mairie", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE), R.color.orange);
		lignes.put(ligne4.getNom(), ligne4);
		
		Ligne ligne5 =  new Ligne("5", "Luscanen - Kerthomas - République", "Poulfanc - Kersec", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA), R.color.violet);
		lignes.put(ligne5.getNom(), ligne5);
		
		Ligne ligne6 =  new Ligne("6", "Fourchêne - Radieuse - République", "Gare SNCF - Delestraint - CCIM", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED), R.color.magenta);
		lignes.put(ligne6.getNom(), ligne6);
		
		Ligne ligne7 =  new Ligne("7", "Séné Ajoncs - CCIM", "République - Gare SNCF", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN), R.color.vert);
		lignes.put(ligne7.getNom(), ligne7);
		
		Ligne ligne8 =  new Ligne("8", "Plescop - Vannes", "Saint-Nolff", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN), R.color.vert_fonce);
		lignes.put(ligne8.getNom(), ligne8);
		
		Ligne ligne9 =  new Ligne("9", "Meucon", "Vannes", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE), R.color.rose);
		lignes.put(ligne9.getNom(), ligne9);
		
		Ligne ligne10 =  new Ligne("10", "Theix - Vannes", "Ploeren", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE), R.color.bleu);
		lignes.put(ligne10.getNom(), ligne10);
		
		Log.d("res: ",""+ Ligne.getNbLignes());
	*/
	}
	
	protected void chargerArrets(Context c){
		/*
		Arret cassard = new Arret(new LatLng(47.636847, -2.778987), "Cassard", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret surville = new Arret(new LatLng(47.636586, -2.774759), "Surville", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret ponant = new Arret(new LatLng(47.638755, -2.777463), "Ponant", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret cliscouet = new Arret(new LatLng(47.639977, -2.766433), "Cliscouët", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret suffren = new Arret(new LatLng(47.641524, -2.776208), "Suffren", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret iut = new Arret(new LatLng(47.643885, -2.777744), "Iut", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret armorique = new Arret(new LatLng(47.647015, -2.778537), "Armorique", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret pleinCiel = new Arret(new LatLng(47.648945, -2.777347), "Plein ciel", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 6")));
		Arret kerarden = new Arret(new LatLng(47.649126, -2.774171), "Kerarden", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 6")));
		Arret radieuse = new Arret(new LatLng(47.648967, -2.770534), "Radieuse", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 3", "Ligne 6")));
		Arret fromentin = new Arret(new LatLng(47.649082, -2.767143), "Frometin", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 6")));
		Arret narhouet = new Arret(new LatLng(47.648641, -2.764418), "Narhouët", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret france = new Arret(new LatLng(47.646936, -2.764397), "France", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret valery = new Arret(new LatLng(47.646394, -2.762165), "Valéry", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 10")));
		Arret ampere = new Arret(new LatLng(47.646480, -2.760170), "Ampère", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 10")));
		Arret lePort = new Arret(new LatLng(47.653187, -2.759376), "Le port", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 2", "Ligne 5", "Ligne 7", "Ligne 10", "Ligne 10")));
		Arret republique = new Arret(new LatLng(47.656129, -2.759773), "République", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 2", "Ligne 3", "Ligne 4", "Ligne 5", "Ligne 6", "Ligne 7", "Ligne 8", "Ligne 10")));
		Arret leBrix = new Arret(new LatLng(47.659012, -2.759118), "Le Brix", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 3", "Ligne 4", "Ligne 6", "Ligne 7", "Ligne 8")));
		Arret hugo = new Arret(new LatLng(47.661014, -2.757327), "Hugo", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 3", "Ligne 4", "Ligne 6", "Ligne 7", "Ligne 8", "Ligne 9")));
		Arret pontSncf = new Arret(new LatLng(47.663449, -2.755953), "Pont SNCF", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 4", "Ligne 6", "Ligne 7", "Ligne 8", "Ligne 9")));
		Arret bourdonnaye = new Arret(new LatLng(47.666484, -2.756565), "Bourdonnaye", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 9")));
		Arret brizeux = new Arret(new LatLng(47.668052, -2.756887), "Brizeux", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 9")));
		Arret stGuen = new Arret(new LatLng(47.670609, -2.757251), "St Guen", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 9")));
		Arret moulin = new Arret(new LatLng(47.673210, -2.759623), "Moulin", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret degas = new Arret(new LatLng(47.673513, -2.762798), "Degas", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret bonnard = new Arret(new LatLng(47.672444, -2.764590), "Bonnard", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret kerizac = new Arret(new LatLng(47.672451, -2.767197), "Kerizac", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret claudeMonet = new Arret(new LatLng(47.672451, -2.768794), "Claude Monet", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret saintExupery = new Arret(new LatLng(47.675575, -2.770479), "Saint-Exupéry", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret montherlant = new Arret(new LatLng(47.677337, -2.767850), "Montherlant", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret gauguin = new Arret(new LatLng(47.677207, -2.763623), "Gauguin", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret menimur = new Arret(new LatLng(47.677381, -2.761230), "ménimur", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 9")));
		Arret kereliza = new Arret(new LatLng(47.680256, -2.763687), "Keréliza", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 9")));
		Arret kerniol = new Arret(new LatLng(47.682047, -2.765264), "Kerniol", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1", "Ligne 9")));
		Arret papin = new Arret(new LatLng(47.677236, -2.771991), "Papin", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret audic = new Arret(new LatLng(47.677713, -2.774781), "Audic", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		Arret tenenio = new Arret(new LatLng(47.680133, -2.773633), "Ténénio", "Vannes", new ArrayList<String>(Arrays.asList("Ligne 1")));
		
		
		
		
		arrets.put(cassard.toString(), cassard);
		arrets.put(surville.toString(), surville);
		arrets.put(ponant.toString(), ponant);
		arrets.put(pleinCiel.toString(), pleinCiel);
		arrets.put(cliscouet.toString(), kerarden);
		arrets.put(suffren.toString(), suffren);
		arrets.put(iut.toString(), iut);
		arrets.put(armorique.toString(), armorique);
		arrets.put(pleinCiel.toString(), pleinCiel);
		arrets.put(kerarden.toString(), kerarden);
		arrets.put(radieuse.toString(), radieuse);
		arrets.put(fromentin.toString(), fromentin);
		arrets.put(narhouet.toString(), narhouet);
		arrets.put(france.toString(), france);
		arrets.put(valery.toString(), valery);
		arrets.put(ampere.toString(), ampere);
		arrets.put(lePort.toString(), lePort);
		arrets.put(republique.toString(), republique);
		arrets.put(leBrix.toString(), leBrix);
		arrets.put(hugo.toString(), hugo);
		arrets.put(pontSncf.toString(), pontSncf);
		arrets.put(bourdonnaye.toString(), bourdonnaye);
		arrets.put(brizeux.toString(), brizeux);
		arrets.put(stGuen.toString(), stGuen);
		arrets.put(moulin.toString(), moulin);
		arrets.put(degas.toString(), degas);
		arrets.put(bonnard.toString(), bonnard);
		arrets.put(kerizac.toString(), kerizac);
		arrets.put(claudeMonet.toString(), claudeMonet);	
		arrets.put(saintExupery.toString(), saintExupery);
		arrets.put(montherlant.toString(), montherlant);
		arrets.put(gauguin.toString(), gauguin);
		arrets.put(menimur.toString(), menimur);
		arrets.put(kereliza.toString(), kereliza);
		arrets.put(kerniol.toString(), kerniol);
		arrets.put(papin.toString(), papin);
		arrets.put(audic.toString(), audic);
		arrets.put(tenenio.toString(), tenenio);
		*/
	}

	public String toString(){
		return "VANNES";
	}
	
}
