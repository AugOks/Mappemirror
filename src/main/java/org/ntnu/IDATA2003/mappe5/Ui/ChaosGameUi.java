package org.ntnu.IDATA2003.mappe5.Ui;


import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.Scanner;
import org.ntnu.IDATA2003.mappe5.ChaosGame;

/**
 * This class is the user interface for the Chaos Game application.
 * It displays a menu for the user to pick a fractal to print.
 * It will display the fractal based on the amount of steps the user wants to run.
 */

public class ChaosGameUi {

  /**
   * Constants for the menu options.
   *
   * <p> SIERPINSKI is the Sierpinski triangle.
   * <p> JULIASET is the Julia set.
   * <p> QUIT is to quit the application.
   * <p> height and width are the dimensions of the canvas.
   * <p> controller is the controller for the Chaos Game.
   * <p> mainGame is the instance of the Chaos Game.
   */
  private final static int SIERPINSKI = 1;
  private final static int JULIASET = 2;
  private final static int QUIT = 3;
  private static final int height = 50;
  private static final int width = 150;
  private final ChaosGameController controller;
  private ChaosGame mainGame;

  public ChaosGameUi() {
    this.controller = new ChaosGameController(this);
  }

  /**
   * The main Application screen. Starts the application and gets user input.
   */
  public void start() {
    boolean finished = false;
    while (!finished) {
      int menuChoice = displayMenu();

      switch (menuChoice) {
        case SIERPINSKI:
          this.printFractal(menuChoice);
          break;
        case JULIASET:
          this.printFractal(menuChoice);
          break;
        case QUIT:
          System.out.println("* Thank you for using Chaos Game *");
          finished = true;
          break;

        default:
          System.out.println("Invalid choice - Please pick a number from the menu;)");
      }
    }
  }

  private int displayMenu() {
    System.out.println("Pick an option");
    System.out.println("1. Sierpinski triangle");
    System.out.println("2. Julia set");
    System.out.println("3. Quit");

    return this.parseInput();
  }

  private void printCanvas() {
    int index_i = mainGame.getCanvas().getHeight();
    int index_j = mainGame.getCanvas().getWidth();
    int[][] canvas = mainGame.getCanvas().getCanvasArray();
    ArrayList<String> canvasConsole = new ArrayList<>();
    String line = "";

    //TODO refactor this to use StringBuilder
    for (int i = 0; i < index_i; i++) {
      for (int j = 0; j < index_j; j++) {
        if (canvas[i][j] == 0) {
          line += "-";
        } else {
          line += "X";
        }
      }
      canvasConsole.add(line);
      line = "";
    }
    for (String s : canvasConsole) {
      System.out.println(s);
    }
  }

  /**
   * Prints the fractal to the console.
   * The amount of steps to run is based on user input.
   *
   * @param menuChoice the fractal the user wants to print.
   */
  public void printFractal(int menuChoice) {
    System.out.println("How many steps to run?");
    int steps = this.parseInput();
    if (menuChoice == 1) {
      this.mainGame = new ChaosGame(controller.createSierpinksi(), height, width);
    }
    if (menuChoice == 2) {
      this.mainGame = new ChaosGame(controller.createJulia(), height, width);
    }
    this.mainGame.runSteps(steps);
    printCanvas();

  }

  /**
   * Gets input from user and returns the int they typed.
   * The input is parsed to an int.
   * If the input is not a number, the user is prompted to try again.
   *
   * @return the int the user typed.
   */
  public int parseInput() {
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.nextLine();
    userInput = userInput.replaceAll("\\s", "");
    int userInputInt = 0;
    try {
      userInputInt = parseInt(userInput);
    } catch (IllegalArgumentException ignored) {
      System.out.println("Invalid input - Please pick a number from the menu;)");
    }
    return userInputInt;
  }

}
