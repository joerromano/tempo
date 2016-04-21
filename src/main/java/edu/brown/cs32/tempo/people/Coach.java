package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.HashSet;

public class Coach extends Person {
	Collection<Team> teams; // Teams that the coach is in charge

	public Coach(String email, String name, int location, Team team) {
		super(email, name, location);
		this.teams = new HashSet<Team>();
		teams.add(team);
	}
	
	public Coach(String email, String name, int location, Collection<Team> team) {
		super(email, name, location);
		this.teams = new HashSet<Team>();
		teams.addAll(team);
	}

	public Coach(String email, String name, int location) {
		super(email, name, location);
		this.teams = new HashSet<Team>();
	}

	/**
	 * Returns all the team(s) for a given coach
	 * 
	 * @return this coach's team(s)
	 */
	public Collection<Team> getTeams() {
		return teams;
	}
	
	public Collection<Athlete> getAllAtheletes() {
		Collection<Athlete> ret = new HashSet<Athlete>();
		
		for(Team t : teams){
			ret.addAll(t.getRoster());
		}
		
		return ret;
	}

	/**
	 * Adds one team
	 * 
	 * @param w
	 *            - the team to be added
	 * @return - true if added, otherwise false
	 */
	public boolean addTeam(Team t) {
		return teams.add(t);
	}

	/**
	 * Adds multiple teams
	 * 
	 * @param w
	 *            - teams to be added
	 * @return - true if added, otherwise false
	 */
	public boolean addTeam(Collection<Team> t) {
		return teams.addAll(t);
	}

	/**
	 * Remove a specific team
	 * 
	 * @param t
	 *            - team to be removed
	 * @return - true if removed, otherwise false
	 */
	public boolean removeTeam(Team t) {
		return teams.remove(t);
	}

	/**
	 * Remove all teams for the coach
	 */
	public void clearTeams() {
		teams.clear();
	}

}
