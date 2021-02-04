package org.example.first;

public class App {
  public static void main(String[] args) {
    SliderWindow window = new SliderWindow();
    window.setVisible(true);
    window.startThreads();
  }
}
