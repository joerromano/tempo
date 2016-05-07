package jsonWrappers;

import java.util.Collection;
import java.util.Date;

import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.workout.Workout;

public class GroupWrapper {
  private Collection<Athlete> members;
  private Collection<Workout> workouts;
  private Date date;
  private String name;
  private String id;

  public GroupWrapper(Group g) {
    this.members = g.getMembers();
    this.workouts = g.getWorkout();
    this.date = g.getDate();
    this.name = g.getName();
    this.id = g.getId();
  }
}