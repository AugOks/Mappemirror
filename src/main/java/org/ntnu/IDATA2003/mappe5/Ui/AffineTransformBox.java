package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;


/**
 * Represents the input boxes for a single matrix, and a single vector.
 * This class allows easy access and manipulation of the values in the matrix and vector.
 */
public class AffineTransformBox {
  private TextField a;
  private TextField b;
  private TextField c;
  private TextField d;
  private  TextField vectorX;
  private TextField vectorY;

  /**
   * Constructor for the TransformBox class.
   * Takes in a matrix and a Vector and creates the various textfields that corresponde to their
   * values.
   * @param matrix the matrix to be represented.
   * @param vector the vector to be represented.
   */
    public AffineTransformBox(Matrix2x2 matrix, Vector2D vector) {
        this.a = new TextField();
        this.b = new TextField();
        this.c = new TextField();
        this.d = new TextField();
        this.vectorX = new TextField();
        this.vectorY = new TextField();
        createTextField(this.a, "a00", matrix.getA());
        createTextField(this.b, "a01", matrix.getB());
        createTextField(this.c, "a10", matrix.getC());
        createTextField(this.d, "a11", matrix.getD());
        createTextField(this.vectorX, "b0", vector.getX0());
        createTextField(this.vectorY, "b1", vector.getY0());
    }

  /**
   * Creates a textfield with the values of the matrix and awaits any changes.
   * @param field the textfield to be created.
   * @param name the prompt text.
   * @param value the value of the textfield.
   */
  private void createTextField(TextField field, String name, double value){
    field.setPromptText(name);
    field.setMaxWidth(60);
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      field.setText(newValue);
    });
  }

  /**
   * Returns the gridpane containing the matrix and vector textfields.
   * @return the gridpane containing the matrix and vector.
   */
  public GridPane getGridBox(){
GridPane gridPane = new GridPane();
    gridPane.add(new Label("  "),0,0);

    GridPane firstMatrix = new GridPane();
    firstMatrix.add(new Label("Matrix:"), 0, 0);
    firstMatrix.add(a, 0, 1);
    firstMatrix.add(b, 1, 1);
    firstMatrix.add(c, 0, 2);
    firstMatrix.add(d, 1, 2);

    GridPane firstVector = new GridPane();
    firstVector.add(new Label("Vector:"), 0, 0);
    firstVector.add(vectorX, 0, 1);
    firstVector.add(vectorY, 0, 2);

    gridPane.add(firstMatrix,0,1);
    gridPane.add(new Label("  "),1,1);
    gridPane.add(firstVector,2,1);
    return gridPane;
  }

  /**
   * Get the a value of this matrix.
   * @return the a value of this matrix.
   */
  public TextField getA() {
    return a;
  }

  /**
   * Get the b value of this matrix.
   * @return the b value of this matrix
   */
  public TextField getB() {
    return b;
  }

  /**
   * Get the c value of this matrix.
   * @return the c value of this matrix
   */
  public TextField getC() {
    return c;
  }

  /**
   * Get the d value of this matrix.
   * @return the d value of this matrix
   */
  public TextField getD() {
    return d;
  }

  /**
   * Get the x value of this vector.
   * @return
   */
  public TextField getVectorX() {
    return vectorX;
  }

  /**
   * Get the y value of this vector.
   * @return the y value of this vector
   */
  public TextField getVectorY() {
    return vectorY;
  }
}
