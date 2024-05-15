package org.ntnu.IDATA2003.mappe5.Ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.AnimationFailedException;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.FailedToWriteToFileException;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.ResourceNotFoundException;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameFileHandler;



/**
 * Controller for the ChaosGameGui in accordance with the MVC pattern.
 */
public class ChaosGameControllerGui {

  private final ChaosGameDescriptionFactory factory;
  private final ChaosGameGui gameGui;
  private final ChaosGameFileHandler fileHandler;
  private ChaosGame theGame;
  private ChaosGameAnimations chaosGameAnimations;
  private final ChaosGameDialogHandler dialogHandler;

  /**
   * Constructor for the ChaosGameControllerGui.
   *
   * @param gameGui the ChaosGameGui to be controlled.
   */
  public ChaosGameControllerGui(ChaosGameGui gameGui) {
    factory = new ChaosGameDescriptionFactory();
    this.fileHandler = new ChaosGameFileHandler();
    this.gameGui = gameGui;;
    this.dialogHandler = ChaosGameDialogHandler.getInstance();


  }

  /**
   * Save the current game to a file.
   */
  public void saveToFile() {
    File file = dialogHandler.saveToFileDialog();
    if (file != null) {
      theGame.getDescription().setName(file.getName());
      try {
        this.fileHandler.writeToFile(file.getPath(), theGame.getDescription());
      } catch (FailedToWriteToFileException e) {
        dialogHandler.genericErrorDialog(e.getMessage());
      }
    }
  }
  /**
   * FileChooser method for choosing a file.
   */
  public void openFromFile() {
    File file = dialogHandler.readFromFileDialog();
    if (file != null) {
      try {
        this.changeDescription(this.fileHandler.getcontentsOfFile(file.getPath()));
      } catch (ResourceNotFoundException | NumberFormatException e) {
        dialogHandler.genericErrorDialog(e.getMessage());
      }
      gameGui.createInputNode(theGame.getDescription(),1000000);
    }
  }

  /**
   * Create a fractal from the ChaosGameDescriptionFactory based on the enum value.
   *
   * @param fractal the enum of the fractal to be created.
   */
  public void createFractal(ChaosGameDescriptionFactory.Fractals fractal) {
    ChaosGameDescription description =
        factory.createDescription(fractal);
    theGame = new ChaosGame(description, gameGui.getHeightForCanvas(), gameGui.getWidthForCanvas());
    gameGui.createCanvas(theGame, 1000000);
    gameGui.createInputNode(theGame.getDescription(),1000000);
    this.chaosGameAnimations = new ChaosGameAnimations(theGame.getDescription(), this);
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
    theGame.setDescription(description, gameGui.getHeightForCanvas(), gameGui.getWidthForCanvas());
  }

  /**
   * Get the description of the current game.
   * @return the current description of the game.
   */
  public ChaosGameDescription getDescription() {
    return theGame.getDescription();
  }


  /**
   * Run the game for a given amount of steps.
   *
   * @param steps the amount of steps to run the game.
   */
  public void runGame(int steps){
      theGame.runSteps(steps);
  }

  /**
   * Change the coordinates of the current game.
   *
   * @param minCoords the new minimum coordinates.
   * @param maxCoords the new maximum coordinates.
   */
  public void changeCoords(Vector2D minCoords, Vector2D maxCoords){
    if(minCoords == null){
      throw new IllegalArgumentException("Min coordinates cannot be null");
    }
    ChaosGameDescription currentGame = getDescription();
    currentGame.setMinCoords(minCoords);
    currentGame.setMaxCoords(maxCoords);
    changeDescription(currentGame);
  }

  /**
   * Exits the application.
   * If the user presses yes, the application will exit.
   */
  public void exitApplication() {
    if ( this.dialogHandler.exitDialog()) {
      System.exit(0);
    }
  }

  public void createNewFractal(){
    this.dialogHandler.createNewFractalDialog();
  }

  /**
   * Starts the dance party animation of the user presses "Yes" on the confirmation .
   */
  public void danceParty() {
    ChaosGameDescription startDescription = this.getDescription();
    if (this.dialogHandler.dancePartyDialog()) {
      this.chaosGameAnimations = new ChaosGameAnimations(startDescription, this);
      this.chaosGameAnimations.danceParty();
    }
  }

  /**
   * Slide into the DMs of the Julia set.
   */
  public void slideIntoJuliaDMs(){
    if (!(this.getDescription().getTransform(0) instanceof JuliaTransform)) {
      dialogHandler.genericErrorDialog("The current fractal is not a Julia set");
      return;
    }
    try {
      chaosGameAnimations.juliaSliderAnimation(this.getDescription());
    } catch (AnimationFailedException e) {
      dialogHandler.genericErrorDialog("Failed to animate the Julia set");
    }
  }

  /**
   * Get the current game.
   *
   * @return the current game.
   */
  public ChaosGame getGame() {
    return theGame;
  }

  /**
   * Show the about dialog.
   */
  public void showAbout(){
    try {
      this.dialogHandler.showAboutDialog();
    } catch (ResourceNotFoundException e) {
        dialogHandler.genericErrorDialog("sorry, something went wrong when trying to create the" +
            "about dialog");
    }
  }

}

