package datas;

import java.util.Calendar;

import android.text.format.Time;
import android.util.Log;

public class Horaire extends Time implements Comparable<Horaire>{
	
	private int id, idAuteur;
	
	/**
	 * Constructeur pour envoyer des données
	 * @param id
	 * @param horaire
	 * @param idAuteur
	 */
	
	public Horaire(int id, String horaire){
		this.format("hh:mm");
		this.hour=Integer.parseInt(horaire.substring(0, 2));
		this.minute=Integer.parseInt(horaire.substring(3, 5));
		this.idAuteur=-1;
		this.id=id;
	}
	
	public Horaire(int id, String horaire, int idAuteur){
		this.format("hh:mm");
		this.hour=Integer.parseInt(horaire.substring(0, 2));
		this.minute=Integer.parseInt(horaire.substring(3, 5));
		this.idAuteur=idAuteur;
		this.id=id;
	}
	/**
	 * Constructeur pour recevoir des données
	 * @param id
	 * @param horaire
	 * @param idAuteur
	 */
	public Horaire(int h, int m, int idAuteur){
		this.format("hh:mm");
		this.hour=h;
		this.minute=m;
		this.idAuteur=idAuteur;
		id=-1;
	}
	
	public int getIdAuteur(){
		return this.idAuteur;
	}
	
	public static String getDayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		
		String dayOfWeek=null;
		
		switch (day) {
		    case Calendar.SUNDAY:
		        dayOfWeek="dimanche";
		    break;
		    case Calendar.SATURDAY:
		        dayOfWeek="samedi";
		    break;
		    default:
		    	dayOfWeek="semaine";
		    break;
		}
		return dayOfWeek;
	}
	
	public String toString(){
		String ret="";
		if(hour<10){
			ret+="0";
		}
		ret+=hour+":";
		if(minute<10){
			ret+="0";
		}
		ret+=minute;
		return ret;
	}
	
	public int getId(){
		return id;
	}

	@Override
	public int compareTo(Horaire another) {
		// TODO Auto-generated method stub
		return this.toString().compareTo(another.toString());
	}

}
