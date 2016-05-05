package jsonWrappers;

import java.util.Collection;
import java.util.HashMap;

import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;

public class TeamWrapper {
  private String id;
  private Collection<Athlete> roster;
  private Collection<Group> groups;
  private String name;
  private PostalCode location;

  public TeamWrapper(Team t) {
    this.id = t.getId();
    this.roster = t.getRoster();
    this.groups = t.getGroups();
    this.name = t.getName();
    this.location = t.getLocation();
  }
}
