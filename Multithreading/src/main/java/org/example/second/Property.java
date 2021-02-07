package org.example.second;

import java.util.Random;
import java.util.UUID;

public class Property {
  public int price;
  public int processTime;
  public String uniqueID;

  public Property() {
    Random random = new Random();
    price = random.nextInt(200);
    processTime = random.nextInt(1000) + 200;
    uniqueID = UUID.randomUUID().toString();
  }
}
