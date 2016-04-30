package datasource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.brown.cs.cs32.tempo.SparkServer;
import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

/**
 * Retrieves and verifies information from the database, according to the
 * specifications of the Datasource interface.
 * 
 * @author lucicooke
 *
 */
public class SQLDatasource implements Datasource {
	public SecureRandom random = new SecureRandom();
	
  /**
   * getWorkout retrieves a workout from the database by id.
   * @param id - the id of the workout.
   * @return Workout - the workout.
   */
  @Override
  public Workout getWorkout(String id) {
    // TODO : check cache?
    String query = "SELECT * FROM workout " + "WHERE id = ?;";
    String workout_id = null;
    String date = null;
    int intensity = -1;
    String location = null;
    String type = null;
    double score = -1;
    String time = null;
    try (PreparedStatement ps = 
    		Db.getConnection().prepareStatement(query)) {
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        workout_id = rs.getString(1);
        date = rs.getString(2);
        intensity = rs.getInt(3);
        location = rs.getString(4);
        type = rs.getString(5);
        score = rs.getDouble(6);
        time = rs.getString(7);
      } else {
      	String message = String.format(
            "ERROR: [getWorkout] " + "No workout in the database with id: %s", id);
        throw new IllegalArgumentException(message);
      }
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (getWorkout)");
      System.exit(1);
    }
    assert (workout_id != null);
    assert (date != null);
    assert (intensity != -1);
    assert (location != null);
    assert (type != null);
    assert (score != -1);
    assert (time != null);
    PostalCode workout_location = new PostalCode(location);
    Date workout_date = null;
    try {
      workout_date = SparkServer.MMDDYYYY.parse(date);
    } catch (ParseException e) {
      System.out.println("ERROR: ParseException triggered (getWorkout)");
    }
    Workout toReturn = new Workout(workout_id, workout_date, intensity, workout_location,
        type, score, time); 
    return toReturn;
  }

  /**
   * getTeam retrieves a team from the database by id. 
   * The coach of the team must exist in the database
   * for this method to work.
   * @param id - the id of the team.
   * @return - the team with the given id.
   */
  @Override
  public Team getTeam(String id) {
  	// TODO : get coach
    // TODO : cache?
    String query = "SELECT * FROM team " + "WHERE id = ?;";
    String team_id = null;
    String name = null;
    String coach_id = null;
    String location = null;
    boolean pub_priv = false;
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        team_id = rs.getString(1);
        name = rs.getString(2);
        coach_id = rs.getString(3);
        location = rs.getString(4);
        pub_priv = rs.getBoolean(5);
      } else {
        String message = String.format(
            "ERROR: [getTeam] " + "No team in the database with id: %s", id);
        throw new IllegalArgumentException(message);
      }

    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (getTeam)");
      System.exit(1);
    }
    assert (team_id != null);
    assert (name != null);
    assert (coach_id != null);
    assert (location != null);
    return new Team(team_id, name, new PostalCode(location), getCoach(coach_id), pub_priv);
  }

  /**
   * authenticate checks for the correct password in the database. NOTE : this
   * returns a coach with the base information. Does not contain information
   * about which teams this coach is associated with. For that, call 'getTeams'
   */
  @Override
  public Coach authenticate(String email, String pwd) {
    // TODO : how are we storing passwords in the database
    String query = "SELECT * FROM coach " + "WHERE email = ?;";
    String coach_pwd = null;
    String name = null;
    String location = null;
    String coach_id = null;
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, email);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        coach_id = rs.getString(1);
        name = rs.getString(2);
        location = rs.getString(4);
        coach_pwd = rs.getString(5);
      } else {
        String message = String.format(
            "ERROR: [getTeam] " + "No coach in the database with email: %s",
            email);
        throw new IllegalArgumentException(message);
      }

    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (getCoach)");
      System.exit(1);
    }
    assert (coach_pwd != null);
    assert (name != null);
    assert (location != null);
    assert (coach_id != null);
    if (coach_pwd.equals(pwd)) {
      PostalCode pc = new PostalCode(location);
      return new Coach(coach_id, email, name, pc);
    } else {
    	String message = String.format(
          "ERROR: [authenticate] " + "Incorrect password for email: %s",
          email);
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * addGroup adds a group to the database. The agony field is set to -1
   * because we're not using that currently. This will also generate
   * a random ID for the group. The given team must exist in the database
   * for the group to be added. If not, this will throw an 
   * IllegalArgumentException.
   * 
   * This inserts a row into group_table, and a row in team_group. 
   * @param t - the team
   * @param name - the name of the group
   * @param start - the date at which the group starts
   * @return - the group, if it has been added.
   */
  @Override
  public Group addGroup(Team t, String name, Date start) {
  	String newID = new BigInteger(80, random).toString(32);
  	try {
  		getTeam(t.getId());
  		System.out.println("team id retrieved");
  	} catch (IllegalArgumentException e) {
  		String message = String.format(
          "ERROR: [addGroup] " + "No team exists for id: %s",
          t.getId());
      throw new IllegalArgumentException(message);
  	}
    String query = "INSERT INTO group_table VALUES(?, ?, ?, ?);";
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, newID);
      ps.setString(2, start.toString());
      ps.setString(3, name);
      ps.setInt(4, -1);
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addGroup)");
      System.exit(1);
    }
    String query2 = "INSERT INTO team_group VALUES(?,?);";
    try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
    	ps2.setString(1, t.getId());
    	ps2.setString(2, newID);
    	ps2.executeUpdate();
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (addGroup)");
      System.exit(1);
    }
    Group toReturn = new Group(name, start, newID);
    return toReturn;
  }

  /**
   * getGroup returns a group from the database, given an ID.
   * Throws an IllegalArgumentException if the group is not found.
   * @param groupId - the ID of the group to retrieve.
   * @return the group with the corresponding ID.
   */
  @Override
  public Group getGroup(String groupId) {
    String query = "SELECT * FROM group_table WHERE " + "id = ?;";
    String name = null;
    String date = null;
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, groupId);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        name = rs.getString(3);
        date = rs.getString(2);
      } else {
        String message = String.format(
            "ERROR: [getTeam] " + "No group in the database with id: %s",
            groupId);
        throw new IllegalArgumentException(message);
      }
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (getGroup)");
      System.exit(1);
    }
    try {
    	System.out.println("date : " + date);
      return new Group(name, SparkServer.MMDDYYYY.parse(date), groupId);
    } catch (ParseException e) {
      System.out.println("ERROR: ParseException triggered (getGroup)");
      return null;
    }
  }

  /**
   * renameGroup renames an existing group in the database.
   * If the group does not exist, an IllegalArgumentException 
   * is thrown.
   * @param g - the group to rename.
   * @param newName - the new name of the group.
   * @return the updated group.
   */
  @Override
  public Group renameGroup(Group g, String newName) {
  	System.out.println("g id : " + g.getId());
  	try {
  		System.out.println("0");
  		getGroup(g.getId());
  		System.out.println("1");
  	} catch (IllegalArgumentException e) {
  		System.out.println("2");
  		String message = String.format(
          "ERROR: [renameGroup] " + "No group exists for id: %s",
          g.getId());
      throw new IllegalArgumentException(message);
  	}
  	System.out.println("3");
  	//update group_table set name = "new_name" where id="t00qsmem2msb31l7";
    String query = "UPDATE group_table SET name = ? where id = ?";
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, newName);
      ps.setString(2, g.getId());
      ps.executeUpdate();
      System.out.println("4");
    } catch (SQLException e) {
    	System.out.println("5");
      System.out.println("ERROR: SQLException triggered (addWorkout)");
      return null;
    }
    System.out.println("6");
    return new Group(newName, g.getDate(), g.getId());
  }
  
  /**
   * addWorkout adds a workout to the database, as well as to the corresponding
   * group.
   * @param g - the group which the workout corresponds to.
   * @param w - the workout to be added to the database.
   * @return - the group, if added successfully, null if the group does not exist/
   */
  @Override
  public Group addWorkout(Group g, Workout w) {
  	try {
  		getGroup(g.getId());
  	} catch (IllegalArgumentException e) {
  		String message = String.format(
          "ERROR: [getTeam] " + "No group in the database with id: %s",
          g.getId());
      throw new IllegalArgumentException(message);
  	}
  	String query = "INSERT INTO workout(id, date, intensity, location, type, score, time) "
    		+ "VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
    	ps.setString(1, w.getId());
      ps.setString(2, w.getDate().toString());
      ps.setInt(3, w.getIntensity());
      ps.setString(4, w.getLocation().getPostalCode());
      ps.setString(5, w.getType());
      ps.setDouble(6, w.getScore());
      ps.setString(7, w.getTime());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addWorkout)");
      return null;
    }
    String query2 = "INSERT INTO group_workout(g_id, w_id) "
  			+ "VALUES(?,?)";
    try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
    	ps2.setString(1, g.getId());
    	ps2.setString(2, w.getId());
    	ps2.executeUpdate();
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (addWorkout)");
      return null;
    }
    return g;
  }

  /**
   * getCoach returns a coach from the database by ID.
   * If no coach with that ID exists in the database,
   * an IllegalArgumentException is thrown.
   * @param id - the id of the coach.
   * @return Coach - the coach with that ID.
   */
  @Override
  public Coach getCoach(String id) {
  	String query = "SELECT * FROM coach " + "WHERE id = ?;";
  	String coach_id = null;
  	String name = null;
  	String email = null;
  	String location = null;
  	String coach_pwd = null;
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        coach_id = rs.getString(1);
        name = rs.getString(2);
        email = rs.getString(3);
        location = rs.getString(4);
        coach_pwd = rs.getString(5);
      } else {
        String message = String.format(
            "ERROR: [getCoach] " + "No coach in the database with id: %s",
            email);
        throw new IllegalArgumentException(message);
      }
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (getCoach)");
      System.exit(1);
    }
    assert (coach_id != null);
    assert (name != null);
    assert (email != null);
    assert (location != null);
    assert (coach_pwd != null);
    return new Coach(coach_id, email, name, 
    		getPostalCodeFromString(location));
  }
  
  /**
   * getPostalCodeFromString formats the string location returned from
   * the database. Adds zeros as needed to the start of the string.
   * @param location - string zip code.
   * @return - postal code, containing the corrected zip code.
   */
  private PostalCode getPostalCodeFromString(String location) {
  	if (location.length() == 5) {
  		return new PostalCode(location);
  	} else {
  		StringBuilder sb = new StringBuilder();
  		sb.append(location);
  		while (sb.length() < 5) {
  			sb.insert(0, Integer.toString(0));
  		}
  		return new PostalCode(sb.toString());
  	}
  }
  
  // select * from group_table where 
  // id in (select group_id from team_group where team_id="test_team");
  @Override
  public Collection<Group> getGroups(Team team, Date start, Date end) {
  	String query = "SELECT * FROM group_table WHERE id IN "
  			+ "(SELECT group_id FROM team_group WHERE team_id = ?);";
  	Collection<Group> potentialGroups = new ArrayList<Group>();
  	try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
  		ps.setString(1, team.getId());
  		ResultSet rs = ps.executeQuery();
      while (rs.next()) {
      	String group_id = rs.getString(1);
      	Date date = null;
      	try {
					date = SparkServer.MMDDYYYY.parse(rs.getString(2));
				} catch (ParseException e) {
					System.out.println("ERROR: ParseException triggered (getGroups)");
		      return null;
				}
      	String name = rs.getString(3);
      	if (date.before(end) && date.after(start)) {
      		potentialGroups.add(new Group(name, date, group_id));
      	}
      }
  	} catch (SQLException e) {
  		System.out.println("ERROR: SQLException triggered (getGroups)");
      return null;
  	}
    return potentialGroups;
  }

  @Override
  public Athlete addMember(Team t, String email, String number, String name,
      PostalCode location) {
  	try {
  		getTeam(t.getId());
  	} catch (IllegalArgumentException e) {
  		String message = String.format(
          "ERROR: [addMember] " + "No team in database with id: %s",
          t.getId());
      throw new IllegalArgumentException(message);
  	}
    String query1 = "SELECT * FROM athlete WHERE email = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
    	ps1.setString(1, email);
    	ResultSet rs = ps1.executeQuery();
    	if (rs.next()) {
    		String message = String.format(
            "ERROR: [addMember] " + "Athlete already exists with email: %s",
            email);
        throw new IllegalArgumentException(message);
    	} else {
    		String newID = new BigInteger(80, random).toString(32);
    		//begin transaction; insert into coach_team values("c1", "t1"); 
    		//insert into coach_workout values("c1", "w1"); commit;
    		String query2 = "BEGIN TRANSACTION; " 
    				+ "INSERT INTO athlete VALUES(?,?,?,?,?); "
    				+ "INSERT INTO team_athlete VALUES(?, ?); "
    				+ "COMMIT;";
	    	try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
	    		ps1.setString(1, newID);
	    		ps2.setString(2, name);
	    		ps2.setString(3, email);
	    		ps2.setString(4, number);
	    		ps2.setString(5, location.getPostalCode());
	    		ps2.setString(6, t.getId());
	    		ps2.setString(7, newID);
	    		ps2.executeUpdate();
	    	} catch (SQLException e) {
	    		System.out.println("ERROR: SQLException triggered (addMember)");
	        System.exit(1);
	    	}
    	}
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (addMember)");
      System.exit(1);
    }
    return null;
  }

  @Override
  public Group updateMembers(Group g, List<String> athletes) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Group updateWorkouts(Group g, List<String> workouts) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean deleteGroupById(String id) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Workout updateWorkout(String workoutId, Workout w) {
    // TODO Auto-generated method stub
    return null;
  }

}
