package jsonWrappers;

import java.util.Collection;
import java.util.Date;

import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.workout.Workout;

public class GroupWrapper {
  @Expose
  private Collection<Athlete> members;
  @Expose
  private Collection<Workout> workouts;
  @Expose
  private Date date;
  @Expose
  private String name;
  @Expose
  private String id;

  public GroupWrapper(Group g) {
    this.members = g.getMembers();
    this.workouts = g.getWorkout();
    this.date = g.getDate();
    this.name = g.getName();
    this.id = g.getId();
  }
}