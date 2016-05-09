package edu.brown.cs.cs32.tempo;

import static spark.Spark.post;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import datasource.Datasource;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.publisher.Publisher;
import edu.brown.cs32.tempo.workout.Suggestions;
import edu.brown.cs32.tempo.workout.Workout;
import jsonWrappers.GroupUpdate;
import jsonWrappers.GroupWrapper;
import jsonWrappers.RawGroup;
import jsonWrappers.TeamWrapper;

public class SparkPathsSetup {
  JsonTransformer transformer = new JsonTransformer();
  Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
  SparkServer s;
  Datasource data;

  public SparkPathsSetup(SparkServer s, Datasource data) {
    this.s = s;
    this.data = data;
  }

  public void teamSetup() {
    post("/newteam", (req, res) -> {
      Coach c = s.authenticate(req, res);
      String name = s.parse(req.body()).get("name");
      return new TeamWrapper(data.addTeam(c, name));
    } , transformer);

    post("/renameteam", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      Team t = data.getTeam(json.get("team"));
      String newName = json.get("name");
      return ImmutableMap.of("success", data.renameTeam(t, newName));
    } , transformer);

    post("/disbandteam", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      Team t = data.getTeam(json.get("team"));
      return ImmutableMap.of("success", data.disbandTeam(t));
    } , transformer);

    post("/addmember", (req, res) -> {
      s.authenticate(req, res);
      Team t = s.getCurrentTeam(req);
      Map<String, String> json = s.parse(req.body());
      String name = json.get("name");
      String number = json.get("number");
      String email = json.get("email");

      String id;
      try {
        id = json.get("id");
      } catch (Exception e) {
        id = null;
      }
      PostalCode location = new PostalCode(json.get("location"));
      System.out.printf(
          "all good: adding %s to %s\nNumber: %s Email: %s Id: %s", name, t,
          number, email, id);
      if (id == null) {
        System.out.println("adding");
        try {
          return data.addMember(t, email, number, name, location);
        } catch (IllegalArgumentException e) {
          return ImmutableMap.of("error", "Athlete with email already exists");
        }
      } else {
        System.out.println("editing");
        return data.editAthlete(id, name, number, email, location);
      }
    } , transformer);

    post("/removemember", (req, res) -> {
      Coach c = s.authenticate(req, res);
      Team t = s.getCurrentTeam(req);
      String id = s.parse(req.body()).get("id");
      return data.removeAthlete(t, id);
    } , transformer);
  }

  public void groupSetup() {
    // gets a group based on a team & start/end date
    post("/group", (req, res) -> {
      Coach c = s.authenticate(req, res);
      Map<String, String> map = s.parse(req.body());
      System.out.println(map);
      Team team = s.getCurrentTeam(req);
      Date start = null;
      Date end = null;
      try {
        start = SparkServer.MMDDYYYY.parse(map.get("start"));
        end = SparkServer.MMDDYYYY.parse(map.get("end"));
      } catch (Exception e) {
        System.out.printf("Date parsing error: %s\n", e.getLocalizedMessage());
      }

      Collection<GroupWrapper> groups = new ArrayList<>();
      Set<Athlete> assignedAthletes = new HashSet<>();
      for (Group g : data.getGroups(team, start, end)) {
        assignedAthletes.addAll(g.getMembers());
        groups.add(new GroupWrapper(g));
      }
      Set<Athlete> unassigned = Sets.difference(new HashSet<>(team.getRoster()),
          assignedAthletes);
      System.out.printf("Returned group %s\n", groups);
      System.out.printf("Unassigned athletes: %s\n", unassigned);
      return ImmutableMap.of("groups", groups, "unassigned",
          unassigned.toArray());
    } , transformer);
    // add a training group
    post("/add", (req, res) -> {
      Coach c = s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      String name = json.get("name");
      Team t = s.getCurrentTeam(req);
      Date start = null;
      try {
        start = SparkServer.MMDDYYYY.parse(json.get("start"));
      } catch (Exception e) {
        System.out.printf("Date parsing error: %s\n", e.getLocalizedMessage());
      }
      return data.addGroup(t, name, start);
    } , transformer);

    post("/publish", (req, res) -> {
      Coach c = s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      String groupId = json.get("id");
      // TODO how does publishing work? email/sms api?
      // How to get the group associated w/id?
      Group g = data.getGroup(groupId);

      Publisher.publish(g);
      return true;
    } , transformer);

    post("/renamegroup", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      String groupId = json.get("id");
      String newName = json.get("name");
      Group g = data.getGroup(groupId);
      return data.renameGroup(g, newName);
    } , transformer);

    post("/updateweek", (req, res) -> {
      s.authenticate(req, res);
      Type listType = new TypeToken<ArrayList<RawGroup>>() {
      }.getType();
      List<RawGroup> groups = new Gson().fromJson(req.body(), listType);
      for (RawGroup rg : groups) {
        Group g = data.getGroup(rg.getId());
        data.updateMembers(g, rg.getAthletes());
      }

      return true;
    } , transformer);

    post("/updategroup", (req, res) -> {
      s.authenticate(req, res);
      GroupUpdate gUpdate = gson.fromJson(req.body(), GroupUpdate.class);
      Group g = data.getGroup(gUpdate.id);
      data.updateMembers(g, gUpdate.members);
      return data.updateWorkouts(g, gUpdate.workouts);
    } , transformer);

    post("/deletegroup", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      String id = json.get("id");
      return data.deleteGroupById(id);
    } , transformer);

    post("/updateworkout", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      String workoutId = json.get("id");
      Workout w = gson.fromJson(req.body(), Workout.class);
      return data.updateWorkout(workoutId, w);
    } , transformer);

    post("/addworkout", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      Group g = data.getGroup(json.get("groupid"));
      System.out.println("Adding workout to " + g);
      Map<String, String> map = s.parse(json.get("workout"));
      System.out.println(map);
      return data.addWorkout(g, map);

    } , transformer);

    post("/usesuggestion", (req, res) -> {
      s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      Group g = data.getGroup(json.get("groupid"));
      String type = json.get("type");
      Workout w = null;
      try {
        Date d = SparkServer.MMDDYYYY.parse(json.get("date"));
        System.out.printf("Finding suggestions for %s on day %s and type %s", g,
            d, type);
        DateTime dt = new DateTime(d);
        int day = dt.dayOfWeek().get();
        w = Suggestions.getSuggestions(g, dt, type).get(day).get(0);
        System.out.println(w);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      String time = json.get("time");
      Map<String, String> map = new HashMap<>();
      map.put("date", json.get("date"));
      map.put("intensity", "" + w.getIntensity());
      map.put("location", w.getLocation().toString());
      map.put("type", w.getType());
      map.put("score", "" + w.getScore());
      map.put("time", time);
      System.out.println("Adding workout " + map);
      return data.addWorkout(g, map);
    } , transformer);
  }

  public void accountSetup() {
    post("/newaccount", (req, res) -> {
      Map<String, String> json = s.parse(req.body());
      String email = json.get("email");
      String name = json.get("name");
      String pwd = json.get("password");
      String team_name = json.get("team_name");
      PostalCode loc = new PostalCode(json.get("location"));
      return data.addCoach(name, email, loc, pwd);
    } , transformer);

    post("/update", (req, res) -> {
      Coach c = s.authenticate(req, res);
      Map<String, String> json = s.parse(req.body());
      if (json.containsKey("old_password")
          && json.containsKey("new_password")) {
        String oldPwd = json.get("old_password");
        String newPwd = json.get("new_password");
        return data.updatePassword(c, oldPwd, newPwd);
      } else {
        Map<String, Boolean> results = new HashMap<>();
        if (json.containsKey("name")) {
          String name = json.get("name");
          results.put("name", data.updateName(c, name));
        }
        if (json.containsKey("phone")) {
          System.out.println("ERROR: Currently we do not support phone updates"
              + " since coaches don't have them!");
        }
        return results;
      }
    } , transformer);
  }
}
