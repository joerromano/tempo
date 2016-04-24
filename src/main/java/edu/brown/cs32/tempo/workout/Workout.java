package edu.brown.cs32.tempo.workout;

import java.util.Date;

import edu.brown.cs32.tempo.location.PostalCode;

public class Workout {
  private Date date;
  private int intensity; // a number from 1-3
  private PostalCode location;
  private String type;
  private double score;
  private String time;
  private String id;

  public final static String AM = "AM";
  public final static String PM = "PM";

  public Workout(String id, Date date, int intensity, PostalCode location,
      String type, double score, String time) {
    this.id = id;
    this.date = date;
    this.intensity = intensity;
    this.location = location;
    this.type = type;
    this.score = score;
    this.time = time;
  }

  public void setIntensity(int intensity) {
    this.intensity = intensity;
  }

  public int getInensity() {
    return intensity;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public void setLocation(PostalCode location) {
    this.location = location;
  }

  public PostalCode getLocation() {
    return location;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Date getDate() {
    return date;
  }

  // public String toString();
  // public String toHTML():

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
