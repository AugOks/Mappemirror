package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.ntnu.IDATA2003.mappe5.entity.PixelOutOfBoundsException;
import org.ntnu.IDATA2003.mappe5.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;

/**
 * The GUI for the chaos game app.
 * This class is responsible for creating the GUI for the chaos game app.
 */
public class ChaosGameGui extends Application implements ChaosGameObserver {

  private HBox canvasCenterPane; // The canvas for the fractal
  private InputNode input; // The right pane with the input fields
  private ChaosGameControllerGui controller; // The controller for the chaos game app
  private Scene scene; // The scene for the chaos game app


  //TODO remove this todo
  /**
   * Constructor for the ChaosGameGui.
   */
  public ChaosGameGui(){
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
   * Gets the scene for the chaos game app.
   * @return the scene for the chaos game app.
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Starts the chaos game app with the start screen.
   *
   * @param primaryStage the primary stage for the chaos game app.
   */
  @Override
  public void start(Stage primaryStage) throws Exception  {
    try {

      //Create the root pane and maximize the size to the users screen
      BorderPane root = new BorderPane();
      root.getStyleClass().add("root");
      Screen screen = Screen.getPrimary();

      Rectangle2D bounds = screen.getVisualBounds();
      this.scene = new Scene(root,bounds.getWidth(), bounds.getHeight());
      scene.setFill(Color.BLUE);

      // The top pane with the
      root.setTop(createTopPane());

      // The center pane with the canvas
      HBox centerPane = createCenterPane();
      root.setCenter(centerPane);

      //The right pane
      ScrollPane scrollPane = new ScrollPane();
      scrollPane.setContent(createRightPane());
      root.setRight(scrollPane);

      // The left pane
      VBox leftPane = new VBox();
      leftPane.getStyleClass().add("leftPane");
      root.setLeft(leftPane);

      // Listener for the size of the screen, change the dimensions of fractal
      scene.widthProperty().addListener(event -> {
        controller.changeDescription(controller.getDescription());
      });
      scene.heightProperty().addListener(event -> {
        controller.changeDescription(controller.getDescription());
      });

      scene.getStylesheets().add(getClass().getResource("/css/stylesheet.css").toExternalForm());
      scene.setCursor(Cursor.DEFAULT);
      primaryStage.setTitle("Chaos Game");
      primaryStage.setScene(scene);
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
    createCanvas(controller.getGame(), input.getCurrentSteps());
  }

  /**
   * Creates the right pane with buttons for the different transformations
   * and the input fields for the transformations.
   *
   * @return HBox a HBox containing the components for the right pane.
   */
  private VBox createRightPane() {

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
    });

    //Button for the barnsley fern transformation
    Button barnsleyButton = new Button("Barnsley Fern");
    barnsleyButton.getStyleClass().add("button-rightPane");
    barnsleyButton.setOnAction(e -> controller.createBarnsleyFern());


    HBox buttonBox = new HBox();
    buttonBox.getChildren().addAll(juliaButton,sierpinskiButton, barnsleyButton);
    buttonBox.setSpacing(20);
    buttonBox.setPrefWidth(350);
    buttonBox.setAlignment(Pos.CENTER);
    rightPane.getChildren().addAll(buttonBox, this.input.getInputNode());
    rightPane.setAlignment(Pos.TOP_CENTER);
    rightPane.getStyleClass().add("rightPane");

    return rightPane;
  }

  /**
   * Gets the height of the canvas.
   *
   * @return the height of the canvas.
   */
  public int getHeightForCanvas(){
   BorderPane root =  (BorderPane) scene.getRoot();
   double returnValue =  scene.getHeight() - root.getTop().getBoundsInLocal().getHeight()-10;
   return (int) returnValue-5;
  }

  /**
   * Gets the width of the canvas.
   *
   * @return the width of the canvas.
   */
  public int getWidthForCanvas(){
    double returnValue =  scene.getWidth()*0.70;
    return (int) returnValue;
  }

  /**
   * Creates the top pane with the banner.
   *
   * @return the VBox containing the banner.
   */

  private VBox createTopPane(){
    VBox topPane = new VBox();
    MenuBar menu = createMenuBar();
    VBox bannerPane = new VBox();

    Image banner = new Image(getClass().getResource("/header.png").toExternalForm());

    ImageView bannerView = new ImageView(banner);
    bannerView.getStyleClass().add("header-logo");

    bannerPane.getChildren().addAll(bannerView);
    bannerPane.getStyleClass().add("header");
    topPane.getChildren().addAll(menu, bannerPane);
    return topPane;
  }

  /**
   * Creates the menu bar for the chaos game app.
   * The menu bar contains the file, edit and help menu.
   *
   * @return the menu bar for the chaos game app.
   */
  private MenuBar createMenuBar(){
    MenuBar menu = new MenuBar();

    Menu file = new Menu("File");
    MenuItem openFile = new MenuItem("Open file");
    openFile.setOnAction(e -> controller.openFromFile());

    MenuItem exit = new MenuItem("Exit app");
    exit.setOnAction(e -> {
      this.controller.exitApplication();
    });

    MenuItem saveToFile = new MenuItem("Save file");
    saveToFile.setOnAction(e -> controller.saveToFile());

    file.getItems().addAll(openFile, saveToFile,new SeparatorMenuItem(),exit);

    Menu edit = new Menu("Edit");
    CheckMenuItem showSliders = new CheckMenuItem("Show coords slider");
    edit.getItems().add(showSliders);
    //TODO setting options for having sliders for min max coords.

    Menu help = new Menu("Help");

    Menu danceParty = new Menu("Dance Party");
    MenuItem dance = new MenuItem("Dance");
    dance.setOnAction(e -> controller.danceParty());
    danceParty.getItems().add(dance);

    menu.getMenus().addAll(file,edit,help,danceParty);
    return  menu;
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
  public void createInputNode(ChaosGameDescription description, int stepsInt) {
    if (this.input == null) {
      this.input = new InputNode(description, stepsInt, this.controller);
    } else {
      this.input.changeInputNode(description, stepsInt);
    }

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

    WritableImage writable_image = new WritableImage(getWidthForCanvas(), getHeightForCanvas());
    PixelWriter writer = writable_image.getPixelWriter();

    for (int i = 0; i < index_i; i++) {
      for (int j = 0; j < index_j; j++) {

        if (canvasArray[i][j] != 0) {
          int scalar = canvasArray[i][j]* 255;
          if (canvasArray[i][j]%2 == 0) {
            scalar = canvasArray[i][j]*30999;
          }
          if (canvasArray[i][j] %3 == 0) {
            scalar = canvasArray[i][j]*51343;
          }
          if (canvasArray[i][j] %5 == 0) {
            scalar = canvasArray[i][j]* 102345;
          }
            if (canvasArray[i][j] %7 == 0) {
                scalar = canvasArray[i][j]* 540000;
            }
          String hexColor = String.format("#%06X", (0xFFFFFF & scalar));
          Color c = Color.valueOf(hexColor);

          writer.setColor(j, i, c);
        }
      }
    }
    ImageView fractal = new ImageView(writable_image);
    this.canvasCenterPane.getChildren().clear();
    this.canvasCenterPane.getChildren().add(new HBox(fractal));
  }


}
