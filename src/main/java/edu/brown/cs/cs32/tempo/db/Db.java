package edu.brown.cs.cs32.tempo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Abstract representation of a database
 * @author lucicooke
 *
 */
public abstract class Db {
  private static Connection conn;
  private static String url;

  /**
   * Sets the path to the database.
   * @param path
   *          - the path to the database.
   */
  public static void setURL(String path) {
    conn = null;
    url = path;
  }
  
  private static void setConnection(Connection conn2) {
  	conn = conn2;
  }

  /**
   * Gets a Connection to the database.
   * @return a Connection
   */
  public static Connection getConnection() {
    // Return null if no path given
    if (url == null) {
      return null;
    }

    if (conn != null) {
      // return saved connection;
    	System.out.println("connection already exists");
      return conn;
    } else {
    	System.out.println("making new connection");
    	try {
        Class.forName("org.sqlite.JDBC");
        String urlToDB = "jdbc:sqlite:" + url;
        conn = DriverManager.getConnection(urlToDB);
        // conn.setAutoCommit(false);
        Statement stat = conn.createStatement();
        stat.executeUpdate("PRAGMA foreign_keys = ON;");
      } catch (SQLException e) {
        System.out.println("ERROR: Invalid database address: " + url);
        System.exit(1);
      } catch (ClassNotFoundException e) {
        System.out.println("ERROR: ClassNotFoundException");
        System.exit(1);
      }
      setConnection(conn);
      return conn;
    }

    // Create and return new Connection
    
  }

  public static void close() {
    try {
      conn.close();
    } catch (SQLException e) {
      System.out.println("ERROR: SQLException triggered during Db.close");
      System.exit(1);
    }
  }
}