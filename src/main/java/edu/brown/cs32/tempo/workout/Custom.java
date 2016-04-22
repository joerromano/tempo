package edu.brown.cs32.tempo.workout;

import java.util.Date;

import edu.brown.cs32.tempo.location.PostalCode;

public class Custom extends Workout{

	public Custom(Date date, int intensity, PostalCode location, String type, String coachComment, double score,
			String time) {
		super(date, intensity, location, type, coachComment, score, time);
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
