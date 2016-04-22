package edu.brown.cs32.tempo.workout;

import java.util.Collection;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import edu.brown.cs32.tempo.people.Athlete;

interface Suggestions {

	public final static int NUM_WORKOUTS = 5;

	public static Collection<Workout> getSuggestions(Collection<Athlete> athletes, Date date) {
		ArrayList<Workout> retW = new ArrayList<Workout>();
		for (Athlete a : athletes) {
			retW.addAll(a.getWorkouts());
		}

		if (retW.size() > 4) {
			return retW.subList(0, Suggestions.NUM_WORKOUTS);
		} else {
			return retW.subList(0, retW.size());
		}
	}

	public static Collection<Workout> getSuggestions(Athlete athlete, Date date) {
		ArrayList<Workout> retW = new ArrayList<Workout>(athlete.getWorkouts());
		if (retW.size() > 4) {
			return retW.subList(0, Suggestions.NUM_WORKOUTS);
		} else {
			return retW.subList(0, retW.size());
		}
	}
}
