package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
 * Retrieves and verifies information from the database, according to
 * the specifications of the Datasource interface.
 * @author lucicooke
 *
 */
public class SQLDatasource implements Datasource {

  @Override
  public Workout getWorkout(String id) {
  	// TODO : check cache?
  	String query = "SELECT * FROM workout " +
  			"WHERE id = ?;";
  	String workout_id = null;
  	String date = null;
  	int intensity = -1;
  	String location = null;
  	String type = null;
  	double score = -1;
  	String time = null;
  	try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
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
  			String message = String.format("ERROR: [getWorkout] "
  					+ "No workout in the database with id: %s", id);
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
    return new Workout(workout_id, workout_date, intensity, 
    		workout_location, type, score, time);
  }

  @Override
  public Team getTeam(String id) {
    // TODO : check cache?
  	String query = "SELECT * FROM team " + 
  			"WHERE id = ?;";
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
  			String message = String.format("ERROR: [getTeam] "
  					+ "No team in the database with id: %s", id);
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
  	return new Team(team_id, name, location, getCoach(coach_id), pub_priv);
  }

  /**
   * authenticate checks for the correct password in the database.
   * NOTE : this returns a coach with the base information. Does
   * not contain information about which teams this coach is associated
   * with. For that, call 'getTeams'
   */
  @Override
  public Coach authenticate(String email, String pwd) {
    // TODO : how are we storing passwords in the database
  	String query = "SELECT * FROM coach " + 
  			"WHERE email = ?;";
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
  			String message = String.format("ERROR: [getTeam] "
  					+ "No coach in the database with email: %s", email);
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
  		return null;
  	}
  }

  @Override
  public Group addGroup(Team t, String name, Date start) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Group getGroup(String groupId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Group renameGroup(Group g, String newName) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Collection<Group> getGroups(Team team, Date start, Date end) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Group updateMembers(Group g, List<String> athletes) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Athlete addMember(Team t, String email, String number, String name,
      PostalCode location) {
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

  @Override
  public Group addWorkout(Group g, Workout w) {
    // TODO Auto-generated method stub
    return null;
  }

	@Override
	public Coach getCoach(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
