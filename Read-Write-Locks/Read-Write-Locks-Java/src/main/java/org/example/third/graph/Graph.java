package org.example.third.graph;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Graph {
  private int vertexNumber = 0;
  private final Map<String, List<Edge>> adjacencyList;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();

  public Graph() {
    this.adjacencyList = new HashMap<>();
  }

  public void addVertex(String label) {
    System.out.println("Adding vertex with label " + label);
    adjacencyList.putIfAbsent(label, new ArrayList<>());
    vertexNumber++;
  }

  public void addEdge(String name1, String name2, int price) {
    System.out.println("Adding edge between " + name1 + " " + name2);
    lock.writeLock().lock();
    adjacencyList.get(name1).add(new Edge(name2, price));
    adjacencyList.get(name2).add(new Edge(name1, price));
    lock.writeLock().unlock();
  }

  public void changeEdgePrice(String name1, String name2, int newPrice) {
    System.out.println("Changing edge price between " + name1 + " " + name2 + " to " + newPrice);
    lock.writeLock().lock();

    List<Edge> edgeList1 = adjacencyList.get(name1);
    List<Edge> edgeList2 = adjacencyList.get(name2);

    for (Edge edge : edgeList1) {
      if (edge.cityName.equals(name2)) edge.price = newPrice;
    }
    for (Edge edge : edgeList2) {
      if (edge.cityName.equals(name1)) edge.price = newPrice;
    }
    lock.writeLock().unlock();
  }

  public void removeEdge(String name1, String name2) {
    System.out.println("Removing edge between " + name1 + " " + name2);
    lock.writeLock().lock();
    List<Edge> edgeList1 = adjacencyList.get(name1);
    List<Edge> edgeList2 = adjacencyList.get(name2);
    edgeList1.removeIf(edge -> edge.cityName.equals(name2));
    edgeList2.removeIf(edge -> edge.cityName.equals(name1));
    lock.writeLock().unlock();
  }

  public void removeVertex(String name) {
    System.out.println("Removing the vertex " + name);
    lock.writeLock().lock();
    vertexNumber--;
    List<Edge> edgeList = adjacencyList.get(name);
    for (Edge edge : edgeList) {
      adjacencyList.get(edge.cityName).removeIf(temp -> temp.cityName.equals(name));
    }
    adjacencyList.remove(name);
    lock.writeLock().unlock();
  }

  public void findPath(String s, String d) {
    System.out.println("Finding path between " + s + " " + d);
    lock.readLock().lock();
    Set<String> isVisited = new HashSet<>();
    ArrayList<String> pathList = new ArrayList<>();
    pathList.add(s);
    findPathRecur(s, d, isVisited, pathList);
    lock.readLock().unlock();
  }

  public String printGraph() {
    lock.readLock().lock();
    StringBuilder sb = new StringBuilder();
    for (String v : adjacencyList.keySet()) {
      sb.append(v);
      sb.append(adjacencyList.get(v));
    }
    lock.readLock().unlock();
    return sb.toString();
  }

  private void findPathRecur(
      String u, String d, Set<String> isVisited, List<String> localPathList) {
    if (u.equals(d)) {
      System.out.print(localPathList);
      System.out.println(" Price = " + calculatePathPrice(localPathList));
      return;
    }
    isVisited.add(u);
    for (Edge i : adjacencyList.get(u)) {
      String cityName = i.cityName;
      if (!isVisited.contains(cityName)) {
        localPathList.add(cityName);
        findPathRecur(cityName, d, isVisited, localPathList);
        localPathList.remove(cityName);
      }
    }
    isVisited.remove(u);
  }

  private int calculatePathPrice(List<String> pathList) {
    int fullPrice = 0;
    String previousCity = pathList.get(0);

    for (String city : pathList) {
      List<Edge> edgeList = adjacencyList.get(previousCity);
      if (!previousCity.equals(city))
        for (Edge edge : edgeList)
          if (edge.cityName.equals(city)) {
            fullPrice += edge.price;
            break;
          }
      previousCity = city;
    }
    return fullPrice;
  }
}
