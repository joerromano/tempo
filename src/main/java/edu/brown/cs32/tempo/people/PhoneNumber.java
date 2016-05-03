package edu.brown.cs32.tempo.people;

/**
 * Phone number wrapper class
 *
 * @author Tom
 */
public class PhoneNumber {
  public final String number;

  /**
   * Construct a phone number from a string
   * 
   * @param s
   *          The phone number to be entered
   */
  public PhoneNumber(String s) {
    number = s;
  }

  @Override
  public String toString() {
    return number;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((number == null) ? 0 : number.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PhoneNumber other = (PhoneNumber) obj;
    if (number == null) {
      if (other.number != null)
        return false;
    } else if (!number.equals(other.number))
      return false;
    return true;
  }
}
