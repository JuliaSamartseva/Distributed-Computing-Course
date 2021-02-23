package org.example.logic;

import java.awt.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cell {
  private boolean alive;
  private int color;
  private ReentrantReadWriteLock lock;

  public Cell(boolean alive, int color) {
    this.alive = alive;
    this.color = color;
    lock = new ReentrantReadWriteLock(true);
  }

  public Cell(boolean alive) {
    this.alive = alive;
    this.color = Color.WHITE.getRGB();
    lock = new ReentrantReadWriteLock(true);
  }

  public synchronized boolean isAlive() {
    return alive;
  }

  public synchronized void setAlive(boolean alive) {
    this.alive = alive;
  }

  public synchronized int getColor() {
    return color;
  }

  public synchronized void setColor(int color) {
    this.color = color;
  }
}
