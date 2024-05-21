package org.ntnu.IDATA2003.mappe5.view;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import org.ntnu.IDATA2003.mappe5.controller.ChaosGameControllerGui;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.ResourceNotFoundException;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescriptionFactory;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameObserver;

/**
 * The GUI for the chaos game app.
 * This class is responsible for creating the GUI for the chaos game app.
 */
public class ChaosGameGui extends Application implements ChaosGameObserver {

  private final ChaosGameControllerGui controller; // The controller for the chaos game app
  private final ScrollPane scrollPane = new ScrollPane(); // The scroll pane for the right pane
  private HBox canvasCenterPane; // The canvas for the fractal
  private InputNode input; // The right pane with the input fields
  private Scene scene; // The scene for the chaos game app
  private Color colorChoice = null; // The color choice for the canvas
  private boolean zoomScrollOnOff = false; // The zoom scroll for the canvas


  /**
   * Constructor for the ChaosGameGui.
   */
  public ChaosGameGui() {
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
   *
   * @return the scene for the chaos game app.
   */
  public Scene getScene() {
    return scene;
  }

  /**
   * Starts the chaos game app with the start screen.
   *
   * @param primaryStage the primary stage for the chaos game app.
   * @throws Exception                 if the app fails to start.
   * @throws ResourceNotFoundException if the app fails to fetch a resource.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    try {

      //Create the root pane and maximize the size to the users screen
      BorderPane root = new BorderPane();
      root.getStyleClass().add("root");

      //Get the screen size
      Screen screen = Screen.getPrimary();

      Rectangle2D bounds = screen.getVisualBounds();
      this.scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
      scene.setFill(Color.BLUE);

      // The top pane with the
      root.setTop(createTopPane());

      // The center pane with the canvas
      HBox centerPane = createCenterPane();
      root.setCenter(centerPane);

      //The right pane
      this.scrollPane.setContent(createRightPane());
      this.scrollPane.getStyleClass().add("scroll-pane");
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

      scene.getStylesheets().add(
          Objects.requireNonNull(getClass().getResource("/css/stylesheet.css"))
              .toExternalForm());

      scene.setCursor(Cursor.DEFAULT);
      primaryStage.setTitle("Chaos Game");
      primaryStage.setMaximized(true);
      primaryStage.setScene(scene);
      primaryStage.getIcons().add(new Image(
          Objects.requireNonNull(getClass().getResource("/images/iconChaosGame.png"))
              .toExternalForm()));
      primaryStage.show();
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException("failed to fetch a resource");
    } catch (Exception e) {
      Files.createDirectories(Path.of("logs"));
      Handler handler = new FileHandler("logs/ChaosGame.log");
      Logger logger = Logger.getLogger("ChaosGame");
      Logger.getLogger("ChaosGame").addHandler(handler);
      logger.log(Level.SEVERE, e.getMessage());

    }
  }

  /**
   * Method for receiving changes made in chaos game based on observer pattern.
   */
  public void update() {
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
    rightPane.getChildren().addAll(this.input.getInputNode());
    rightPane.setAlignment(Pos.TOP_CENTER);

    return rightPane;
  }

  /**
   * Gets the height of the canvas.
   *
   * @return the height of the canvas.
   */
  public int getHeightForCanvas() {
    BorderPane root = (BorderPane) scene.getRoot();
    double returnValue = scene.getHeight() - root.getTop().getBoundsInLocal().getHeight() - 10;
    return (int) returnValue - 5;
  }

  /**
   * Gets the width of the canvas.
   *
   * @return the width of the canvas.
   */
  public int getWidthForCanvas() {
    double returnValue = scene.getWidth() * 0.70;
    return (int) returnValue;
  }

  /**
   * Creates the top pane with the banner.
   *
   * @return the VBox containing the banner.
   * @throws ResourceNotFoundException if the banner is not found.
   */

  private VBox createTopPane() {


    Image banner = null;
    try {
      banner = new Image(Objects.requireNonNull(getClass()
              .getResource("/images/header.png"))
          .toExternalForm());
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException("Could not find the banner");
    }
    VBox topPane = new VBox();
    VBox bannerPane = new VBox();
    ImageView bannerView = new ImageView(banner);
    bannerView.getStyleClass().add("header-logo");
    bannerPane.getChildren().addAll(bannerView);
    bannerPane.getStyleClass().add("header");
    topPane.getChildren().addAll(createMenuBar(), bannerPane);
    return topPane;
  }

  /**
   * Creates the menu bar for the chaos game app.
   * The menu bar contains the file, edit and help menu.
   *
   * @return the menu bar for the chaos game app.
   */
  private MenuBar createMenuBar() {

    MenuItem openFile = new MenuItem("Open file");
    openFile.setOnAction(e -> controller.openFromFile());

    MenuItem exit = new MenuItem("Exit app");
    exit.setOnAction(e -> {
      this.controller.exitApplication();
    });
    MenuItem saveToFile = new MenuItem("Save file");
    saveToFile.setOnAction(e -> controller.saveToFile());

    MenuItem newFractal = new MenuItem("New fractal");
    newFractal.setOnAction(e -> {
      controller.createBlankFractal();
    });
    Menu file = new Menu("File");
    file.getItems().addAll(openFile, saveToFile, newFractal, new SeparatorMenuItem(), exit);


    MenuItem julia = new MenuItem("Julia");
    julia.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.JULIA));

    MenuItem sierpinski = new MenuItem("Sierpinski");
    sierpinski.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.SIERPINSKI));

    MenuItem barnsleyFern = new MenuItem("Barnsley Fern");
    barnsleyFern.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.BARNSLEY));

    MenuItem spiderweb = new MenuItem("Spiderweb");
    spiderweb.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.SPIDERWEB));

    MenuItem square = new MenuItem("Square");
    square.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.SQUARE));

    MenuItem pentagon = new MenuItem("Pentagon");
    pentagon.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.PENTAGON));

    MenuItem kochCurve = new MenuItem("Koch Curve");
    kochCurve.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.KOCHCURVE));

    MenuItem dragonFire = new MenuItem("Dragon Fire");
    dragonFire.setOnAction(e -> controller.createNewFractalDescription(
        ChaosGameDescriptionFactory.Fractals.DRAGONFIRE));

    Menu preMade = new Menu("Pre-made");
    preMade.getItems()
        .addAll(julia, sierpinski, barnsleyFern, spiderweb, square, pentagon, kochCurve,
            dragonFire);


    CheckMenuItem showSliders = new CheckMenuItem("Show coords slider");
    showSliders.setOnAction(e -> input.createInputNode(controller.getDescription(),
        showSliders.isSelected()));

    MenuItem colorPicker = new MenuItem("Color picker");
    colorPicker.setOnAction(e -> {
      ChaosGameDialogHandler handler = ChaosGameDialogHandler.getInstance();
      ChaosGameDialogHandler.ColorChoiceDialog colorChoiceDialog = handler.getColorChoiceDialog();
      if (colorChoiceDialog.showAndWait().isPresent()) {
        this.colorChoice = colorChoiceDialog.getResult();
      } else {
        this.colorChoice = null;
      }
      controller.changeDescription(controller.getDescription());
    });

    CheckMenuItem zoomScroll = new CheckMenuItem("Zoom scroll");
    zoomScroll.setOnAction(e -> {
      this.zoomScrollOnOff = zoomScroll.isSelected();
    });
    Menu edit = new Menu("Edit");
    edit.getItems().addAll(showSliders, zoomScroll, colorPicker);


    MenuItem help = new MenuItem("FAQ");
    help.setOnAction(e -> controller.showHelp());
    MenuItem about = new MenuItem("About");
    about.setOnAction(e -> controller.showAbout());

    Menu info = new Menu("Info");
    info.getItems().addAll(help, about);

    MenuItem dance = new MenuItem("Dance Party");
    dance.setOnAction(e -> controller.danceParty());
    MenuItem juliaSlide = new MenuItem("Julia Slide");
    juliaSlide.setOnAction(e -> {
      controller.createNewFractalDescription(ChaosGameDescriptionFactory.Fractals.JULIA);
      controller.slideIntoJuliaDms();
    });

    MenuItem wackySlide = new MenuItem("Wacky Slide");
    wackySlide.setOnAction(e -> {
      controller.createNewFractalDescription(ChaosGameDescriptionFactory.Fractals.JULIA);
      controller.wackySliderAnimation();
    });
    Menu animations = new Menu("Animations");
    animations.getItems().addAll(dance, juliaSlide, wackySlide);
    MenuBar menu = new MenuBar();
    menu.getMenus().addAll(file, edit, preMade, animations, info);
    menu.getStyleClass().add("menu-bar");
    return menu;
  }

  /**
   * Creates the center pane with the canvas.
   * Starts the application with an already remade fractal.
   *
   * @return the HBox containing the main canvas.
   */

  private HBox createCenterPane() {
    this.canvasCenterPane = new HBox();
    this.setZoomScrollEvent(this.canvasCenterPane);
    this.canvasCenterPane.getStyleClass().add("centerPane");

    this.controller.createNewFractalDescription(ChaosGameDescriptionFactory.Fractals.JULIA);

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
  private void setZoomScrollEvent(Node node) {
    node.setOnScroll(event -> {
      if (!zoomScrollOnOff) {
        return;
      }
      double mouseYaxisCoords = event.getY() / (getHeightForCanvas());
      double mouseXaxisCoords = (event.getX() / (getWidthForCanvas()));
      int direction = event.getDeltaY() > 0 ? -1 : 1;
      double zoomFactor = Math.pow(1.1, direction);
      Vector2D maxCoords = this.controller.getDescription().getMaxCoords();
      Vector2D minCoords = this.controller.getDescription().getMinCoords();
      // determine which quadrant to zoom in on.
      if (mouseXaxisCoords < 0.5 && mouseYaxisCoords < 0.5) {
        maxCoords.setX0(
            maxCoords.getX0() + (minCoords.getX0() - maxCoords.getX0()) * (1 - zoomFactor));
        minCoords.setY0(
            minCoords.getY0() + (maxCoords.getY0() - minCoords.getY0()) * (1 - zoomFactor));
      } else if (mouseXaxisCoords < 0.5 && mouseYaxisCoords > 0.5) {
        maxCoords.setY0(
            maxCoords.getY0() + (minCoords.getY0() - maxCoords.getY0()) * (1 - zoomFactor));
        maxCoords.setX0(
            maxCoords.getX0() + (minCoords.getX0() - maxCoords.getX0()) * (1 - zoomFactor));
      } else if (mouseXaxisCoords > 0.5 && mouseYaxisCoords < 0.5) {
        minCoords.setX0(
            minCoords.getX0() + (maxCoords.getX0() - minCoords.getX0()) * (1 - zoomFactor));
        minCoords.setY0(
            minCoords.getY0() + (maxCoords.getY0() - minCoords.getY0()) * (1 - zoomFactor));
      } else if (mouseXaxisCoords > 0.5 && mouseYaxisCoords > 0.5) {
        maxCoords.setY0(
            maxCoords.getY0() + (minCoords.getY0() - maxCoords.getY0()) * (1 - zoomFactor));
        minCoords.setX0(
            minCoords.getX0() + (maxCoords.getX0() - minCoords.getX0()) * (1 - zoomFactor));
      }


      controller.changeCoords(minCoords, maxCoords);
    });
  }

  /**
   * Creates the input box for the chaos game.
   * It contains the input fields that is the same for both the julia and affine transformation.
   *
   * @param description the description of the chaos game.
   * @param stepsInt    the amount of steps to run the chaos game.
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
   * @param game  the chaos game to create the canvas for.
   * @param steps the amount of steps to run the chaos game.
   */
  public void createCanvas(ChaosGame game, int steps) {

    ChaosCanvas canvas = game.getCanvas();
    int indexI = canvas.getHeight();
    int indexJ = canvas.getWidth();
    int[][] canvasArray = canvas.getCanvasArray();
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
    } else {
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

  public void setColorChoice(Color colorChoice) {
    this.colorChoice = colorChoice;
  }


}
