package datasource;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.brown.cs32.tempo.people.Athlete;
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
   * Return all groups associated with a team between start and end dates
   *
   * @param team
   *          team
   * @param start
   *          Starting date
   * @param end
   *          Ending date
   * @return A set of groups
   */
  Collection<Group> getGroups(Team team, Date start, Date end);

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

  /**
   * Adds a training group to a team. The group would start at the given date
   * and end a week later.
   *
   * @param t
   *          A team
   * @param name
   *          The name of the training group
   * @param start
   *          The start date of the training group
   * @return The group that was added
   */
  Group addGroup(Team t, String name, Date start);

  /**
   * Adds an athlete to a team.
   *
   * @param t
   *          A team
   * @param a
   *          An athlete
   * @return The athlete that was added
   */
  Athlete addMember(Team t, Athlete a);

  /**
   * Returns the group associated w/an ID.
   *
   * @param groupId
   *          String group ID
   * @return A group
   */
  Group getGroup(String groupId);

  /**
   * Renames a group.
   *
   * @param g
   *          A group
   * @param newName
   *          The group's new name
   * @return the renamed group
   */
  Group renameGroup(Group g, String newName);

  /**
   * Updates a group's members
   *
   * @param g
   *          A group
   * @param athletes
   *          A list of athlete id's
   * @return the updated group
   */
  Group updateMembers(Group g, List<String> athletes);
  
  Coach getCoach(String id);
}
