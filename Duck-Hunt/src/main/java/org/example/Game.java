package org.example;

import org.example.graphics.Screen;
import org.example.graphics.Sprite;
import org.example.input.Keyboard;
import org.example.logic.Duck;
import org.example.logic.DuckGenerator;
import org.example.logic.Shooter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Game extends Canvas implements Runnable {

  public static int width = 400;
  public static int height = width / 16 * 9;
  public static int scale = 3;

  private final JFrame frame;
  private final Screen screen;
  private final Keyboard key;
  private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
  private Thread thread;
  private volatile boolean running = false;

  private final Shooter shooter;
  private final List<Duck> ducks;

  public Game() {
    Dimension size = new Dimension(width * scale, height * scale);
    setPreferredSize(size);
    frame = new JFrame();
    key = new Keyboard();
    setFrameProperties();
    screen = new Screen(width, height);
    ducks = Collections.synchronizedList(new ArrayList<Duck>());

    shooter =
        new Shooter(
            width / 2 - (Sprite.shooter.getWidth() / 2),
            height - Sprite.shooter.getHeight(),
            key,
            screen,
            ducks);

    generateDucks();
  }

  public void generateDucks() {
    Thread generatingThread =
        new Thread(new DuckGenerator(width, height, shooter.getHeight(), ducks));
    generatingThread.start();
  }

  public synchronized void start() {
    running = true;
    thread = new Thread(this, "Duck game");
    thread.start();
  }

  public synchronized void stop() {
    running = false;
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double delta = 0;
    double ns = 1000000000.0 / 60.0;
    long timer = System.currentTimeMillis();
    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      if (delta >= 1) {
        update();
        delta--;
      }
      render();
      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
      }
    }
  }

  public void update() {
    key.update();
    shooter.update();
    for (Duck duck : ducks) {
      duck.update();
    }
  }

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    screen.clear();
    screen.renderBackground();
    shooter.render(screen);

    Iterator<Duck> iterator = ducks.iterator();
    while (iterator.hasNext()) {
      Duck duck = iterator.next();
      duck.render(screen);
      if (duck.isRemoved()) {
        iterator.remove();
      }
    }

    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = screen.getPixels()[i];
    }

    Graphics g = bs.getDrawGraphics();
    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    g.dispose();
    bs.show();
  }

  private void setFrameProperties() {
    frame.setResizable(false);
    frame.setTitle("Game of Life");
    frame.addKeyListener(key);
    frame.add(this);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
