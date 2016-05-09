package edu.brown.cs32.tempo.workout;

import java.util.Date;

import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.location.PostalCode;

public class Workout {
	@Expose
	private String id;
	@Expose
	private Date date;
	@Expose
	private int intensity;
	@Expose
	private PostalCode location;
	@Expose
	private String type;
	@Expose
	private double score;
	@Expose
	private String time;

	public final static String AM = "AM";
	public final static String PM = "PM";

	public Workout(String id, Date date, int intensity, PostalCode location, String type, double score, String time) {
		this.id = id;

		this.date = date;
		this.intensity = intensity;
		this.location = location;
		this.type = type;
		this.score = score;
		this.time = time;
	}

	public int getIntensity() {
		return intensity;
	}

	public void setIntensity(int intensity) {
		this.intensity = intensity;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setLocation(PostalCode location) {
		this.location = location;
	}

	public PostalCode getLocation() {
		return location;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return String.format("ID: %s Date: %s Intensity: %d Location: %s Score: %1.2f", id, date.toString(), intensity,
				location, score);
	}

	public String toHTML() {
		return "";
	}

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean sameWorkout(Workout w) {
		return score == w.getScore() &&
				intensity == w.getIntensity() &&
				type.equals(w.getTime()) && 
				time.equals(w.getTime());
	}
}
