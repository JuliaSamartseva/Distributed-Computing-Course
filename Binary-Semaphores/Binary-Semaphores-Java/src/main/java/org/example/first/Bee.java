package org.example.first;

import java.util.Random;

public class Bee implements Runnable {
  private final int speed;
  private final Bowl bowl;
  public static Bear bear;

  public Bee(int maximumSpeed, Bowl bowl) {
    this.bowl = bowl;
    Random random = new Random();
    speed = random.nextInt(maximumSpeed);
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      System.out.println("Running for the honey  ---  " + Thread.currentThread().getName());
      try {
        Thread.sleep(speed);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Adding the honey  ---  " + Thread.currentThread().getName());
      bowl.addHoney();
      if (bowl.isFull()) {
        bear.wakeUp();
      }
    }
  }
}
