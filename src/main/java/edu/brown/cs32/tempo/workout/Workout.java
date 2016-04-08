package edu.brown.cs32.tempo.workout;

import java.util.Date;

public abstract class Workout {
	private Date date;
	private int intensity;
	private String location;
	private String type;
	private String coachComment;
	private double mileage;	
	
	public void setCoachComment(String coachComment){
		this.coachComment = coachComment;
	}
	
	public String getCoachComment(){
		return coachComment;
	}
	
	public void setIntensity(int intensity){
		this.intensity = intensity;
	}
	
	public int getInensity(){
		return intensity;
	}

	public void setType(String type){
		this.type = type;
	}
	
	public String getType(){
		return type;
	}

	public void setMileage(double mileage){
		this.mileage = mileage;
	}
	
	public double getMileage(){
		return mileage;
	}
	
	public void setLocation(String location){
		this.location = location;
	}
	
	public String getLocation(){
		return location;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}

	public abstract String toString();
	
	public abstract String toHTML();
}
