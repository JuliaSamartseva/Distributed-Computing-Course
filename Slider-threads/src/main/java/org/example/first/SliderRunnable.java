package org.example.first;

import javax.swing.*;

public class SliderRunnable implements Runnable {
  public static JSlider slider;
  private final Direction direction;

  public SliderRunnable(Direction direction) {
    this.direction = direction;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      synchronized (slider) {
        slider.setValue(slider.getValue() + direction.value);
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
