package org.example.logic;

import org.example.graphics.Screen;
import org.example.graphics.Sprite;

import java.util.List;

public class Bullet extends Move {
  public static int FIRE_RATE = 15;
  private final Sprite sprite = Sprite.bullet;
  private final List<Duck> ducks;

  Bullet(int x, int y, List<Duck> ducks) {
    this.x = x;
    this.y = y;
    this.ducks = ducks;
  }

  public void update() {
    if (!checkCollision()) {
      int ya = -4;
      move(0, ya);
    }
  }

  public void render(Screen screen) {
    screen.renderMovement(x, y, sprite);
  }

  private boolean checkCollision() {
    for (Duck duck : ducks) {
      if ((x >= duck.x - 5 && x <= (duck.x + duck.getWidth() + 5))
          && (y >= duck.y - 5 && y <= (duck.y + duck.getHeight() + 5))) {
        duck.remove();
        remove();
        return true;
      }
    }
    return false;
  }
}
