package org.ntnu.IDATA2003.mappe5.view;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.controller.InputNodeController;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

/**
 * Class for creating a grid with text fields for min and max coordinates.
 * Used in the ChaosGameGui class
 */
public class MinMaxCoordsNode {

  private final TextField minX;  // The text field for the minimum x coordinate
  private final TextField minY; // The text field for the minimum y coordinate
  private final TextField maxX; // The text field for the maximum x coordinate
  private final TextField maxY; // The text field for the maximum y coordinate
  private final Vector2D minCoords; // The vector for the minimum coordinates
  private final Vector2D maxCoords; // The vector for the maximum coordinates
  private final InputNodeController controller; // The controller for the input node
  private boolean isValueValid = true; // A boolean value to determine if the value is valid

  /**
   * Constructor for the MinMaxCoordsBox class.
   *
   * @param minCoords the minimum coordinates
   * @param maxCoords the maximum coordinates
   */
  public MinMaxCoordsNode(Vector2D minCoords, Vector2D maxCoords, InputNodeController controller) {
    this.controller = controller;
    this.maxCoords = maxCoords;
    this.minCoords = minCoords;

    this.minX = new TextField();
    this.minY = new TextField();
    this.maxX = new TextField();
    this.maxY = new TextField();

    this.createTextField(this.minX, "minX", minCoords.getX0());
    this.createTextField(this.minY, "minY", minCoords.getY0());
    this.createTextField(this.maxX, "maxX", maxCoords.getX0());
    this.createTextField(this.maxY, "maxY", maxCoords.getY0());
  }

  /**
   * Creates a text field with a prompt text and a value.
   *
   * @param field the text field
   * @param name  the prompt text
   * @param value the value
   */
  private void createTextField(TextField field, String name, double value) {
    field.getStyleClass().add("minMaxField");
    field.setPromptText(name);
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        field.setText(newValue);
        if (name.equalsIgnoreCase("minx")) {
          minCoords.setX0(Double.parseDouble(newValue));
        } else if (name.equalsIgnoreCase("miny")) {
          minCoords.setY0(Double.parseDouble(newValue));
        } else if (name.equalsIgnoreCase("maxx")) {
          maxCoords.setX0(Double.parseDouble(newValue));
        } else if (name.equalsIgnoreCase("maxy")) {
          maxCoords.setY0(Double.parseDouble(newValue));
        }
        this.isValueValid = true;
      } catch (Exception e) {
        this.isValueValid = false;
      }
    });
  }

  /**
   * Creates a grid with text fields for min and max coordinates.
   * includes text fields and sliders for the min and max coordinates.
   *
   * @param sliders a boolean value to determine if sliders should be added to the grid.
   * @return the grid containing the min max text fields.
   */
  public Node getMinMaxNode(boolean sliders) {

    Label minLabel = new Label("Min coords:");
    minLabel.getStyleClass().add("input-title");
    Label maxLabel = new Label("Max coords:");
    maxLabel.getStyleClass().add("input-title");

    GridPane minGrid = new GridPane();
    minGrid.add(minLabel, 0, 0);

    minGrid.add(minX, 0, 1);
    minGrid.add(new Label(" "), 1, 1);
    minGrid.add(minY, 0, 2);
    minGrid.add(new Label(" "), 1, 2);
    minGrid.add(new Label("  "), 0, 3);

    GridPane allGrids = new GridPane();
    allGrids.add(minGrid, 0, 0);

    if (sliders) {
      allGrids.add(createSlider(minCoords.getX0(), minX, "minx"), 0, 1);
      allGrids.add(new Label(" "), 0, 2);
      allGrids.add(createSlider(minCoords.getY0(), minY, "miny"), 0, 3);
      allGrids.add(new Label(" "), 0, 4);

      allGrids.add(createSlider(maxCoords.getX0(), maxX, "maxx"), 1, 1);
      allGrids.add(new Label(" "), 0, 2);
      allGrids.add(createSlider(maxCoords.getY0(), maxY, "maxy"), 1, 3);
      allGrids.add(new Label(" "), 0, 4);
    }
    GridPane maxGrid = new GridPane();
    maxGrid.add(maxLabel, 0, 0);
    maxGrid.add(maxX, 0, 1);
    maxGrid.add(new Label(" "), 1, 1);
    maxGrid.add(maxY, 0, 2);
    maxGrid.add(new Label(" "), 1, 2);

    allGrids.add(maxGrid, 1, 0);

    return allGrids;
  }


  /**
   * Returns whether the value is changed.
   *
   * @return true if the value is changed, false otherwise.
   */
  public boolean isValueValid() {
    return isValueValid;
  }


  /**
   * Returns the minimum coordinates.
   *
   * @return the minimum coordinates as a Vector2D object.
   */
  public Vector2D getMinCoords() {
    return new Vector2D(Double.parseDouble(minX.getText()), Double.parseDouble(minY.getText()));
  }

  /**
   * Returns the maximum coordinates.
   *
   * @return the maximum coordinates as a Vector2D object.
   */
  public Vector2D getMaxCoords() {
    return new Vector2D(Double.parseDouble(maxX.getText()), Double.parseDouble(maxY.getText()));
  }

  /**
   * The slider is used to change the value of the text field and the min or max coordinates.
   *
   * @param startValue the start value of the slider.
   * @param textField  the text field to be updated.
   * @param id         the id of the slider.
   * @return the slider.
   */
  private Slider createSlider(double startValue, TextField textField, String id) {
    Slider slider;
    double minvalue = -(startValue * 1.5);
    double maxvalue = (startValue * 1.5);
    if (Math.signum(startValue) == 1) {
      slider = new Slider(minvalue, maxvalue, startValue);
    } else if (Math.signum(startValue) == -1) {
      slider = new Slider(maxvalue, minvalue, startValue);
    } else {
      slider = new Slider(-1, 1, startValue);
    }
    slider.setId(id);
    slider.setShowTickLabels(false);
    slider.setShowTickMarks(false);
    slider.setMajorTickUnit(1.5f);
    slider.setBlockIncrement(1.5f);
    slider.setMaxWidth(130);
    this.sliderListener(slider, textField);
    return slider;
  }

  /**
   * The listener for the sliders.
   * Changes the value of the text field when the slider is moved.
   * Also changes the value of the min or max coordinates.
   *
   * @param slider    The slider to be listened to.
   * @param textField The text field to be updated.
   */
  private void sliderListener(Slider slider, TextField textField) {
    slider.valueProperty().addListener(
        (observable, oldValue, newValue) -> {
          if (slider.getId().equalsIgnoreCase("minx")) {
            minCoords.setX0(slider.getValue());
            isValueValid = true;
          } else if (slider.getId().equalsIgnoreCase("miny")) {
            minCoords.setY0(newValue.doubleValue());
            isValueValid = true;
          } else if (slider.getId().equalsIgnoreCase("maxx")) {
            maxCoords.setX0(newValue.doubleValue());
            isValueValid = true;
          } else if (slider.getId().equalsIgnoreCase("maxy")) {
            maxCoords.setY0(newValue.doubleValue());
            isValueValid = true;
          }
          textField.setText(String.valueOf(newValue));
          controller.changeMinMaxCoords(this.minCoords, this.maxCoords);
        }
    );
  }
}
