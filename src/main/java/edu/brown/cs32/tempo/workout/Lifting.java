package edu.brown.cs32.tempo.workout;

import java.util.Date;

import edu.brown.cs32.tempo.location.PostalCode;

public class Lifting extends Workout{
	public Lifting(Date date, int intensity, PostalCode location, String type, String coachComment, double score,
			String time) {
		super(date, intensity, location, type, coachComment, score, time);
	}

	private int reps;
	private int sets;
	
	public int getSets() {
		return sets;
	}
	public void setSets(int sets) {
		this.sets = sets;
	}
	public int getReps() {
		return reps;
	}
	public void setReps(int reps) {
		this.reps = reps;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toHTML() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
