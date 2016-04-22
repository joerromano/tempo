package edu.brown.cs32.tempo.workout;

import java.util.Date;

import edu.brown.cs32.tempo.location.PostalCode;

public class Run extends Workout{
	public Run(Date date, int intensity, PostalCode location, String type, String coachComment, double score,
			String time) {
		super(date, intensity, location, type, coachComment, score, time);
	}

	private double mileage;	
	
	public void setMileage(double mileage){
		this.mileage = mileage;
	}
	
	public double getMileage(){
		return mileage;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public String toHTML() {
		return null;
	}

}
