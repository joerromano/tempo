package datasource;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.PhoneNumber;
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
   * @param email
   * @param number
   * @param name
   * @param location
   * @return The update Athlete
   */
  Athlete addMember(Team t, String email, String number, String name,
      PostalCode location);

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

  /**
   * Updates a group's workouts by id.
   *
   * @param g
   *          A group
   * @param workouts
   *          A list of workout id's (as strings)
   * @return The update group
   */
  Group updateWorkouts(Group g, List<String> workouts);

  /**
   * Deletes a group.
   *
   * @param id
   *          A group id
   * @return True if there was no error, false otherwise
   */
  boolean deleteGroupById(String id);

  /**
   * Updates a workout with a given id.
   *
   * @param workoutId
   *          String workout id
   * @param w
   *          The new updated workout
   * @return
   */
  Workout updateWorkout(String workoutId, Workout w);

  /**
   * Adds a workout to a group.
   *
   * @param g
   *          The group
   * @param w
   *          The workout to be added
   * @return Returns the group
   */
  Group addWorkout(Group g, Workout w);

  /**
   * Renames a team.
   *
   * @param t
   *          The team to be renamed
   * @param newName
   *          The new name
   * @return True if the team was successfully renamed, false otherwise
   */
  boolean renameTeam(Team t, String newName);

  /**
   * Disbands a team.
   *
   * @param t
   *          The team to be disbanded (deleted)
   * @return True if the team was deleted, false otherwise
   */
  boolean disbandTeam(Team t);

  /**
   * Deletes a coach and all associated teams.
   *
   * @param c
   *          The coach
   * @return True if the coach was successfully deleted, false otherwise
   */
  boolean deleteCoach(Coach c);

  /**
   * Updates a coach's password.
   *
   * @param c
   *          The coach
   * @param oldPwd
   *          The old password as a string
   * @param newPwd
   *          The new password as a string
   * @return True for success, false for failure
   */
  boolean updatePassword(Coach c, String oldPwd, String newPwd);

  /**
   * Updates a coach's name.
   *
   * @param c
   *          The coach
   * @param name
   *          The new name
   * @return True for success, false for failure
   */
  boolean updateName(Coach c, String name);

  /**
   * Updates a coach's phone number.
   *
   * @param c
   *          The coach
   * @param phone
   *          The new phone number (as a PhoneNumber object)
   * @return true for success, false for failure
   */
  boolean updatePhone(Coach c, PhoneNumber phone);

  /**
   * Adds a team to a coach.
   * 
   * @param c
   *          The coach
   * @param name
   *          The name of the team to be added
   * @return The created team
   */
  Team addTeam(Coach c, String name);

  /**
   * Edit athlete information
   * 
   * @param id
   *          Unique string id
   * @param name
   *          String name
   * @param number
   *          String phone number
   * @param email
   *          String Email Address
   * @param location
   *          A PostalCode
   * @return The updated athlete
   */
  Athlete editAthlete(String id, String name, String number, String email,
      PostalCode location);

  /**
   * Creates a new account/coach.
   *
   * @param name
   *          The coach's name
   * @param email
   *          The new email
   * @param loc
   *          The PostalCode
   * @param pwd
   *          The coach's password
   * @return The new coach object
   */
  Coach addCoach(String name, String email, PostalCode loc, String pwd);

  /**
   * Removes an athlete from a team
   * 
   * @param t
   *          The team
   * @param id
   *          The athlete id
   * @return True if the athlete was successfully removed, otherwise false
   */
  boolean removeAthlete(Team t, String id);
}
