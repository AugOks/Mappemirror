package org.ntnu.IDATA2003.mappe5.view;

import java.io.File;
import java.util.Objects;
import java.util.Optional;
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
 * Made this is a singleton class to avoid creating multiple instances of the class but unsure
 * if it is necessary.
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
    Optional<ButtonType> result = exitAlert.showAndWait();
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
      final ImageView dialogHeaderIcon;
      dialogHeaderIcon = new ImageView(Objects.requireNonNull(getClass()
              .getResource("/discoBall.png"))
          .toExternalForm());
      dialogHeaderIcon.setFitHeight(48);
      dialogHeaderIcon.setFitWidth(48);
      alertDanceParty.getDialogPane().setGraphic(dialogHeaderIcon);

      Optional<ButtonType> result = alertDanceParty.showAndWait();

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


    Spinner spinner = new Spinner();
    spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20));
    spinner.setEditable(true);

    radioButtonJulia.setOnAction(event -> {
      if (radioButtonJulia.isSelected()) {
        if (grid.getChildren().contains(spinner)) {
          removeNodesFromGrid(grid, 4, 7);
        }
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
      this.numberOfTransforms = (int) spinner.getValue();
    } else if (radioButtonJulia.isSelected()) {
      this.numberOfTransforms = 0;
    }
    System.out.println(this.numberOfTransforms);
    return this.numberOfTransforms;
  }

  /**
   * Method for removing the node for affine transformation from the grid in the dialog.
   *
   * @param grid     the grid to add the nodes to.
   * @param startRow the row to start adding nodes.
   * @param endRow   the row to end adding nodes.
   */
  private void removeNodesFromGrid(GridPane grid, int startRow, int endRow) {
    for (int i = startRow; i <= endRow; i++) {
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
          Objects.requireNonNull(getClass().getResource("/iconChaosGame.png"))
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
