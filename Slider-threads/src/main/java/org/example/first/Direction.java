package org.example.first;

public enum Direction {
  LEFT(-1),
  RIGHT(1);

  public final int value;

  private Direction(int value) {
    this.value = value;
  }
}
