package org.example;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class App {
  public static void printMatrix(Double[][] matrix) {
    System.out.println("Matrix: ");
    int n = matrix.length;
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.println();
    }
  }

  public static Double[][] fillRandom(int n) {
    Random random = new Random();
    Double[][] matrix = new Double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        matrix[i][j] = (double) random.nextInt(100);
      }
    }
    return matrix;
  }

  public static void main(String[] args) {
    int dimension = 1000;
    Double[][] A = fillRandom(dimension);
    Double[][] B = fillRandom(dimension);
    Double[][] C = fillRandom(dimension);

    long start = System.currentTimeMillis();
    new ForkJoinPool().invoke(new MatrixMultiplication(A, B, C));
    long end = System.currentTimeMillis();
    System.out.println("Matrix [" + dimension + "x" + dimension + "] Time: " + (end - start) + "ms" );
  }
}
