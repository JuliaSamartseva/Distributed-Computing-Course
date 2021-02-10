package org.example.second;

public class Barber extends Thread {
  private final BarberShop barberShop;

  public Barber(BarberShop barberShop) {
    this.barberShop = barberShop;
  }

  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      try {
        barberShop.customerReady.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      barberShop.getCustomerFromQueue();
      barberShop.barberReady.release();
      cutHair();
    }
  }

  public void cutHair() {
    System.out.println("Barber is cutting hair");
    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Barber has completed their cut.");
  }
}
