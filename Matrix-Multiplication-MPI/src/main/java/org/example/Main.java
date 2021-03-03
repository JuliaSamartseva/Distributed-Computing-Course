package org.example;

public class Main {

  public static void main(String[] args) {
    int[] dimension = {100, 1000, 5000};

    for (int i = 0; i < 3; i++) {
      Serial.calculate(args, dimension[i]);
      BlockStriped.calculate(args, dimension[i]);
      FoxMethod.calculate(args, dimension[i]);
      CannonMethod.calculate(args, dimension[i]);
    }
  }
}
