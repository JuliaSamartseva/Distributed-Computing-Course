package org.example.second;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicInteger;

public class SliderRunnable implements Runnable {
  public static JSlider slider;
  private final JLabel label;
  private final Direction direction;
  private static final AtomicInteger semaphore = new AtomicInteger(0);
  private volatile boolean running = true;

  public SliderRunnable(Direction direction, JLabel label) {
    this.direction = direction;
    this.label = label;
  }

  public void releaseSemaphore() {
    semaphore.decrementAndGet();
  }

  public void stopThread() {
    running = false;
  }

  @Override
  public void run() {
    if (!semaphore.compareAndSet(0, 1)) {
      stopThread();
      label.setVisible(true);
    }

    while (running) {
      int newValue = slider.getValue() + direction.value;
      if (newValue <= 90 && newValue >= 10) slider.setValue(newValue);

      try {
        Thread.sleep(50);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
