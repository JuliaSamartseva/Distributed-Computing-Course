package org.example.second;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class App {
    public static void main(String[] args) throws InterruptedException {
        int size = 10;
        BlockingQueue<Property> property = new LinkedBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            property.add(new Property());
        }

        BlockingQueue<Property> carriedProperty = new LinkedBlockingQueue<>(size);
        BlockingQueue<Property> loadedProperty = new LinkedBlockingQueue<>(size);

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
