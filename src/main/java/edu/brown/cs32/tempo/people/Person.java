package edu.brown.cs32.tempo.people;

import edu.brown.cs32.tempo.location.PostalCode;

public abstract class Person {
	private String name; // Name of the person
	private PostalCode location; // Location of the person
	private String email;
	private String id;
	
	public Person(String id, String email, String name, PostalCode location){
		this.setEmail(email);
		this.name = name;
		this.location = location;
	}
	
	/**
	 * Gets the default location for the person
	 * @return - the location for the person
	 */
	public PostalCode getLocation(){
		return location;
	}
	
	/**
	 * Changes the default location for the person
	 */
	public void setLocation(PostalCode location){
		this.location = location;
	}
	
	/**
	 * Get the name for the person
	 * @return - the name of the person
	 */
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
