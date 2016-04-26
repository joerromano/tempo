package datasource;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.brown.cs.cs32.tempo.db.Db;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLDatasourceUnitTest {
  private static SQLDatasource datasource;

  @BeforeClass
  public static void setUp() {
    Db.setURL("tempo.sqlite3");
    datasource = new SQLDatasource();
  }

  @Test
  public void getSourceTest() {
    datasource.addWorkout(new Group("example group", null, "gid"),
        new Workout("wid", null, 0, null, null, 0, null));
    Workout workout1 = datasource.getWorkout("id1");
    // assertEquals(new VertexImpl(new NodeProxy("/n/0")), e.getSource());
  }

}
