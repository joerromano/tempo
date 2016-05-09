package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.location.PostalCode;

public class Coach extends Person {

  Map <String, Team> teams; // Teams that the coach is in charge


  public Coach(String id, String email, String name, PostalCode location,
      Team team) {
    super(id, email, name, location);
    this.teams = new HashMap<String, Team>();
    teams.put(team.getId(), team);
  }

  public Coach(String id, String email, String name, PostalCode location,
      Collection<Team> team) {
    super(id, email, name, location);
    this.teams = new HashMap<String, Team>();
    
    for(Team t : team){
    	teams.put(t.getId(), t);
    }
  }

  public Coach(String id, String email, String name, PostalCode location) {
    super(id, email, name, location);
    this.teams = new HashMap<String, Team>();
  }

  /**
   * Returns all the team(s) for a given coach
   * 
   * @return this coach's team(s)
   */
  public Collection<Team> getTeams() {
    return teams.values();
  }
  
  public Collection<String> getTeamsID(){
	  return teams.keySet();
  }

  public Team getTeamById(String teamId) {
    return teams.get(teamId);
  }

  public Collection<Athlete> getAllAtheletes() {
    Collection<Athlete> ret = new HashSet<Athlete>();

    for (Team t : teams.values()) {
      ret.addAll(t.getRoster());
    }

    return ret;
  }

  /**
   * Adds one team
   * 
   * @param w
   *          - the team to be added
   * @return - true if added, otherwise false
   */
  public boolean addTeam(Team t) {
    return teams.put(t.getId(), t) != null;
  }

  /**
   * Adds multiple teams
   * 
   * @param w
   *          - teams to be added
   * @return - true if added, otherwise false
   */
  public void addTeam(Collection<Team> team) {
	  for(Team t : team){
		  teams.put(t.getId(), t);
	  }
  }

  /**
   * Remove a specific team
   * 
   * @param t
   *          - team to be removed
   * @return - true if removed, otherwise false
   */
  public boolean removeTeam(Team t) {
    return teams.remove(t.getId()) != null;
  }
  
  public boolean removeTeam(String id){
	  return teams.remove(id) != null;
  }

  /**
   * Remove all teams for the coach
   */
  public void clearTeams() {
    teams.clear();
  }

}
