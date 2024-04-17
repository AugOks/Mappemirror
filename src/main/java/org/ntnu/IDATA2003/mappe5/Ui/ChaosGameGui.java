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
    createInputBoxJulia(game, steps);
    //----
    this.scene.setCursor(Cursor.WAIT);
    //----
    ChaosCanvas canvas = game.getCanvas();
    int index_i = canvas.getHeight();
    int index_j = canvas.getWidth();
    int[][] canvasArray =  canvas.getCanvasArray();
    game.runSteps(steps);

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
    //----
    this.scene.setCursor(Cursor.DEFAULT);
    //----
  }

  private void createInputBoxJulia(ChaosGame game, int stepsInt){
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
      try {
        if (!newValue.isEmpty()) {
          int newValueInt = Integer.parseInt(newValue);
          if (newValueInt<=0){
            throw new NumberFormatException("Number is negative");
            //TODO pop up warning?
          }
        }
      } catch (NumberFormatException e) {
        // The user have entered a non-integer character, hence just keep the
        // oldValue and ignore the newValue.
        steps.setText(oldValue);
      }
    });

    // X0 value of the min coords
    TextField minX = new TextField();
    minX.setPromptText("x0");
    minX.setText(String.valueOf(minCoords.getX0()));
    minX.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty()) {
          Integer.parseInt(newValue);
        }
        //TODO add gard for incorrect input

      } catch (NumberFormatException e) {
        // The user have entered a non-integer character, hence just keep the
        // oldValue and ignore the newValue.
        minX.setText(oldValue);
      }
    });

    // The Y0 value of the min coords.
    TextField minY = new TextField();
    minY.setPromptText("y0");
    minY.setText(String.valueOf(minCoords.getY0()));
    minY.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty()) {
          int newValueInt = Integer.parseInt(newValue);
        }
        //TODO add gard for incorrect input

      } catch (NumberFormatException e) {
        // The user have entered a non-integer character, hence just keep the
        // oldValue and ignore the newValue.
        minY.setText(oldValue);
      }
    });

    //The X1 value of the max coords
    TextField maxX = new TextField();
    maxX.setPromptText("x1");
    maxX.setText(String.valueOf(maxCoords.getX0()));
    maxX.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty()) {
          int newValueInt = Integer.parseInt(newValue);
        }
        //TODO add gard for incorrect input

      } catch (NumberFormatException e) {
        // The user have entered a non-integer character, hence just keep the
        // oldValue and ignore the newValue.
        maxX.setText(oldValue);
      }
    });

    // The Y1 value of the max coords
    TextField maxY = new TextField();
    maxY.setPromptText("y1");
    maxY.setText(String.valueOf(maxCoords.getY0()));
    maxY.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        if (!newValue.isEmpty()) {
          int newValueInt = Integer.parseInt(newValue);
        }
        //TODO add gard for incorrect input

      } catch (NumberFormatException e) {
        // The user have entered a non-integer character, hence just keep the
        // oldValue and ignore the newValue.
        maxY.setText(oldValue);
      }
    });

    //The real number input slider
    Slider real = new Slider(-1, 1, 0);
    real.setShowTickMarks(true);
    real.setShowTickLabels(true);
    real.setMajorTickUnit(0.25f);
    real.setBlockIncrement(0.1f);

    Label realL = new Label("Value: "+0);

    // Adding Listener to value property.
    //TODO add the source webpage you found this from
    real.valueProperty().addListener(
        new ChangeListener<Number>() {
          public void changed(ObservableValue<? extends Number >
                                  observable, Number oldValue, Number newValue)
          {
            realL.setText("Value: " + newValue);
          }
        });

    //
    Slider imag = new Slider(-1, 1, 0);
    imag.setShowTickMarks(true);
    imag.setShowTickLabels(true);
    imag.setMajorTickUnit(0.25f);
    imag.setBlockIncrement(0.1f);

    Label imagL = new Label("Value: "+0);

    // Adding Listener to value property.
    imag.valueProperty().addListener(
        new ChangeListener<Number>() {
          public void changed(ObservableValue<? extends Number >
                                  observable, Number oldValue, Number newValue)
          {
            imagL.setText("Value: " + newValue);
          }
        });

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

}
