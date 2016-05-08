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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.google.common.collect.ImmutableMap;

import datasource.Datasource;
import datasource.DummySource;
import edu.brown.cs32.tempo.people.Coach;
import edu.brown.cs32.tempo.people.Group;
import edu.brown.cs32.tempo.people.Team;
import edu.brown.cs32.tempo.workout.Workout;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Spark;
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
  private static final String WORKOUT_FILE = "workoutpg.ftl";
  private static final String SETTINGS_FILE = "settings.ftl";
  private static final String LOGIN_POPOVER = null;
  private static final String TEAM_POPOVER = null;
  private static final String NEW_WORKOUT_POPOVER = null;
  private static final String USER_SESSION_ID = "coach";
  public static final DateFormat MMDDYYYY = new SimpleDateFormat("MMddyyyy");
  private static final String CURRENT_TEAM = "team";
  private static final String DELETE_PAGE = "delete.ftl";
  private static final String GROUP_FILE = null; // TODO!
  private static final String TEAM_MANAGE_FILE = "teammanage.ftl";
  public final int PORT;

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
   * Initializes a Spark server at localhost:<port> with a given data source.
   *
   * @param data
   *          A datasource, providing information for workouts, teams, etc
   * @param port
   *          The port number
   */
  public SparkServer(Datasource data, int port) {
    this(port);
    this.data = data;
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
    Spark.setPort(PORT);
    try {
      data = new DummySource();
    } catch (ParseException e) {
      System.out.printf("Date parsing error %s\n", e.getLocalizedMessage());
    }
  }

  /**
   * Runs the SparkServer
   */
  public void run() {
    externalStaticFileLocation("src/main/resources/static");
    exception(Exception.class, new ExceptionPrinter());

    SparkPathsSetup setup = new SparkPathsSetup(this, data);
    setup.teamSetup();
    setup.groupSetup();
    setup.accountSetup();
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
      Map<String, Object> variables = new HashMap<>();

      variables.put("title", "Workout schedule");
      variables.put("coach", c);
      variables.put("team", getCurrentTeam(req));

      return new ModelAndView(variables, SCHEDULE_FILE); // TODO
    } , freeMarker);
    get("/library", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, Object> variables = ImmutableMap.of("title",
          "Workout library", "coach", c);
      return new ModelAndView(variables, LIBRARY_FILE); // TODO
    } , freeMarker);
    get("/teammanage", (req, res) -> {
      Coach c = authenticate(req, res);
      Team t = getCurrentTeam(req);
      Map<String, Object> variables = ImmutableMap.of("title",
          "Team management", "coach", c, "team", t);
      return new ModelAndView(variables, TEAM_MANAGE_FILE);
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
      variables.put("workout", w);
      return new ModelAndView(variables, WORKOUT_FILE);
    } , freeMarker);
    get("/group/:id", (req, res) -> {
      Coach c = getAuthenticatedUser(req);
      String id = req.params(":id");
      Group g = data.getGroup(id);
      String title = "Group " + g.getName();
      Map<String, Object> variables = new HashMap<>();
      variables.put("title", title);
      variables.put("coach", c);
      variables.put("group", g);
      return new ModelAndView(variables, GROUP_FILE);
    } , freeMarker);
    get("/settings", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, Object> variables = new HashMap<>();
      variables.put("title", "Settings");
      variables.put("coach", c);
      variables.put("team", getCurrentTeam(req));
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
  }

  private void jsonPOSTsetup() {
    JsonTransformer transformer = new JsonTransformer();

    post("/login", (req, res) -> {
      QueryParamsMap qm = req.queryMap();
      String email = qm.value("email");
      String pwd = qm.value("password");
      System.out.printf("Attempting to login %s with password %s\n", email,
          pwd);
      try {
        Coach c = data.authenticate(email, pwd);

        if (c != null) {
          addAuthenticatedUser(req, c);
          if (!c.getTeams().isEmpty()) {
            setCurrentTeam(req, c.getTeams().iterator().next());
            res.redirect("/schedule");
          }
          res.redirect("/settings");
          halt();
        }
      } catch (Exception e) {
        return ImmutableMap.of("success", "false");
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

    post("/suggestions", (req, res) -> {
      return null; // TODO
    } , transformer);

    post("/search", (req, res) -> {
      return null; // TODO
    } , transformer);

    post("/library", (req, res) -> {
      Coach c = authenticate(req, res);
      Map<String, String> json = parse(req.body());
      String sortBy = json.get("sort");
      int from = Integer.parseInt(json.get("from"));
      int to = Integer.parseInt(json.get("to"));
      return data.getLibrary(c, sortBy, from, to);
    } , transformer);

    post("/newteam", (req, res) -> {
      Coach c = authenticate(req, res);
      String name = parse(req.body()).get("name");
      return data.addTeam(c, name);
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

  Coach authenticate(Request req, Response res) {
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
    req.session().attribute(CURRENT_TEAM, t.getId());
  }

  Team getCurrentTeam(Request req) {
    if (req.session().attribute(CURRENT_TEAM) != null) {
      return data.getTeam(req.session().attribute(CURRENT_TEAM));
    } else {
      return null;
    }
  }

  Map<String, String> parse(String s) {
    try {
      return JSONParser.toMap(s);
    } catch (JSONException e) {
      System.out.printf("JSON error: %s", e.getLocalizedMessage());
      return null;
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
