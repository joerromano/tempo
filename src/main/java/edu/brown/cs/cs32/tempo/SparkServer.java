package edu.brown.cs.cs32.tempo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import org.json.JSONObject;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
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
  private static final String WORKOUT_FILE = null;
  private static final String SETTINGS_FILE = null;
  private static final String LOGIN_POPOVER = null;
  private static final String TEAM_POPOVER = null;
  private static final String NEW_WORKOUT_POPOVER = null;
  private final int PORT;

  /**
   * Initializes a Spark server at localhost:4567.
   */
  public SparkServer() {
    PORT = 4567;
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
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes
    // Spark.get("/index", (req, res) -> {
    // Map<String, Object> variables = ImmutableMap.of("title", "Index");
    // return new ModelAndView(variables, "index.ftl");
    // } , freeMarker);
    // Spark.get("/userhome", (req, res) -> {
    // Map<String, Object> variables = ImmutableMap.of("title", "Index");
    // return new ModelAndView(variables, "userhome.ftl");
    // } , freeMarker);
    // Spark.get("/coachhome", (req, res) -> {
    // Map<String, Object> variables = ImmutableMap.of("title", "Index");
    // return new ModelAndView(variables, "coachhome.ftl");
    // } , freeMarker);
    Spark.get("/home", (req, res) -> {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Tempo: Your workout solution");
      return new ModelAndView(variables, HOME_FILE); // TODO
    } , freeMarker);
    Spark.get("/schedule", (req, res) -> {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Workout schedule");
      return new ModelAndView(variables, SCHEDULE_FILE); // TODO
    } , freeMarker);
    Spark.get("/library", (req, res) -> {
      Map<String, Object> variables =
          ImmutableMap.of("title", "Workout library");
      return new ModelAndView(variables, LIBRARY_FILE); // TODO
    } , freeMarker);
    Spark.get("/team/:id", (req, res) -> {
      String id = req.params(":id");
      // TODO get team with id, set title
      String title = "Team " + id;
      Map<String, Object> variables = ImmutableMap.of("title", title);
      return new ModelAndView(variables, TEAM_FILE); // TODO
    } , freeMarker);
    Spark.get("/workout/:id", (req, res) -> {
      String id = req.params(":id");
      // TODO get workout with id, set title
      String title = "Workout " + id;
      Map<String, Object> variables = ImmutableMap.of("title", title);
      return new ModelAndView(variables, WORKOUT_FILE); // TODO
    } , freeMarker);
    Spark.get("/settings", (req, res) -> {
      // TODO how will authentication work?
      Map<String, Object> variables = ImmutableMap.of("title", "Settings");
      return new ModelAndView(variables, SETTINGS_FILE);
    } , freeMarker);

    Spark.post("/popover/login", (req, res) -> {
      // TODO return the login popover
      return new ModelAndView(Collections.EMPTY_MAP, LOGIN_POPOVER);
    } , freeMarker);

    Spark.post("/popover/teamregister", (req, res) -> {
      // TODO return the team register popover
      return new ModelAndView(Collections.EMPTY_MAP, TEAM_POPOVER);
    } , freeMarker);

    Spark.post("/popover/create_workout", (req, res) -> {
      // TODO return the new workout popover
      return new ModelAndView(Collections.EMPTY_MAP, NEW_WORKOUT_POPOVER);
    } , freeMarker);

    // JSON-related POST requests
    jsonPOSTsetup();
  }

  private void jsonPOSTsetup() {
    Gson gson = new Gson();
    JsonTransformer transformer = new JsonTransformer();
    Spark.post("/group", (req, res) -> {
      JSONObject json;
      try {
        json = new JSONObject(req.body());
        String team = json.getString("team");
        String date = json.getString("date");

        // TODO get all groups associated with
        // that team and date and return a list
        // of those groups
      } catch (Exception e) {
        System.out.printf("JSON error: %s", e.getLocalizedMessage());
        return null;
      }
      return null;
    } , transformer);

    Spark.post("/suggestions", (req, res) -> {
      return null; // TODO
    } , transformer);

    Spark.post("/add", (req, res) -> {
      return null; // TODO
    } , transformer);

    Spark.post("/publish", (req, res) -> {
      return null; // TODO
    } , transformer);

    Spark.post("/search", (req, res) -> {
      return null; // TODO
    } , transformer);
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
