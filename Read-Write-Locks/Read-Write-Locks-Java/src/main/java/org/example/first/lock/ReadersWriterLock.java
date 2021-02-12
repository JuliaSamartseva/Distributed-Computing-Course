package org.example.first.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReadersWriterLock implements CustomReadersWriterLock {
  private volatile boolean writer = false;
  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();
  private final CustomLock readLock = new ReadLock();
  private final CustomLock writeLock = new WriteLock();

  private int readAcquires, readReleases = 0;

  @Override
  public CustomLock readLock() {
    return readLock;
  }

  @Override
  public CustomLock writeLock() {
    return writeLock;
  }

  class ReadLock implements CustomLock {
    @Override
    public void lock() {
      lock.lock();
      try {
        readAcquires++;
        while (writer) {
          condition.await();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }

    @Override
    public void unlock() {
      lock.lock();
      try {
        readReleases++;
        if (readAcquires == readReleases) condition.signalAll();
      } finally {
        lock.unlock();
      }
    }
  }

  public class WriteLock implements CustomLock {
    @Override
    public void lock() {
      lock.lock();
      try {
        while (readAcquires != readReleases) {
          condition.await();
        }
        writer = true;
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }

    @Override
    public void unlock() {
      lock.lock();
      try {
        writer = false;
        condition.signalAll();
      } finally {
        lock.unlock();
      }
    }
  }
}
