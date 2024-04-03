package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
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

  private VBox createCenterPane(){
    VBox centerPane = new VBox();

    centerPane.getStyleClass().add("centerPane");
    centerPane.getChildren().add(createCanvas());
    return centerPane;
  }

  private ImageView createCanvas(){
    ChaosGameDescription description  = controller.createBarnsleyFern();
    ChaosGame game = new ChaosGame(description,1000, 500);
    ChaosCanvas canvas = game.getCanvas();
    int[][] canvasArray =  canvas.getCanvasArray();
    game.runSteps(10000);
    WritableImage writable_image = new WritableImage(500, 1000);
    /*
    GraphicsContext graphics_context = canvasView.getGraphicsContext2D();
    graphics_context.setFill(Color.RED);

    for (int i = 0; i < 10; i++) {
      writable_image.getPixelWriter().setPixels(i, 0, canvas.getHeight()-1, canvas.getWidth()-1, PixelFormat.getIntArgbInstance(),
                                                  canvasArray[i], 0, canvas.getWidth()-1);
    }
         */
    ImageView fractal = new ImageView(writable_image);

    return fractal;
  }
}
