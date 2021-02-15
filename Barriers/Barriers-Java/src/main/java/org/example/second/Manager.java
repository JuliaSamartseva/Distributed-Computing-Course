package org.example.second;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Manager {
  private final int wordLength;
  private final int rowsNumber = 4;
  private final ArrayList<Worker> runnables = new ArrayList<>(rowsNumber);
  private final ExecutorService service = Executors.newFixedThreadPool(rowsNumber);
  protected String[] rows;
  protected CyclicBarrier barrier;

  public Manager(int wordLength) {
    this.wordLength = wordLength;
    barrier =
        new CyclicBarrier(
            rowsNumber,
            () -> {
              if (checkCount()) {
                for (Worker worker : runnables) worker.stopExecution();
                service.shutdown();
              }
            });
    rows = new String[4];
    generateInitialRows();
  }

  public void startWorkerThreads() {
    for (int i = 0; i < rowsNumber; i++) {
      Worker worker = new Worker(i, this);
      runnables.add(worker);
      service.submit(worker);
    }
  }

  private boolean checkCount() {
    int[] quantities = new int[rowsNumber];
    for (int i = 0; i < quantities.length; i++) {
      quantities[i] = 0;
      for (int j = 0; j < rows[i].length(); j++) {
        if (rows[i].charAt(j) == 'A' || rows[i].charAt(j) == 'B') quantities[i]++;
      }
    }
    System.out.println(Arrays.toString(rows));
    System.out.println(Arrays.toString(quantities));
    return threeEquals(quantities);
  }

  private boolean threeEquals(int[] quantities) {
    if (quantities[0] == quantities[1] && quantities[1] == quantities[2]) return true;
    else if (quantities[0] == quantities[1] && quantities[1] == quantities[3]) return true;
    else if (quantities[0] == quantities[2] && quantities[2] == quantities[3]) return true;
    else return quantities[1] == quantities[2] && quantities[2] == quantities[3];
  }

  private void generateInitialRows() {
    char[] availableCharacters = {'A', 'B', 'C', 'D'};
    Random random = new Random();
    for (int i = 0; i < rows.length; i++) {
      StringBuilder rowBuilder = new StringBuilder();
      for (int j = 0; j < wordLength; j++) {
        rowBuilder.append(availableCharacters[random.nextInt(availableCharacters.length)]);
      }
      rows[i] = rowBuilder.toString();
    }
  }
}
