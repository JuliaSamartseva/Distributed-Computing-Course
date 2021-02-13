package org.example.first;

import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
  public static void main(String[] args) throws IOException {
    FileManager manager = new FileManager("src/main/resources/file.txt");
    Data dataToRemove = new Data("Remove", "100000000");
    Thread threadWriter1 =
        new Thread(
            () -> {
              try {
                for (int i = 0; i < 5; i++) {
                  Thread.sleep(10);
                  manager.addRecord(new Data("MyName" + i, "394893849" + i));
                }
                manager.addRecord(dataToRemove);
              } catch (IOException | InterruptedException e) {
                e.printStackTrace();
              }
            });

    Thread threadWriter2 =
        new Thread(
            () -> {
              try {
                for (int i = 0; i < 5; i++) {
                  Thread.sleep(20);
                  manager.addRecord(new Data("Name" + i, "100000" + i));
                }
              } catch (IOException | InterruptedException e) {
                e.printStackTrace();
              }
            });

    Thread threadReader1 =
        new Thread(
            () -> {
              try {
                Thread.sleep(30);
                manager.findNameByPhone("1000002");
                Thread.sleep(30);
                manager.findNameByPhone("1000001");
              } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
              }
            });

    Thread threadReader2 =
        new Thread(
            () -> {
              try {
                Thread.sleep(30);
                manager.findPhoneByName("MyName1");
                Thread.sleep(30);
                manager.findPhoneByName("Non-existent-name");
              } catch (FileNotFoundException | InterruptedException e) {
                e.printStackTrace();
              }
            });

    Thread threadRemover1 =
        new Thread(
            () -> {
              try {
                Thread.sleep(100);
                manager.removeRecord(dataToRemove);
              } catch (InterruptedException | IOException e) {
                e.printStackTrace();
              }
            });

    threadWriter1.start();
    threadWriter2.start();
    threadReader1.start();
    threadReader2.start();
    threadRemover1.start();
  }
}
