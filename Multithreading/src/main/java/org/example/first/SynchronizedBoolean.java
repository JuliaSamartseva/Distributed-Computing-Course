package org.example.first;

public class SynchronizedBoolean {
  private final Object lock = new Object();
  private boolean bool;

  public SynchronizedBoolean(boolean bool) {
    this.bool = bool;
  }

  public boolean getBoolean() {
    synchronized (lock) {
      return bool;
    }
  }

  public void makeFalse() {
    synchronized (lock) {
      bool = false;
    }
  }

  public void makeTrue() {
    synchronized (lock) {
      bool = true;
    }
  }
}
