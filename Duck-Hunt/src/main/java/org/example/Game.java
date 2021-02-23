package org.example;

import org.example.graphics.Screen;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

  public static int width = 400;
  public static int height = width / 16 * 9;
  public static int scale = 3;

  private final JFrame frame;
  private final Screen screen;
  private BufferedImage background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  private final int[] pixels = ((DataBufferInt) background.getRaster().getDataBuffer()).getData();
  private Thread thread;
  private volatile boolean running = false;
  private List<Layer> layerStack = new ArrayList<Layer>();

  public Game() {
    Dimension size = new Dimension(width * scale, height * scale);
    setPreferredSize(size);
    frame = new JFrame();
    setFrameProperties();
    screen = new Screen(width, height);
    try {
      background = ImageIO.read(new File("src/main/resources/background.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
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

  public void update() {}

  public void render() {
    BufferStrategy bs = getBufferStrategy();
    if (bs == null) {
      createBufferStrategy(3);
      return;
    }
    screen.clear();


    Graphics g = bs.getDrawGraphics();
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
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
