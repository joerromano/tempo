package jsonWrappers;

import java.util.List;

public class RawGroup {
  private String id;
  private List<String> athletes;

  public String getId() {
    return id;
  }

  public List<String> getAthletes() {
    return athletes;
  }

  @Override
  public String toString() {
    return String.format("ID: %s Athletes: %s", id, athletes);
  }
}
