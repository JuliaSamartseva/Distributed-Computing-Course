package org.example.first.lock;

public interface CustomReadersWriterLock {
  CustomLock readLock();

  CustomLock writeLock();
}
