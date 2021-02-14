package org.example.second;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Worker implements Runnable {
  private final int index;
  private final Manager manager;
  private volatile boolean running = true;

  public Worker(int index, Manager manager) {
    this.index = index;
    this.manager = manager;
  }

  @Override
  public void run() {
    Random random = new Random();
    Map<Character, Character> map = new HashMap<>();
    addDefaultOptions(map);

    while (running) {
      int randomIndex = random.nextInt(manager.rows[index].length());
      Character previousCharacter = manager.rows[index].charAt(randomIndex);
      StringBuilder builder = new StringBuilder(manager.rows[index]);
      builder.setCharAt(randomIndex, map.get(previousCharacter));
      manager.rows[index] = builder.toString();

      try {
        manager.barrier.await();
      } catch (InterruptedException | BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  private void addDefaultOptions(Map<Character, Character> map) {
    map.put('A', 'C');
    map.put('C', 'A');
    map.put('B', 'D');
    map.put('D', 'B');
  }

  public void stopExecution() {
    running = false;
  }
}
