package org.example.first;

import java.util.ArrayList;
import java.util.List;

public class Manager {
  private final int workersNumber;
  private final Forest forest;
  private final List<Thread> workers = new ArrayList<>();
  private final BlockingQueue<Integer> tasks;
  private final SynchronizedBoolean foundTarget = new SynchronizedBoolean(false);

  public Manager(Forest forest, int workersNumber) {
    this.forest = forest;
    this.workersNumber = workersNumber;
    tasks = new BlockingQueue<>(forest.getForest().length);
    for (int i = 0; i < forest.getForest().length; i++) {
      try {
        tasks.enqueue(i);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void findTarget() {
    // Creating workers to which tasks will be submitted.
    for (int i = 0; i < workersNumber; i++) {
      TaskExecutor executor = new TaskExecutor(forest);
      Thread worker = new Thread(executor);
      executor.registerListener(
          thread -> {
            if (foundTarget.getBoolean() || executor.isFoundTarget()) {
              foundTarget.makeTrue();
              executor.stopExecution();
            } else submitTaskToThread(executor);
          });
      submitTaskToThread(executor);
      workers.add(worker);
    }

    startThreads();
  }

  private void startThreads() {
    for (Thread worker : workers) {
      worker.start();
    }
  }

  private synchronized void submitTaskToThread(TaskExecutor worker) {
    if (tasks.getCurrentSize() != 0) {
      try {
        worker.submitTask(tasks.dequeue());
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
