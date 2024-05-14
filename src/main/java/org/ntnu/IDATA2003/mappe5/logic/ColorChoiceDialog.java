package org.ntnu.IDATA2003.mappe5.logic;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ColorChoiceDialog extends Dialog<Color> {
  private HashMap<String, Color> availableColors;
  public ColorChoiceDialog() {
    this.setAvailableColors();
    this.setTitle("Choose a color");
    ComboBox<String> colorComboBox = new ComboBox<>();
    colorComboBox.setPlaceholder(new Label("Choose a color"));
    colorComboBox.setItems(FXCollections.observableArrayList(availableColors.keySet()));
    this.setHeaderText("Choose a color for the fractal");
    this.getDialogPane().setContent(colorComboBox);
    this.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    this.setResultConverter(buttonType -> {
      if (buttonType == ButtonType.OK) {
        return availableColors.get(colorComboBox.getValue());
      }
      return null;
    });
  }

  /**
   * Sets the available colors for the dialog.
   */
  public void setAvailableColors() {
    this.availableColors = new HashMap<>();
    this.availableColors.put("default", null);
    this.availableColors.put("Red", Color.RED);
    this.availableColors.put("Blue", Color.BLUE);
    this.availableColors.put("Green", Color.GREEN);
    this.availableColors.put("Yellow", Color.YELLOW);
    this.availableColors.put("Black", Color.BLACK);
    this.availableColors.put("White", Color.WHITE);
    this.availableColors.put("Purple", Color.PURPLE);
    this.availableColors.put("Orange", Color.ORANGE);
    this.availableColors.put("Pink", Color.PINK);
    this.availableColors.put("Brown", Color.BROWN);
    this.availableColors.put("Cyan", Color.CYAN);
    this.availableColors.put("Gray", Color.GRAY);
    this.availableColors.put("Light Gray", Color.LIGHTGRAY);
    this.availableColors.put("Dark Gray", Color.DARKGRAY);
    this.availableColors.put("Magenta", Color.MAGENTA);
    this.availableColors.put("Aliceblue",Color.ALICEBLUE);
    this.availableColors.put("Antiquewhite",Color.ANTIQUEWHITE);
    this.availableColors.put("Aqua",Color.AQUA);
    this.availableColors.put("Aquamarine",Color.AQUAMARINE);
    this.availableColors.put("Azure",Color.AZURE);
    this.availableColors.put("Beige",Color.BEIGE);
    this.availableColors.put("Bisque",Color.BISQUE);
    this.availableColors.put("Blanchedalmond",Color.BLANCHEDALMOND);
    this.availableColors.put("Blueviolet",Color.BLUEVIOLET);
    this.availableColors.put("Burlywood",Color.BURLYWOOD);


        }
}
