package datasource;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLFillDataUnitTest {
	private static SQLDatasource datasource;
	private static SQLFillData filler;
	private static Random random; 

  @BeforeClass
  public static void setUp() {
    Db.setURL("tempoNew.sqlite3");
    datasource = new SQLDatasource();
    random = new Random();
    filler = new SQLFillData();
  }
  
  @Test
  public void coachFillTeamsTest() {
  	String randoEmail = "coach_email2_" + new BigInteger(80, random).toString(32) + "@yahoo.com";
  	Coach addedCoach = datasource.addCoach("coach joe", randoEmail, new PostalCode("12345"), "another_pwd");
  	datasource.addTeam(addedCoach, "bwtf");
  	Coach authenticatedCoach = datasource.authenticate(randoEmail, "another_pwd");
  	
  	Collection<Team> teams = authenticatedCoach.getTeams();
  	assertEquals(teams.size(), 1);
  }
  
  @Test
  public void teamFillAthletesAndCoachAndGroupsTest() {
  	String email1 = "jj" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	String randoEmail = "coach_email2_" + new BigInteger(80, random).toString(32);
  	Coach addedCoach = datasource.addCoach("coach joe", randoEmail, new PostalCode("12345"), "another_pwd");
  	Team addedTeam = datasource.addTeam(addedCoach, "bwtf");
  	Athlete addedAth1 = datasource.addMember(addedTeam, email1, "5041236870", "jj", new PostalCode("12345"));
  	Group g = datasource.addGroup(addedTeam, "midD", new Date());
  	
  	Team retrievedTeam = datasource.getTeam(addedTeam.getId());
  	assertEquals(retrievedTeam.getRoster().size(), 1);
  	assertEquals(retrievedTeam.getCoach().size(), 1);
  	assertEquals(retrievedTeam.getGroups().size(), 1);
  }
  
  @Test
  public void groupGetAthletesTest() {
  	Date d = new Date();
  	Group g = new Group("group_name", d, "test_id");
  	List<String> workouts = new ArrayList<String>();
  	workouts.add("testWKph0p0ko391v6krjs");
  	workouts.add("testWKl98s98rjjdkslugj");
  	workouts.add("testWKmccnd38uvue47omc");
  	workouts.add("testWKobklk141a4gl5h92");
  	Group receievedGroup = datasource.updateWorkouts(g, workouts);
  	assertEquals(receievedGroup.getWorkout().size(), 4);
  	
  	List<String> athletes = new ArrayList<String>();
  	athletes.add("igtmr8nnnrbnk7po");
  	athletes.add("lr4t4id3u4eh3npa");
  	athletes.add("hsrd1amgdej96jl");
  	Group receivedGroup = datasource.updateMembers(new Group("group_name", d, "test_id"), athletes);
  	assertEquals(receievedGroup.getWorkout().size(), 4);
  	
  	
  	
  }

}
