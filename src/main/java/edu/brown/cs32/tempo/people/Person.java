package edu.brown.cs32.tempo.people;

import com.google.gson.annotations.Expose;

import edu.brown.cs32.tempo.location.PostalCode;

public abstract class Person {
  @Expose
  private String name; // Name of the person
  @Expose
  private PostalCode location; // Location of the person
  @Expose
  private String email;
  @Expose
  private String id;

  public Person(String id, String email, String name, PostalCode location) {
    this.id = id;
    this.setEmail(email);
    this.name = name;
    this.location = location;
  }

  /**
   * Gets the default location for the person
   *
   * @return - the location for the person
   */
  public PostalCode getLocation() {
    return location;
  }

  /**
   * Changes the default location for the person
   */
  public void setLocation(PostalCode location) {
    this.location = location;
  }

  /**
   * Get the name for the person
   *
   * @return - the name of the person
   */
  public String getName() {
    return name;
  }

  public void setName(String name) {
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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Person other = (Person) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

}
