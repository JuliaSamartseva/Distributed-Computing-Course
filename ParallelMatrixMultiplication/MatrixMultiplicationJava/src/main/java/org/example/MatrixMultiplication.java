package org.example;

import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiplication extends RecursiveAction {
  private final int current;
  private final Double[][] A;
  private final Double[][] B;
  private final Double[][] C;
  private final boolean main;

  public MatrixMultiplication(Double[][] A, Double[][] B, Double[][] C) {
    this.current = -1;
    this.A = A;
    this.B = B;
    this.C = C;
    this.main = true;
  }

  private MatrixMultiplication(int current, Double[][] A, Double[][] B, Double[][] C, boolean main) {
    this.current = current;
    this.A = A;
    this.B = B;
    this.C = C;
    this.main = main;
  }

  @Override
  protected void compute() {
    if (main) {
      ArrayList<MatrixMultiplication> subtasks = new ArrayList<>();
      for (int i = 0; i < A.length; i++)
        subtasks.add(new MatrixMultiplication(i, A, B, C, false));
      invokeAll(subtasks);
    } else multiply();
  }

  void multiply() {
    if (current >= 0 && current < A.length) {
      for (int i = 0; i < A.length; i++) {
        for (int j = 0; j < B.length; j++)
          C[current][i] += A[current][j] * B[j][i];
      }
    }
  }
}
