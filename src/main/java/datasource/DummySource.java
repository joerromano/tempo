package datasource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

/**
 * Dummy Datasource to test SparkServer.
 *
 * @author Tom
 *
 */
public class DummySource implements Datasource {
  private Athlete joe;
  private Coach hillary;
  private Team team;

  public DummySource() {
    joe = new Athlete("joeshmoe@example.com", "Joe Shmoe", 2912);
    List<Athlete> athletes = new ArrayList<>();
    hillary = new Coach("hillz@clinton.com", "Hillary Clinton", 2912);
    athletes.add(joe);
    team = new Team("Example Team", "Providence, RI", hillary, athletes);
  }

  @Override
  public Workout getWorkout(String id) {
    return new Workout() {
      @Override
      public String toString() {
        return "Example Workout";
      }

      @Override
      public String toHTML() {
        return String.format("<div>%s</div>", toString());
      }
    };
  }

  @Override
  public Team getTeam(String id) {
    return team;
  }

  @Override
  public Set<Group> getGroups(String t, String d) {
    HashSet<Group> groups = new HashSet<>();
    groups.add(new Group(team.getRoster(),
        Collections.singletonList(getWorkout(null)), new Date()));
    return groups;
  }

  @Override
  public Coach authenticate(String email, String pwd) {
    return hillary;
  }
}
