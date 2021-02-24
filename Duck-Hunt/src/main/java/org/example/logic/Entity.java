package org.example.logic;

public abstract class Entity {
  protected int gameWidth;
  protected int gameHeight;
  protected int x, y;
  private boolean removed = false;

  public boolean isRemoved() {
    return removed;
  }

  public void remove() {
    removed = true;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
