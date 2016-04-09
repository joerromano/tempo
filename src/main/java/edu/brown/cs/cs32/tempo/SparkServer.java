package edu.brown.cs.cs32.tempo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.json.JSONObject;

import com.google.gson.Gson;

import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;
import spark.Spark;
import spark.template.freemarker.FreeMarkerEngine;

public class SparkServer {
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
      return null; // TODO
    } , freeMarker);
    Spark.get("/schedule", (req, res) -> {
      return null; // TODO
    } , freeMarker);
    Spark.get("/team/:id", (req, res) -> {
      return null; // TODO
    } , freeMarker);
    Spark.get("/workout/:id", (req, res) -> {
      return null; // TODO
    } , freeMarker);
    Spark.get("/settings", (req, res) -> {
      return null; // TODO
    } , freeMarker);

    Spark.post("/popover/login", (req, res) -> {
      // TODO return the login popover
      return null;
    } , freeMarker);

    Spark.post("/popover/teamregister", (req, res) -> {
      // TODO return the team register popover
      return null;
    } , freeMarker);

    Spark.post("/popover/create_workout", (req, res) -> {
      // TODO return the new workout popover
      return null;
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
