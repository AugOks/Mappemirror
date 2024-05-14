package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameObserver;
import org.ntnu.IDATA2003.mappe5.logic.ColorChoiceDialog;

/**
 * The GUI for the chaos game app.
 * This class is responsible for creating the GUI for the chaos game app.
 */
public class ChaosGameGui extends Application implements ChaosGameObserver {

  private HBox canvasCenterPane; // The canvas for the fractal
  private InputNode input; // The right pane with the input fields
  private ChaosGameControllerGui controller; // The controller for the chaos game app
  private Scene scene; // The scene for the chaos game app
  private ScrollPane scrollPane = new ScrollPane();
  private Color colorChoice = null;



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

      //Get the screen size
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
      this.scrollPane.setContent(createRightPane());
      root.setRight(this.scrollPane);
      root.getRight().getStyleClass().add("rightPane");
      this.scrollPane.setMinHeight(getHeightForCanvas());

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
      primaryStage.setMaximized(true);
      primaryStage.setScene(scene);
      primaryStage.getIcons().add(new Image(getClass().getResource("/iconChaosGame.png").toExternalForm()));
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
    rightPane.getStyleClass().add("rightPane");

    // Button for the julia transformation
    Button juliaButton = new Button("Julia Set");
    juliaButton.getStyleClass().add("button-rightPane");
    juliaButton.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.JULIA));

    //Button for the sierpinski transformation
    Button sierpinskiButton = new Button("Sierpinski");
    sierpinskiButton.getStyleClass().add("button-rightPane");
    sierpinskiButton.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.SIERPINSKI));

    //Button for the barnsley fern transformation
    Button barnsleyButton = new Button("Barnsley Fern");
    barnsleyButton.getStyleClass().add("button-rightPane");
    barnsleyButton.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.BARNSLEY));

    // Add the buttons and the input fields to the right pane
    HBox buttonBox = new HBox();
    buttonBox.getChildren().addAll(juliaButton, sierpinskiButton, barnsleyButton);
    buttonBox.setSpacing(20);
    buttonBox.setPrefWidth(350);
    buttonBox.setAlignment(Pos.CENTER);
    rightPane.getChildren().addAll(buttonBox, this.input.getInputNode());
    rightPane.setAlignment(Pos.TOP_CENTER);

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
    //TODO style the menu bar better than this...

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

    Menu preMade = new Menu("Pre-made");

    MenuItem julia = new MenuItem("Julia");
    julia.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.JULIA));

    MenuItem sierpinski = new MenuItem("Sierpinski");
    sierpinski.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.SIERPINSKI));

    MenuItem barnsleyFern = new MenuItem("Barnsley Fern");
    barnsleyFern.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.BARNSLEY));

    MenuItem spiderweb = new MenuItem("Spiderweb");
    spiderweb.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.SPIDERWEB));

    MenuItem square = new MenuItem("Square");
    square.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.SQUARE));

    MenuItem pentagon = new MenuItem("Pentagon");
    pentagon.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.PENTAGON));

    MenuItem kochCurve = new MenuItem("Koch Curve");
    kochCurve.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.KOCHCURVE));

    MenuItem dragonFire = new MenuItem("Dragon Fire");
    dragonFire.setOnAction(e -> controller.createFractal(
        ChaosGameDescriptionFactory.Fractals.DRAGONFIRE));

    preMade.getItems().addAll(julia,sierpinski,barnsleyFern,spiderweb,square,pentagon,kochCurve,
                              dragonFire);

    Menu edit = new Menu("Edit");
    CheckMenuItem showSliders = new CheckMenuItem("Show coords slider");
    edit.getItems().add(showSliders);
    showSliders.setOnAction(e -> input.createInputNode(controller.getDescription(),
        showSliders.isSelected()));
    MenuItem colorPicker = new MenuItem("Color picker");
    colorPicker.setOnAction(e -> {
      ColorChoiceDialog colorChoiceDialog = new ColorChoiceDialog();
      if (colorChoiceDialog.showAndWait().isPresent()){
        this.colorChoice = colorChoiceDialog.getResult();
      }else {
        this.colorChoice = null;
      }
    });
    edit.getItems().add(colorPicker);

    Menu help = new Menu("Help");
    MenuItem about = new MenuItem("About");
    about.setOnAction(e -> controller.showAbout());
    help.getItems().add(about);

    Menu danceParty = new Menu("Dance Party");
    MenuItem dance = new MenuItem("Dance");
    dance.setOnAction(e -> controller.danceParty());
    danceParty.getItems().add(dance);

    menu.getMenus().addAll(file, edit, preMade, danceParty, help);
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
    this.setZoomScrollEvent(this.canvasCenterPane);
    this.canvasCenterPane.getStyleClass().add("centerPane");

    this.controller.createFractal(ChaosGameDescriptionFactory.Fractals.JULIA);

    this.canvasCenterPane.setAlignment(Pos.CENTER);
    return canvasCenterPane;
  }

  /**
   * Sets the zoom scroll event for the canvas.
   * The zoom scroll event will zoom in and out of the canvas in the four quadrants (-1,1), (-1,-1)
   * (1,-1), (1,1) in an x-y plane with the origin in the middle.
   *
   * @param node the node to set the zoom scroll event for.
   */
  private void setZoomScrollEvent(Node node){
    node.setOnScroll(event -> {

      double mouseYCoords = event.getY()/(getHeightForCanvas());
      double mouseXCoords = (event.getX()/(getWidthForCanvas()));
      int direction = event.getDeltaY() > 0 ? -1 : 1;
      double zoomFactor = Math.pow(1.1, direction);
      Vector2D maxCoords = this.controller.getDescription().getMaxCoords();
      Vector2D minCoords = this.controller.getDescription().getMinCoords();
      // Zoom in on the top left quadrant (-1,1)
      if ( mouseXCoords < 0.5 && mouseYCoords < 0.5 ) {
        maxCoords.setX0(maxCoords.getX0() + (minCoords.getX0() - maxCoords.getX0())*(1-zoomFactor));
        minCoords.setY0(minCoords.getY0() + (maxCoords.getY0() - minCoords.getY0())*(1-zoomFactor));
      }
      //Zoom in on the bottom left quadrant(-1,-1)
      else if (mouseXCoords < 0.5 && mouseYCoords > 0.5 ) {
        maxCoords.setY0(maxCoords.getY0() + (minCoords.getY0() - maxCoords.getY0())*(1-zoomFactor));
        maxCoords.setX0(maxCoords.getX0() + (minCoords.getX0() - maxCoords.getX0())*(1-zoomFactor));
      }
      //Zoom in on the top right quadrant(1,1)
      else if ( mouseXCoords > 0.5 && mouseYCoords < 0.5) {
        minCoords.setX0(minCoords.getX0() + (maxCoords.getX0() - minCoords.getX0())*(1-zoomFactor));
        minCoords.setY0(minCoords.getY0() + (maxCoords.getY0() - minCoords.getY0())*(1-zoomFactor));
      }
      //Zoom in on the bottom right quadrant (1,-1)
     else if (mouseXCoords > 0.5 && mouseYCoords > 0.5) {
        maxCoords.setY0(maxCoords.getY0() + (minCoords.getY0() - maxCoords.getY0())*(1-zoomFactor));
        minCoords.setX0(minCoords.getX0() + (maxCoords.getX0() - minCoords.getX0())*(1-zoomFactor));
      }


      controller.changeCoords(minCoords, maxCoords);
    });
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
    int indexI = canvas.getHeight();
    int indexJ = canvas.getWidth();
    int[][] canvasArray =  canvas.getCanvasArray();
      game.runSteps(steps);
    WritableImage writableImage = new WritableImage(getWidthForCanvas(), getHeightForCanvas());
    PixelWriter writer = writableImage.getPixelWriter();
    Color c;
    if (colorChoice == null) {
      for (int i = 0; i < indexI; i++) {
        for (int j = 0; j < indexJ; j++) {
          if (canvasArray[i][j] != 0) {
            c = Color.rgb(canvasArray[i][j], 0, 0, 1);
            writer.setColor(j, i, c);
          }
        }
      }
    }else {
      for (int i = 0; i < indexI; i++) {
        for (int j = 0; j < indexJ; j++) {
          if (canvasArray[i][j] != 0) {
            writer.setColor(j, i, colorChoice);
          }
        }
      }
    }

    ImageView fractal = new ImageView(writableImage);
    this.canvasCenterPane.getChildren().clear();
    this.canvasCenterPane.getChildren().add(new HBox(fractal));
  }


}
