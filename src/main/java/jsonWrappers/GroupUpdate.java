package jsonWrappers;

import java.util.List;

import com.google.gson.annotations.Expose;

public class GroupUpdate {
  @Expose
  public String id;
  @Expose
  public List<String> members, workouts;
}
