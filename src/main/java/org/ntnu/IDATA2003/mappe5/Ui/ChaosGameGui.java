package org.ntnu.IDATA2003.mappe5.Ui;


import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.PixelOutOfBoundsException;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;
import javafx.scene.control.Slider;

public class ChaosGameGui extends Application implements ChaosGameObserver {

  private HBox canvasCenterPane; // The canvas for the fractal
  private HBox inputBox; // The right pane with the input fields
  private ChaosGameControllerGui controller; // The controller for the chaos game app
  private List<TransformBox> transformBoxes; // The input fields for the affine transformation
  private MinMaxCoordsBox minMaxCoordsBox; // The input fields for the min/max coords
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
    createCanvas(controller.getGame(), 10000000);
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

    sierpinskiButton.setOnAction(e -> controller.createSierpinski());

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

  public void createInputBox(ChaosGameDescription description, int stepsInt){

    this.inputBox.getChildren().clear();

    this.minMaxCoordsBox = new MinMaxCoordsBox(description.getMinCoords(),
        description.getMaxCoords());

    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      steps.setText(newInput);
    });

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


    //The real number input slider
    Slider real = new Slider(-1, 1, 0);
    real.setShowTickMarks(true);
    real.setShowTickLabels(true);
    real.setMajorTickUnit(0.25f);
    real.setBlockIncrement(0.1f);
    Label realL = new Label("Value: "+0);
    this.sliderListener(real, realL);

    //The imaginary number input slider
    Slider imag = new Slider(-1, 1, 0);
    imag.setShowTickMarks(true);
    imag.setShowTickLabels(true);
    imag.setMajorTickUnit(0.25f);
    imag.setBlockIncrement(0.1f);
    Label imagL = new Label("Value: "+0);
    this.sliderListener(imag, imagL);

    //Puts the sliders in the grid
    grid.add(new Label("Real number:"), 0, 0);
    grid.add(real, 1, 0);
    grid.add(realL, 2, 0);
    grid.add(new Label("Imag number:"), 0, 1);
    grid.add(imag, 1, 1);
    grid.add(imagL,2,1);

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
    int placement = 0;

    GridPane grid = new GridPane();
    for(Transform2D transform:  description.getAllTransforms()){
      AffineTransform2D castedTransform = (AffineTransform2D) transform;
      this.transformBoxes.add(new TransformBox(castedTransform.getMatrix(),
            castedTransform.getVector()));

      int number = indexTransform+1;
      grid.add(new Label("Transform "+number+":"), 0, placement);
      placement++;
      grid.add(this.transformBoxes.get(indexTransform).getGridBox(), 0, placement);
      indexTransform++;
      placement++;
    }


    return grid;
  }

  /**
   * Listens to the slider and updates the label with the value of the slider.
   * @param slider the slider to listen to.
   * @param label the label to update.
   */
  private void sliderListener(Slider slider, Label label){
    slider.valueProperty().addListener(
        new ChangeListener<Number>() {
          public void changed(ObservableValue<? extends Number >
                                  observable, Number oldValue, Number newValue)
          {
            String displayValue = String.format( "Value:  %.2f", newValue);
            label.setText(displayValue);
          }
        });
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
