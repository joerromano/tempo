package edu.brown.cs.cs32.tempo;

import java.io.File;

import datasource.SQLDatasource;
import edu.brown.cs.cs32.tempo.db.Db;
import joptsimple.OptionParser;
import joptsimple.OptionSpec;

public final class Main {

  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;
  private SparkServer server;

  private Main(String[] args) {
    this.args = args;
    OptionParser opt = new OptionParser();
    opt.allowsUnrecognizedOptions();
    OptionSpec<File> fileOption = opt.nonOptions().ofType(File.class);
    if (opt.parse(args).has(fileOption)) {
      File dbPath = opt.parse(args).valueOf(fileOption);
      System.out.printf("Using database at %s", dbPath.getPath());
      Db.setURL(dbPath.getPath());
      server = new SparkServer(new SQLDatasource());
    } else {
      server = new SparkServer();
    }
  }

  private void run() {
    server.run();
  }
}
