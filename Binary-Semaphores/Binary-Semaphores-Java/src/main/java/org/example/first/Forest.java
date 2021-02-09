package org.example.first;

import java.util.ArrayList;

public class Forest {
  private final Bear bear;
  private final ArrayList<Bee> bees;

  public Forest(int beesNumber, int bowlCapacity, int maxBeeSpeed) {
    Bowl bowl = new Bowl(bowlCapacity);
    bear = new Bear(bowl);
    bees = new ArrayList<>(beesNumber);
    for (int i = 0; i < beesNumber; i++) bees.add(new Bee(maxBeeSpeed, bowl));
    Bee.bear = bear;
  }

  public void start() {
    for (Bee bee : bees) {
      Thread beeThread = new Thread(bee);
      beeThread.start();
    }

    Thread bearThread = new Thread(bear);
    bearThread.start();
  }
}
