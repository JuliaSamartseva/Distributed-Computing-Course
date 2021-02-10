package org.example.second;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class BarberShop extends Thread {
  private final AtomicInteger freeSeats;
  private final int customersPoolNumber;
  public Semaphore customerReady = new Semaphore(0);
  public Semaphore barberReady = new Semaphore(0);
  public Semaphore allEmpty = new Semaphore(1);

  public BarberShop(int freeSeats, int customersPoolNumber) {
    this.freeSeats = new AtomicInteger(freeSeats);
    this.customersPoolNumber = customersPoolNumber;
  }

  public boolean hasFreeSpace() {
    int number = freeSeats.get();
    if (number > 0) {
      freeSeats.decrementAndGet();
      return true;
    }
    return false;
  }

  public void getCustomerFromQueue() {
    freeSeats.incrementAndGet();
  }

  @Override
  public void run() {
    Barber barber = new Barber(this);
    barber.start();

    Random random = new Random();
    for (int i=1; i < customersPoolNumber; i++) {
      Customer customer = new Customer(this);
      customer.start();
      try {
        Thread.sleep(random.nextInt(200));
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
