package edu.brown.cs32.tempo.workout;

import java.util.Date;

import edu.brown.cs32.tempo.location.PostalCode;

public abstract class Workout {
	private Date date;
	private int intensity;
	private PostalCode location;
	private String type;
	private String coachComment;
	private double score;
	private String time;
	
	
	private final static String AM = "AM";
	private final static String PM = "PM";
	
	public Workout(Date date, int intensity, PostalCode location, 
			String type, String coachComment, double score, String time){
		this.date = date;
		this.intensity = intensity;
		this.location = location;
		this.type = type;
		this.coachComment = coachComment;
		this.score = score;
		this.time = time;
	}
	
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
	
	public void setLocation(PostalCode location){
		this.location = location;
	}
	
	public PostalCode getLocation(){
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
