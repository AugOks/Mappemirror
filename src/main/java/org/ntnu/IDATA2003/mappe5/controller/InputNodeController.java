package org.ntnu.IDATA2003.mappe5.controller;

import java.util.List;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.view.ChaosGameDialogHandler;
import org.ntnu.IDATA2003.mappe5.view.FractalInputNode;
import org.ntnu.IDATA2003.mappe5.view.InputNode;
import org.ntnu.IDATA2003.mappe5.view.MinMaxCoordsNode;


/**
 * Controller for the input nodes in the GUI.
 */
public class InputNodeController {

  private final ChaosGameControllerGui controller;
  private final ChaosGameDialogHandler textField = ChaosGameDialogHandler.getInstance();
  private ChaosGameDescription currentDescription;

  /**
   * Constructor for the InputNodeController class.
   *
   * @param controllerGui      the controller for the GUI.
   * @param currentDescription the current description of the game.
   * @param inputNode          the input node for the GUI.
   */

  public InputNodeController(ChaosGameControllerGui controllerGui,
                             ChaosGameDescription currentDescription, InputNode inputNode) {
    this.controller = controllerGui;
    this.currentDescription = currentDescription;

  }

  /**
   * Runs the fractal with the given min/max coordinates and fractal input.
   *
   * @param minMaxCoordsNode the min/max coordinates node of the fractal.
   * @param fractalInputNode the fractal input node.
   */
  public void run(MinMaxCoordsNode minMaxCoordsNode, FractalInputNode fractalInputNode) {
    if (!minMaxCoordsNode.isValueValid() && !fractalInputNode.isValueValid()) {
      textField.genericErrorDialog("The values in the min/max coordinates and "
          + "fractal input are invalid");
    } else if (!minMaxCoordsNode.isValueValid()) {
      textField.genericErrorDialog("One of the values in the min/max coordinates is invalid");
    } else if (!fractalInputNode.isValueValid()) {
      textField.genericErrorDialog("One of the values in the fractal input is invalid");
    }

    if (fractalInputNode.isValueValid() && minMaxCoordsNode.isValueValid()) {
      currentDescription.setTransforms(fractalInputNode.getTransforms());
      currentDescription.setMaxCoords(minMaxCoordsNode.getMaxCoords());
      currentDescription.setMinCoords(minMaxCoordsNode.getMinCoords());
      controller.changeDescription(currentDescription);
    }
  }

  /**
   * Changes the description of the fractal.
   *
   * @param description the new description of the fractal.
   */
  public void changeDescription(ChaosGameDescription description) {
    this.currentDescription = description;
  }

  /**
   * Changes the transforms of the fractal.
   *
   * @param transforms the new transforms of the fractal.
   */
  public void changeTransform(List<Transform2D> transforms) {
    currentDescription.setTransforms(transforms);
    controller.changeDescription(currentDescription);
  }

  /**
   * Changes the min/max coordinates of the fractal.
   *
   * @param minCoords the new minimum coordinates of the fractal.
   * @param maxCoords the new maximum coordinates of the fractal.
   */
  public void changeMinMaxCoords(Vector2D minCoords, Vector2D maxCoords) {
    controller.changeCoords(minCoords, maxCoords);
  }

  /**
   * Sets the value of a matrix element based on the name of the element.
   *
   * @param name     the name of the element.
   * @param matrix   the matrix to update.
   * @param newValue the new value of the element.
   */
  public void matrixConditionalSetValue(String name, Matrix2x2 matrix, String newValue) {
    if (name.equals("a00")) {
      matrix.setA(Double.parseDouble(newValue));
    } else if (name.equals("a01")) {
      matrix.setB(Double.parseDouble(newValue));
    } else if (name.equals("a10")) {
      matrix.setC(Double.parseDouble(newValue));
    } else {
      matrix.setD(Double.parseDouble(newValue));
    }
  }

  /**
   * Sets the value of a vector element based on the name of the element.
   *
   * @param name     the name of the element.
   * @param vector   the vector to update.
   * @param newValue the new value of the element.
   */
  public void vectorConditionalSetValue(String name, Vector2D vector, String newValue) {
    if (name.equals("b0")) {
      vector.setX0(Double.parseDouble(newValue));
    } else {
      vector.setY0(Double.parseDouble(newValue));
    }
  }

  /**
   * Sets the value of a complex number element based on the name of the element.
   *
   * @param newValue        the new value of the element.
   * @param field           the text field to update.
   * @param realSlider      the real slider to update.
   * @param imaginarySlider the imaginary slider to update.
   * @param complex         the complex number to update.
   */
  public void complexSetValue(String newValue, TextField field, Slider realSlider,
                              Slider imaginarySlider, Complex complex) {
    double value = Double.parseDouble(newValue);
    if (field.getId().equalsIgnoreCase("real")) {
      complex.setX0(Double.parseDouble(newValue));
      realSlider.setValue(Double.parseDouble(newValue));
    } else {
      complex.setY0(value);
      imaginarySlider.setValue(value);
    }
  }

  /**
   * Sets the value of a complex number element based on the name of the element.
   *
   * @param slider    the slider to update.
   * @param textField the text field to update.
   * @param newValue  the new value of the element.
   * @param complex   the complex number to update.
   */
  public void sliderSetComplexValue(Slider slider, TextField textField, Number newValue,
                                    Complex complex) {
    if (slider.getId().equalsIgnoreCase("real")) {
      complex.setX0((double) newValue);
    } else {
      complex.setY0((double) newValue);
    }
    textField.setText(String.valueOf(newValue));
  }

}
