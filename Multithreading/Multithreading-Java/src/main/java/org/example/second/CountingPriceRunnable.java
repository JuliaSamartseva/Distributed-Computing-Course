package org.example.second;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public class CountingPriceRunnable implements Runnable {
  private volatile boolean isRunning = true;
  private final BlockingQueue<Property> property;
  private final int targetItemsNumber;
  private int currentItemsNumber = 0;
  private int finalSum = 0;
  private static Logger log;

  public CountingPriceRunnable(BlockingQueue<Property> property, int targetItemsNumber) {
    this.property = property;
    this.targetItemsNumber = targetItemsNumber;
    log = Logger.getLogger(CountingPriceRunnable.class.getName());
  }

  @Override
  public void run() {
    while (isRunning) {
      Property currentItem = null;
      try {
        currentItem = property.take();
        log.info("Summing up the price " + currentItem.price + " of item " + currentItem.uniqueID);
        finalSum += currentItem.price;
        currentItemsNumber++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (currentItemsNumber == targetItemsNumber) {
        System.out.println("Final sum = " + finalSum);
        stopRunning();
      }
    }
  }

  public void stopRunning() {
    isRunning = false;
  }
}
