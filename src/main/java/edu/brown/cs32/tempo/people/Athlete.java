package edu.brown.cs32.tempo.people;
import java.util.Collection;
import java.util.HashSet;

import edu.brown.cs32.tempo.workout.Workout;

public class Athlete extends Person {
	// The workouts for this athlete
	private Collection<Workout> workouts;
	
	public Athlete(String email, String name, String location){
		super(email, name, location);
		workouts = new HashSet<Workout>();
	}
	
	/**
	 * Returns all the workouts for a given athlete
	 * @return this athlete's workouts
	 */
	public Collection<Workout> getWorkouts(){
		return workouts;
	}
	
	/**
	 * Adds one workout
	 * @param w - the workout to be added
	 * @return - true if added, otherwise false
	 */
	public boolean addWorkouts(Workout w){
		return workouts.add(w);
	}
	
	/**
	 * Adds multiple workouts
	 * @param w - workouts to be added
	 * @return - true if added, otherwise false
	 */
	public boolean addWorkouts(Collection<Workout> w){
		return workouts.addAll(w);
	}
	
	/**
	 * Remove a specific workout
	 * 
	 * @param w
	 *            - workout to be removed
	 * @return - true if removed, otherwise false
	 */
	public boolean removeWorkouts(Workout w){
		return workouts.remove(w);
	}
	
	/**
	 * Remove all workouts for the athlete
	 */
	public void clearWorkouts(){
		workouts.clear();
	}
}
