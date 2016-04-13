package datasource;

import java.util.Set;

import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public interface Datasource {

  /**
   * Returns the workout with some id.
   *
   * @param id
   *          Unique String identifier
   * @return A workout
   */
  Workout getWorkout(String id);

  /**
   * Returns the team with some id.
   *
   * @param id
   *          Unique String identifier
   * @return A team
   */
  Team getTeam(String id);

  /**
   * Return all groups associated with a team and date
   *
   * @param team
   *          String team ID
   * @param date
   *          String date TODO how to format?
   * @return A set of groups
   */
  Set<Group> getGroups(String team, String date);
}
