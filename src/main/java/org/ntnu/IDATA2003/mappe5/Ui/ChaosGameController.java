package org.ntnu.IDATA2003.mappe5.Ui;

import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.ntnu.IDATA2003.mappe5.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.ChaosGameFileHandler;
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
   * TODO: Change this to get from file.
   * @return
   */
  public ChaosGameDescription createSierpinksi() {
   return this.handler.readFromFile("sierpinski");
  }

  public ChaosGameDescription createMandelbrot(){
    Vector2D minCoords = new Vector2D(-1.6, -1.0);
    Vector2D maxCoords = new Vector2D(1.6, 1.0);

    this.handler.readFromFile("sierpinski");

    //return new ChaosGameDescription(transformList, minCoords, maxCoords);
    return  null;
  }

  /**
   * Gets input from user and returns the int they typed.
   * @return
   */
  public  int parseInput(){
    Scanner scanner = new Scanner(System.in);
    String userInput = scanner.nextLine();
    userInput = userInput.replaceAll("\\s", "");
    int userInputInt  = 0;
    try {
      userInputInt = parseInt(userInput);
    } catch (IllegalArgumentException ignored) {
      ;
    }
    return userInputInt;
  }

}
