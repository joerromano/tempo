package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import edu.brown.cs32.tempo.workout.Workout;

public class Group {
	// Once a group is created, its members are immutable
	private Collection<Athlete> members;
	private ListMultimap<Date, Workout> workouts;
	private Date date;
	private String name;
	
	public Group(String name, Date date){
		this.members = new HashSet<Athlete>();
		this.workouts = ArrayListMultimap.create();
		this.date = date;
	}
	
	public Group(Collection<Athlete> members, Date date){
		this.members.addAll(members);
		this.workouts = ArrayListMultimap.create();
		this.date = date;
	}
	
	public Group(Collection<Athlete> members, Collection<Workout> workouts, Date date){
		members.addAll(members);
		this.workouts = ArrayListMultimap.create();
		addWorkout(workouts);
		this.date = date;
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
	
	public Collection<Workout> getWorkoutOn(Date on){
		return workouts.get(on);
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
	
	public void clearAllWorkouts(){
		workouts.clear();
	}
	
	public double getAgony(){
		double miles = 0.0;
		for(Workout w : workouts.values()){
			miles += w.getScore();
		}
		return miles;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
