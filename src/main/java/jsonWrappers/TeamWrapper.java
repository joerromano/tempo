package jsonWrappers;

import java.util.Collection;

import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;

public class TeamWrapper {
  @Expose
  private String id;
  @Expose
  private Collection<Athlete> roster;
  @Expose
  private Collection<Group> groups;
  @Expose
  private String name;
  @Expose
  private PostalCode location;

  public TeamWrapper(Team t) {
    this.id = t.getId();
    this.roster = t.getRoster();
    this.groups = t.getGroups();
    this.name = t.getName();
    this.location = t.getLocation();
  }
}
