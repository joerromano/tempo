package datasource;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
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

  @Override
  public Athlete addMember(Team t, String email, String number, String name,
      PostalCode location) {
    // TODO Auto-generated method stub
    return null;
  }

}
