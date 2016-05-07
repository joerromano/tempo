package jsonWrappers;

import java.util.List;

import com.google.gson.annotations.Expose;

public class RawGroup {
  @Expose
  private String id;
  @Expose
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
