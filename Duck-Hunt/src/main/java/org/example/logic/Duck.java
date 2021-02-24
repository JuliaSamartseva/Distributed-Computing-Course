package org.example.logic;

import org.example.graphics.Screen;
import org.example.graphics.Sprite;

public class Duck extends Move {
  private final int speed;
  private Sprite[] sprite;
  private Direction direction;
  private int counter = 0;
  private int currentDuck = 0;

  public Duck(int x, int y, Direction direction, int speed, int width, int height) {
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.speed = speed;
    this.gameWidth = width;
    this.gameHeight = height;

    switch (direction) {
      case LEFT:
        initialiseLeft();
        break;
      case RIGHT:
        initialiseRight();
        break;
    }
  }

  public void update() {
    int xa = 0;

    switch (direction) {
      case LEFT:
        xa -= speed;
        break;
      case RIGHT:
        xa += speed;
        break;
    }

    if (x + xa + 10 >= gameWidth) initialiseLeft();
    else if (x + xa + 10 <= 0) initialiseRight();

    move(xa, 0);
  }

  private void initialiseLeft() {
    direction = Direction.LEFT;
    sprite = Sprite.getLeftDucks();
  }

  private void initialiseRight() {
    direction = Direction.RIGHT;
    sprite = Sprite.getRightDucks();
  }

  public void render(Screen screen) {
    counter++;
    if (counter > 20) {
      currentDuck = currentDuck == 0 ? 1 : 0;
      counter = 0;
    }

    screen.renderMovement(x, y, sprite[currentDuck]);
  }

  public int getWidth() {
    return sprite[currentDuck].getWidth();
  }

  public int getHeight() {
    return sprite[currentDuck].getHeight();
  }
}
