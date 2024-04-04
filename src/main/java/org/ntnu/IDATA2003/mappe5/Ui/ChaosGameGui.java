package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ntnu.IDATA2003.mappe5.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;
import javafx.scene.canvas.*;

public class ChaosGameGui extends Application implements ChaosGameObserver {

  private Canvas canvasView;
  private ChaosGameController controller;

  public ChaosGameGui(){
    controller = new ChaosGameController(this);
  }

  public static void mainApp(String[] args) {

    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception  {
    try {

      BorderPane root = new BorderPane();

      // The header for chaos game
      root.setTop(createTopPane());

      //The right pane with all
      root.setRight(createRightPane());

      // The center pane with the canvas
      root.setCenter(createCenterPane());

      Scene scene = new Scene(root, 400, 500);
      scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
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
  private HBox createRightPane() {

    HBox rightPane = new HBox();
    GridPane grid = new GridPane();

    // Button for the julia transformation
    Button juliaButton = new Button("Julia Transformation");
    juliaButton.getStyleClass().add("button-rightPane");
    juliaButton.setOnAction(e -> System.out.println("Julia Transformation"));

    //Button for the affine transformation
    Button affineButton = new Button("Affine Transformation");
    affineButton.getStyleClass().add("button-rightPane");
    affineButton.setOnAction(e -> System.out.println("Affine Transformation"));

    grid.add(juliaButton, 1, 1);
    grid.add(affineButton, 1, 4);

    rightPane.getChildren().add(grid);
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
    HBox centerPane = new HBox();

    centerPane.getStyleClass().add("centerPane");
    centerPane.getChildren().add(createCanvas());
    centerPane.setAlignment(Pos.CENTER);
    return centerPane;
  }

  private HBox createCanvas(){

    ChaosGameDescription description  = controller.createSierpinksi();
    ChaosGame game = new ChaosGame(description,500, 1000);
    ChaosCanvas canvas = game.getCanvas();
    int index_i = canvas.getHeight();
    int index_j = canvas.getWidth();
    int[][] canvasArray =  canvas.getCanvasArray();
    game.runSteps(10000000);

    WritableImage writable_image = new WritableImage(1000, 500);
    PixelWriter writer = writable_image.getPixelWriter();

    for (int i = 0; i < index_i; i++) {
      for (int j = 0; j < index_j; j++) {
        if (canvasArray[i][j] == 1) {
          writer.setColor(j, i, Color.BLACK);
        }
      }
    }
    ImageView fractal = new ImageView(writable_image);
    return new HBox(fractal);
  }
}
