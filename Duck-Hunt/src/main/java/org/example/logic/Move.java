package org.example.logic;

public abstract class Move extends Entity {

  public void move(int xa, int ya) {
    if (xa != 0 && ya != 0) {
      move(xa, 0);
      move(0, ya);
      return;
    }

    if (x + xa > 0 && x + xa + 10 < gameWidth) x += xa;
    y += ya;
  }
}
