package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLDatasource implements Datasource {

  @Override
  public Workout getWorkout(String id) {
  	// TODO : check cache?
  	String query = "SELECT * FROM workout " +
  			"WHERE id = ?;";
  	String workout_id;
  	String date;
  	int intensity;
  	String location;
  	String type;
  	double score;
  	String time;
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
  	assert (id != null);
  	Date workout_date = SparkServer.MMDDYYY.parse(date);
  	workout_date
  	
    return new Workout(workout_id, Date date, int intensity, 
    		PostalCode location, String type, double score, String time);
  }

  @Override
  public Team getTeam(String id) {
    // TODO : check cache?
  	String query = "SELECT * FROM team " + 
  			"WHERE id = ?;";
  	try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
  		ps.setString(1, id);
  		ResultSet rs = ps.executeQuery();
  		if (rs.next()) {
  			id = rs.getString(1);
  		}
  		
  	} catch (SQLException e) {
  		System.out.println("ERROR: SQLException triggered (getWorkout)");
      System.exit(1);
  	}
  	assert (id != null);
  	return new Team(id);
  }

  @Override
  public Coach authenticate(String email, String pwd) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Group addGroup(Team t, String name, Date start) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Athlete addMember(Team t, Athlete a) {
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

}
