package datasource;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import edu.brown.cs32.tempo.people.PhoneNumber;
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
  private SQLFillData filler = new SQLFillData();

  /**
   * getWorkout retrieves a workout from the database by id.
   * 
   * @param id
   *          - the id of the workout.
   * @return Workout - the workout.
   */
  @Override
  public Workout getWorkout(String id) {
    // TODO : cache!
    String query = "SELECT * FROM workout " + "WHERE id = ?;";
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
        String message = String.format(
            "ERROR: [getWorkout] " + "No workout in the database with id: %s",
            id);
        throw new IllegalArgumentException(message);
      }
    } catch (SQLException e) {
      e.printStackTrace();
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
      workout_date = this.getDateFromString(date);
    } catch (ParseException e) {
      e.printStackTrace();
      System.out.println("ERROR: ParseException triggered (getWorkout)");
    }
    Workout toReturn = new Workout(workout_id, workout_date, intensity,
        workout_location, type, score, time);
    return toReturn;
  }

  /**
   * getTeam retrieves a team from the database by id. The coach of the team
   * must exist in the database for this method to work.
   * 
   * @param id
   *          - the id of the team.
   * @return - the team with the given id.
   */
  @Override
  public Team getTeam(String id) {
    String query = "SELECT * FROM team " + "WHERE id = ?;";
    String team_id = null;
    String name = null;
    String location = null;
    boolean pub_priv = false;
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, id);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        team_id = rs.getString(1);
        name = rs.getString(2);
        location = rs.getString(3);
        pub_priv = rs.getBoolean(4);
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
    assert (location != null);
    Team toReturn = new Team(team_id, name, new PostalCode(location), pub_priv);
    filler.teamFillAthletes(toReturn);
    filler.teamGetCoach(toReturn);
    filler.teamGetGroups(toReturn);
    return toReturn;
  }

  /**
   * authenticate checks for the correct password in the database. NOTE : this
   * returns a coach with the base information. Does not contain information
   * about which teams this coach is associated with. For that, call 'getTeams'
   */
  @Override
  public Coach authenticate(String email, String pwd) {
    // TODO : encrypt passwords
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
        String message = String.format("ERROR: [authenticate] "
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
      Coach toReturn = new Coach(coach_id, email, name, pc);
      filler.coachFillTeams(toReturn);
      return toReturn;
    } else {
      String message = String.format(
          "ERROR: [authenticate] " + "Incorrect password for email: %s", email);
      throw new IllegalArgumentException(message);
    }
  }

  /**
   * addGroup adds a group to the database. The agony field is set to -1 because
   * we're not using that currently. This will also generate a random ID for the
   * group. The given team must exist in the database for the group to be added.
   * If not, this will throw an IllegalArgumentException.
   * 
   * This inserts a row into group_table, and a row in team_group.
   * 
   * @param t
   *          - the team
   * @param name
   *          - the name of the group
   * @param start
   *          - the date at which the group starts
   * @return - the group, if it has been added.
   */
  @Override
  public Group addGroup(Team t, String name, Date start) {
    String newID = new BigInteger(80, random).toString(32);
    try {
      getTeam(t.getId());
    } catch (IllegalArgumentException e) {
      String message = String.format(
          "ERROR: [addGroup] " + "No team exists for id: %s", t.getId());
      throw new IllegalArgumentException(message);
    }
    String query = "INSERT INTO group_table VALUES(?, ?, ?, ?);";
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, newID);
      ps.setString(2, SparkServer.MMDDYYYY.format(start));
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
   * getGroup returns a group from the database, given an ID. Throws an
   * IllegalArgumentException if the group is not found.
   * 
   * @param groupId
   *          - the ID of the group to retrieve.
   * @return the group with the corresponding ID.
   */
  @Override
  public Group getGroup(String groupId) {
    // TODO : fill athletes, workouts
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
    	Group g = new Group(name, this.getDateFromString(date), groupId); 
    	filler.groupGetAthletes(g);
    	filler.groupGetWorkouts(g);
      return g;
    } catch (ParseException e) {
      System.out.println("ERROR: ParseException triggered (getGroup)");
      return null;
    }
  }

  /**
   * renameGroup renames an existing group in the database. If the group does
   * not exist, an IllegalArgumentException is thrown.
   * 
   * @param g
   *          - the group to rename.
   * @param newName
   *          - the new name of the group.
   * @return the updated group.
   */
  @Override
  public Group renameGroup(Group g, String newName) {
    // TODO : fill athletes, workouts
    try {
      getGroup(g.getId());
    } catch (IllegalArgumentException e) {
      String message = String.format(
          "ERROR: [renameGroup] " + "No group exists for id: %s", g.getId());
      throw new IllegalArgumentException(message);
    }
    // update group_table set name = "new_name" where id="t00qsmem2msb31l7";
    String query = "UPDATE group_table SET name = ? where id = ?";
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, newName);
      ps.setString(2, g.getId());
      ps.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addWorkout)");
      return null;
    }
    return new Group(newName, g.getDate(), g.getId());
  }

  /**
   * addWorkout adds a workout to the database, as well as to the corresponding
   * group.
   * 
   * @param g
   *          - the group which the workout corresponds to.
   * @param w
   *          - the workout to be added to the database.
   * @return - the group, if added successfully, null if the group does not
   *         exist/
   */
  @Override
  public Group addWorkout(Group g, Workout w) {
    // TODO : fill athletes, workouts
    try {
      getGroup(g.getId());
    } catch (IllegalArgumentException e) {
      String message = String.format(
          "ERROR: [addWorkout] " + "No group in the database with id: %s",
          g.getId());
      throw new IllegalArgumentException(message);
    }
    String query = "INSERT INTO workout(id, date, intensity, location, type, score, time) "
        + "VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
      ps.setString(1, w.getId());
      ps.setString(2, SparkServer.MMDDYYYY.format(w.getDate()));
      ps.setInt(3, w.getIntensity());
      ps.setString(4, w.getLocation().getPostalCode());
      ps.setString(5, w.getType());
      ps.setDouble(6, w.getScore());
      ps.setString(7, w.getTime());
      ps.executeUpdate();
    } catch (SQLException e) {
      String queryTest = "SELECT * FROM workout WHERE id = ?";
      try (PreparedStatement psTest = Db.getConnection()
          .prepareStatement(queryTest)) {
        psTest.setString(1, w.getId());
        ResultSet rsTest = psTest.executeQuery();
        if (rsTest.next()) {
          String message = String.format(
              "ERROR: [addWorkout] " + "Workout already exists with id: %s",
              w.getId());
          throw new IllegalArgumentException(message);
        } else {
          System.out.println("ERROR: SQLException triggered (addWorkout1)");
        }
      } catch (SQLException e2) {
        System.out.println("ERROR: SQLException triggered (addWorkout2)");
      }
      System.out.println("ERROR: SQLException triggered (addWorkout3)");
      return null;
    }
    String query2 = "INSERT INTO group_workout(g_id, w_id) " + "VALUES(?,?)";
    try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
      ps2.setString(1, g.getId());
      ps2.setString(2, w.getId());
      ps2.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addWorkout4)");
      return null;
    }
    return g;
  }

  /**
   * getCoach returns a coach from the database by ID. If no coach with that ID
   * exists in the database, an IllegalArgumentException is thrown.
   * 
   * @param id
   *          - the id of the coach.
   * @return Coach - the coach with that ID.
   */
  @Override
  public Coach getCoach(String id) {
    // TODO : fill teams
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
    Coach toReturn = new Coach(coach_id, email, name, getPostalCodeFromString(location)); 
    filler.coachFillTeams(toReturn);
    return toReturn;
  }

  /**
   * getPostalCodeFromString formats the string location returned from the
   * database. Adds zeros as needed to the start of the string.
   * 
   * @param location
   *          - string zip code.
   * @return - postal code, containing the corrected zip code.
   */
  public PostalCode getPostalCodeFromString(String location) {
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
          "ERROR: [getDateFromString] " + "Date must be 8 or fewer chars");
      throw new ParseException(message, 0);
    }
  }

  @Override
  public Collection<Group> getGroups(Team team, Date start, Date end) {
    // TODO : for each group, fill: workouts, athletes
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
          date = getDateFromString(rs.getString(2));
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

  /**
   * addMember inserts a row into the athlete table, and inserts a row into the
   * team_athlete table to associate the athlete with a team.
   */
  @Override
  public Athlete addMember(Team t, String email, String number, String name,
      PostalCode location) {
    // TODO : fill: workouts, teams
    try {
      getTeam(t.getId());
    } catch (IllegalArgumentException e) {
      String message = String.format(
          "ERROR: [addMember] " + "No team in database with id: %s", t.getId());
      throw new IllegalArgumentException(message);
    }
    String newID = new BigInteger(80, random).toString(32);
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
        String query2 = "INSERT INTO athlete VALUES(?,?,?,?,?)";
        try (PreparedStatement ps2 = Db.getConnection()
            .prepareStatement(query2)) {
          ps2.setString(1, newID);
          ps2.setString(2, name);
          ps2.setString(3, email);
          ps2.setString(4, number);
          ps2.setString(5, location.getPostalCode());
          ps2.executeUpdate();
        } catch (SQLException e) {
          System.out.println("ERROR: SQLException triggered (addMember)");
          System.exit(1);
        }
        String query3 = "INSERT INTO team_athlete VALUES(?,?)";
        try (PreparedStatement ps3 = Db.getConnection()
            .prepareStatement(query3)) {
          ps3.setString(1, t.getId());
          ps3.setString(2, newID);
          ps3.executeUpdate();
        } catch (SQLException e) {
          System.out.println("ERROR: SQLException triggered (addMember)");
          System.exit(1);
        }
      }
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addMember)");
      System.exit(1);
    }
    Athlete toReturn = new Athlete(newID, email, name, location);
    toReturn.setNumber(new PhoneNumber(number));
    return toReturn;
  }

  // note : group only lasts for a week
  @Override
  public Group updateMembers(Group g, List<String> athletes) {
    // TODO : for returned group, fill: workouts
    try {
      getGroup(g.getId());
      for (String athID : athletes) {
        getAthlete(athID);
      }
    } catch (IllegalArgumentException e) {
      String message = String.format("ERROR: [updateMembers] "
          + "Group and all athletes must exist in database");
      throw new IllegalArgumentException(message);
    }
    String query1 = "DELETE FROM group_athlete WHERE g_id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, g.getId());
      ps1.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (updateMembers)");
      System.exit(1);
    }
    for (String athID : athletes) {
      String query2 = "INSERT INTO group_athlete VALUES(?,?)";
      try (
          PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
        ps2.setString(1, g.getId());
        ps2.setString(2, athID);
        ps2.executeUpdate();
      } catch (SQLException e) {
        System.out.println("ERROR: SQLException triggered (updateMembers)");
        System.exit(1);
      }
    }
    Collection<Athlete> members = new ArrayList<Athlete>();
    for (String id : athletes) {
    	Athlete a = this.getAthlete(id);
    	members.add(a);
    }
    
    g.setMembers(members);
    filler.groupGetWorkouts(g);
    
    return g;
  }

  // Note: groups only last a week
  @Override
  public Group updateWorkouts(Group g, List<String> workouts) {
    try {
      getGroup(g.getId());
      for (String w : workouts) {
        getAthlete(w);
      }
    } catch (IllegalArgumentException e) {
      String message = String.format("ERROR: [updateWorkouts] "
          + "Group and all workouts must exist in database");
      throw new IllegalArgumentException(message);
    }
    String query1 = "DELETE FROM group_workout WHERE g_id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, g.getId());
      ps1.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (updateWorkouts)");
      System.exit(1);
    }
    for (String wkID : workouts) {
      String query2 = "INSERT INTO group_workout VALUES(?,?)";
      try (
          PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
        ps2.setString(1, g.getId());
        ps2.setString(2, wkID);
        ps2.executeUpdate();
      } catch (SQLException e) {
        System.out.println("ERROR: SQLException triggered (updateWorkouts)");
        System.exit(1);
      }
    }
    filler.groupGetAthletes(g);
    ArrayList<Workout> wks = new ArrayList<Workout>();
    for (String id : workouts) {
    	Workout wk = this.getWorkout(id);
    	wks.add(wk);
    }
    g.setWorkouts(wks);
    return g;
  }

  @Override
  public boolean deleteGroupById(String id) {
    try {
      getGroup(id);
    } catch (IllegalArgumentException e) {
      String message = String.format("ERROR: [deleteGroupById] "
          + "Group doesn't exist in database with id: %s", id);
      throw new IllegalArgumentException(message);
    }
    String query1 = "DELETE FROM group_table WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, id);
      ps1.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (deleteGroupById)");
      System.exit(1);
    }
    return true;
  }

  // TODO : w.getId = workoutId, right?
  @Override
  public Workout updateWorkout(String workoutId, Workout w) {
    assert (w.getId().equals(workoutId));
    try {
      getWorkout(workoutId);
    } catch (IllegalArgumentException e) {
      String message = String.format("ERROR: [updateWorkout] "
          + "Workout doesn't exist in database with id: %s", workoutId);
      throw new IllegalArgumentException(message);
    }
    String query1 = "DELETE FROM workout WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, workoutId);
      ps1.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (updateWorkout)");
      System.exit(1);
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
      System.out.println("ERROR: SQLException triggered (updateWorkout)");
      return null;
    }
    return w;
  }

  private Athlete getAthlete(String athleteID) {
    return null;
  }

  // only adds to team to team table
  public boolean addTeam(Team t) {
    String query1 = "INSERT INTO team VALUES(?,?,?,?);";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, t.getId());
      ps1.setString(2, t.getName());
      ps1.setString(3, t.getLocation().getPostalCode());
      ps1.setBoolean(4, t.getPubPriv());
      ps1.executeUpdate();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addTeam)");
      return false;
    }
    return true;
  }

  @Override
  public boolean renameTeam(Team t, String newName) {
    assert (!t.getName().equals(newName));
    try {
      this.getTeam(t.getId());
    } catch (IllegalArgumentException e) {
      return false;
    }
    String query1 = "UPDATE team SET name = ? WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, newName);
      ps1.setString(2, t.getId());
      ps1.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean disbandTeam(Team t) {
    try {
      getTeam(t.getId());
    } catch (IllegalArgumentException e) {
      return false;
    }
    String query1 = "DELETE FROM team WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, t.getId());
      ps1.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    String query2 = "DELETE FROM team_athlete WHERE t_id = ?";
    try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
      ps2.setString(1, t.getId());
      ps2.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    String query3 = "DELETE FROM coach_team WHERE t_id = ?";
    try (PreparedStatement ps3 = Db.getConnection().prepareStatement(query3)) {
      ps3.setString(1, t.getId());
      ps3.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    String query4 = "DELETE FROM team_group WHERE team_id = ?";
    try (PreparedStatement ps4 = Db.getConnection().prepareStatement(query4)) {
      ps4.setString(1, t.getId());
      ps4.executeUpdate();
    } catch (SQLException e) {
    	return false;
    }
		return true;
	}

  public Coach addCoach(String name, String email, PostalCode location,
      String pwd) {

    String query1 = "SELECT * FROM coach WHERE email = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, email);
      ResultSet rs1 = ps1.executeQuery();
      if (rs1.next()) {
        String message = String.format("ERROR: [addCoach] "
            + "Coach already exists in database with email: %s", email);
        throw new IllegalArgumentException(message);
      } else {
        String newID = "coach_" + new BigInteger(80, random).toString(32);
        String query2 = "INSERT INTO coach VALUES(?,?,?,?,?)";
        try (PreparedStatement ps2 = Db.getConnection()
            .prepareStatement(query2)) {
          ps2.setString(1, newID);
          ps2.setString(2, name);
          ps2.setString(3, email);
          ps2.setString(4, location.getPostalCode());
          ps2.setString(5, pwd);
          ps2.executeUpdate();
          return new Coach(newID, email, name, location);
        } catch (SQLException e) {
          System.out.println("ERROR: SQLException triggered (addCoach)");
          return null;
        }
      }
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered (addCoach)");
      return null;
    }
  }

  @Override
  public boolean deleteCoach(Coach c) {
    try {
      getCoach(c.getId());
    } catch (IllegalArgumentException e) {
      return false;
    }
    String query1 = "DELETE FROM coach WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
      ps1.setString(1, c.getId());
      ps1.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    String query2 = "DELETE FROM coach_team WHERE c_id = ?";
    try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
      ps2.setString(1, c.getId());
      ps2.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    String query3 = "DELETE FROM coach_team WHERE c_id = ?";
    try (PreparedStatement ps3 = Db.getConnection().prepareStatement(query3)) {
      ps3.setString(1, c.getId());
      ps3.executeUpdate();
    } catch (SQLException e) {
      return false;
    }
    return true;
  }

  @Override
  public boolean updatePassword(Coach c, String oldPwd, String newPwd) {
    try {
      authenticate(c.getEmail(), oldPwd);
    } catch (IllegalArgumentException e) {
      String message = String.format("ERROR: [updatePassword] "
          + "Incorrect credentials for coach with email: %s", c.getEmail());
      throw new IllegalArgumentException(message);
		}
		String query1 = "UPDATE coach SET pwd = ? WHERE id = ?";
		try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
			ps1.setString(1, newPwd);
			ps1.setString(2, c.getId());
			ps1.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateName(Coach c, String name) {
		assert(!name.equals(c.getName()));
		try {
			getCoach(c.getId());
		} catch (IllegalArgumentException e) {
			String message = String.format(
          "ERROR: [updateName] " + "Coach doesn't exists with id: %s",
          c.getId());
      throw new IllegalArgumentException(message);
		}
		String query1 = "UPDATE coach SET name = ? WHERE id = ?";
		try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
			ps1.setString(1, name);
			ps1.setString(2, c.getId());
			ps1.executeUpdate();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}


	// TODO : make sure coach will not be able to add two teams of same name
	@Override
	public Team addTeam(Coach c, String name) {
		String newID = "team_" + new BigInteger(80, random).toString(32);
		String query1 = "INSERT INTO team VALUES(?,?,?,?);";
  	try(PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
  		ps1.setString(1, newID);
  		ps1.setString(2, name);
  		ps1.setString(3, c.getLocation().getPostalCode());
  		ps1.setBoolean(4, false);
  		ps1.executeUpdate();
  	} catch (SQLException e) {
  		System.out.println("ERROR: SQLException triggered (addTeam)");
  		return null;
  	}
  	
  	String query2 = "INSERT INTO coach_team VALUES(?,?);";
  	try(PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
  		ps2.setString(1, c.getId());
  		ps2.setString(2, newID);
  		ps2.executeUpdate();
  	} catch (SQLException e) {
  		System.out.println("ERROR: SQLException triggered (addTeam)");
  		return null;
  	}
  	Team toReturn = new Team(newID, name, c.getLocation(), false);
  	toReturn.addCoach(c);
		return toReturn;
	}

	@Override
	public Athlete editAthlete(String id, String name, String number,
			String email, PostalCode location) {
		Athlete currentAthlete = null;
		try {
			currentAthlete = getAthlete(id);
		} catch (IllegalArgumentException e) {
			String message = String.format(
          "ERROR: [removeAthlete] " + "Athlete doesn't exists with id: %s",
          id);
      throw new IllegalArgumentException(message);
		}
		String query1 = "DELETE FROM athlete WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
    	ps1.setString(1, id);
    	ps1.executeUpdate();
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (removeAthlete)");
    	return null;
    }
    String query2 = "INSERT INTO athlete VALUES(?,?,?,?,?)";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query2)) {
    	ps1.setString(1, id);
    	ps1.setString(2, name);
    	ps1.setString(3, email);
    	ps1.setString(4, number);
    	ps1.setString(5, location.getPostalCode());
    	ps1.executeUpdate();
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (removeAthlete)");
    	return null;
    }
		
		Athlete toReturn = new Athlete(id, email, name, location);
//		toReturn.addTeam(currentAthlete.getTeam());
		return toReturn;
		
	}

	@Override
	public boolean removeAthlete(Team t, String id) {
		try {
			getAthlete(id);
		} catch (IllegalArgumentException e) {
			String message = String.format(
          "ERROR: [removeAthlete] " + "Athlete doesn't exists with id: %s",
          id);
      throw new IllegalArgumentException(message);
		}
		String query1 = "DELETE FROM athlete WHERE id = ?";
    try (PreparedStatement ps1 = Db.getConnection().prepareStatement(query1)) {
    	ps1.setString(1, id);
    	ps1.executeUpdate();
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (removeAthlete)");
    	return false;
    }
    String query2 = "DELETE FROM team_athlete WHERE ath_id = ?";
    try (PreparedStatement ps2 = Db.getConnection().prepareStatement(query2)) {
    	ps2.setString(1, id);
    	ps2.executeUpdate();
    } catch (SQLException e) {
    	System.out.println("ERROR: SQLException triggered (removeAthlete)");
    	return false;
    }
		return true;
	}

	@Override
	public List<Workout> getLibrary(Coach c, String sortBy, int from, int to) {
		try {
			this.getCoach(c.getId());
		} catch (IllegalArgumentException e) {
			String message = String.format(
          "ERROR: [getLibrary] " + "Coach doesn't exists with id: %s",
          c.getId());
      throw new IllegalArgumentException(message);
		}
		ArrayList<Workout> toReturn = new ArrayList<Workout>();
		String query = "SELECT * FROM workout WHERE id IN "
  			+ "(SELECT w_id FROM coach_workout WHERE c_id = ?);";
		try (PreparedStatement ps = Db.getConnection().prepareStatement(query)) {
			ps.setString(1, c.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String id = rs.getString(1);
				Date date;
				try {
					date = this.getDateFromString(rs.getString(2));
				} catch (ParseException e) {
					String message = String.format(
		          "ERROR: [getLibrary] " + "ParseException");
		      throw new IllegalArgumentException(message);
				}
				int intensity = rs.getInt(3);
				PostalCode location = this.getPostalCodeFromString(rs.getString(4));
				String type = rs.getString(5);
				double score = rs.getDouble(6);
				String time = rs.getString(7);
				Workout toAdd = new Workout(id, date, intensity, location, type, score, time);
				toReturn.add(toAdd);
			}
		} catch (SQLException e) {
			System.out.println("ERROR: SQLException triggered (removeAthlete)");
    	return null;
		}
		return toReturn;
	}
    


}
