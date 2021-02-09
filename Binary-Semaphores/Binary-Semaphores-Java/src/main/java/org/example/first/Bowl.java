package org.example.first;

public class Bowl {
  private final int capacity;
  private int size = 0;

  public Bowl(int capacity) {
    this.capacity = capacity;
  }

  public synchronized void addHoney() {
    if (isFull()) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    size++;
  }

  public synchronized void eatHoney() {
    System.out.println("The bear is eating honey.");
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    size = 0;
    notifyAll();
  }

  public synchronized boolean isFull() {
    return size == capacity;
  }
}
