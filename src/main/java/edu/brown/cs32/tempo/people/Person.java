package edu.brown.cs32.tempo.people;

public abstract class Person {
	private String name; // Name of the person
	private int location; // Location of the person
	private String email;
	
	public Person(String email, String name, int location){
		this.setEmail(email);
		this.name = name;
		this.location = location;
	}
	
	/**
	 * Gets the default location for the person
	 * @return - the location for the person
	 */
	public int getLocation(){
		return location;
	}
	
	/**
	 * Changes the default location for the person
	 */
	public void setLocation(int location){
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
}
