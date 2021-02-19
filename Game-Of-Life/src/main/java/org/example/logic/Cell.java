package org.example.logic;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cell {
  public boolean alive;

  public Cell(boolean alive) {
    this.alive = alive;
  }

  public synchronized boolean isAlive() {
    return alive;
  }

  public synchronized void setAlive(boolean alive) {
    this.alive = alive;
  }
}
