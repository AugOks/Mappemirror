package org.ntnu.IDATA2003.mappe5.Ui;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.ResourceNotFoundException;

/**
 * Class for creating dialogs in the ChaosGameGui class.
 * Made this is a singleton class to avoid creating multiple instances of the class but unsure
 * if it is necessary.
 */
public class ChaosGameDialogHandler {

  private static  ChaosGameDialogHandler instance = null;
  /**
   * Constructor for the ChaosGameDialogHandler class.
   */

  private ChaosGameDialogHandler() {
  }

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
  public boolean exitDialog(){
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
      final ImageView DIALOG_HEADER_ICON;
        DIALOG_HEADER_ICON = new ImageView(Objects.requireNonNull(getClass()
            .getResource("/discoBall.png")).toExternalForm());
      DIALOG_HEADER_ICON.setFitHeight(48);
      DIALOG_HEADER_ICON.setFitWidth(48);
      alertDanceParty.getDialogPane().setGraphic(DIALOG_HEADER_ICON);

      Optional<ButtonType> result = alertDanceParty.showAndWait();

      if (alertDanceParty.getResult() == yesD) {
        dancePartyConfirmation=true;
      }
    }catch (NullPointerException e){
      throw new ResourceNotFoundException("Could not find the icon");
    }
    catch (Exception ignore) {}
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
    final ImageView DIALOG_HEADER_ICON;
    try {
      DIALOG_HEADER_ICON = new ImageView(Objects.requireNonNull(getClass()
          .getResource("/iconChaosGame.png")).toExternalForm());
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException("Could not find the icon");
    }
    DIALOG_HEADER_ICON.setFitHeight(48);
    DIALOG_HEADER_ICON.setFitWidth(48);
    about.getDialogPane().setGraphic(DIALOG_HEADER_ICON);
    about.getDialogPane().setMinSize(700, 400);

    VBox dialogPaneContent = new VBox();
    GridPane grid = new GridPane();

    String header = "Welcome to chaos game!";
    Text text1 = new Text("Chaos game is one of many established methods for generating fractals "
                          +  "online.\n"
                          +  "A fractal can be defined as complex pattern that are self-similar "
                          + "across different scales. In other word, beautiful figure \n"
                          + "that will repeat the same pattern "
                          + "regardless of how much you zoom and your viewpoint.\n");
    Text text2 = new Text("The application creates fractals choosing a initial starting point, "
                          + "and so transforming that point using \n"
                          +" a predefined mathematical operation."
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
    about.setHeaderText(header);
    grid.add(text1,0,1);
    grid.add(text2,0,2);
    grid.add(text3,0,3);
    dialogPaneContent.getChildren().add(grid);
    about.getDialogPane().setContent(dialogPaneContent);
    about.showAndWait();
  }

  public ColorChoiceDialog getColorChoiceDialog(){
    return new ColorChoiceDialog();
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

  /**
   * Method for creating a file chooser dialog.
   * @return the file chosen by the user.
   */
  public File readFromFileDialog(){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    return fileChooser.showOpenDialog(null);
  }

  /**
   * Method for saving current fractal to file.
   * @return the file chosen by the user.
   */
  public File saveToFileDialog(){
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save Resource File");
    return fileChooser.showSaveDialog(null);
  }

  /**
   * Method for creating a generic error dialog.
   * @param message
   */
  public void genericErrorDialog(String message){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
