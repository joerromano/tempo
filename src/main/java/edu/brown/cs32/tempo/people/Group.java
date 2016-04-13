package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import edu.brown.cs32.tempo.workout.Workout;

public class Group {
	// Once a group is created, its members are immutable
	private Collection<Athlete> members;
	private HashMap<Date, Workout> workouts;
	
	public Group(Collection<Athlete> members){
		this.members.addAll(members);
		workouts = new HashMap<Date,Workout>();
	}
	
	public Group(Collection<Athlete> members, Collection<Workout> workouts){
		members.addAll(members);
		this.workouts = new HashMap<Date,Workout>();
		addWorkout(workouts);
	}
	
	public void addWorkout(Collection<Workout> workouts){
		for(Workout w : workouts){
			this.workouts.put(w.getDate(), w);
		}
	}
	
	public void addWorkout(Workout workout){
		this.workouts.put(workout.getDate(), workout);
	}
	
	public Collection<Workout> getWorkout(){
		return workouts.values();
	}
	
	public Collection<Workout> getWorkouts(Date from){
		Collection<Workout> ret = new HashSet<Workout>();
		for(Workout w : workouts.values()){
			if(w.getDate().after(from))
				ret.add(w);
		}
		
		return ret;
	}
	
	public Collection<Workout> getWorkouts(Date from, Date to){
		Collection<Workout> ret = new HashSet<Workout>();
		for(Workout w : workouts.values()){
			if(w.getDate().after(from) && w.getDate().before(to))
				ret.add(w);
		}
		
		return ret;
	}
	
	public double getAgony(){
		double miles = 0.0;
		for(Workout w : workouts.values()){
			miles += w.getScore();
		}
		return miles;
	}
}
