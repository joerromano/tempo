package edu.brown.cs.cs32.tempo;

public final class Main {

  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;
  private SparkServer server;

  private Main(String[] args) {
    this.args = args;
    server = new SparkServer();
  }

  private void run() {
    server.run();
  }
}
