package org.ntnu.IDATA2003.mappe5.Ui;


import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.TransformsParser;

/**
 * This class is the user interface for the Chaos Game application.
 * It displays a menu for the user to pick a fractal to print.
 * It will display the fractal based on the amount of steps the user wants to run.
 */

public class ChaosGameUi {

  /**
   * Constants for the menu options.
   *
   * <ul>
   * <li> SIERPINSKI is the Sierpinski triangle.</li>
   * <li> JULIASET is the Julia set.</li>
   * <li> BARNSLEY_FERN is the barnsley fern.</li>
   * <li> QUIT is to quit the application.</li>
   * <li> height and width are the dimensions of the canvas.</li>
   * <li> controller is the controller for the Chaos Game.</li>
   * <li> mainGame is the instance of the Chaos Game.</li>
   * </ul>
   */
  private static final  int SIERPINSKI = 1;
  private static final  int JULIASET = 2;
  private static final  int BARNSLEY_FERN = 3;
  private static final  int USER_AFFINE = 4;
  private static final  int USER_JULIA = 5;
  private static final  int QUIT = 6;
  private static final int height = 50;
  private static final int width = 150;
  private final ChaosGameController controller;
  private ChaosGame mainGame;

  public ChaosGameUi() {
    this.controller = new ChaosGameController(new ChaosGameGui());
  }

  /**
   * The main Application screen.
   * Starts the application and gets user input.
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
        case BARNSLEY_FERN:
          this.printFractal(menuChoice);
          break;
        case USER_AFFINE:
          this.printFractal(menuChoice);
          break;
        case USER_JULIA:
          this.printFractal(menuChoice);
          break;
        case QUIT:
          System.out.println("* Thank you for using Chaos Game *");
          finished = true;
          break;

        default:
          System.out.println("Invalid choice - Please pick a number from the menu");
      }
    }
  }

  private int displayMenu() {
    System.out.println("Pick an option");
    System.out.println("1. Sierpinski triangle");
    System.out.println("2. Julia set");
    System.out.println("3. Barnsley fern");
    System.out.println("4. make your own Julia transform");
    System.out.println("5. make your own Affine transform");
    System.out.println("6. Quit");

    return this.parseInput();
  }

  private void printCanvas() {
    int index_i = mainGame.getCanvas().getHeight();
    int index_j = mainGame.getCanvas().getWidth();
    int[][] canvas = mainGame.getCanvas().getCanvasArray();
    ArrayList<String> canvasConsole = new ArrayList<>();
    StringBuilder line = new StringBuilder();

    for (int i = 0; i < index_i; i++) {
      for (int j = 0; j < index_j; j++) {
        if (canvas[i][j] == 0) {
          line.append("-");
        } else {
          line.append("X");
        }
      }
      canvasConsole.add(line.toString());
      line = new StringBuilder();
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
    System.out.println("How many steps to run? (10000+ for best results)");
    int steps = this.parseInput();
    if (menuChoice == 1) {
      this.mainGame = new ChaosGame(controller.createSierpinksi(), height, width);
    }
    if (menuChoice == 2) {
      this.mainGame = new ChaosGame(controller.createJulia(), height, width);
    }
    if (menuChoice == 3) {
      this.mainGame = new ChaosGame(controller.createBarnsleyFern(), height, width);
    }
    if (menuChoice == 4) {
      this.mainGame = new ChaosGame(this.userDefinedJulia(), height, width);
    }
    if (menuChoice == 5) {
      this.mainGame = new ChaosGame(this.userDefinedAffineTransform(), height, width);
    }
    this.mainGame.runSteps(steps);
    printCanvas();

  }

  /**
   * Creates a user defined Julia set from user input.
   *
   * @return a chaosGame description containing the fractal.
   */
  public ChaosGameDescription userDefinedJulia() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("name of the transform: ");
    String name = scanner.nextLine();

    System.out.println("min coords x value: ");
    double minCoordsX = scanner.nextDouble();
    System.out.println("min coords y value: ");
    double minCoordsY = scanner.nextDouble();
    Vector2D minCoords = new Vector2D(minCoordsX, minCoordsY);

    System.out.println("max coords x value: ");
    double maxCoordsX = scanner.nextDouble();
    System.out.println("max coords y value: ");
    double maxCoordsY = scanner.nextDouble();
    Vector2D maxCoords = new Vector2D(maxCoordsX, maxCoordsY);

    System.out.println("Constant C real number: ");
    double real = scanner.nextDouble();
    System.out.println("constant c Imaginary number: ");
    double imag = scanner.nextDouble();
    Complex complex = new Complex(real, imag);

    return controller.createUserDefinedJulia(name, minCoords, maxCoords, complex);
  }

  /**
   * Creates a user defined affine transform from user input.
   *
   * @return a chaosGame description containing the fractal.
   */
  public ChaosGameDescription userDefinedAffineTransform() {
    //TODO add gards for input

    Scanner scanner = new Scanner(System.in);
    List<AffineTransform2D> transformList = new ArrayList<>();

    System.out.println("Name of the transform: ");
    String name = scanner.nextLine();
    System.out.println("Min coords x value: ");
    double minCoordsX = scanner.nextDouble();
    System.out.println("Min coords y value: ");
    double minCoordsY = scanner.nextDouble();
    Vector2D minCoords = new Vector2D(minCoordsX, minCoordsY);

    System.out.println("Max coords x value: ");
    double maxCoordsX = scanner.nextDouble();
    System.out.println("Max coords y value: ");
    double maxCoordsY = scanner.nextDouble();
    Vector2D maxCoords = new Vector2D(maxCoordsX, maxCoordsY);

    boolean finisched = false;
    int index = 1;
    ArrayList<String> dirtyArrayList = new ArrayList<>();

    while (!finisched) {
      Scanner scannerWhile = new Scanner(System.in);
      System.out.println("Matrix for transform " + index + " : ");
      String matrix = scannerWhile.nextLine();

      System.out.println("Vector for transform " + index + " : ");
      String vector = scannerWhile.nextLine();
      dirtyArrayList.add(matrix + "," + vector);

      System.out.println("Want another transformation? [yes/No]");
      String newTransform = scannerWhile.nextLine();

      if (newTransform.equalsIgnoreCase("no")) {
        finisched = true;
      } else if (newTransform.equalsIgnoreCase("yes")) {
        index++;
      } else {
        System.out.println("Incorrect value, try again");
        //TODO add an additional while loop so it dont ask for value of matrix and vector again
      }
    }
    TransformsParser parser = new TransformsParser();
    List<Transform2D> transforms = parser.parseAffineTransforms(dirtyArrayList);

    return controller.createUserDefinedAffine(name, minCoords, maxCoords, transforms);
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
