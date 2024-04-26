package org.ntnu.IDATA2003.mappe5.Ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.PixelOutOfBoundsException;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameFileHandler;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;


/**
 * Controller for the ChaosGameGui in accordance with the MVC pattern.
 */
public class ChaosGameControllerGui implements ChaosGameObserver {
  private ChaosGameDescriptionFactory factory;
  private ChaosGameGui gameGui;

  private ChaosGameFileHandler fileHandler;

  private ChaosGame theGame;

  public ChaosGameControllerGui(ChaosGameGui gameGui) {
    factory = new ChaosGameDescriptionFactory();
    this.gameGui = gameGui;

  }

  /**
   * Filechooser method for choosing a file.
   */ //TODO: MAke the button for this method.
  public  void fileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(null);
    this.changeDescription(this.fileHandler.getcontentsOfFile(file.getAbsolutePath()));
  }


  /**
   * Sets the description for the sierpinski fractal.
   */
  public void createSierpinski() {
    ChaosGameDescription description =
        factory.createDescription(ChaosGameDescriptionFactory.Fractals.SIERPINSKI);
    theGame = new ChaosGame(description, 500, 900);
    gameGui.createCanvas(theGame, 1000000);
    gameGui.createInputBox(theGame.getDescription(),1000000);
    theGame.addSubscriber(gameGui);

  }

  /**
   * Sets the description for the mandelbrot fractal.
   */
  public void createJulia() {
    ChaosGameDescription description =
        factory.createDescription(ChaosGameDescriptionFactory.Fractals.JULIA);
    theGame = new ChaosGame(description, 500, 900);
    gameGui.createCanvas(theGame,10000000);
    gameGui.createInputBox(theGame.getDescription(),10000000);
    theGame.addSubscriber(gameGui);

  }

  /**
   * Sets the description for the barnsley fern fractal.
   */
  public void createBarnsleyFern() {

    ChaosGameDescription description =
        factory.createDescription(ChaosGameDescriptionFactory.Fractals.BARNSLEY);
    theGame = new ChaosGame(description, 500, 900);
    gameGui.createCanvas(theGame, 70000000);
    gameGui.createInputBox(theGame.getDescription(),70000000);
    theGame.addSubscriber(gameGui);
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

  /**
   * Change the description of the current game.
   * @param description the new description of the game.
   */
  public void changeDescription(ChaosGameDescription description) {
    theGame.setDescription(description);
  }

  /**
   * Get the description of the current game.
   * @return the current description of the game.
   */
  public ChaosGameDescription getDescription() {
    return theGame.getDescription();
  }

  /**
   * automated update method, don't use this method!
   */
  @Override
  public void update() {

  }

  public void runGame(int steps){
    try {
      theGame.runSteps(steps);
    } catch (PixelOutOfBoundsException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Changes the minimum coordinates of the current game.
   * @param minCoords the new minimum coordinates.
   */
  public void changeMinCoords(Vector2D minCoords){
    if(minCoords == null){
      throw new IllegalArgumentException("Min coordinates cannot be null");
    }
    ChaosGameDescription currentGame = getDescription();
    currentGame.setMinCoords(minCoords);
    changeDescription(currentGame);
  }

  /**
   * Changes the maximum coordinates of the current game.
   * @param maxCoords the new maximum coordinates.
   */
  public void changeMaxCoords(Vector2D maxCoords){
    if(maxCoords == null){
      throw new IllegalArgumentException("Max coordinates cannot be null");
    }
    ChaosGameDescription currentGame = getDescription();
    currentGame.setMaxCoords(maxCoords);
    changeDescription(currentGame);
  }

  /**
   * Changes the complex number of the current game.
   * @param real the real part of the complex number.
   * @param imag the imaginary part of the complex number.
   */
  public void changeComplex(double real, double imag) {
    ChaosGameDescription currentGame = getDescription();
    List<Transform2D> transforms = new ArrayList<>();
    Complex complex = new Complex(real, imag);
    transforms.add(new JuliaTransform(complex, 1));
    transforms.add(new JuliaTransform(complex, -1));
    currentGame.setTransforms(transforms);
    changeDescription(currentGame);
  }

  public ChaosGame getGame() {
    return theGame;
  }
}

