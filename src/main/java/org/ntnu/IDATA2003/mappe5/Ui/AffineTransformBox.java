package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;


/**
 * Represents the input boxes for a single matrix, and a single vector.
 * This class allows easy access and manipulation of the values in the matrix and vector.
 */
public class AffineTransformBox {

  Vector2D vector;
  Matrix2x2 matrix;
  private TextField a;
  private TextField b;
  private TextField c;
  private TextField d;
  private  TextField vectorX;
  private TextField vectorY;

  /**
   * Constructor for the TransformBox class.
   * Takes in a matrix and a Vector and creates the various text fields that correspond to their
   * values.
   * @param matrix the matrix to be represented.
   * @param vector the vector to be represented.
   */
    public AffineTransformBox(Matrix2x2 matrix, Vector2D vector) {
      this.vector = vector;
      this.matrix = matrix;
      this.a = createTextField("a00", matrix.getA());
      this.b = createTextField("a01", matrix.getB());
      this.c = createTextField("a10", matrix.getC());
      this.d = createTextField("a11", matrix.getD());
      this.vectorX = createTextField("b0", vector.getX0());
      this.vectorY = createTextField("b1", vector.getY0());
    }

  /**
   * Creates a textfield with the values of the matrix and awaits any changes.
   *
   * @param name  the prompt text.
   * @param value the value of the textfield.
   */
  private TextField createTextField(String name, double value){
    TextField field = new TextField();
    field.setPromptText(name);
    field.setMaxWidth(60);
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      field.setText(newValue);
    });
    return field;
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
   * Returns the matrix.
   * @param modify if the method should return the factory matrix or the modified matrix.
   * @return the matrix.
   */
  public Matrix2x2 getMatrix(boolean modify) {
    if (modify) {
      matrix.setA(Double.parseDouble(a.getText()));
      matrix.setB(Double.parseDouble(b.getText()));
      matrix.setC(Double.parseDouble(c.getText()));
      matrix.setD(Double.parseDouble(d.getText()));
    }
    return matrix;

  }

  public AffineTransform2D getTransform(){
    return new AffineTransform2D(getMatrix(true), getVector(true));
  }

  /**
   * Returns the vector.
   * @param modify if the method should return the factory vector or the modified vector.
   * @return the vector.
   */
  public Vector2D getVector(boolean modify){
    if (modify) {
      this.vector.setX0(Double.parseDouble(vectorX.getText()));
      this.vector.setY0(Double.parseDouble(vectorY.getText()));
    }
    return vector;
  }
}
