package org.example.first;

public class WorkersRunnable implements Runnable {
  private final int left;
  private final int right;
  private final WorkersRow row;
  private volatile boolean running = true;

  public WorkersRunnable(int left, int right, WorkersRow row) {
    this.left = left;
    this.right = right;
    this.row = row;
  }

  @Override
  public void run() {
    while (running) {
      if (left > 0) checkState(left - 1, left);
      for (int i = left; i < right; i++) {
        checkState(i, i + 1);
      }
      if (right < row.workersDirections.size() - 1)
        checkState(right, right + 1);
      try {
        row.barrier.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void stopExecution() {
    running = false;
  }

  private void checkState(int firstCoordinate, int secondCoordinate) {
    if (row.workersDirections.get(firstCoordinate).equals(Direction.RIGHT)
        && row.workersDirections.get(secondCoordinate).equals(Direction.LEFT)) {
      row.workersDirections.set(firstCoordinate, Direction.LEFT);
      row.workersDirections.set(secondCoordinate, Direction.RIGHT);
    }
  }
}
