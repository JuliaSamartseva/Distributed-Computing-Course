package org.example.logic;

import org.example.graphics.Screen;
import org.example.graphics.Sprite;
import org.example.input.Keyboard;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Shooter extends Move implements EventListener {
  private final Sprite sprite = Sprite.shooter;

  private final Keyboard input;
  private List<Bullet> bullets = new ArrayList<>();
  private int fireRate = Bullet.FIRE_RATE;

  public Shooter(Keyboard input) {
    this.input = input;
  }

  public Shooter(int x, int y, Keyboard input, Screen screen) {
    this.gameHeight = screen.getHeight();
    this.gameWidth = screen.getWidth();
    this.x = x;
    this.y = y;
    this.input = input;
  }

  public void update() {
    if (fireRate > 0) fireRate--;

    int xa = 0;

    if (input.left) xa -= 3;
    if (input.right) xa += 3;

    move(xa, 0);

    for (Bullet bullet : bullets) {
      bullet.update();
    }

    if (input.shooting) {
      shoot();
    }
  }

  private void shoot() {
    if (fireRate <= 0) {
      bullets.add(new Bullet(x + sprite.getWidth() / 2, y - sprite.getHeight()));
      fireRate = Bullet.FIRE_RATE;
    }
  }

  public int getHeight() {
    return sprite.getHeight();
  }

  public void render(Screen screen) {
    screen.renderMovement(x, y, sprite);
    for (Bullet bullet : bullets) bullet.render(screen);
  }
}
