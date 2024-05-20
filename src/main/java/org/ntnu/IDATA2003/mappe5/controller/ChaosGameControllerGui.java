package org.ntnu.IDATA2003.mappe5.controller;

import java.io.File;
import javafx.scene.paint.Color;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.AnimationFailedException;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.FailedToWriteToFileException;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.ResourceNotFoundException;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescriptionFactory;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameFileHandler;
import org.ntnu.IDATA2003.mappe5.view.ChaosGameAnimations;
import org.ntnu.IDATA2003.mappe5.view.ChaosGameDialogHandler;
import org.ntnu.IDATA2003.mappe5.view.ChaosGameGui;


/**
 * Controller for the ChaosGameGui in accordance with the MVC pattern.
 */
public class ChaosGameControllerGui {

  private final ChaosGameDescriptionFactory factory;
  private final ChaosGameGui gameGui;
  private final ChaosGameFileHandler fileHandler;
  private final ChaosGameDialogHandler dialogHandler;
  private ChaosGame theGame;
  private ChaosGameAnimations chaosGameAnimations;

  /**
   * Constructor for the ChaosGameControllerGui.
   *
   * @param gameGui the ChaosGameGui to be controlled.
   */
  public ChaosGameControllerGui(ChaosGameGui gameGui) {
    factory = new ChaosGameDescriptionFactory();
    this.fileHandler = new ChaosGameFileHandler();
    this.gameGui = gameGui;
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
      gameGui.createInputNode(theGame.getDescription(), 1000000);
    }
  }

  /**
   * Create a fractal from the ChaosGameDescriptionFactory based on the enum value.
   *
   * @param fractal the enum of the fractal to be created.
   */
  public void createNewFractalDescription(ChaosGameDescriptionFactory.Fractals fractal) {
    ChaosGameDescription description = factory.createDescription(fractal);
    this.createNewFractal(description);
  }

  /**
   * Create a new fractal from a given description.
   *
   * @param description the description of the fractal to be created.
   */
  private void createNewFractal(ChaosGameDescription description) {
    try {
      theGame =
          new ChaosGame(description, gameGui.getHeightForCanvas(), gameGui.getWidthForCanvas());
    } catch (IllegalArgumentException e) {
      dialogHandler.genericErrorDialog(e.getMessage());
    }
    gameGui.createCanvas(theGame, 1000000);
    gameGui.createInputNode(theGame.getDescription(), 1000000);
    this.chaosGameAnimations = new ChaosGameAnimations(theGame.getDescription(), this);
    theGame.addSubscriber(gameGui);
  }

  /**
   * Change the description of the current game.
   *
   * @param description the new description of the game.
   */
  public void changeDescription(ChaosGameDescription description) {
    theGame.setDescription(description, gameGui.getHeightForCanvas(), gameGui.getWidthForCanvas());
  }

  /**
   * Get the description of the current game.
   *
   * @return the current description of the game.
   */
  public ChaosGameDescription getDescription() {
    return theGame.getDescription();
  }


  /**
   * Change the coordinates of the current game.
   *
   * @param minCoords the new minimum coordinates.
   * @param maxCoords the new maximum coordinates.
   */
  public void changeCoords(Vector2D minCoords, Vector2D maxCoords) {
    if (minCoords == null) {
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
    if (this.dialogHandler.exitDialog()) {
      System.exit(0);
    }
  }

  /**
   * Create a new blank fractal based on the user input.
   */
  public void createBlankFractal() {
    int transforms = this.dialogHandler.createNewFractalDialog();
    if (transforms == 0) {
      this.createNewFractalDescription(ChaosGameDescriptionFactory.Fractals.BLANKJULIA);
    } else {
      this.changeDescription(this.factory.createDescription(
          ChaosGameDescriptionFactory.Fractals.BLANKAFFINE, transforms));
      this.createNewFractal(this.getDescription());
    }
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
  public void slideIntoJuliaDms() {
    try {
      chaosGameAnimations.chooseJuliaAnimation("normal");
    } catch (AnimationFailedException e) {
      dialogHandler.genericErrorDialog("Failed to animate the Julia set");
    }
  }

  /**
   * Wacky slider animation for the Julia set.
   */
  public void wackySliderAnimation() {
    try {
      chaosGameAnimations.chooseJuliaAnimation("wacky");
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
   * Show the 'about' dialog.
   */
  public void showAbout() {
    try {
      this.dialogHandler.showAboutDialog();
    } catch (ResourceNotFoundException e) {
      dialogHandler.genericErrorDialog("sorry, something went wrong when trying to create the"
          + "about dialog");
    }
  }

  public void showHelp() {
    try {
      this.dialogHandler.showHelpDialog();
    } catch (ResourceNotFoundException e) {
      dialogHandler.genericErrorDialog("sorry, something went wrong when trying to create the"
          + "help dialog");
    }
  }


  public void setColorChoiceC(Color colorChoice) {
    this.gameGui.setColorChoice(colorChoice);
  }

}

