package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

/**
 * Class for creating a grid with textfields for min and max coordinates.
 * Used in the ChaosGameGui class
 */
public class MinMaxCoordsBox {
  // Textfields for min and max coordinates
  private TextField minX;
    private TextField minY;
    private TextField maxX;
    private TextField maxY;

    /**
     * Constructor for the MinMaxCoordsBox class.
     * @param minCoords the minimum coordinates
     * @param maxCoords the maximum coordinates
     */
    public MinMaxCoordsBox(Vector2D minCoords, Vector2D maxCoords) {
        this.minX = new TextField();
        this.minY = new TextField();
        this.maxX = new TextField();
        this.maxY = new TextField();
        this.createTextField(this.minX, "minX", minCoords.getX0());
        this.createTextField(this.minY, "minY", minCoords.getY0());
        this.createTextField(this.maxX, "maxX", maxCoords.getX0());
        this.createTextField(this.maxY, "maxY", maxCoords.getY0());
        this.setListener(this.minX, "minX", minCoords.getX0());
        this.setListener(this.minY, "minY", minCoords.getY0());
        this.setListener(this.maxX, "maxX", maxCoords.getX0());
        this.setListener(this.maxY, "maxY", maxCoords.getY0());
    }

    /**
     * Creates a textfield with a prompt text and a value.
     * @param field the textfield
     * @param name the prompt text
     * @param value the value
     */
  private void createTextField(TextField field, String name, double value){
    field.setPromptText(name);
    field.setMaxWidth(80);
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      field.setText(newValue);
    });
  }

  /**
   * Creates a grid with textfields for min and max coordinates.
   * @return the grid containing the min max textfields.
   */
  public GridPane getMinMaxGrid(){
      GridPane grid = new GridPane();
    grid.add(new Label("Min coords:"), 0, 0);
    grid.add(minX, 1, 0);
    grid.add(new Label(" x0"), 2, 0);
    grid.add(minY, 1, 1);
    grid.add(new Label(" y0"), 2, 1);
    grid.add(new Label("  "),0,2);

    grid.add(new Label("Max coords:"), 0, 3);
    grid.add(maxX, 1, 3);
    grid.add(new Label(" x1"), 2, 3);
    grid.add(maxY, 1, 4);
    grid.add(new Label(" y1"), 2, 4);

    grid.add(new Label("  "),0,5);
    return grid;
  }

  /**
   * Sets a listener for the textfield.
   * @param field the textfield to assign a listner to.
   * @param name the name of the textfield.
   * @param value the current value of the corresponding vector.
   */
  private void setListener(TextField field, String name, double value){
    field.setPromptText(name);
    field.setMaxWidth(80);
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      field.setText(newValue);
    });
  }

  /**
   * Returns the minimum coordinates.
   * @return the minimum coordinates as a Vector2D object.
   */
  public Vector2D getMinCoords() {
    return new Vector2D(Double.parseDouble(minX.getText()), Double.parseDouble(minY.getText()));
  }

  /**
   * Returns the maximum coordinates.
   * @return the maximum coordinates as a Vector2D object.
   */
  public Vector2D getMaxCoords() {
    return new Vector2D(Double.parseDouble(maxX.getText()), Double.parseDouble(maxY.getText()));
  }
}
