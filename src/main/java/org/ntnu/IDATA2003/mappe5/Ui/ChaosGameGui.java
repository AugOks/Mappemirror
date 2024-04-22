package org.ntnu.IDATA2003.mappe5.Ui;

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
import org.ntnu.IDATA2003.mappe5.entity.PixelOutOfBoundsException;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;
import javafx.scene.control.Slider;

//TODO find out why the cursor is not changing
public class ChaosGameGui extends Application implements ChaosGameObserver {

  private HBox canvasCenterPane;
  private HBox inputBox;
  private ChaosGameControllerGui controller;
  private Scene scene;

  public ChaosGameGui(){

    controller = new ChaosGameControllerGui(this);
  }

  public static void mainApp(String[] args) {

    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception  {
    try {

      BorderPane root = new BorderPane();
      this.scene = new Scene(root, 400, 500);

      // The header for chaos game
      root.setTop(createTopPane());

      // The center pane with the canvas
      HBox centerPane = createCenterPane();
      root.setCenter(centerPane);

      //The right pane with all
      root.setRight(createRightPane());

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

  }

  /**
   * Creates the right pane with buttons for the different transformations
   * @return HBox a HBox containing the components for the right pane.
   */
  private VBox createRightPane() {

    VBox rightPane = new VBox();
    GridPane grid = new GridPane();

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

  private VBox createTopPane(){
    VBox bannerPane = new VBox();

    Image banner = new Image(getClass().getResource("/header.png").toExternalForm());

    ImageView bannerView = new ImageView(banner);
    bannerView.getStyleClass().add("header-logo");

    bannerPane.getChildren().addAll(bannerView);
    bannerPane.getStyleClass().add("header");

    return bannerPane;
  }

  private HBox createCenterPane(){
    this.canvasCenterPane = new HBox();

    this.canvasCenterPane.getStyleClass().add("centerPane");

    this.controller.createJulia();

    this.canvasCenterPane.setAlignment(Pos.CENTER);
    return canvasCenterPane;
  }

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

  public void createInputBoxJulia(ChaosGame game, int stepsInt){
    ChaosGameDescription description = game.getDescription();
    Vector2D minCoords = description.getMinCoords();
    Vector2D maxCoords = description.getMaxCoords();
    GridPane grid = new GridPane();

    // ------ Adds text fields to dialog  ------

    // Amount of steps to run the game for
    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      steps.setText(newInput);
    });

    // X0 value of the min coords
    TextField minX = new TextField();
    minX.setPromptText("x0");
    minX.setText(String.valueOf(minCoords.getX0()));
    minX.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      minX.setText(newInput);
    });

    // The Y0 value of the min coords.
    TextField minY = new TextField();
    minY.setPromptText("y0");
    minY.setText(String.valueOf(minCoords.getY0()));
    minY.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      minY.setText(newInput);
    });

    //The X1 value of the max coords
    TextField maxX = new TextField();
    maxX.setPromptText("x1");
    maxX.setText(String.valueOf(maxCoords.getX0()));
    maxX.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      maxX.setText(newInput);
    });

    // The Y1 value of the max coords
    TextField maxY = new TextField();
    maxY.setPromptText("y1");
    maxY.setText(String.valueOf(maxCoords.getY0()));
    maxY.textProperty().addListener((observable, oldValue, newValue) -> {
      String newInput = textInputListener(newValue,oldValue);
      maxY.setText(newInput);
    });

    //The real number input slider
    //TODO refactor this and add to css file
    Slider real = new Slider(-1, 1, 0);
    real.setShowTickMarks(true);
    real.setShowTickLabels(true);
    real.setMajorTickUnit(0.25f);
    real.setBlockIncrement(0.1f);
    Label realL = new Label("Value: "+0);
    this.sliderListener(real, realL);

    Slider imag = new Slider(-1, 1, 0);
    imag.setShowTickMarks(true);
    imag.setShowTickLabels(true);
    imag.setMajorTickUnit(0.25f);
    imag.setBlockIncrement(0.1f);

    Label imagL = new Label("Value: "+0);
    this.sliderListener(imag, imagL);

    //Makes sure the steps is an integer
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty()) {
          Integer.parseInt(newValue);
        }
      } catch (NumberFormatException e) {
        // The user have entered a non-integer character, hence just keep the
        // oldValue and ignore the newValue.
        steps.setText(oldValue);
      }
    });

    //Adds text for each text input
    grid.add(new Label("Run Steps:"), 0, 0);
    grid.add(steps, 1, 0);

    grid.add(new Label("  "),0,1);

    grid.add(new Label("Min coords:"), 0, 2);
    grid.add(minX, 1, 2);
    grid.add(new Label(" x0"), 2, 2);
    grid.add(minY, 1, 3);
    grid.add(new Label(" y0"), 2, 3);

    grid.add(new Label("  "),0,4);

    grid.add(new Label("Max coords:"), 0, 5);
    grid.add(maxX, 1, 5);
    grid.add(new Label(" x1"), 2, 5);
    grid.add(maxY, 1, 6);
    grid.add(new Label(" y1"), 2, 6);

    grid.add(new Label("  "),0,7);

    grid.add(new Label("Real number:"), 0, 8);
    grid.add(real, 1, 8);
    grid.add(realL, 2, 8);
    grid.add(new Label("Imag number:"), 0, 9);
    grid.add(imag, 1, 9);
    grid.add(imagL,2,9);

    this.inputBox = new HBox(grid);
    this.inputBox.setBackground(new Background(new BackgroundFill(Color.DARKGREY,null,null)));
  }

  /**
   * Listens to the slider and updates the label with the value of the slider.
   * @param slider the slider to listen to
   * @param label the label to update
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
