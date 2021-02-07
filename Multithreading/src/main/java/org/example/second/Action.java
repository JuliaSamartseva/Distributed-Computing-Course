package org.example.second;

public enum Action {
  CARRY("Carrying property."),
  LOAD("Loading property.");

  public final String label;

  private Action(String label) {
    this.label = label;
  }
}
