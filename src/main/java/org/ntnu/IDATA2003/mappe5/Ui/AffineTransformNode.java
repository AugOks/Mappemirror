package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;


/**
 * Represents the input boxes for a single matrix, and a single vector.
 * This class allows easy access and manipulation of the values in the matrix and vector.
 */
public class AffineTransformNode implements FractalInputNode {

  private List<AffineTransform2D> transforms;

  /**
   * Constructor for the TransformBox class.
   * Takes in a matrix and a Vector and creates the various text fields that correspond to their
   * values.
   */
  public AffineTransformNode(ChaosGameDescription description) {
    if(description == null){
      throw new IllegalArgumentException("Description cannot be null");
    }
    if (description.getTransform(0) instanceof AffineTransform2D){
      this.transforms = new ArrayList<>();
      for (Transform2D transform : description.getAllTransforms()){
        this.transforms.add((AffineTransform2D) transform);
      }

    }
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
   * Returns a node containing the fractal input text fields.
   * @return the node containing all the text fields for a fractal.
   */
  @Override
  public Node getFractalNode() {
    GridPane gridPane = new GridPane();
    for (int i = 0; i < transforms.size(); i++){
      AffineTransform2D transform = transforms.get(i);
      Matrix2x2 matrix = transform.getMatrix();
      Vector2D vector = transform.getVector();
      gridPane.add(new Label("  "),0,i);

      GridPane firstMatrix = new GridPane();
      firstMatrix.add(new Label("Transform " + i),0,0);
      firstMatrix.add(new Label("Matrix:"), 0, 1);
      firstMatrix.add(createTextField("a00", matrix.getA()), 0, 2);
      firstMatrix.add(createTextField("a01", matrix.getB()), 1, 2);
      firstMatrix.add(createTextField("a10", matrix.getC()), 0, 3);
      firstMatrix.add(createTextField("a11", matrix.getD()), 1, 3);

      GridPane firstVector = new GridPane();
      firstVector.add(new Label("") ,0,0);
      firstVector.add(new Label("Vector:"), 0, 1);
      firstVector.add(createTextField("b0", vector.getY0()), 0, 2);
      firstVector.add(createTextField("b1", vector.getY0()), 0, 3);

      gridPane.add(firstMatrix,0,i+2);
      gridPane.add(new Label("  "),1,i+2);
      gridPane.add(firstVector,2,i+2);


    }
    return gridPane;
  }
}
