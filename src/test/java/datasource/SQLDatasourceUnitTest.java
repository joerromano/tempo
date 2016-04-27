package datasource;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLDatasourceUnitTest {
  private static SQLDatasource datasource;

  @BeforeClass
  public static void setUp() {
    Db.setURL("tempoNew.sqlite3");
    datasource = new SQLDatasource();
  }

  @Test(expected=IllegalArgumentException.class)
  public void getNonExistantWorkoutTest() {
  	Date d1 = new Date();
  	System.out.println("d1 : " + d1.toString());
//    datasource.addWorkout(new Group("example group", d1, "gid1"),
//        new Workout("wid", new Date(), 2, new PostalCode("here"), "tempo", 2.1, "AM"));
    Workout workout1 = datasource.getWorkout("id1");
    // assertEquals(new VertexImpl(new NodeProxy("/n/0")), e.getSource());
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

}
