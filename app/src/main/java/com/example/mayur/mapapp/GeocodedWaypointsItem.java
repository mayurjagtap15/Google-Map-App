package com.example.mayur.mapapp;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class GeocodedWaypointsItem{

	@SerializedName("types")
	private List<String> types;

	@SerializedName("geocoder_status")
	private String geocoderStatus;

	@SerializedName("partial_match")
	private boolean partialMatch;

	@SerializedName("place_id")
	private String placeId;

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setGeocoderStatus(String geocoderStatus){
		this.geocoderStatus = geocoderStatus;
	}

	public String getGeocoderStatus(){
		return geocoderStatus;
	}

	public void setPartialMatch(boolean partialMatch){
		this.partialMatch = partialMatch;
	}

	public boolean isPartialMatch(){
		return partialMatch;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"GeocodedWaypointsItem{" + 
			"types = '" + types + '\'' + 
			",geocoder_status = '" + geocoderStatus + '\'' + 
			",partial_match = '" + partialMatch + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}