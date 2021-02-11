package org.example.second;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class ProcessRunnable implements Runnable {
  private volatile boolean isRunning = true;
  private final BlockingQueue<Property> property;
  private final BlockingQueue<Property> processedProperty;
  private final Action action;
  private static Logger log;

  public ProcessRunnable(
          BlockingQueue<Property> property, BlockingQueue<Property> processedProperty, Action action) {
    this.property = property;
    this.processedProperty = processedProperty;
    this.action = action;
    log = Logger.getLogger(ProcessRunnable.class.getName());
  }

  @Override
  public void run() {
    while (isRunning) {
      if (!property.isEmpty()) {
        Property currentItem = property.poll();

        log.info(
                "Start "
                        + action.label
                        + " Property id: "
                        + currentItem.uniqueID
                        + " Thread id: "
                        + Thread.currentThread().getName());
        try {
          Thread.sleep(currentItem.processTime);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        log.info(
                "End "
                        + action.label
                        + " Property id: "
                        + currentItem.uniqueID
                        + " Thread id: "
                        + Thread.currentThread().getName());
        processedProperty.add(currentItem);
      }
    }
  }

  public void stopRunning() {
    isRunning = false;
  }
}
