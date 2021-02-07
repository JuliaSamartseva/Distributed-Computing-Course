package org.example.second;

import java.util.ArrayList;

public class App {
  public static void main(String[] args) throws InterruptedException {
    int size = 2;
    ArrayList<Property> property = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      property.add(new Property());
    }

    ArrayList<Property> carriedProperty = new ArrayList<>();
    ArrayList<Property> loadedProperty = new ArrayList<>();

    ProcessRunnable ivanovRunnable = new ProcessRunnable(property, carriedProperty, Action.CARRY);
    Thread ivanov = new Thread(ivanovRunnable);

    ProcessRunnable petrovRunnable =
        new ProcessRunnable(carriedProperty, loadedProperty, Action.LOAD);
    Thread petrov = new Thread(petrovRunnable);

    Thread nechyporyk = new Thread(new CountingPriceRunnable(loadedProperty, size));

    ivanov.start();
    petrov.start();
    nechyporyk.start();
    nechyporyk.join();
    ivanovRunnable.stopRunning();
    petrovRunnable.stopRunning();
  }
}
