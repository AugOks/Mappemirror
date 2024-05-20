package org.ntnu.IDATA2003.mappe5.view;

import java.io.File;
import java.util.Objects;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.ResourceNotFoundException;

/**
 * Class for creating dialogs in the ChaosGameGui class.
 * <p>
 * Made this is a singleton class to avoid creating multiple instances of the class.
 * Some would say Singletons should be avoided for anything but the most niche
 * use cases. from what we can see If we were to adhere to the concerns of others there would be no
 * class in this project that would qualify by the strictest definition as a singleton.
 * Despite all this we still wanted to show that we can implement a singleton,
 * even though it might not be the most ideal situation.
 */
public class ChaosGameDialogHandler {

  private static ChaosGameDialogHandler instance = null;
  private int numberOfTransforms;

  /**
   * Constructor for the ChaosGameDialogHandler class.
   */

  private ChaosGameDialogHandler() {
  }

  /**
   * Method for getting the instance of the ChaosGameDialogHandler class.
   *
   * @return the instance of the ChaosGameDialogHandler class.
   */
  public static synchronized ChaosGameDialogHandler getInstance() {
    if (instance == null) {
      instance = new ChaosGameDialogHandler();
    }
    return instance;
  }

  /**
   * Method for creating an exit dialog.
   *
   * @return true if the user confirms the exit, false otherwise.
   */
  public boolean exitDialog() {
    boolean exitConfirmation = false;
    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
    Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION,
        "  ",
        yes,
        no);
    exitAlert.setHeaderText("Are you sure you want to exit the application?");
    exitAlert.showAndWait();
    if (exitAlert.getResult() == yes) {
      exitConfirmation = true;
    }
    return exitConfirmation;
  }

  /**
   * Method for creating a dialog for the user to choose whether to have a dance party.
   *
   * @return true if the user confirms the dance party, false otherwise.
   * @throws ResourceNotFoundException if the icon for the dialog is not found.
   */
  public boolean dancePartyDialog() {
    boolean dancePartyConfirmation = false;
    try {
      ButtonType yesD = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
      ButtonType noD = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
      Alert alertDanceParty = new Alert(Alert.AlertType.CONFIRMATION, "  ", yesD, noD);
      alertDanceParty.setTitle("Dance party");
      alertDanceParty.setHeaderText("Do you want to have a dance party?");
      alertDanceParty.setContentText("Warning: This dance party contain flashing lights and catchy"
                                     +" music!");
      final ImageView dialogHeaderIcon;
      dialogHeaderIcon = new ImageView(Objects.requireNonNull(getClass()
              .getResource("/images/discoBall.png"))
          .toExternalForm());
      dialogHeaderIcon.setFitHeight(48);
      dialogHeaderIcon.setFitWidth(48);
      alertDanceParty.getDialogPane().setGraphic(dialogHeaderIcon);
      alertDanceParty.showAndWait();

      if (alertDanceParty.getResult() == yesD) {
        dancePartyConfirmation = true;
      }
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException("Could not find the icon");
    }
    return dancePartyConfirmation;
  }

  /**
   * Method for creating an about ChaosGame dialog.
   *
   * @throws ResourceNotFoundException if the icon for the dialog is not found.
   */
  public void showAboutDialog() {

    Alert about = new Alert(Alert.AlertType.INFORMATION);
    about.setTitle("About");
    about.setHeaderText("Chaos Game");
    setIconToCgDialog(about);
    about.getDialogPane().setMinSize(700, 400);


    Text text1 = new Text("Chaos game is one of many established methods for generating fractals "
        + "online.\n"
        + "A fractal can be defined as complex pattern that are self-similar "
        + "across different scales. In other word, beautiful figure \n"
        + "that will repeat the same pattern "
        + "regardless of how much you zoom and your viewpoint.\n");
    Text text2 = new Text("The application creates fractals choosing a initial starting point, "
        + "and so transforming that point using \n"
        + " a predefined mathematical operation."
        + "Iteratively creating a long sequence of points later on drawn on a "
        + "canvas to create a fractal.\n"
        + "The most known fractals includes the "
        + "Julia set and the Sierpinski triangle. \n");
    Text text3 = new Text("This particular Chaos Game was created by students as NTNU as a part "
        + " of their exam for the course IDATx2024. \n"
        + "The main objective was to show competence in newly learned knowledge "
        + "such as design patterns,\n"
        + " Java inheritance and polymorphism, and creating a GUI using JavaFX.");

    text1.setFont(Font.font("open sans", FontWeight.MEDIUM, FontPosture.REGULAR, 14));
    text2.setFont(Font.font("open sans", FontWeight.MEDIUM, FontPosture.REGULAR, 14));
    text3.setFont(Font.font("open sans", FontWeight.MEDIUM, FontPosture.REGULAR, 14));
    String header = "Welcome to chaos game!";
    about.setHeaderText(header);

    GridPane grid = new GridPane();
    grid.add(text1, 0, 1);
    grid.add(text2, 0, 2);
    grid.add(text3, 0, 3);

    VBox dialogPaneContent = new VBox();
    dialogPaneContent.getChildren().add(grid);
    about.getDialogPane().setContent(dialogPaneContent);
    about.showAndWait();
  }

  public void showHelpDialog() {
    Alert help = new Alert(Alert.AlertType.INFORMATION);
    help.setTitle("FAQ");
    help.setHeaderText("FAQ to Chaos Game");
    setIconToCgDialog(help);
    help.getDialogPane().setMinSize(700, 400);

    Text question1 = new Text("What is Julia Transform? \n");
    Text question2 = new Text("What is Affine Transform? \n");
    Text question3 = new Text("How does transforming a point work? \n");

    Text answer1 =new Text("Julia set in one of the types of fractals chaos game can generate.\n"
                         + "The inputs values required for the Julia set is a complex number,"
                         + " which consist of a real and imaginary number.\nThe complex number is "
                         + "defined as Z in the formula.\nJulia has the total of two types of"
                         + " transformation, where the difference is sign of the start of "
                         + "the formula:");

    Text answer2 = new Text("Affine transformation is another type of fractal this chaos game"
                          + " can generate.\nOne transformation is defined by the multiplication"
                          + " of a matrix with 2x2 values and one 2D vector as shown \non the"
                          + " formula below and then the addition of the current point.\n");

    Text answer3 = new Text("For each steps we run, one point will be transformed to another"
                          + " point based on one of different transforms defined. \n"
                          + "These transformations can be both be affine and Julia."
                          + " This will iterate as many times as run steps is defined by the "
                          + "user.");

    question1.setFont(Font.font("open sans", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
    question2.setFont(Font.font("open sans", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
    question3.setFont(Font.font("open sans", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 16));
    answer1.setFont(Font.font("open sans", FontWeight.MEDIUM, FontPosture.REGULAR, 14));
    answer2.setFont(Font.font("open sans", FontWeight.MEDIUM, FontPosture.REGULAR, 14));
    answer3.setFont(Font.font("open sans", FontWeight.MEDIUM, FontPosture.REGULAR, 14));

    ImageView juliaImage = new ImageView(Objects.requireNonNull(getClass()
            .getResource("/images/JuliaTransform.png")).toExternalForm());
    ImageView affineImage1 = new ImageView(Objects.requireNonNull(getClass()
            .getResource("/images/AffineTransform.png")).toExternalForm());
    ImageView affineImage2 = new ImageView(Objects.requireNonNull(getClass()
            .getResource("/images/AffineTransform2.png")).toExternalForm());

    String header = "FQA!";
    help.setHeaderText(header);

    GridPane grid = new GridPane();
    grid.add(question1, 0, 1);
    grid.add(answer1, 0, 2);
    grid.add(juliaImage, 0, 3);

    grid.add(new Separator(Orientation.HORIZONTAL), 0,4);
    grid.add(question2, 0, 5);
    grid.add(answer2, 0, 6);
    GridPane affineGrid = new GridPane();
    affineGrid.add(affineImage1, 0, 0);
    affineGrid.add(affineImage2,0,1);
    grid.add(affineGrid, 0, 7);

    grid.add(new Separator(Orientation.HORIZONTAL), 0,8);
    grid.add(question3, 0, 9);
    grid.add(answer3, 0, 10);

    VBox dialogPaneContent = new VBox();
    dialogPaneContent.getChildren().add(grid);
    help.getDialogPane().setContent(dialogPaneContent);
    help.showAndWait();
  }

  /**
   * Method for creating a new fractal dialog.
   *
   * @return the number of transforms for the new fractal.
   */
  public int createNewFractalDialog() {

    Alert createNewFractal = new Alert(Alert.AlertType.CONFIRMATION);
    createNewFractal.getDialogPane().setMinSize(300, 300);
    setIconToCgDialog(createNewFractal);
    createNewFractal.setTitle("Create new fractal");


    final ToggleGroup group = new ToggleGroup();
    RadioButton radioButtonJulia = new RadioButton("Julia Set");
    RadioButton radioButtonAffine = new RadioButton("Affine Transformation");
    radioButtonJulia.setToggleGroup(group);
    radioButtonAffine.setToggleGroup(group);
    radioButtonAffine.isSelected();

    createNewFractal.setTitle("New fractal");
    createNewFractal.setHeaderText("Create a new fractal");

    GridPane grid = new GridPane();
    grid.add(new Label("Choose the type of fractal you want to create"), 0, 0);
    grid.add(new Label(""), 0, 1);
    grid.add(radioButtonJulia, 0, 2);
    grid.add(radioButtonAffine, 0, 3);

    Spinner<Integer> spinner = new Spinner<>();
    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20));
    spinner.setEditable(true);

    radioButtonJulia.setOnAction(event -> {
      if (radioButtonJulia.isSelected() && (grid.getChildren().contains(spinner))) {
        removeNodesFromGrid(grid);

      }
    });
    Separator line = new Separator(Orientation.HORIZONTAL);
    radioButtonAffine.setOnAction(event -> {
      if (radioButtonAffine.isSelected()) {
        grid.add(new Label("  "), 0, 4);
        grid.add(line, 0, 5);
        grid.add(new Label("Number of transforms"), 0, 6);
        grid.add(spinner, 0, 7);
      }
    });
    createNewFractal.getDialogPane().setContent(grid);
    createNewFractal.showAndWait();
    if (radioButtonAffine.isSelected()) {
      this.numberOfTransforms = spinner.getValue();
    } else if (radioButtonJulia.isSelected()) {
      this.numberOfTransforms = 0;
    }
    return this.numberOfTransforms;
  }

  /**
   * Method for removing the node for affine transformation from the grid in the dialog.
   *
   * @param grid the grid to add the nodes to.
   */
  private void removeNodesFromGrid(GridPane grid) {
    for (int i = 4; i <= 7; i++) {
      final int row = i;
      grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row
          && GridPane.getColumnIndex(node) == 0);
    }
  }

  public ColorChoiceDialog getColorChoiceDialog() {
    return new ColorChoiceDialog();
  }

  /**
   * Method for creating a file chooser dialog.
   *
   * @return the file chosen by the user.
   */
  public File readFromFileDialog() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    return fileChooser.showOpenDialog(null);
  }

  /**
   * Method for saving current fractal to file.
   *
   * @return the file chosen by the user.
   */
  public File saveToFileDialog() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Resource File");
    return fileChooser.showSaveDialog(null);
  }

  /**
   * Method for creating a generic error dialog.
   *
   * @param message the message to display in the dialog.
   */
  public void genericErrorDialog(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Method for setting the icon to the ChaosGame dialog.
   *
   * @param alert the alert to set the icon to.
   * @throws ResourceNotFoundException if the icon for the dialog is not found.
   */
  private void setIconToCgDialog(Alert alert) {
    final ImageView dialogHeaderIcon;
    try {
      dialogHeaderIcon = new ImageView(
          Objects.requireNonNull(getClass().getResource("/images/iconChaosGame.png"))
              .toExternalForm());
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException("Could not find the icon");
    }
    dialogHeaderIcon.setFitHeight(48);
    dialogHeaderIcon.setFitWidth(48);
    alert.getDialogPane().setGraphic(dialogHeaderIcon);
  }

  /**
   * inner class for creating a dialog for the user to choose a color.
   * The dialog contains a combobox with the available colors.
   * The user can choose a color from the combobox.
   */
  public static class ColorChoiceDialog extends Dialog<Color> {

    /**
     * Constructor for the ColorChoiceDialog class.
     * Creates a dialog for the user to choose a color.
     */
    public ColorChoiceDialog() {
      this.setTitle("Choose a color");
      this.getDialogPane().setMinSize(220, 150);
      this.setHeaderText("Choose a color for the fractal");
      ColorPicker colorPicker = new ColorPicker();
      colorPicker.setOnAction(event -> this.setResult(colorPicker.getValue()));
      this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
      this.getDialogPane().setContent(colorPicker);
      this.setResultConverter(buttonType -> {
        if (buttonType == ButtonType.OK) {
          return this.getResult();
        }
        return null;
      });
    }

  }
}
