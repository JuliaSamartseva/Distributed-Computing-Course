package org.example.first;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<Type> {
  private final Queue<Type> queue = new LinkedList<>();
  private final int size;

  public BlockingQueue(int size) {
    this.size = size;
  }

  public synchronized void enqueue(Type task) throws InterruptedException {
    while (queue.size() == size) {
      wait();
    }
    if (queue.size() == 0) {
      notifyAll();
    }
    queue.offer(task);
  }

  public synchronized Type dequeue() throws InterruptedException {
    while (queue.size() == 0) {
      wait();
    }
    if (queue.size() == size) {
      notifyAll();
    }
    return queue.poll();
  }

  public int getCurrentSize() {
    return queue.size();
  }
}
