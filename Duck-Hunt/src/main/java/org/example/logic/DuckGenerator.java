package org.example.logic;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DuckGenerator implements Runnable {
  private final int width;
  private final int height;
  private final int shooterHeight;
  private final List<Duck> ducks;

  public DuckGenerator(int width, int height, int shooterHeight, List<Duck> ducks) {
    this.width = width;
    this.height = height;
    this.shooterHeight = shooterHeight;
    this.ducks = ducks;
  }

  @Override
  public void run() {
    Direction[] directions = new Direction[2];
    directions[0] = Direction.LEFT;
    directions[1] = Direction.RIGHT;
    Random random = new Random();

    while (!Thread.currentThread().isInterrupted()) {
      if (ducks.size() < 5) {
        try {
          TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Direction randomDirection = directions[random.nextInt(2)];
        switch (randomDirection) {
          case RIGHT:
            ducks.add(
                new Duck(
                    0,
                    random.nextInt(height - 5 * shooterHeight),
                    randomDirection,
                    random.nextInt(3) + 1,
                    width,
                    height));
            break;
          case LEFT:
            ducks.add(
                new Duck(
                    width - 10,
                    random.nextInt(height - 5 * shooterHeight),
                    randomDirection,
                    random.nextInt(3) + 1,
                    width,
                    height));
            break;
        }
      }
    }
  }
}
