package org.example.first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkersRow {
  private final int workersNumber;
  private final int threadsNumber;
  private ExecutorService service;
  private final ArrayList<WorkersRunnable> runnables;
  protected List<Direction> workersDirections;
  protected CustomCyclicBarrier barrier;

  public WorkersRow(int workersNumber, int threadsNumber) {
    this.workersNumber = workersNumber;
    this.threadsNumber = threadsNumber;
    runnables = new ArrayList<>(threadsNumber);
    barrier = new CustomCyclicBarrier(threadsNumber, () -> {
      printRowState();
      if (checkRowState()) {
        for (WorkersRunnable runnable : runnables)
          runnable.stopExecution();
        service.shutdown();
      }
    });
    initialiseWorkersDirections();
  }

  public void startWorkerThreads() {
    int threadWorkersNumber = workersNumber / threadsNumber;
    if (threadWorkersNumber < 50) throw new IllegalArgumentException();
    service = Executors.newFixedThreadPool(threadsNumber);
    int left = 0;
    int right = left + threadWorkersNumber - 1;
    for (int i = 0; i < threadsNumber; i++) {
      WorkersRunnable runnable = new WorkersRunnable(left, right, this);
      service.submit(runnable);
      runnables.add(runnable);
      left = right + 1;
      if (i == threadsNumber - 2) right = workersNumber - 1;
      else right = left + threadWorkersNumber - 1;
    }
  }

  protected void printRowState() {
    System.out.println();
    for (int i = 0; i < workersNumber; i++) {
      System.out.print(workersDirections.get(i) + " ");
    }
  }

  protected boolean checkRowState() {
    for (int i = 0; i < workersNumber - 1; i++) {
      if (workersDirections.get(i).equals(Direction.RIGHT)
          && workersDirections.get(i + 1).equals(Direction.LEFT)) return false;
    }
    return true;
  }

  private void initialiseWorkersDirections() {
    Random random = new Random();
    workersDirections = Collections.synchronizedList(new ArrayList<>(workersNumber));
    for (int i = 0; i < workersNumber; i++) {
      if (random.nextInt(2) == 0) workersDirections.add(Direction.LEFT);
      else workersDirections.add(Direction.RIGHT);
    }
  }
}
