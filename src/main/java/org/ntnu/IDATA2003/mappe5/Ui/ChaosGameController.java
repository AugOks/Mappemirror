package org.ntnu.IDATA2003.mappe5.Ui;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.ntnu.IDATA2003.mappe5.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.ChaosGameFileHandler;
import org.ntnu.IDATA2003.mappe5.Complex;
import org.ntnu.IDATA2003.mappe5.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.Transform2D;
import org.ntnu.IDATA2003.mappe5.Vector2D;
import static java.lang.Integer.parseInt;

public class ChaosGameController {

  private ChaosGameUi gameUi;
  private ChaosGameFileHandler handler;

  public ChaosGameController(ChaosGameUi gameUi){
    this.gameUi = gameUi;
    this.handler = new ChaosGameFileHandler();
  }

  /**
   * Sets the description for the sierpinski fractal.
   *
   * @return ChaosGameDescription for the sierpinski fractal.
   */
  public ChaosGameDescription createSierpinksi() {

   ChaosGameDescription sierpinksi =  this.handler.readFromFile("sierpinski");
   handler.writeToFile(sierpinksi);
   return  sierpinksi;
  }

  /**
   * TODO set the description based on the file.
   * Sets the description for the mandelbrot fractal.
   *
   * @return ChaosGameDescription for the mandelbrot fractal.
   */
  public ChaosGameDescription createMandelbrot(){
    Vector2D minCoords = new Vector2D(-1.6, -1.0);
    Vector2D maxCoords = new Vector2D(1.6, 1.0);
    Complex c = new Complex(-0.74543, 0.11301);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c,1));
    transformList.add(new JuliaTransform(c,-1));
    ChaosGameDescription game =new ChaosGameDescription(transformList, minCoords, maxCoords, "mandelbrot");
    handler.writeToFile(game);
    return game;

  }

  /**
   * Gets input from user and returns the int they typed.
   *
   * @return the int the user typed.
   */
  public  int parseInput(){
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.nextLine();
    userInput = userInput.replaceAll("\\s", "");
    int userInputInt  = 0;
    try {
      userInputInt = parseInt(userInput);
    } catch (IllegalArgumentException ignored) {
      System.out.println("Invalid input - Please pick a number from the menu;)");
      //TODO refactor this, should be in ui
    }
    return userInputInt;
  }
  public void writeTofile(){
    handler.writeAllGamesToFile();
  }

}
