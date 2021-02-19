package org.example;

import org.example.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
  private static final long serialVersionUID = 1L;

  public static int width = 400;
  public static int height = width / 16 * 9;
  public static int scale = 3;

  private Thread thread;
  private final JFrame frame;
  private volatile boolean running = false;
  private final Screen screen;

  private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

  public Game() {
    Dimension size = new Dimension(width * scale, height * scale);
    setPreferredSize(size);
    frame = new JFrame();
    setFrameProperties();
    screen = new Screen(width, height);
  }

  public synchronized void start() {
    running = true;
    thread = new Thread(this, "Display");
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
    while (running) {
      long lastTime = System.nanoTime();
      long timer = System.currentTimeMillis();
      final double ns = 1000000000.0 / 60.0;
      double delta = 0;
      int frames = 0;
      int updates = 0;
      while (running) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;

        while (delta >= 1) {
          update();
          updates++;
          delta--;
        }

        render();
        frames++;

        if (System.currentTimeMillis() - timer > 1000) {
          timer += 1000;

          System.out.println(updates + "updates, " + frames + "fps");
          frame.setTitle(
              "Display" + " running at the blinding speed of " + "updates " + frames + " and fps");

          frames = 0;
          updates = 0;
        }
      }
      stop();
    }
  }

  public void update() {}

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    screen.clear();
    screen.render();
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = screen.pixels[i];
    }
    Graphics g = bs.getDrawGraphics();

    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    g.dispose();
    bs.show();
  }

  private void setFrameProperties() {
    frame.setResizable(false);
    frame.setTitle("Game of Life");
    frame.add(this);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
