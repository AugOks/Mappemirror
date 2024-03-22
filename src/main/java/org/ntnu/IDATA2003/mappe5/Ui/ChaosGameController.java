package org.ntnu.IDATA2003.mappe5.Ui;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.ntnu.IDATA2003.mappe5.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.ChaosGameFileHandler;
import org.ntnu.IDATA2003.mappe5.Complex;
import org.ntnu.IDATA2003.mappe5.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.Transform2D;
import org.ntnu.IDATA2003.mappe5.Vector2D;

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

    return sierpinksi;
  }

  /**
   * Sets the description for the mandelbrot fractal.
   *
   * @return ChaosGameDescription for the mandelbrot fractal.
   */
  public ChaosGameDescription createJulia() {

    ChaosGameDescription julia = this.handler.readFromFile("juliaset");

    return julia;

  }
  /**
   * Sets the description for the sierpinski fractal.
   *
   * @return ChaosGameDescription for the sierpinski fractal.
   */

  public ChaosGameDescription createBarnsleyFern() {
    ChaosGameDescription barnsleyFern = this.handler.readFromFile("barnsley-fern");
    return barnsleyFern;
  }

  public ChaosGameDescription createUserDefinedJulia(String name, Vector2D minCoords, Vector2D maxCoords, Complex complex){
    List<Transform2D> transform2DList = new ArrayList<>();

    transform2DList.add(new JuliaTransform(complex, 1));
    transform2DList.add(new JuliaTransform(complex, -1));
    return new ChaosGameDescription(transform2DList, minCoords, maxCoords, name);
  }

  public ChaosGameDescription createuserDefinedAffine(String name,Vector2D minCoords, Vector2D maxCoords,
                                                      List<Transform2D> transforms){
    return  new ChaosGameDescription(transforms, minCoords, maxCoords, name);
  }
}
