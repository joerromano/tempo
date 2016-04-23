package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.HashSet;

public class Team {
  private Collection<Coach> coaches;
  private Collection<Athlete> roster;
  private Collection<Group> groups;
  private String name;
  private String location;
  private String id;

  public Team(String name, String location, Coach coach) {
    this.setName(name);
    this.setLocation(location);
    coaches = new HashSet<Coach>();
    coaches.add(coach);
    roster = new HashSet<Athlete>();
    groups = new HashSet<Group>();
  }

  public Team(String name, String location, Coach coach, Athlete athlete) {
    this.setName(name);
    this.setLocation(location);

    coaches = new HashSet<Coach>();
    coaches.add(coach);

    roster = new HashSet<Athlete>();
    roster.add(athlete);

    groups = new HashSet<Group>();
  }

  public Team(String name, String location, Coach coach,
      Collection<Athlete> athlete) {
    this.setName(name);
    this.setLocation(location);

    coaches = new HashSet<Coach>();
    coaches.add(coach);

    roster = new HashSet<Athlete>();
    roster.addAll(athlete);

    groups = new HashSet<Group>();
  }

  /**
   * Returns all the athlete(s) for a given team
   * 
   * @return this team's athlete
   */
  public Collection<Athlete> getRoster() {
    return roster;
  }

  /**
   * Adds one athlete
   * 
   * @param w
   *          - the athlete to be added
   * @return - true if added, otherwise false
   */
  public boolean addAthlete(Athlete a) {
    return roster.add(a);
  }

  /**
   * Adds multiple teams
   * 
   * @param w
   *          - teams to be added
   * @return - true if added, otherwise false
   */
  public boolean addAthlete(Collection<Athlete> a) {
    return roster.addAll(a);
  }

  /**
   * Remove a specific athlete
   * 
   * @param a
   *          - athlete to be removed
   * @return - true if removed, otherwise false
   */
  public boolean removeAthlete(Athlete a) {
    return roster.remove(a);
  }

  /**
   * Remove all athletes for the team
   */
  public void clearRoster() {
    roster.clear();
  }

  /**
   * Returns all the coach(es) for a given team
   * 
   * @return this team's coaches
   */
  public Collection<Coach> getCoach() {
    return coaches;
  }

  /**
   * Adds one coach
   * 
   * @param w
   *          - the coach to be added
   * @return - true if added, otherwise false
   */
  public boolean addCoach(Coach c) {
    return coaches.add(c);
  }

  /**
   * Adds multiple coaches
   * 
   * @param w
   *          - coaches to be added
   * @return - true if added, otherwise false
   */
  public boolean addCoach(Collection<Coach> c) {
    return coaches.addAll(c);
  }

  /**
   * Remove a specific coach
   * 
   * @param a
   *          - coach to be removed
   * @return - true if removed, otherwise false
   */
  public boolean removeCoach(Coach c) {
    return coaches.remove(c);
  }

  /**
   * Remove all coaches for the team
   */
  public void clearCoach() {
    coaches.clear();
  }

  public boolean addGroup(Group g) {
    return groups.add(g);
  }

  public boolean addGroup(Collection<Group> g) {
    return groups.addAll(g);
  }

  public boolean removeGroup(Group g) {
    return groups.remove(g);
  }

  public boolean removeGroup(Collection<Group> g) {
    return groups.removeAll(g);
  }

  public void clearGroup() {
    groups.clear();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Collection<Group> getGroups() {
    return groups;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getId() {
    return id;
  }
}
