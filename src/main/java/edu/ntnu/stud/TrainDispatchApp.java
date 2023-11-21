package edu.ntnu.stud;

/**
 * This is the main class for the train dispatch application.
 * <p>
 * author: August Oksavik
 * version: 0.8
 */

public class TrainDispatchApp {

  public static void main(String[] args) {
    DispatchUi trainApp = new DispatchUi();
    DepartureRegistry registry = new DepartureRegistry();
    trainApp.init();
    trainApp.start();
  }
}
