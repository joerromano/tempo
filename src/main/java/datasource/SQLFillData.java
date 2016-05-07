package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import edu.brown.cs.cs32.tempo.SparkServer;
import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLFillData {
	

	public void coachFillTeams(Coach c) {
		String query = "SELECT * FROM team WHERE id IN "
  			+ "(SELECT t_id FROM coach_team WHERE c_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				PostalCode location = new PostalCode(rs.getString(3));
				boolean pubPriv = rs.getBoolean(4);
				Team toAdd = new Team(id, name, location, pubPriv);
				teamFillAthletes(toAdd);
				c.addTeam(toAdd);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (coachFillTeam)");
		}
	}
	
	public void teamFillAthletes(Team t) {
		String query = "SELECT * FROM athlete WHERE id IN "
  			+ "(SELECT ath_id FROM team_athlete WHERE t_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, t.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				PostalCode location = new PostalCode(rs.getString(4));
				
				Athlete toAdd = new Athlete(id, email, name, location);
				toAdd.addTeam(t);
				t.addAthlete(toAdd);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (teamFillAthlete)");
		}
	}
	
	public void teamGetCoach(Team t) {
		String query = "SELECT * FROM coach WHERE id IN "
  			+ "(SELECT c_id FROM coach_team WHERE t_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, t.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				PostalCode location = new PostalCode(rs.getString(4));
				Coach toAdd = new Coach(id, email, name, location, t);
				t.addCoach(toAdd);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (teamGetCoach)");
		}
	}
	
	public void teamGetGroups(Team t) {
		String query = "SELECT * FROM group_table WHERE id IN "
  			+ "(SELECT group_id FROM team_group WHERE team_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, t.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				Date date = null;
				try {
					date = getDateFromString(rs.getString(2));
				} catch (ParseException e) {
					System.out.println("ERROR: ParseException triggered (teamGetGroups)");
				}
				String name = rs.getString(3);
				Group group = new Group(name, date, id);
				
				t.addGroup(group);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (teamGetGroups)");
		}
	}
	
	public void groupGetAthletes(Group g) {
		String query = "SELECT * FROM athlete WHERE id IN "
  			+ "(SELECT ath_id FROM group_athlete WHERE g_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, g.getId());
			ResultSet rs = ps.executeQuery();
			Collection<Athlete> members = new ArrayList<Athlete>();
			while (rs.next()) {
				String id = rs.getString(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				PostalCode location = new PostalCode(rs.getString(4));
				Athlete toAdd = new Athlete(id, email, name, location);
				members.add(toAdd);
			}
			g.setMembers(members);
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (groupGetAthletes)");
		}
	}
	
	public void groupGetWorkouts(Group g) {
		String query = "SELECT * FROM workout WHERE id IN "
  			+ "(SELECT w_id FROM group_workout WHERE g_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, g.getId());
			ResultSet rs = ps.executeQuery();
			
			ArrayList<Workout> wks = new ArrayList<Workout>();
			while (rs.next()) {
				String id = rs.getString(1);
				Date date = null;
				try {
					date = getDateFromString(rs.getString(2));
				} catch (ParseException e) {
					System.out.println("ERROR: ParseException triggered (groupGetWorkouts)");
				}
				int intensity = rs.getInt(3);
				PostalCode location = new PostalCode(rs.getString(4));
				String type = rs.getString(5);
				double score = rs.getDouble(6);
				String time = rs.getString(7);
				
				Workout toAdd = new Workout(id, date, intensity, location, type, score, time);
				wks.add(toAdd);
			}
			g.setWorkouts(wks);
			
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (groupGetWorkouts)");
		}
	}
	
	private Date getDateFromString(String date) throws ParseException {
    // System.out.println("gDfS date : " + date);
    // System.out.println("gDfS date.length : " + date.length());
    if (date.length() == 8) {
      // System.out.println("1");
      return SparkServer.MMDDYYYY.parse(date);
    } else if (date.length() < 8) {
      // System.out.println("2");
      StringBuilder sb = new StringBuilder();
      sb.append(date);
      while (sb.length() < 8) {
        sb.insert(0, "0");
      }
      return SparkServer.MMDDYYYY.parse(sb.toString());
    } else {
      // System.out.println("3");
      String message = String.format(
          "ERROR: [getDateFromStringF] " + "Date must be 8 or fewer chars");
      throw new ParseException(message, 0);
    }
  }
	
	
}
