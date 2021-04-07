package org.example.second;

public class App {
  public static void main(String[] args) {
    Manager manager = new Manager(10);
    manager.startWorkerThreads();
  }
}
