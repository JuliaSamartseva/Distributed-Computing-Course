package org.example;

import org.example.graphics.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {
  private static final long serialVersionUID = 1L;

  public static int width = 400;
  public static int height = width / 16 * 9;
  public static int scale = 3;
  private final JFrame frame;
  private final Screen screen;
  private final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  private final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
  private Thread thread;
  private volatile boolean running = false;

  public Game() {
    setPreferredSize(new Dimension(width * scale, height * scale));
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
      final double ns = 1000000000.0 / 6.0;
      double delta = 0;
      while (running) {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        while (delta >= 1) {
          update();
          delta--;
        }
        render();
        if (System.currentTimeMillis() - timer > 1000) {
          timer += 1000;
        }
      }
      stop();
    }
  }

  public void update() {
    screen.board.generateNext();
  }

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
    frame.addKeyListener(
        new KeyListener() {
          @Override
          public void keyTyped(KeyEvent e) {}

          @Override
          public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
              case KeyEvent.VK_2:
                screen.regenerateBoard(2);
                break;
              case KeyEvent.VK_3:
                screen.regenerateBoard(3);
                break;
              case KeyEvent.VK_4:
                screen.regenerateBoard(4);
                break;
              default:
                screen.regenerateBoard(1);
                break;
            }
          }

          @Override
          public void keyReleased(KeyEvent e) {}
        });
    frame.setVisible(true);
  }
}
