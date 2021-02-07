package org.example.first;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Manager {
  private final int workersNumber;
  private final Forest forest;
  private final List<Thread> workers = new ArrayList<>();
  private final Queue<Integer> tasks = new LinkedList<>();
  private final SynchronizedBoolean foundTarget = new SynchronizedBoolean(false);

  public Manager(Forest forest, int workersNumber) {
    this.forest = forest;
    this.workersNumber = workersNumber;
    for (int i = 0; i < forest.getForest().length; i++) {
      tasks.add(i);
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
    if (!tasks.isEmpty()) {
      worker.submitTask(tasks.poll());
    }
  }
}
