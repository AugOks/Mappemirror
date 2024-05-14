package org.ntnu.IDATA2003.mappe5.Ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
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

import org.ntnu.IDATA2003.mappe5.Ui.DanceParty;
import org.ntnu.IDATA2003.mappe5.Ui.ChaosGameDialogHandler;


/**
 * Controller for the ChaosGameGui in accordance with the MVC pattern.
 */
public class ChaosGameControllerGui implements ChaosGameObserver {

  private ChaosGameDescriptionFactory factory;
  private ChaosGameGui gameGui;
  private ChaosGameFileHandler fileHandler;
  private ChaosGame theGame;
  private DanceParty danceParty;
  private ChaosGameDialogHandler dialogHandler;

  /**
   * Constructor for the ChaosGameControllerGui.
   *
   * @param gameGui the ChaosGameGui to be controlled.
   */
  public ChaosGameControllerGui(ChaosGameGui gameGui) {
    factory = new ChaosGameDescriptionFactory();
    this.fileHandler = new ChaosGameFileHandler();
    this.gameGui = gameGui;
    this.danceParty = null;
    this.dialogHandler = new ChaosGameDialogHandler();

  }

<<<<<<< Updated upstream
  //TODO move this to dialog handler
  public void saveToFile(){
    FileChooser savefile = new FileChooser();
    savefile.setTitle("Save fractal to file");
    File file = savefile.showSaveDialog(gameGui.getScene().getWindow());
    if (file != null) {
      theGame.getDescription().setName(file.getName());
      this.fileHandler.writeToFile(file.getPath(), theGame.getDescription());
    }
=======
  /**
   * File chooser method for choosing a file.
   */ //TODO: MAke the button for this method.
  public  void fileChooser() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    File file = fileChooser.showOpenDialog(null);
    //System.out.println(file.getPath());
    this.changeDescription(this.fileHandler.getcontentsOfFile(file.getPath()));
>>>>>>> Stashed changes
  }


  /**
   * FileChooser method for choosing a file.
   */
  //TODO move this to dialog handler
  public void openFromFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open fractal from file");
    File file = fileChooser.showOpenDialog(gameGui.getScene().getWindow());
    if (file != null) {
      this.changeDescription(this.fileHandler.getcontentsOfFile(file.getPath()));
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
   * Automated update method, don't use this method!
   */
  //TODO change the javadoc of this method.
  @Override
  public void update() {

  }

  /**
   * Run the game for a given amount of steps.
   *
   * @param steps the amount of steps to run the game.
   */
  public void runGame(int steps){
    try {
      theGame.runSteps(steps);
    } catch (PixelOutOfBoundsException e) {
      throw new RuntimeException(e);
    }
  }

  public ChaosGame getGame() {
    return theGame;
  }
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

  /**
<<<<<<< Updated upstream
   * Starts the dance party animation of the user presses "Yes" on the confirmation .
   */
  public void danceParty(){
    ChaosGameDescription startDescription = this.getDescription();
    if (this.dialogHandler.dancePartyDialog()) {
      this.danceParty = new DanceParty(startDescription);
      this.danceParty.danceParty(this);
    }
=======
   * Returns the current game.
   *
   * @return the game.
   */
  public ChaosGame getGame() {
    return theGame;
>>>>>>> Stashed changes
  }

  public void showAbout(){
    this.dialogHandler.showAboutDialog();
  }

}

