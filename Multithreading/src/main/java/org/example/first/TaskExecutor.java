package org.example.first;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TaskExecutor implements Runnable {
  private volatile boolean running = true;
  private volatile boolean foundTarget = false;
  private final List<Listener> listeners = new ArrayList<>();
  private Integer currentTask = null;
  private final Forest forest;
  private static Logger log;

  // Task is a row number inside the forest
  public TaskExecutor(Forest forest) {
    log = Logger.getLogger(TaskExecutor.class.getName());
    this.forest = forest;
  }

  public void submitTask(int task) {
    currentTask = task;
  }

  @Override
  public void run() {
    while (running) {
      if (currentTask != null) {
        log.info("Starting running task by thread " + Thread.currentThread().getName());
        for (int i = 0; i < forest.getForest().length; i++)
          if (forest.getForest()[currentTask][i]) {
            foundTarget = true;
            log.info("Task has been found by " + Thread.currentThread().getName());
          }
        log.info("Task has been completed by " + Thread.currentThread().getName());
        currentTask = null;
        notifyListeners();
      }
    }
  }

  public void stopExecution() {
    log.info("Thread has stopped its execution: " + Thread.currentThread().getName());
    running = false;
  }

  public boolean isFoundTarget() {
    return foundTarget;
  }

  // Listeners functionality

  private void notifyListeners() {
    for (Listener listener : listeners) {
      listener.workDone(this);
    }
  }

  public void registerListener(Listener listener) {
    listeners.add(listener);
  }
}
