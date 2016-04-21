package datasource;

import java.util.Set;

import edu.brown.cs32.tempo.people.Coach;
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

  /**
   * Password authentication. Returns null if no coach exists/bad password
   *
   * @param email
   *          A string email
   * @param pwd
   *          A string password
   * @return The Coach associated w/that email, or null if a failure occurs
   */
  Coach authenticate(String email, String pwd);
}
