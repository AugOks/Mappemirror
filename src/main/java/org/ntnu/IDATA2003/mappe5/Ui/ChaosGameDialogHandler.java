package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class for creating dialogs in the ChaosGameGui class.
 */
public class ChaosGameDialogHandler {

  /**
   * Constructor for the ChaosGameDialogHandler class.
   */
  public ChaosGameDialogHandler() {
    ;
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
   */
  public boolean dancePartyDialog() {
    boolean dancePartyConfirmation = false;
    try {
      ButtonType yesD = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
      ButtonType noD = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
      Alert alertDanceParty = new Alert(Alert.AlertType.CONFIRMATION, "  ", yesD, noD);
      alertDanceParty.setTitle("Dance party");
      alertDanceParty.setHeaderText("Do you want to have a dance party?");
      final ImageView DIALOG_HEADER_ICON = new ImageView(getClass().getResource("/discoBall.png").toExternalForm());
      DIALOG_HEADER_ICON.setFitHeight(48);
      DIALOG_HEADER_ICON.setFitWidth(48);
      alertDanceParty.getDialogPane().setGraphic(DIALOG_HEADER_ICON);

      Optional<ButtonType> result = alertDanceParty.showAndWait();

      if (alertDanceParty.getResult() == yesD) {
        dancePartyConfirmation=true;
      }
    } catch (Exception ignore) {}
    return dancePartyConfirmation;
  }

  /**
   * Method for creating an about ChaosGame dialog.
   */
  public void showAboutDialog() {
    //TODO write more about the application
    Alert about = new Alert(Alert.AlertType.INFORMATION);
    about.setTitle("About");
    about.setHeaderText("Chaos Game");
    final ImageView DIALOG_HEADER_ICON = new ImageView(getClass().getResource("/iconChaosGame.png").toExternalForm());
    DIALOG_HEADER_ICON.setFitHeight(48);
    DIALOG_HEADER_ICON.setFitWidth(48);
    about.getDialogPane().setGraphic(DIALOG_HEADER_ICON);
    about.getDialogPane().setMinSize(900, 600);

    //Todo add all information about the application here

    VBox dialogPaneContent = new VBox();
    Text text = new Text("This application is a chaos game application that allows you to create fractals using affine transformations.");

    dialogPaneContent.getChildren().add(text);
    about.getDialogPane().setContent(dialogPaneContent);
    about.showAndWait();
  }


}
