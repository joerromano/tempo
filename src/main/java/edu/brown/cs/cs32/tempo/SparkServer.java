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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import datasource.Datasource;
import datasource.DummySource;
import edu.brown.cs32.tempo.people.Athlete;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Publisher;
import edu.brown.cs32.tempo.people.Team;
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
  private static final DateFormat MMDDYYYY = new SimpleDateFormat("MMddyyyy");
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
    data = new DummySource();
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
      Map<String, Object> variables = new HashMap<>();
      variables.put("title", "Tempo: Your workout solution");
      variables.put("coach", c);
      return new ModelAndView(variables, HOME_FILE); // TODO
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
      // TODO bug: stays logged in
      return null;
    });

    post("/popover/login", (req, res) -> {
      return new ModelAndView(Collections.EMPTY_MAP, LOGIN_POPOVER);
    } , freeMarker);

    post("/popover/teamregister", (req, res) -> {
      return new ModelAndView(Collections.EMPTY_MAP, TEAM_POPOVER);
    } , freeMarker);

    post("/popover/create_workout", (req, res) -> {
      return new ModelAndView(Collections.EMPTY_MAP, NEW_WORKOUT_POPOVER);
    } , freeMarker);
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
        res.redirect("/schedule");
        addAuthenticatedUser(req, c);
        halt();
      }
      return false;
    } , transformer);

    // gets a group based on a team & start/end date
    post("/group", (req, res) -> {
      // TODO authenticate
      Map<String, String> map = parse(req.body());
      String team = map.get("team");

      Date start = null;
      Date end = null;
      try {
        start = MMDDYYYY.parse(map.get("start"));
        end = MMDDYYYY.parse(map.get("end"));
      } catch (Exception e) {
        System.out.printf("Date parsing error: %s\n", e.getLocalizedMessage());
      }

      Set<Group> groups = data.getGroups(team, start, end);
      return groups;
    } , transformer);

    post("/suggestions", (req, res) -> {
      return null; // TODO
    } , transformer);

    // add a training group
    post("/add", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String team = json.get("team");
      String name = json.get("name");
      Team t = getTeam(c, team);
      Date start = null;
      try {
        start = MMDDYYYY.parse(json.get("start"));
      } catch (Exception e) {
        System.out.printf("Date parsing error: %s\n", e.getLocalizedMessage());
      }
      data.addGroup(t, name, start);
      return null;
    } , transformer);

    post("/addmember", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String team = json.get("team");
      String name = json.get("name");
      String number = json.get("number");
      String email = json.get("email");
      int location = Integer.parseInt(json.get("location"));
      // TODO ^ how does phone number factor in?
      Athlete a = new Athlete(email, name, location);
      Team t = getTeam(c, name);
      data.addMember(t, a);
      return null;
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
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String groupId = json.get("id");
      String newName = json.get("name");
      Group g = data.getGroup(groupId);
      data.renameGroup(g, newName);
      return true;
    } , transformer);

    post("/updateweek", (req, res) -> {
      Coach c = authenticate(req, res);
      List<RawGroup> groups = gson.fromJson(req.body(), List.class);
      for (RawGroup rg : groups) {
        Group g = data.getGroup(rg.getId());
        // TODO groups don't have a public members class...
        // So later add rg.getAthletes to g
      }
      return null;
    } , transformer);

    post("/search", (req, res) -> {
      return null; // TODO
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
  }

  private Map<String, String> parse(String s) {
    try {
      return JSONParser.toMap(s);
    } catch (JSONException e) {
      System.out.printf("JSON error: %s", e.getLocalizedMessage());
      return null;
    }
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
