package org.example.third.graph;

import java.io.IOException;

public class App {
  public static void main(String[] args) throws IOException {
    Graph graph = new Graph();
    Thread thread1 = new Thread(() -> {
      graph.addVertex("Kyiv");
      graph.addVertex("Lviv");
      graph.addVertex("Lutsk");
      graph.addVertex("Odessa");
      graph.addVertex("Bratislava");
      graph.addVertex("Paris");
      graph.addVertex("Barcelona");
      graph.addEdge("Kyiv", "Lviv", 10);
      graph.addEdge("Lviv", "Lutsk", 15);
      graph.addEdge("Lutsk", "Odessa", 20);
      graph.addEdge("Bratislava", "Lviv", 200);
      graph.addEdge("Paris", "Lviv", 100);
      graph.addEdge("Paris", "Barcelona", 20);
      graph.addEdge("Bratislava", "Paris", 30);
    });

    Thread thread2 = new Thread(() -> {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      graph.findPath("Kyiv", "Barcelona");
    });

    Thread thread3 = new Thread(() -> {
      try {
        Thread.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      graph.findPath("Kyiv", "Barcelona");
      graph.addVertex("Berlin");
    });

    Thread thread4 = new Thread(() -> {
      try {
        Thread.sleep(150);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      graph.changeEdgePrice("Paris", "Barcelona", 1000);
      graph.removeVertex("Bratislava");
    });

    thread1.start();
    thread2.start();
    thread3.start();
    thread4.start();
  }
}
