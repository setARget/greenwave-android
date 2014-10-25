package datas;

import com.google.android.gms.maps.model.LatLng;

public class Place {
	private String name;
	private LatLng latlng;
	private String id;
	
	public Place(String name, LatLng latlng){
		this.name=name;
		this.latlng=latlng;
	}
	
	public Place(String name, String placeID, LatLng latlng){
		this.name=name;
		this.latlng=latlng;
		this.id=placeID;
	}
	
	public LatLng getLatLng(){
		return latlng;
	}
	
	public void setLatLng(LatLng newLatLng){
		this.latlng=newLatLng;
	}
	
	public String toString(){
		return name;
	}
	
	public String getID(){
		return id;
	}
}
