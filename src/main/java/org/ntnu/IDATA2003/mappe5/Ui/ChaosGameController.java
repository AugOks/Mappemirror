package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameFileHandler;


/**
 * Controller for the ChaosgameUI in accordance with the MVC pattern.
 */
public class ChaosGameController {
  private ChaosGameDescriptionFactory factory;

  private ChaosGameGui gameGui;
  private ChaosGameFileHandler handler;

  public ChaosGameController(ChaosGameGui gameGui) {
    factory = new ChaosGameDescriptionFactory();
    this.gameGui = gameGui;
    this.handler = new ChaosGameFileHandler();
  }

  /**
   * Sets the description for the sierpinski fractal.
   *
   * @return ChaosGameDescription for the sierpinski fractal.
   */
  public ChaosGameDescription createSierpinksi() {
    //ChaosGameDescription sierpinksi = this.handler.readFromFile("sierpinski");

    return factory.createDescription(ChaosGameDescriptionFactory.fractals.SIERPINSKI);
  }

  /**
   * Sets the description for the mandelbrot fractal.
   *
   * @return ChaosGameDescription for the mandelbrot fractal.
   */
  public ChaosGameDescription createJulia() {
    //ChaosGameDescription julia = this.handler.readFromFile("juliaset");

    return factory.createDescription(ChaosGameDescriptionFactory.fractals.JULIA);

  }

  /**
   * Sets the description for the barnsley fern fractal.
   *
   * @return ChaosGameDescription for the Barnsley fern fractal.
   */
  public ChaosGameDescription createBarnsleyFern() {
    //ChaosGameDescription barnsleyFern = this.handler.readFromFile("barnsley-fern");
    //TODO make error handling for these methods.
    return factory.createDescription(ChaosGameDescriptionFactory.fractals.BARNSLEY);
  }

  /**
   * Create a julia transform based on user input.
   *
   * @param name      the name of the transform if any.
   * @param minCoords the maximum coordinates of the transform.
   * @param maxCoords the minimum coordiantes of the transform.
   * @param complex   the Complex constant to be turned into transformations.
   * @return the Chaosgame description containing all the values for the fractal.
   */
  public ChaosGameDescription createUserDefinedJulia(String name, Vector2D minCoords,
                                                     Vector2D maxCoords, Complex complex) {
    List<Transform2D> transform2DList = new ArrayList<>();

    transform2DList.add(new JuliaTransform(complex, 1));
    transform2DList.add(new JuliaTransform(complex, -1));
    return new ChaosGameDescription(transform2DList, minCoords, maxCoords, name);
  }

  /**
   * Create a affine transformation based on user input.
   *
   * @param name       the name of the fractal if any.
   * @param minCoords  the minimum coordinates of the fractal.
   * @param maxCoords  the maximum coordinates of the fractal.
   * @param transforms The list of transforms for the fractal.
   * @return the chaosgame description containing all the values of the fractal.
   */
  public ChaosGameDescription createuserDefinedAffine(String name, Vector2D minCoords,
                                                      Vector2D maxCoords,
                                                      List<Transform2D> transforms) {
    return new ChaosGameDescription(transforms, minCoords, maxCoords, name);
  }
}
