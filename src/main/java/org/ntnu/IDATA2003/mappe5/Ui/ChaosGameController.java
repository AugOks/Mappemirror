package org.ntnu.IDATA2003.mappe5.Ui;

import static java.lang.Integer.parseInt;

import java.util.Scanner;
import org.ntnu.IDATA2003.mappe5.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.ChaosGameFileHandler;

public class ChaosGameController {

  private ChaosGameUi gameUi;
  private ChaosGameFileHandler handler;

  public ChaosGameController(ChaosGameUi gameUi) {
    this.gameUi = gameUi;
    this.handler = new ChaosGameFileHandler();
  }

  /**
   * Sets the description for the sierpinski fractal.
   *
   * @return ChaosGameDescription for the sierpinski fractal.
   */
  public ChaosGameDescription createSierpinksi() {
    ChaosGameDescription sierpinksi = this.handler.readFromFile("sierpinski");
    handler.writeToFile(sierpinksi);
    return sierpinksi;
  }

  /**
   * Sets the description for the mandelbrot fractal.
   *
   * @return ChaosGameDescription for the mandelbrot fractal.
   */
  public ChaosGameDescription createJulia() {
    /*
    Vector2D minCoords = new Vector2D(-1.6, -1.0);
    Vector2D maxCoords = new Vector2D(1.6, 1.0);
    Complex c = new Complex(-0.74543, 0.11301);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c,1));
    transformList.add(new JuliaTransform(c,-1));
    ChaosGameDescription julia = new ChaosGameDescription(transformList, minCoords, maxCoords, "julia");

     */
    ChaosGameDescription julia = this.handler.readFromFile("juliaset");
    handler.writeToFile(julia);
    return julia;

  }

}
