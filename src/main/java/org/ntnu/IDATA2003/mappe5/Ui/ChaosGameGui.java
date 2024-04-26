package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.PixelOutOfBoundsException;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;

public class ChaosGameGui extends Application implements ChaosGameObserver {

  //TODO: consider refactoring affineTransformBox and SliderBox to inherit from a super inputbox class

  private HBox canvasCenterPane; // The canvas for the fractal
  private HBox inputBox; // The right pane with the input fields
  private ChaosGameControllerGui controller; // The controller for the chaos game app
  private List<AffineTransformBox> transformBoxes; // The input fields for the affine transformation
  private JuliaSliderBox sliderBox = null; // The input slider for the julia transformation
  private MinMaxCoordsBox minMaxCoordsBox; // The input fields for the min/max coords
  private int currentSteps = 10;
  private Scene scene; // The scene for the chaos game app



  /**
   * Constructor for the ChaosGameGui.
   */
  public ChaosGameGui(){
    this.transformBoxes = new ArrayList<>();
    controller = new ChaosGameControllerGui(this);
  }

  /**
   * The main method for the chaos game app.
   * This method launches the app.
   *
   * @param args the arguments for the main method.
   */
  public static void mainApp(String[] args) {
    launch(args);
  }

  /**
   * Starts the chaos game app with the start screen.
   *
   * @param primaryStage the primary stage for the chaos game app.
   */
  @Override
  public void start(Stage primaryStage) throws Exception  {
    try {

      BorderPane root = new BorderPane();
      this.scene = new Scene(root, 400, 500);

      // The header
      root.setTop(createTopPane());

      //The right pane
      root.setRight(createRightPane());

      // The center pane with the canvas
      HBox centerPane = createCenterPane();
      root.setCenter(centerPane);

      // The left pane
      VBox leftPane = new VBox();
      leftPane.getStyleClass().add("leftPane");
      root.setLeft(leftPane);

      scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
      scene.setCursor(Cursor.DEFAULT);
      primaryStage.setTitle("Chaos Game");
      primaryStage.setScene(scene);
      primaryStage.setMaximized(true);
      primaryStage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method for receiving changes made in chaos game based on observer pattern.
   */
  public void update(){
    this.canvasCenterPane.getChildren().clear();
    createCanvas(controller.getGame(), currentSteps);
  }

  /**
   * Creates the right pane with buttons for the different transformations
   * and the input fields for the transformations.
   *
   * @return HBox a HBox containing the components for the right pane.
   */
  private VBox createRightPane() {
    this.inputBox = new HBox();

    VBox rightPane = new VBox();

    // Button for the julia transformation
    Button juliaButton = new Button("Julia Set");
    juliaButton.getStyleClass().add("button-rightPane");
    juliaButton.setOnAction(e -> controller.createJulia());

    //Button for the sierpinski transformation
    Button sierpinskiButton = new Button("Sierpinski");
    sierpinskiButton.getStyleClass().add("button-rightPane");

    sierpinskiButton.setOnAction(e -> {
      controller.createSierpinski();
      //TODO BAD CODE!!!!
      this.sliderBox = null;
    });

    //Button for the barnsley fern transformation
    Button barnsleyButton = new Button("Barnsley Fern");
    barnsleyButton.getStyleClass().add("button-rightPane");

    barnsleyButton.setOnAction(e -> controller.createBarnsleyFern());

    HBox buttonBox = new HBox();
    buttonBox.getChildren().addAll(juliaButton,sierpinskiButton, barnsleyButton);
    //TODO add css style to these buttons
    buttonBox.setSpacing(20);
    buttonBox.setAlignment(Pos.CENTER);
    rightPane.getChildren().addAll(buttonBox, this.inputBox);
    rightPane.setAlignment(Pos.TOP_CENTER);
    rightPane.getStyleClass().add("rightPane");

    return rightPane;
  }

  /**
   * Creates the top pane with the banner.
   *
   * @return the VBox containing the banner.
   */

  private VBox createTopPane(){
    VBox bannerPane = new VBox();

    Image banner = new Image(getClass().getResource("/header.png").toExternalForm());

    ImageView bannerView = new ImageView(banner);
    bannerView.getStyleClass().add("header-logo");

    bannerPane.getChildren().addAll(bannerView);
    bannerPane.getStyleClass().add("header");

    return bannerPane;
  }

  /**
   * Creates the center pane with the canvas.
   * Starts the application with an already remade fractal.
   *
   * @return the HBox containing the main canvas.
   */

  private HBox createCenterPane(){
    this.canvasCenterPane = new HBox();

    this.canvasCenterPane.getStyleClass().add("centerPane");

    this.controller.createSierpinski();

    this.canvasCenterPane.setAlignment(Pos.CENTER);
    return canvasCenterPane;
  }

  /**
   * Creates the input box for the chaos game.
   * It contains the input fields that is the same for both the julia and affine transformation.
   *
   * @param description the description of the chaos game.
   * @param stepsInt the amount of steps to run the chaos game.
   */
  //TODO refactor this method to its own class.

  public void createInputBox(ChaosGameDescription description, int stepsInt){

    this.inputBox.getChildren().clear();

    this.minMaxCoordsBox = new MinMaxCoordsBox(description.getMinCoords(),
        description.getMaxCoords());

    TextField steps = new TextField();

    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      this.currentSteps = Integer.parseInt(newInput);
    });
    steps.setText(String.valueOf(currentSteps));
    HBox stepBox = new HBox();
    stepBox.getChildren().addAll(new Label("steps"), steps);
    stepBox.setSpacing(10);
    HBox box = new HBox();

    if (description.getTransform(0) instanceof AffineTransform2D){
      box.getChildren().add(createInputBoxAffine(description));
    }else{
      box.getChildren().add(createInputBoxJulia(description));
    }

    GridPane grid = new GridPane();
    grid.add(stepBox, 0, 0);
    grid.add(this.minMaxCoordsBox.getMinMaxGrid(), 0, 1);
    grid.add(box, 0, 2);

    this.inputBox.setBackground(new Background(new BackgroundFill(Color.DARKGREY,
        null,null)));
    this.inputBox.getChildren().add(grid);

    Button runButton = new Button("Run");
    runButton.getStyleClass().add("button-rightPane");
    runButton.setOnAction(e -> {
      List<Transform2D> newTransforms = new ArrayList<>();
      if(this.sliderBox == null){
        ChaosGameDescription newDescription = description;
        for (AffineTransformBox boxValues: this.transformBoxes){
          newTransforms.add(boxValues.getTransform());
        }
        newDescription.setTransforms(newTransforms);
        controller.changeDescription(newDescription);
      }
    });
    grid.add(new Label(" "),0,3);
    grid.add(runButton, 0, 4);
  }

  /**
   * Creates the canvas for the chaos game.
   * It will draw the canvas based on the game description and the amount of steps.
   *
   * @param game the chaos game to create the canvas for.
   * @param steps the amount of steps to run the chaos game.
   */

  public void createCanvas(ChaosGame game, int steps){

    ChaosCanvas canvas = game.getCanvas();
    int index_i = canvas.getHeight();
    int index_j = canvas.getWidth();
    int[][] canvasArray =  canvas.getCanvasArray();
    try {
      game.runSteps(steps);
    } catch (PixelOutOfBoundsException e) {
      //TODO: dialog box for error
    }

    WritableImage writable_image = new WritableImage(900, 500);
    PixelWriter writer = writable_image.getPixelWriter();

    for (int i = 0; i < index_i; i++) {
      for (int j = 0; j < index_j; j++) {
        if (canvasArray[i][j] == 1) {
          writer.setColor(j, i, Color.WHITE);
        }
      }
    }
    ImageView fractal = new ImageView(writable_image);
    this.canvasCenterPane.getChildren().clear();
    this.canvasCenterPane.getChildren().add(new HBox(fractal));
  }

  /**
   * Creates the input box for the julia transformation.
   * This includes the input fields for the real and imaginary number.
   *
   * @param description the description of the chaos game.
   * @return a grid pane containing the unique input fields for the julia transformation.
   */

  public GridPane createInputBoxJulia(ChaosGameDescription description){
    this.minMaxCoordsBox = new MinMaxCoordsBox(description.getMinCoords(),
        description.getMaxCoords());
    GridPane grid = new GridPane();
    Transform2D transform = description.getTransform(0);
    if (transform instanceof JuliaTransform juliaTransform) {
      this.sliderBox = new JuliaSliderBox(juliaTransform.getComplex());
        grid.add(this.sliderBox.getSliderGrid(), 0, 0);
    }
    return grid;
  }
  /**
   * Creates the input box for the affine transformation
   * @param description the description of the chaos game.
   * @return a grid pane containing the unique input fields for the affine transformation.
   */

  public GridPane createInputBoxAffine(ChaosGameDescription description){
    this.transformBoxes.clear();
    int indexTransform = 0;
    int row = 0;
    int column = 0;

    GridPane grid = new GridPane();

    //TODO refactor this shittt
    for(Transform2D transform:  description.getAllTransforms()){
      AffineTransform2D castedTransform = (AffineTransform2D) transform;
      this.transformBoxes.add(new AffineTransformBox(castedTransform.getMatrix(),
            castedTransform.getVector()));

      int number = indexTransform+1;

      grid.add(new Label("Transform "+number+":"), column, row);
      row++;
      grid.add(this.transformBoxes.get(indexTransform).getGridBox(), column, row);
      row++;
      if (column == 0){
        column++;
        if(row == 2){
          row = 0;
        } else {
          row-=2;
        }
      } else if (column == 1) {
        column--;
        row+=2;
      }
      indexTransform++;

    }
    return grid;
  }


  /**
   * Listens to the text input and updates the text field with the new value.
   * Makes sure that the input is correct, and that the user cannot type it in wrong.
   *
   * @param newValue the new value of the text field.
   * @param oldValue the old value of the text field.
   */
  private String textInputListener(String newValue, String oldValue){
    String returnValue = null;
    try {
      if (!newValue.isEmpty()) {
        int newValueInt;
        if (newValue.substring(newValue.length() - 1).equals(".")){
          newValue = newValue+"0";
        }

        if (newValue.contains("-")){
          newValue =newValue.replace("-", "");
          newValueInt= Integer.parseInt(newValue);
          newValueInt=newValueInt*(-1);
        } else{
          newValueInt= Integer.parseInt(newValue);
        }
        returnValue = newValue;

        //TODO add gard specific to the different types of input felt
      }
    } catch (NumberFormatException e) {
      // The user have entered a non-integer character, hence just keep the
      // oldValue and ignore the newValue.
      returnValue = oldValue;
    }
    return returnValue;
    //TODO here the description should change and the canvas should be updated
  }


}
