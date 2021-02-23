package org.example.logic;

import org.example.graphics.Renderable;

public abstract class Entity implements Renderable {
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
