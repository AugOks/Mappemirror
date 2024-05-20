package org.ntnu.IDATA2003.mappe5.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.controller.InputNodeController;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;


/**
 * Represents the input boxes for a single matrix, and a single vector.
 * This class allows easy access and manipulation of the values in the matrix and vector.
 */
public class AffineTransformNode implements FractalInputNode {

  private List<AffineTransform2D> transforms;
  private boolean valueChanged = true;
  private InputNodeController controller;

  /**
   * Constructor for the TransformBox class.
   * Takes in a matrix and a Vector and creates the various text fields that correspond to their
   * values.
   */
  public AffineTransformNode(ChaosGameDescription description, InputNodeController controller) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }
    this.controller = controller;
    if (description.getTransform(0) instanceof AffineTransform2D) {
      this.transforms = new ArrayList<>();
      for (Transform2D transform : description.getAllTransforms()) {
        this.transforms.add((AffineTransform2D) transform);
      }

    }
  }

  /**
   * Creates a text field for a value for a matrix and adds a listener to it. The listener
   * checks what element in the matrix is being changed and updates the matrix accordingly.
   *
   * @param name  the prompt text.
   * @param value the value of the textfield.
   */
  private TextField createMatrixTextField(String name, double value, Matrix2x2 matrix) {
    TextField field = new TextField();
    field.setPromptText(name);
    field.getStyleClass().add("matrix-vector-field");
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        field.setText(newValue);
        this.controller.matrixConditionalSetValue(name, matrix, newValue);
        this.valueChanged = true;
      } catch (Exception e) {
        this.valueChanged = false;
        ;//TODO Fix this
      }
    });
    return field;
  }

  /**
   * Creates a text field for a value for a vector and adds a listener to it. The listener
   * checks what element in the vector is being changed and updates the vector accordingly.
   *
   * @param name   the prompt text.
   * @param value  the value to initialize the text field with.
   * @param vector the vector to be updated.
   * @return the text field.
   */
  private TextField createVectorTextField(String name, double value, Vector2D vector) {
    TextField field = new TextField();
    field.setPromptText(name);
    field.getStyleClass().add("matrix-vector-field");
    field.setText(String.valueOf(value));
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        field.setText(newValue);
        controller.vectorConditionalSetValue(name, vector, newValue);
        this.valueChanged = true;
      } catch (Exception e) {
        this.valueChanged = false;
      }

    });
    return field;
  }

  /**
   * Returns a node containing the fractal input text fields.
   *
   * @return the node containing all the text fields for a fractal.
   */
  @Override
  public Node getFractalNode() {
    GridPane gridPane = new GridPane();
    for (int i = 0; i < transforms.size(); i++) {

      GridPane entireTransform = new GridPane();
      int transfNumber = i + 1;
      Label transformLabel = new Label("Transform " + transfNumber + ": ");
      transformLabel.getStyleClass().add("input-title");
      entireTransform.add(transformLabel, 0, 0);
      entireTransform.add(new Label(""), 0, 1);

      AffineTransform2D transform = transforms.get(i);
      Matrix2x2 matrix = transform.getMatrix();


      GridPane firstMatrix = new GridPane();
      Label matrixLabel = new Label("Matrix:");
      matrixLabel.getStyleClass().add("input-title");
      firstMatrix.add(matrixLabel, 0, 0);

      firstMatrix.add(createMatrixTextField("a00", matrix.getA(), matrix), 0, 1);
      firstMatrix.add(createMatrixTextField("a01", matrix.getB(), matrix), 1, 1);
      firstMatrix.add(createMatrixTextField("a10", matrix.getC(), matrix), 0, 2);
      firstMatrix.add(createMatrixTextField("a11", matrix.getD(), matrix), 1, 2);

      GridPane firstVector = new GridPane();
      Label vectorLabel = new Label("Vector :");
      Vector2D vector = transform.getVector();
      vectorLabel.getStyleClass().add("input-title");
      firstVector.add(vectorLabel, 0, 0);
      firstVector.add(createVectorTextField("b0", vector.getX0(), vector), 0, 1);
      firstVector.add(createVectorTextField("b1", vector.getY0(), vector), 0, 2);
      firstMatrix.add(new Label("  "), 0, 3);

      entireTransform.add(firstMatrix, 0, i + 1);
      entireTransform.add(new Label("  "), 1, i + 1);
      entireTransform.add(firstVector, 2, i + 1);

      gridPane.add(entireTransform, 0, i);
    }
    return gridPane;
  }

  /**
   * Returns the transforms for the fractal.
   *
   * @return the list of transforms.
   */
  @Override
  public List<Transform2D> getTransforms() {
    return new ArrayList<>(this.transforms);
  }

  @Override
  public boolean isValueValid() {
    return this.valueChanged;
  }


}
