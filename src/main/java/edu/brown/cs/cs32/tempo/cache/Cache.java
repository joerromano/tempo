package edu.brown.cs.cs32.tempo.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.workout.Workout;

/**
 * Representation of a database cache.
 */
public abstract class Cache {
  /**
   * a Map from group to workouts. TODO is necessary?
   */
  public final static Map<Group, Collection<Workout>> groupToWorkouts = new HashMap<>();
  /**
   * TODO is necessary? a Map from group IDs to its athletes.
   */
  public final static Map<Group, Collection<Athlete>> groupToAthletes = new HashMap<>();
  /**
   * TODO is necessary? a Map from way athletes to their workouts.
   */
  public final static Map<Athlete, Collection<Workout>> athleteToWorkouts = new HashMap<>();

}
