package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.workout.Workout;

public class Athlete extends Person {
  // The workouts for this athlete
  @Expose
  private Collection<Workout> workouts;
  private Collection<Team> teams;
  @Expose
  private PhoneNumber number;

  public Athlete(String id, String email, String name, PostalCode location) {
    super(id, email, name, location);
    workouts = new HashSet<Workout>();
    teams = new HashSet<Team>();
  }
  
  public Athlete(String id, String email, String name, PostalCode location, PhoneNumber number) {
    super(id, email, name, location);
    workouts = new HashSet<Workout>();
    teams = new HashSet<Team>();
    this.number = number;
  }

  /**
   * Returns all the workouts for a given athlete
   * 
   * @return this athlete's workouts
   */
  public Collection<Workout> getWorkouts() {
    return workouts;
  }

  public Collection<Workout> getWorkouts(Date from) {
    Collection<Workout> ret = new HashSet<Workout>();
    for (Workout w : workouts) {
      if (w.getDate().after(from))
        ret.add(w);
    }

    return ret;
  }

  public Collection<Workout> getWorkouts(Date from, Date to) {
    Collection<Workout> ret = new HashSet<Workout>();
    for (Workout w : workouts) {
      if (w.getDate().after(from) && w.getDate().before(to))
        ret.add(w);
    }

    return ret;
  }

  /**
   * Adds one workout
   * 
   * @param w
   *          - the workout to be added
   * @return - true if added, otherwise false
   */
  public boolean addWorkouts(Workout w) {
    return workouts.add(w);
  }

  /**
   * Adds multiple workouts
   * 
   * @param w
   *          - workouts to be added
   * @return - true if added, otherwise false
   */
  public boolean addWorkouts(Collection<Workout> w) {
    return workouts.addAll(w);
  }

  /**
   * Remove a specific workout
   * 
   * @param w
   *          - workout to be removed
   * @return - true if removed, otherwise false
   */
  public boolean removeWorkouts(Workout w) {
    return workouts.remove(w);
  }

  /**
   * Remove all workouts for the athlete
   */
  public void clearWorkouts() {
    workouts.clear();
  }

  public boolean addTeam(Team t) {
    return teams.add(t);
  }

  public boolean addTeam(Collection<Team> t) {
    return teams.addAll(t);
  }

  public boolean removeTeam(Team t) {
    return teams.remove(t);
  }

  public boolean removeTeam(Collection<Team> t) {
    return teams.removeAll(t);
  }

  public void clearTeam() {
    teams.clear();
  }

  public Collection<Team> getTeam() {
    return teams;
  }

  public void setTeam(Collection<Team> teams) {
    this.teams = teams;
  }

  public PhoneNumber getNumber() {
    return number;
  }

  public void setNumber(PhoneNumber number) {
    this.number = number;
  }
}
