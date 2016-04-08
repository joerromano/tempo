package edu.brown.cs32.tempo.people;

import java.util.Collection;
import java.util.HashSet;

public class Team {
	private Collection<Coach> coaches;
	private Collection<Athlete> roster;
	private Collection<Group> groups;
	private String name;
	private String location;

	public Team(String name, String location) {
		this.setName(name);
		this.setLocation(location);
		roster = new HashSet<Athlete>();
	}

	public Team(String name, String location, Athlete athlete) {
		this.setName(name);
		this.setLocation(location);
		roster = new HashSet<Athlete>();
		roster.add(athlete);
	}

	public Team(String name, String location, Collection<Athlete> athlete) {
		this.setName(name);
		this.setLocation(location);
		roster = new HashSet<Athlete>();
		roster.addAll(athlete);
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
	 *            - the athlete to be added
	 * @return - true if added, otherwise false
	 */
	public boolean addAthlete(Athlete a) {
		return roster.add(a);
	}

	/**
	 * Adds multiple teams
	 * 
	 * @param w
	 *            - teams to be added
	 * @return - true if added, otherwise false
	 */
	public boolean addAthlete(Collection<Athlete> a) {
		return roster.addAll(a);
	}

	/**
	 * Remove a specific athlete
	 * 
	 * @param a
	 *            - athlete to be removed
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
