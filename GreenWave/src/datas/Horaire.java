package datas;

import android.text.format.Time;

public class Horaire extends Time{
	
	private int h, m;
	
	public Horaire(String horaire){
		this.format("hh:mm");
	}
	
	public Horaire(int h, int m){
		this.format("hh:mm");
		this.h=h;
		this.m=m;
	}

}
