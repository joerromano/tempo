package datasource;

import java.util.Set;

import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

public class SQLDatasource implements Datasource {

  @Override
  public Workout getWorkout(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Team getTeam(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Group> getGroups(String team, String date) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Coach authenticate(String email, String pwd) {
    // TODO Auto-generated method stub
    return null;
  }

}
