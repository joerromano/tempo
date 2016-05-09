package edu.brown.cs32.tempo.location;

import com.google.gson.annotations.Expose;

public class PostalCode {
  @Expose
  private String postalCode;

  public PostalCode(String postalCode) {
    this.setPostalCode(postalCode);
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  @Override
  public String toString() {
    return "PostalCode " + postalCode;
  }
}
