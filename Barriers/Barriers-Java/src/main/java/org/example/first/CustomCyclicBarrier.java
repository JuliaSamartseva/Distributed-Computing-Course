package org.example.first;

public class CustomCyclicBarrier {
  private final int parties;
  private int partiesAwait;
  private Runnable action;
  private boolean isActionable = false;

  public CustomCyclicBarrier(int parties) {
    if (parties <= 0) throw new IllegalArgumentException("Parties number should be more than 0.");
    this.parties = parties;
    this.partiesAwait = parties;
  }

  public CustomCyclicBarrier(int parties, Runnable action) {
    this(parties);
    // Action to be performed after all threads reach the barrier. (combine all results)
    this.action = action;
    isActionable = true;
  }

  public synchronized void await() throws InterruptedException {
    partiesAwait--;
    if (partiesAwait > 0) {
      wait();
    } else {
      partiesAwait = parties;
      notifyAll();
      if (isActionable) action.run();
    }
  }

  public int getParties() {
    return parties;
  }

  public int getPartiesCurrentlyWaiting() {
    return parties - partiesAwait;
  }
}
