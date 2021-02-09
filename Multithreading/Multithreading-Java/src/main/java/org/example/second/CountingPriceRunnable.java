package org.example.second;

import java.util.ArrayList;
import java.util.logging.Logger;

public class CountingPriceRunnable implements Runnable {
  private volatile boolean isRunning = true;
  private final ArrayList<Property> property;
  private final int targetItemsNumber;
  private int currentItemsNumber = 0;
  private int finalSum = 0;
  private static Logger log;

  public CountingPriceRunnable(ArrayList<Property> property, int targetItemsNumber) {
    this.property = property;
    this.targetItemsNumber = targetItemsNumber;
    log = Logger.getLogger(CountingPriceRunnable.class.getName());
  }

  @Override
  public void run() {
    while (isRunning) {
      if (!property.isEmpty()) {
        Property currentItem = property.remove(0);
        log.info("Summing up the price " + currentItem.price + " of item " + currentItem.uniqueID);
        finalSum += currentItem.price;
        currentItemsNumber++;
        if (currentItemsNumber == targetItemsNumber) {
          System.out.println("Final sum = " + finalSum);
          stopRunning();
        }
      }
    }
  }

  public void stopRunning() {
    isRunning = false;
  }
}
