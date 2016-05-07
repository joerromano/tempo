package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cs32.tempo.SparkServer;
import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLDatasourceUnitTest {
  private static SQLDatasource datasource;
	private static Random random; 

  @BeforeClass
  public static void setUp() {
  	System.out.println("1!");
    Db.setURL("tempoNew.sqlite3");
    datasource = new SQLDatasource();
    random = new Random();
    System.out.println("2!");
  }

  @Test(expected=IllegalArgumentException.class)
  public void getNonExistantWorkoutTest() {
    datasource.getWorkout("id1");
  }
  
  // uses dummy test workout in database
  @Test
  public void getWorkoutTest() {
  	Workout workout1 = datasource.getWorkout("test_id");
  	System.out.println("location : " + workout1.getId());
  	assertEquals(workout1.getId(), "test_id");
  	assertEquals(workout1.getIntensity(), 2);
  	assertEquals(workout1.getType(), "test_type");
  }
  
  @Test
  public void getTeamTest() {
  	Team t1 = datasource.getTeam("test_team");
  	assertEquals(t1.getId(), "test_team");
  	assertEquals(t1.getName(), "test_name");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void getNonexistantTeamTest() {
  	datasource.getTeam("clearly_not_an_id");
  }
  
  @Test
  public void authenticateTest() {
  	Coach returnedCoach = datasource.authenticate("coach_gmail", "great_pwd");
  	assertEquals(returnedCoach.getEmail(), "coach_gmail");
  	assertEquals(returnedCoach.getId(), "test_coach_id");
  	assertEquals(returnedCoach.getLocation().getPostalCode(), "10012");
  	assertEquals(returnedCoach.getName(), "mitchell_baker");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void badPasswordTest() {
  	datasource.authenticate("coach_gmail", "incorrect_pwd");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void authenticateNonexistantCoachTest() {
  	datasource.authenticate("not_an_email", "whatever");
  }
  
  @Test
  public void addGroupTest() {
  	Date now = new Date();
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("02912"), c, true);
  	Group returnedGroup = datasource.addGroup(t, "group_name", now);
  	assertEquals(returnedGroup.getName(), "group_name");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void addGroupWithBadTeamTest() {
  	Date now = new Date();
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("not_a_team", "team_name", new PostalCode("12345"), c, false);
  	datasource.addGroup(t, "group_name2", now);
  }
  
  @Test
  public void getGroupTest() {
  	Group g1 = datasource.getGroup("test_id");
  	assertEquals(g1.getId(), "test_id");
  	assertEquals(g1.getName(), "test_name");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void getNonexistantGroupTest() {
  	datasource.getGroup("not_a_group_duh");
  }
  
  @Test
  public void renameGroupTest() {
  	Date now = new Date();
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("02912"), c, true);
  	Group returnedGroup = datasource.addGroup(t, "group_name", now);
  	
  	Group renamedGroup = datasource.renameGroup(returnedGroup, "renamed_group_name");
  	assertEquals(renamedGroup.getName(), "renamed_group_name");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void renameNonExistantGroupTest() {
  	Group g = new Group("not_a_name", new Date(), "not_a_group_id");
  	datasource.renameGroup(g, "newName");
  }
  
  // this workout already exists in the database
  @Test(expected=IllegalArgumentException.class)
  public void addDuplicateWorkoutTest() {
  	Date d1 = new Date();
  	Group g = new Group("test_group", d1, "test_id");
  	Workout toAdd1 = new Workout("insert_wk", d1, 3, new PostalCode("11201"), "tempo", 2.4, "AM");
  	Group returned = datasource.addWorkout(g, toAdd1);
  	assertEquals(returned, null);
  }
  
  @Test
  public void addWorkoutTest() {
  	Date now = new Date();
  	String rando_id = new BigInteger(80, random).toString(32);
  	Group g = new Group("test_name", now, "test_id");
  	Workout toAdd = new Workout("testWK" + rando_id, now, 1, new PostalCode("10012"), "recovery", 1.8, "PM");
  	Group returned = datasource.addWorkout(g, toAdd);
  	Workout retrieved = datasource.getWorkout(toAdd.getId());
  	assertEquals(toAdd.getId(), retrieved.getId());
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void addWorkoutBadGroupTest() {
  	Date now = new Date();
  	String rando_id = new BigInteger(80, random).toString(32);
  	Group badGroup = new Group("bad_group", now, "bad_group_id");
  	Workout toAdd = new Workout("testWK" + rando_id, now, 1, new PostalCode("10012"), "recovery", 1.8, "PM");
  	Group returned = datasource.addWorkout(badGroup, toAdd);
  }
  
  @Test
  public void getCoachTest() {
  	Coach c1 = datasource.getCoach("test_id");
  	assertEquals(c1.getName(), "test_name");
  	assertEquals(c1.getId(), "test_id");
  	assertEquals(c1.getEmail(), "test_email");
  	assertTrue(c1.getLocation().getPostalCode().equals("02912"));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void getNonExistantCoachTest() {
  	datasource.getCoach("not_a_coach");
  }
  
  @Test
  public void getGroupsTest() throws ParseException {
  	Date now = new Date();
  	String earlierDateString = "10102015";
  	Date earlierDate = SparkServer.MMDDYYYY.parse(earlierDateString);
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("02912"), c, true);
  	Collection<Group> retrievedGroups = datasource.getGroups(t, earlierDate, now);
  	for (Group g : retrievedGroups) {
  		assertTrue(g.getDate().before(now));
  		assertTrue(g.getDate().after(earlierDate));
  	}
  }
  
  @Test
  public void addMemberTest() {
  	String randomEmail = "jj_" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("02912"), c, true);
  	Athlete addedAthlete = datasource.addMember(t, randomEmail, "1231231234", "Simon Belete", new PostalCode("10013"));
  	assertEquals(addedAthlete.getName(), "Simon Belete");
  	assertEquals(addedAthlete.getNumber().number, "1231231234");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void addMemberNoTeamTest() {
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("not_a_team", "team_name", new PostalCode("02912"), c, true);
  	datasource.addMember(t, "not_an_email_yay", "1231231234", "Simon Belete", new PostalCode("10013"));
  }
  
  // TODO : finish testing update members... how to do this?
  @Test
  public void updateMembersTest() {
  	Date d = new Date();
  	Group g = new Group("group_name", d, "test_id");
  	List<String> athletes = new ArrayList<String>();
  	athletes.add("id1");
  	athletes.add("id2");
  	athletes.add("id3");
  	Group receivedGroup = datasource.updateMembers(g, athletes);
  }
  
  @Test
  public void updateMembersTest2() {
  	String randomEmail1 = "jj1_" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	String randomEmail2 = "pj2_" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	
  	Date d = new Date();
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("02912"), c, true);
  	Group g = datasource.addGroup(t, "5k10k", new Date());
  	
  	Athlete addedAthlete1 = datasource.addMember(t, randomEmail1, "1002003333", "JJ", new PostalCode("10013"));
  	Athlete addedAthlete2 = datasource.addMember(t, randomEmail2, "1002004444", "PJ", new PostalCode("10012"));
  	
  	List<String> athletes = new ArrayList<String>();
  	athletes.add(addedAthlete1.getId());
  	athletes.add(addedAthlete2.getId());
  	Group receivedGroup = datasource.updateMembers(g, athletes);
  	assertEquals(receivedGroup.getMembers().size(), athletes.size());
  	
  }
  
  //TODO : finish testing update workout
  @Test
  public void updateWorkoutsTest() {
  	Date d = new Date();
  	Group g = new Group("group_name", d, "test_id");
  	List<String> workouts = new ArrayList<String>();
  	workouts.add("testWKph0p0ko391v6krjs");
  	workouts.add("testWKl98s98rjjdkslugj");
  	workouts.add("testWKmccnd38uvue47omc");
  	workouts.add("testWKobklk141a4gl5h92");
  	Group receievedGroup = datasource.updateWorkouts(g, workouts);
  }
  
  //TODO : finish testing
  @Test(expected=IllegalArgumentException.class)
  public void deleteGroupByIdTest() {
  	Date now = new Date();
  	
  	//TODO : store dates in database like this:
  	String databaseDate = SparkServer.MMDDYYYY.format(now);
  	System.out.println("dataabase Date" + databaseDate);
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("02912"), c, true);
  	Group returnedGroup = datasource.addGroup(t, "group_name", now);
  	datasource.deleteGroupById(returnedGroup.getId());
  	datasource.getGroup(returnedGroup.getId());
  }
  
//  @Test
  public void updateWorkoutTest() {
  	Date now = new Date();
  	String rando_id = new BigInteger(80, random).toString(32);
  	Group g = new Group("test_name", now, "test_id");
  	Workout toAdd = new Workout("testWKu" + rando_id, now, 1, new PostalCode("10012"), "recovery", 1.8, "PM");
  	Group receivedGroup = datasource.addWorkout(g, toAdd);
  	Workout toUpdate = new Workout("testWKu" + rando_id, now, 5, new PostalCode("10013"), "progressive", 1.9, "AM");
  	Workout updatedWkout = datasource.updateWorkout("testWku" + rando_id, toUpdate);
  }
  
  @Test
  public void addTeamTest() {
  	String rando_id = new BigInteger(80, random).toString(32);
  	Team toAdd = new Team("test_team_id1_" + rando_id, "test_team_name1", new PostalCode("11201"), false);
  	boolean success = datasource.addTeam(toAdd);
  	Team received = datasource.getTeam("test_team_id1_" + rando_id);
  	assertEquals(success, true);
  	assertEquals(toAdd.getId(), received.getId());
  	assertEquals(toAdd.getName(), received.getName());
  }
  
  @Test
  public void renameTeamTest() {
  	String rando_id = new BigInteger(80, random).toString(32);
  	Team toAdd = new Team("test_team_id1_" + rando_id, "test_team_name1", new PostalCode("11201"), false);
  	datasource.addTeam(toAdd);
  	boolean success = datasource.renameTeam(toAdd, "new_name");
  	assertEquals(success, true);
  	
  	Team received = datasource.getTeam("test_team_id1_" + rando_id);
  	assertEquals(received.getName(), "new_name");
  	assertEquals(received.getId(), toAdd.getId());
  	
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void disbandTeamTest() {
  	Team toAdd = new Team("to_be_deleted", "test_team_name1", new PostalCode("11201"), false);
  	datasource.addTeam(toAdd);
  	
  	boolean deleted = datasource.disbandTeam(toAdd);
  	assertEquals(deleted, true);
  	
  	datasource.getTeam("to_be_deleted");
  }
  
  @Test
  public void dateParsingTest() throws ParseException {
  	Date now = new Date();
//  	System.out.println("original date : " + now.toString());
  	String databaseDate = SparkServer.MMDDYYYY.format(now);
//  	System.out.println("db date : " + databaseDate);
  	
  	Date parsedDate = SparkServer.MMDDYYYY.parse(databaseDate);
//  	System.out.println("parsed date : " + parsedDate.toString());
  }
  
  @Test
  public void addCoachTest() {
  	String randoEmail = "coach_email" + new BigInteger(80, random).toString(32) + "me.org";
  	Coach addedCoach = datasource.addCoach("tim springfield", randoEmail, new PostalCode("11201"), "pwd1");
  	Coach getCoach = datasource.getCoach(addedCoach.getId());
  	assertEquals(addedCoach.getEmail(), getCoach.getEmail());
  	assertEquals(addedCoach.getName(), getCoach.getName());
  	assertEquals(addedCoach.getId(), getCoach.getId());
  	assertEquals(addedCoach.getLocation().getPostalCode(), getCoach.getLocation().getPostalCode());
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void addDuplicateCoachTest() {
  	datasource.addCoach("tim springfield", "coach_gmail@me.org", new PostalCode("11201"), "pwd1");
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void deleteCoachTest() {
  	String randoEmail = "coach_email@" + new BigInteger(80, random).toString(32);
  	Coach addedCoach = datasource.addCoach("tim springfield", randoEmail, new PostalCode("11201"), "pwd1");
  	boolean deleted = datasource.deleteCoach(addedCoach);
  	assertTrue(deleted);
  	
  	datasource.getCoach(addedCoach.getId());
  }
  
  @Test
  public void updatePasswordTest() {
  	String randoEmail = "coach_email" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	Coach addedCoach = datasource.addCoach("kurt benninger", randoEmail, new PostalCode("11202"), "pwd2");
  	
  	boolean updated = datasource.updatePassword(addedCoach, "pwd2", "much_better_pwd");
  	assertTrue(updated);
  	Coach authenticatedCoach = datasource.authenticate(randoEmail, "much_better_pwd");
  	
  	assertEquals(authenticatedCoach.getEmail(), randoEmail);
  	assertEquals(authenticatedCoach.getId(), addedCoach.getId());
  	assertEquals(authenticatedCoach.getLocation().getPostalCode(), addedCoach.getLocation().getPostalCode());
  }
  
  @Test
  public void updateNameTest() {
  	String randoEmail = "coach_email_" + new BigInteger(80, random).toString(32) + "@verizon.net";
  	Coach addedCoach = datasource.addCoach("coach sami", randoEmail, new PostalCode("11937"), "pwd_awesome");
  	
  	boolean updated = datasource.updateName(addedCoach, "sami jorgensen");
  	assertTrue(updated);
  	
  	Coach retrievedCoach = datasource.getCoach(addedCoach.getId());
  	
  	assertEquals(retrievedCoach.getId(), addedCoach.getId());
  	assertEquals(retrievedCoach.getName(), "sami jorgensen");
  }
  
  @Test
  public void addTeamTestFromCoach() {
  	String randoEmail = "coach_email_" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	Coach addedCoach = datasource.addCoach("simon belete", randoEmail, new PostalCode("45902"), "pwd_amazing");
  	
  	Team addedTeam = datasource.addTeam(addedCoach, "bwxc");
  	assertEquals(addedTeam.getLocation().getPostalCode(), addedCoach.getLocation().getPostalCode());
  	assertEquals(addedTeam.getName(), "bwxc");
  }
  
  @Test
  public void editAthleteTest() {
  	String email = "tom_" + new BigInteger(80, random).toString(32) + "@gmail.com";
  	Coach c = new Coach("test_coach_id", "coach_gmail", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("10012"), c, true);
  	Athlete added = datasource.addMember(t, email, "1234561894", "tom hale", new PostalCode("39914"));
  	
  	Athlete edited = datasource.editAthlete(added.getId(), "Tom Hale", "1234561894", email, new PostalCode("39914"));
  	assertEquals(edited.getEmail(), added.getEmail());
  	assertEquals(edited.getName(), "Tom Hale");
  }
  
  @Test
  public void deleteAthleteTest() {
  	Coach c = new Coach("test_coach_id", "coach_gmail@gmail.com", "mitchell_baker", new PostalCode("10012"));
  	Team t = new Team("test_team", "team_name", new PostalCode("10012"), c, true);
  	Athlete added = datasource.addMember(t, "ath_email1@bing.com", "1234567890", "joe", new PostalCode("10012"));
  	
  	boolean removed = datasource.removeAthlete(t, added.getId());
  	assertTrue(removed);
  }
  

}
