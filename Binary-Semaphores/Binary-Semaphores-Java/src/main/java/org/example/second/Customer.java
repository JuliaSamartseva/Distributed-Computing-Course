package org.example.second;

public class Customer extends Thread {
  private final BarberShop barberShop;
  private volatile boolean cut = false;

  public Customer(BarberShop barberShop) {
    this.barberShop = barberShop;
  }

  @Override
  public void run() {
    while (!cut) {
      if (barberShop.hasFreeSpace()) enter();
      else
        leaveNoSpace();

      try {
        barberShop.customerReady.release();
        barberShop.barberReady.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      getHairCut();
      leave();
    }
  }

  public void enter() {
    System.out.println("Customer entered the barbershop " + Thread.currentThread().getName());
  }

  public void getHairCut() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    cut = true;
    System.out.println("Customer is getting the hair cut " + Thread.currentThread().getName());
  }

  public void leaveNoSpace() {
    System.out.println(
        "Customer has left the shop because there was no space "
            + Thread.currentThread().getName());
  }

  public void leave() {
    System.out.println("Customer has left the shop " + Thread.currentThread().getName());
  }
}
