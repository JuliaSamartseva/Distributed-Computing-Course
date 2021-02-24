package org.example.logic;

import org.example.graphics.Screen;
import org.example.graphics.Sprite;
import org.example.input.Keyboard;

import java.util.EventListener;

public class Shooter extends Move implements EventListener {
  private final Sprite sprite;
  private final Keyboard input;

  public Shooter(Keyboard input) {
    this.input = input;
    sprite = Sprite.shooter;
  }

  public void update() {
    int xa = 0, ya = 0;

    if (input.left) xa -= 5;
    if (input.right) xa += 5;

    move(xa, 0);
  }

  public void render(Screen screen) {
    screen.renderPlayer(x, y, sprite);
  }
}
