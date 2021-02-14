package org.example.first;

public class App {
  public static void main(String[] args) {
    WorkersRow row = new WorkersRow(200, 3);
    row.startWorkerThreads();
  }
}
