package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.workout.Workout;

public class Group {
  // Once a group is created, its members are immutable
  @Expose
  private Collection<Athlete> members;
  private ListMultimap<Date, Workout> workouts;
  @Expose
  private Date date;
  @Expose
  private String name;
  @Expose
  private String id;
  private Team team;

  public Group(String name, Date date, String id) {
    this.name = name;
    this.members = new HashSet<Athlete>();
    this.workouts = ArrayListMultimap.create();
    this.date = date;
    this.setId(id);
  }
  
  public Group(String name, Date date, String id, Team team) {
	    this.name = name;
	    this.members = new HashSet<Athlete>();
	    this.workouts = ArrayListMultimap.create();
	    this.date = date;
	    this.setId(id);
	    this.team = team;
	  }

  public Group(Collection<Athlete> members, Date date, String id) {
    this.members.addAll(members);
    this.workouts = ArrayListMultimap.create();
    this.date = date;
    this.setId(id);
  }

  public Group(Collection<Athlete> members, Collection<Workout> workouts,
      Date date, String id) {
    members.addAll(members);
    this.workouts = ArrayListMultimap.create();
    addWorkout(workouts);
    this.date = date;
    this.setId(id);
  }

  public void setWorkouts(Collection<Workout> workouts) {
    this.workouts = ArrayListMultimap.create();
    this.addWorkout(workouts);
  }

  public void addWorkout(Collection<Workout> workouts) {
    for (Workout w : workouts) {
      this.workouts.put(w.getDate(), w);
    }
  }

  public void addWorkout(Workout workout) {
    this.workouts.put(workout.getDate(), workout);
  }

  public Collection<Workout> getWorkout() {
    return workouts.values();
  }

  public Collection<Workout> getWorkoutOn(Date on) {
    return workouts.get(on);
  }

  public Collection<Workout> getWorkouts(Date from) {
    Collection<Workout> ret = new HashSet<Workout>();
    for (Workout w : workouts.values()) {
      if (w.getDate().after(from))
        ret.add(w);
    }

    return ret;
  }

  public Collection<Workout> getWorkouts(Date from, Date to) {
    Collection<Workout> ret = new HashSet<Workout>();
    for (Workout w : workouts.values()) {
      if (w.getDate().after(from) && w.getDate().before(to))
        ret.add(w);
    }

    return ret;
  }

  public void clearAllWorkouts() {
    workouts.clear();
  }

  public double getAgony() {
    double miles = 0.0;
    for (Workout w : workouts.values()) {
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

  public Collection<Athlete> getMembers() {
    return members;
  }

  public void setMembers(Collection<Athlete> members) {
    this.members = members;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

 public Team getTeam(){
	 return team;
 }
 
 public void setTeam(Team team){
	 this.team = team;
 }
}
