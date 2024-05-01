package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

/**
 * Class for creating a grid with text fields for min and max coordinates.
 * Used in the ChaosGameGui class
 */
public class MinMaxCoordsNode {

    private TextField minX;  // The text field for the minimum x coordinate
    private TextField minY; // The text field for the minimum y coordinate
    private TextField maxX; // The text field for the maximum x coordinate
    private TextField maxY; // The text field for the maximum y coordinate
    private Vector2D minCoords; // The text field for the minimum coordinates
    private Vector2D maxCoords; // The text field for the maximum coordinates

    /**
     * Constructor for the MinMaxCoordsBox class.
     * @param minCoords the minimum coordinates
     * @param maxCoords the maximum coordinates
     */
    public MinMaxCoordsNode(Vector2D minCoords, Vector2D maxCoords) {
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
     * @param name the prompt text
     * @param value the value
     */
  private void createTextField(TextField field, String name, double value){
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
      }catch (Exception e){
        ;//TODO Maybe fix this
      }
    });
  }


  /**
   * Creates a grid with text fields for min and max coordinates.
   *
   * @return the grid containing the min max text fields.
   */
  public Node getMinMaxNode(){
    GridPane grid = new GridPane();

    Label minLabel = new Label("Min coords:");
    minLabel.getStyleClass().add("input-title");
    Label maxLabel = new Label("Max coords:");
    maxLabel.getStyleClass().add("input-title");

    GridPane minGrid = new GridPane();
    minGrid.add(minLabel, 0, 0);
    minGrid.add(minX, 0, 1);
    minGrid.add(new Label(" x0"), 1, 1);
    minGrid.add(minY, 0, 2);
    minGrid.add(new Label(" y0"), 1, 2);
    minGrid.add(new Label("  "),0,3);

    GridPane maxGrid = new GridPane();
    maxGrid.add(maxLabel, 0, 0);
    maxGrid.add(maxX, 0, 1);
    maxGrid.add(new Label(" x1"), 1, 1);
    maxGrid.add(maxY, 0, 2);
    maxGrid.add(new Label(" y1"), 1, 2);
    minGrid.add(new Label("  "),0,3);
    grid.add(minGrid, 0, 0);
    grid.add(maxGrid, 1, 0);

    return grid;
  }
  /*
  GridPane allGrids = new GridPane();
    GridPane minGrid = new GridPane();
    minGrid.add(new Label("Min coords:"), 0, 0);
    minGrid.add(minX, 0, 1);
    minGrid.add(new Label(" x0"), 1, 1);
    minGrid.add(minY, 0, 2);
    minGrid.add(new Label(" y0"), 1, 2);
    minGrid.add(new Label("  "),0,3);
    allGrids.add(minGrid, 0, 0);
    if (isJulia) {
      GridPane sliderGrid = new GridPane();
      sliderGrid.add(createSlider(-0.5, minX), 0, 1);
      sliderGrid.add(createSlider(minCoords.getY0(), minY), 0, 2);
      sliderGrid.add(createSlider(maxCoords.getX0(), maxX), 0, 3);
      sliderGrid.add(createSlider(maxCoords.getY0(), maxY), 0, 4);
        allGrids.add(sliderGrid, 0, 1);
    }
    GridPane maxGrid = new GridPane();
    maxGrid.add(new Label("Max coords:"), 0, 0);
    maxGrid.add(maxX, 0, 1);
    maxGrid.add(new Label(" x1"), 1, 1);
    maxGrid.add(maxY, 0, 2);
    maxGrid.add(new Label(" y1"), 1, 2);
    minGrid.add(new Label("  "),0,3);
    allGrids.add(maxGrid, 1, 0);
    return allGrids;
   */

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
}
