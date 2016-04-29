package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cs32.tempo.SparkServer;
import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLDatasourceUnitTest {
  private static SQLDatasource datasource;
	private static Random random; 

  @BeforeClass
  public static void setUp() {
    Db.setURL("tempoNew.sqlite3");
    datasource = new SQLDatasource();
    random = new Random();
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
  	
  }
  
  // this workout already exists in the database
  @Test
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
  	int rando_id = random.nextInt(50);
  	Group g = new Group("test_name", now, "test_id");
  	Workout toAdd = new Workout("testWK" + rando_id, now, 1, new PostalCode("10012"), "recovery", 1.8, "PM");
  	Group returned = datasource.addWorkout(g, toAdd);
  	Workout retrieved = datasource.getWorkout(toAdd.getId());
  	assertEquals(toAdd.getId(), retrieved.getId());
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void addWorkoutBadGroupTest() {
  	Date now = new Date();
  	int rando_id = random.nextInt(50);
  	Group badGroup = new Group("bad_group", now, "bad_group_id");
  	Workout toAdd = new Workout("testWK" + rando_id, now, 1, new PostalCode("10012"), "recovery", 1.8, "PM");
  	Group returned = datasource.addWorkout(badGroup, toAdd);
  }
  
  @Test
  public void getGroupTest() throws ParseException {
  	Group g1 = datasource.getGroup("test_id");
  	assertEquals(g1.getId(), "test_id");
  }
  
  
  
  @Test
  public void getCoachTest() {
  	Coach c1 = datasource.getCoach("test_id");
  	assertEquals(c1.getName(), "test_name");
  	assertEquals(c1.getId(), "test_id");
  	assertEquals(c1.getEmail(), "test_email");
  	assertTrue(c1.getLocation().getPostalCode().equals("02912"));
  }
  

}
