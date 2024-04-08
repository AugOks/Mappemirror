package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;


/**
 * Controller for the ChaosGameGui in accordance with the MVC pattern.
 */
public class ChaosGameControllerGui {
  private ChaosGameDescriptionFactory factory;
  private ChaosGameGui gameGui;

  public ChaosGameControllerGui(ChaosGameGui gameGui) {
    factory = new ChaosGameDescriptionFactory();
    this.gameGui = gameGui;

  }
  //TODO: implement Filechooser!

  /**
   * Sets the description for the sierpinski fractal.
   */
  public void createSierpinski() {
    ChaosGameDescription description =  factory.createDescription(ChaosGameDescriptionFactory.fractals.SIERPINSKI);
    ChaosGame game = new ChaosGame(description, 500, 900);
    gameGui.createCanvas(game, 1000000);
  }

  /**
   * Sets the description for the mandelbrot fractal.
   */
  public void createJulia() {
    ChaosGameDescription description = factory.createDescription(ChaosGameDescriptionFactory.fractals.JULIA);
    ChaosGame game = new ChaosGame(description, 500, 900);
    gameGui.createCanvas(game,10000000);

  }

  /**
   * Sets the description for the barnsley fern fractal.
   */
  public void createBarnsleyFern() {

    ChaosGameDescription description = factory.createDescription(ChaosGameDescriptionFactory.fractals.BARNSLEY);
    ChaosGame game = new ChaosGame(description, 500, 900);
    gameGui.createCanvas(game, 70000000);
  }

  /**
   * Create a julia transform based on user input.
   *
   * @param name      the name of the transform if any.
   * @param minCoords the maximum coordinates of the transform.
   * @param maxCoords the minimum coordinates of the transform.
   * @param complex   the Complex constant to be turned into transformations.
   * @return the ChaosGame description containing all the values for the fractal.
   */
  public ChaosGameDescription createUserDefinedJulia(String name, Vector2D minCoords,
                                                     Vector2D maxCoords, Complex complex) {
    List<Transform2D> transform2DList = new ArrayList<>();

    transform2DList.add(new JuliaTransform(complex, 1));
    transform2DList.add(new JuliaTransform(complex, -1));
    return new ChaosGameDescription(transform2DList, minCoords, maxCoords, name);
  }

  /**
   * Create an affine transformation based on user input.
   *
   * @param name       the name of the fractal if any.
   * @param minCoords  the minimum coordinates of the fractal.
   * @param maxCoords  the maximum coordinates of the fractal.
   * @param transforms The list of transforms for the fractal.
   * @return the ChaosGame description containing all the values of the fractal.
   */
  public ChaosGameDescription createUserDefinedAffine(String name, Vector2D minCoords,
                                                      Vector2D maxCoords,
                                                      List<Transform2D> transforms) {
    return new ChaosGameDescription(transforms, minCoords, maxCoords, name);
  }
}

