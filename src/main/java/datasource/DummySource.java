package datasource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs.cs32.tempo.SparkServer;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Run;
import edu.brown.cs32.tempo.workout.Workout;

/**
 * Dummy Datasource to test SparkServer.
 *
 * @author Tom
 *
 */
public class DummySource implements Datasource {
  private Athlete simon, luci, tom, joe;
  private Coach jj;
  private Team tempo;
  private PostalCode prov = new PostalCode("02912");

  public DummySource() throws ParseException {
    simon = new Athlete("simon_belete@brown.edu", "Simon Belete", prov);
    joe = new Athlete("joseph_romano@brown.edu", "Joe Romano", prov);
    luci = new Athlete("lucia_cooke@brown.edu", "Luci Cooke", prov);
    tom = new Athlete("thomas_hale@brown.edu", "Tom Hale", prov);
    List<Athlete> athletes = new ArrayList<>();
    athletes.add(simon);
    athletes.add(joe);
    athletes.add(luci);
    athletes.add(tom);
    jj = new Coach("jj@cs.brown.edu", "JJ", prov);
    tempo = new Team("Tempo Team", "Providence, RI", jj, athletes);
    jj.addTeam(tempo);
    Run r1 = new Run(SparkServer.MMDDYYYY.parse("04252016"), 0, prov,
        "Recovery run", "6x100m strides after", 0, "am");
    r1.setMileage(8);
    Run r2 = new Run(SparkServer.MMDDYYYY.parse("04242016"), 0, prov,
        "Long run", "Rhythm the last 5 miles", 0, "am");
    r2.setMileage(13);
    List<Workout> workouts = new ArrayList<>();
    workouts.add(r1);
    workouts.add(r2);
    Group g = new Group(athletes, workouts,
        SparkServer.MMDDYYYY.parse("04242016"));
    tempo.addGroup(g);
  }

  @Override
  public Workout getWorkout(String id) {
    try {
      return new Run(SparkServer.MMDDYYYY.parse("04242016"), 0, prov,
          "Long run", "Rhythm the last 5 miles", 0, "am");
    } catch (ParseException e) {
      return null;
    }
  }

  @Override
  public Team getTeam(String id) {
    return tempo;
  }

  @Override
  public Coach authenticate(String email, String pwd) {
    return jj;
  }

  @Override
  public Collection<Group> getGroups(Team team, Date start, Date end) {
    // TODO is there a cleaner way to do this?
    System.out.println(team.getGroups());
    System.out.println(start);
    System.out.println(end);
    Set<Group> filtered = new HashSet<>();
    for (Group g : team.getGroups()) {
      System.out.println(g.getDate());
      if (!g.getDate().before(start) && !g.getDate().after(end)) {
        filtered.add(g);
      }
    }
    System.out.println(filtered);
    return filtered;
  }

  @Override
  public Group addGroup(Team t, String name, Date start) {
    Group g = new Group(name, start);
    t.addGroup(g);
    return g;
  }

  @Override
  public Athlete addMember(Team t, Athlete a) {
    t.addAthlete(a);
    return a;
  }

  @Override
  public Group getGroup(String groupId) {
    return tempo.getGroups().iterator().next();
  }

  @Override
  public Group renameGroup(Group g, String newName) {
    g.setName(newName);
    return g;
  }

  @Override
  public Group updateMembers(Group g, List<String> athletes) {
    // TODO does nothing
    return g;
  }
}
