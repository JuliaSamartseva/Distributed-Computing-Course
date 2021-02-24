package org.example.logic;

import org.example.graphics.Screen;
import org.example.graphics.Sprite;

public class Bullet extends Move {
  public static int FIRE_RATE = 15;
  private final Sprite sprite = Sprite.bullet;

  Bullet(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public void update() {
    int ya = -4;
    move(0, ya);
  }

  public void render(Screen screen) {
    screen.renderMovement(x, y, sprite);
  }
}
