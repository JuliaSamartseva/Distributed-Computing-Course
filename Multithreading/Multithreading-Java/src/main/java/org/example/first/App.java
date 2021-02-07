package org.example.first;

public class App {
  public static void main(String[] args) {
    Forest forest = new Forest(100);
    Manager manager = new Manager(forest, 3);
    manager.findTarget();
  }
}
