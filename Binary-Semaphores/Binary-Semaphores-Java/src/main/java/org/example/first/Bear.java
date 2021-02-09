package org.example.first;

public class Bear implements Runnable {
  private final Bowl bowl;
  private volatile boolean sleeping = true;

  public Bear(Bowl bowl) {
    this.bowl = bowl;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      if (!sleeping) {
        bowl.eatHoney();
        sleeping = true;
      }
    }
  }

  public void wakeUp() {
    sleeping = false;
  }
}
