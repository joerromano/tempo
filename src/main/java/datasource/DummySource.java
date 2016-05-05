package datasource;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.brown.cs.cs32.tempo.SparkServer;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.PhoneNumber;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;

/**
 * Dummy Datasource to test SparkServer.
 *
 * @author Tom
 *
 */
public class DummySource implements Datasource {
  private Athlete simon, luci, tom, joe;
  private List<Coach> coaches;
  private Coach jj;
  private Team tempo;
  private PostalCode prov = new PostalCode("02912");
  private int id = 0;

  public DummySource() throws ParseException {
    simon = new Athlete("s1", "simon_belete@brown.edu", "Simon Belete", prov);
    simon.setNumber(new PhoneNumber("816-288-5626"));
    joe = new Athlete("s2", "joseph_romano@brown.edu", "Joe Romano", prov);
    joe.setNumber(new PhoneNumber("+1 (781) 697-6044"));
    luci = new Athlete("s3", "lucia_cooke@brown.edu", "Luci Cooke", prov);
    luci.setNumber(new PhoneNumber("+1 (917) 504-6979"));
    tom = new Athlete("s4", "thomas_hale@brown.edu", "Tom Hale", prov);
    tom.setNumber(new PhoneNumber("518-258-6822"));
    List<Athlete> athletes = new ArrayList<>();
    athletes.add(simon);
    athletes.add(joe);
    athletes.add(luci);
    athletes.add(tom);
    jj = new Coach("s5", "jj@cs.brown.edu", "JJ", prov);
    coaches = new ArrayList<>();
    coaches.add(jj);
    tempo = new Team("Tempo Team", prov, jj, athletes, "1321");
    jj.addTeam(tempo);
    Workout r1 = new Workout("w1", SparkServer.MMDDYYYY.parse("05102016"), 0,
        prov, "Recovery run", 8, Workout.AM);
    Workout r2 = new Workout("w2", SparkServer.MMDDYYYY.parse("05122016"), 0,
        prov, "Long run", 13, Workout.AM);
    List<Workout> workouts = new ArrayList<>();
    workouts.add(r1);
    workouts.add(r2);
    Group g = new Group("Example Group", SparkServer.MMDDYYYY.parse("05082016"),
        "g1");
    g.addWorkout(workouts);
    g.setMembers(athletes.subList(2, 4));
    Group g2 = new Group("Week 2", SparkServer.MMDDYYYY.parse("050152016"),
        "g2");
    List<Workout> g2Workouts = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      Date d = SparkServer.MMDDYYYY.parse(String.format("05%d2016", 15 + i));
      Workout r = new Workout("w0" + i, d, 0, prov, "Run",
          new Random().nextInt(4) + 2, Workout.AM);
      g2Workouts.add(r);
    }
    g2.addWorkout(g2Workouts);
    g2.setMembers(athletes);
    tempo.addGroup(g);
    tempo.addGroup(g2);
  }

  @Override
  public Workout getWorkout(String id) {
    try {
      return new Workout("w2", SparkServer.MMDDYYYY.parse("04242016"), 0, prov,
          "Long run", 13, Workout.AM);
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
    Group g = new Group(name, start, String.format("g%d", id++));
    t.addGroup(g);
    return g;
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
    throw new UnsupportedOperationException(
        "DummySource cannot update group members!");
  }

  @Override
  public Athlete addMember(Team t, String email, String number, String name,
      PostalCode location) {
    Athlete a = new Athlete("new id", email, name, location);
    t.addAthlete(a);
    return a;
  }

  @Override
  public Group updateWorkouts(Group g, List<String> workouts) {
    throw new UnsupportedOperationException(
        "DummySource can't update workouts by id");
  }

  @Override
  public boolean deleteGroupById(String id) {
    for (Group g : tempo.getGroups()) {
      if (g.getId().equals(id)) {
        return tempo.getGroups().remove(g);
      }
    }
    return false;
  }

  @Override
  public Workout updateWorkout(String workoutId, Workout w) {
    throw new UnsupportedOperationException(
        "DummySource can't update workouts");
  }

  @Override
  public Group addWorkout(Group g, Workout w) {
    g.addWorkout(w);
    return g;
  }

  @Override
  public Coach getCoach(String id) {
    throw new UnsupportedOperationException(
        "DummySource can't get coaches by id");
  }

  @Override
  public boolean renameTeam(Team t, String newName) {
    t.setName(newName);
    return true;
  }

  @Override
  public boolean disbandTeam(Team t) {
    throw new UnsupportedOperationException(
        "DummySource can't really disband teams");
  }

  @Override
  public boolean deleteCoach(Coach c) {
    throw new UnsupportedOperationException(
        "DummySource can't really delete coaches");
  }

  @Override
  public boolean updatePassword(Coach c, String oldPwd, String newPwd) {
    throw new UnsupportedOperationException(
        "DummySource can't update passwords");
  }

  @Override
  public boolean updateName(Coach c, String name) {
    c.setName(name);
    return true;
  }

  @Override
  public boolean updatePhone(Coach c, PhoneNumber phone) {
    throw new UnsupportedOperationException(
        "DummySource can't update phone numbers");
  }

  @Override
  public Team addTeam(Coach c, String name) {
    Team t = new Team("t" + id, name, c.getLocation(), c, false);
    id++;
    c.addTeam(t);
    return t;
  }

  @Override
  public Athlete editAthlete(String id, String name, String number,
      String email, PostalCode location) {
    throw new UnsupportedOperationException(
        "DummySource cannot edit athlete information.");
  }

  @Override
  public Coach addCoach(String name, String email, PostalCode loc, String pwd) {
    Coach c = new Coach("s" + id, email, name, loc);
    id++;
    coaches.add(c);
    return c;
  }

  @Override
  public boolean removeAthlete(Team t, String id) {
    return t.getRoster().removeIf(e -> e.getId().equals(id));
  }
}
