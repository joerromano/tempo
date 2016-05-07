package datasource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Team;

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
				c.addTeam(toAdd);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (removeAthlete)");
		}
	}
	
}
