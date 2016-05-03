package edu.brown.cs.cs32.tempo;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.post;
import static spark.SparkBase.externalStaticFileLocation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import datasource.Datasource;
import datasource.DummySource;
import edu.brown.cs32.tempo.location.PostalCode;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.PhoneNumber;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.publisher.Publisher;
import edu.brown.cs32.tempo.workout.Workout;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * SparkServer class. Runs a spark server at localhost:4567
 *
 * @author thale1
 *
 */
public class SparkServer {
  // TODO What freemarker files are we using?
  private static final String HOME_FILE = "index.ftl";
  private static final String SCHEDULE_FILE = "coachhome.ftl";
  private static final String LIBRARY_FILE = "workoutlibrary.ftl";
  private static final String TEAM_FILE = null;
  private static final String WORKOUT_FILE = null;
  private static final String SETTINGS_FILE = "settings.ftl";
  private static final String LOGIN_POPOVER = null;
  private static final String TEAM_POPOVER = null;
  private static final String NEW_WORKOUT_POPOVER = null;
  private static final String USER_SESSION_ID = "coach";
  public static final DateFormat MMDDYYYY = new SimpleDateFormat("MMddyyyy");
  private static final String CURRENT_TEAM = "team";
  private static final String DELETE_PAGE = "delete.ftl";
  private final int PORT;

  private Datasource data;

  /**
   * Initializes a Spark server at localhost:4567 with a given data source.
   *
   * @param data
   *          A datasource, providing information for workouts, teams, etc
   */
  public SparkServer(Datasource data) {
    this.data = data;
    PORT = 4567;
  }

  /**
   * Initializes a Spark server at localhost:4567.
   */
  public SparkServer() {
    PORT = 4567;
    try {
      data = new DummySource();
    } catch (ParseException e) {
      System.out.printf("Date parsing error %s\n", e.getLocalizedMessage());
    }
  }

  /**
   * Initializes a Spark server. Server will run at localhost:<port>/
   *
   * @param port
   *          The port number
   */
  public SparkServer(int port) {
    assert port >= 0 : "Port # must be non-negative";
    PORT = port;
  }

  /**
   * Runs the SparkServer
   */
  public void run() {
    externalStaticFileLocation("src/main/resources/static");
    exception(Exception.class, new ExceptionPrinter());

    GETsetup();
    // JSON-related POST requests
    jsonPOSTsetup();
  }

  private void GETsetup() {
    FreeMarkerEngine freeMarker = createEngine();

    get("/", (req, res) -> {
      res.redirect("/home");
      halt();
      return null;
    });
    get("/home", (req, res) -> {
      Coach c = getAuthenticatedUser(req);
      if (c != null) {
        res.redirect("/schedule");
        halt();
      }
      Map<String, Object> variables = ImmutableMap.of("title",
          "Tempo: Your workout solution");
      return new ModelAndView(variables, HOME_FILE);
    } , freeMarker);
    get("/schedule", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, Object> variables = ImmutableMap.of("title",
          "Workout schedule", "coach", c);
      return new ModelAndView(variables, SCHEDULE_FILE); // TODO
    } , freeMarker);
    get("/library", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, Object> variables = ImmutableMap.of("title",
          "Workout library", "coach", c);
      return new ModelAndView(variables, LIBRARY_FILE); // TODO
    } , freeMarker);
    get("/team/:id", (req, res) -> {
      Coach c = getAuthenticatedUser(req);
      String id = req.params(":id");
      Team t = data.getTeam(id);
      String title = "Team " + t.getName();
      Map<String, Object> variables = new HashMap<>();
      variables.put("title", title);
      variables.put("coach", c);
      return new ModelAndView(variables, TEAM_FILE);
    } , freeMarker);
    get("/workout/:id", (req, res) -> {
      Coach c = getAuthenticatedUser(req);
      String id = req.params(":id");
      // TODO set title
      Workout w = data.getWorkout(id);
      String title = "Workout view";
      Map<String, Object> variables = new HashMap<>();
      variables.put("title", title);
      variables.put("coach", c);
      return new ModelAndView(variables, WORKOUT_FILE);
    } , freeMarker);
    get("/settings", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, Object> variables = ImmutableMap.of("title", "Settings",
          "coach", c);
      return new ModelAndView(variables, SETTINGS_FILE);
    } , freeMarker);
    get("/logout", (req, res) -> {
      removeAuthenticatedUser(req);
      res.redirect("/home");
      return null;
    });
    get("/delete", (req, res) -> {
      Coach c = authenticate(req, res);
      removeAuthenticatedUser(req);
      boolean success = data.deleteCoach(c);
      Map<String, Object> variables = ImmutableMap.of("title",
          "Account deleted", "success", success);
      return new ModelAndView(variables, DELETE_PAGE);
    } , freeMarker);
    // TODO do we need this?
    /*
     * post("/popover/login", (req, res) -> { return new
     * ModelAndView(Collections.EMPTY_MAP, LOGIN_POPOVER); } , freeMarker);
     * 
     * post("/popover/teamregister", (req, res) -> { return new
     * ModelAndView(Collections.EMPTY_MAP, TEAM_POPOVER); } , freeMarker);
     * 
     * post("/popover/create_workout", (req, res) -> { return new
     * ModelAndView(Collections.EMPTY_MAP, NEW_WORKOUT_POPOVER); } ,
     * freeMarker);
     */
  }

  /**
   * 
   */
  private void jsonPOSTsetup() {
    Gson gson = new Gson();
    JsonTransformer transformer = new JsonTransformer();

    post("/login", (req, res) -> {
      QueryParamsMap qm = req.queryMap();
      String email = qm.value("email");
      String pwd = qm.value("password");
      Coach c = data.authenticate(email, pwd);
      if (c != null) {
        setCurrentTeam(req, c.getTeams().stream().findFirst().get());
        res.redirect("/schedule");
        addAuthenticatedUser(req, c);
        halt();
      }
      return false;
    } , transformer);

    post("/switchteam", (req, res) -> {
      Coach c = authenticate(req, res);
      QueryParamsMap qm = req.queryMap();
      String teamId = qm.value("team");
      Team t = c.getTeamById(teamId);
      setCurrentTeam(req, t);
      res.redirect("/settings");
      halt();
      return null;
    });

    // gets a group based on a team & start/end date
    post("/group", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> map = parse(req.body());
      System.out.println(map);
      Team team = getCurrentTeam(req);
      Date start = null;
      Date end = null;
      try {
        start = MMDDYYYY.parse(map.get("start"));
        end = MMDDYYYY.parse(map.get("end"));
      } catch (Exception e) {
        System.out.printf("Date parsing error: %s\n", e.getLocalizedMessage());
      }

      Collection<Group> groups = data.getGroups(team, start, end);
      Set<Athlete> assignedAthletes = new HashSet<>();
      for (Group g : groups) {
        assignedAthletes.addAll(g.getMembers());
      }
      Set<Athlete> unassigned = Sets.difference(new HashSet<>(team.getRoster()),
          assignedAthletes);
      System.out.printf("Returned group %s\n", groups);
      System.out.printf("Unassigned athletes: %s\n", unassigned);
      return ImmutableMap.of("groups", groups, "unassigned",
          unassigned.toArray());
    } , transformer);

    post("/suggestions", (req, res) -> {
      return null; // TODO
    } , transformer);

    // add a training group
    post("/add", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String name = json.get("name");
      Team t = getCurrentTeam(req);
      Date start = null;
      try {
        start = MMDDYYYY.parse(json.get("start"));
      } catch (Exception e) {
        System.out.printf("Date parsing error: %s\n", e.getLocalizedMessage());
      }
      return data.addGroup(t, name, start);
    } , transformer);

    post("/addmember", (req, res) -> {
      Coach c = authenticate(req, res);
      Team t = getCurrentTeam(req);
      Map<String, String> json = parse(req.body());
      String name = json.get("name");
      String number = json.get("number");
      String email = json.get("email");
      PostalCode location = new PostalCode(json.get("location"));
      return data.addMember(t, email, number, name, location);
    } , transformer);

    post("/publish", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String groupId = json.get("id");
      // TODO how does publishing work? email/sms api?
      // How to get the group associated w/id?
      Group g = data.getGroup(groupId);

      Publisher.publish(g);
      return true;
    } , transformer);

    post("/renamegroup", (req, res) -> {
      authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String groupId = json.get("id");
      String newName = json.get("name");
      Group g = data.getGroup(groupId);
      return data.renameGroup(g, newName);
    } , transformer);

    post("/updateweek", (req, res) -> {
      authenticate(req, res);
      Type listType = new TypeToken<ArrayList<RawGroup>>() {
      }.getType();
      List<RawGroup> groups = new Gson().fromJson(req.body(), listType);
      for (RawGroup rg : groups) {
        Group g = data.getGroup(rg.getId());
        data.updateMembers(g, rg.getAthletes());
      }

      return null;
    } , transformer);

    post("/updategroup", (req, res) -> {
      authenticate(req, res);
      GroupUpdate gUpdate = gson.fromJson(req.body(), GroupUpdate.class);
      Group g = data.getGroup(gUpdate.id);
      data.updateMembers(g, gUpdate.members);
      return data.updateWorkouts(g, gUpdate.workouts);
    } , transformer);

    post("/deletegroup", (req, res) -> {
      authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String id = json.get("id");
      return data.deleteGroupById(id);
    } , transformer);

    post("/updateworkout", (req, res) -> {
      authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String workoutId = json.get("id");
      Workout w = gson.fromJson(req.body(), Workout.class);
      return data.updateWorkout(workoutId, w);
    } , transformer);

    post("/addworkout", (req, res) -> {
      authenticate(req, res);
      Map<String, String> json = parse(req.body());
      Group g = data.getGroup(json.get("groupid"));
      Workout w = gson.fromJson(json.get("workout"), Workout.class);
      return data.addWorkout(g, w);
    } , transformer);

    post("/search", (req, res) -> {
      return null; // TODO
    } , transformer);

    post("/renameteam", (req, res) -> {
      authenticate(req, res);
      Map<String, String> json = parse(req.body());
      Team t = data.getTeam(json.get("team"));
      String newName = json.get("name");
      return ImmutableMap.of("success", data.renameTeam(t, newName));
    } , transformer);

    post("/disbandteam", (req, res) -> {
      authenticate(req, res);
      Map<String, String> json = parse(req.body());
      Team t = data.getTeam(json.get("team"));
      return ImmutableMap.of("success", data.disbandTeam(t));
    } , transformer);

    post("/update", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      if (json.containsKey("old-password")
          && json.containsKey("new-password")) {
        String oldPwd = json.get("old-password");
        String newPwd = json.get("new-password");
        return data.updatePassword(c, oldPwd, newPwd);
      } else {
        Map<String, Boolean> results = new HashMap<>();
        if (json.containsKey("name")) {
          String name = json.get("name");
          results.put("name", data.updateName(c, name));
        }
        if (json.containsKey("phone")) {
          PhoneNumber phone = new PhoneNumber(json.get("phone"));
          results.put("phone", data.updatePhone(c, phone));
        }
        return results;
      }
    } , transformer);
  }

  private Team getTeam(Coach c, String name) {
    return c.getTeams().stream().filter(e -> e.getName().equals(name))
        .findFirst().get();
  }

  private Coach getCoach(String email, String pwd) {
    return data.authenticate(email, pwd);
  }

  private Coach authenticate(Request req, Response res) {
    Coach c = getAuthenticatedUser(req);
    if (c == null) {
      res.redirect("/home");
      // TODO send to error page?
      halt();
      return null;
    }
    return c;
  }

  private void addAuthenticatedUser(Request request, Coach c) {
    request.session().attribute(USER_SESSION_ID, c);
  }

  private Coach getAuthenticatedUser(Request req) {
    return req.session().attribute(USER_SESSION_ID);
  }

  private void removeAuthenticatedUser(Request request) {
    request.session().removeAttribute(USER_SESSION_ID);
    request.session().removeAttribute(CURRENT_TEAM);
  }

  private void setCurrentTeam(Request req, Team t) {
    req.session().attribute(CURRENT_TEAM, t);
  }

  private Team getCurrentTeam(Request req) {
    return req.session().attribute(CURRENT_TEAM);
  }

  private Map<String, String> parse(String s) {
    try {
      return JSONParser.toMap(s);
    } catch (JSONException e) {
      System.out.printf("JSON error: %s", e.getLocalizedMessage());
      return null;
    }
  }

  private class GroupUpdate {
    private String id;
    private List<String> members, workouts;
  }

  private class RawGroup {
    private final String id;
    private final List<String> athletes;

    public RawGroup(String id, List<String> athletes) {
      this.id = id;
      this.athletes = athletes;
    }

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

  private class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
      return gson.toJson(model);
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);
      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");
        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }
}
