package org.ntnu.IDATA2003.mappe5.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.controller.InputNodeController;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;

/**
 * Represents the input field for a Julia transformation.
 * Contains a slider for the real and imaginary part of a complex number.
 * The user can change the values of the complex number by either using the slider or the text
 * field. The user can also see the current value of the complex number in the text field.
 */
public class JuliaTransformNode implements FractalInputNode {
  private final Slider imaginarySlider;
  private final Slider realSlider;
  private final TextField imagTextField;
  private final TextField realTextField;
  private final Complex complex;
  private final InputNodeController controller;
  private boolean isValueValid = true;

  /**
   * Constructor for the JuliaSliderBox class.
   *
   * @param description the description for the current game.
   */
  public JuliaTransformNode(ChaosGameDescription description, InputNodeController controller) {
    this.controller = controller;
    JuliaTransform julia = (JuliaTransform) description.getTransform(0);
    this.complex = julia.getComplex();
    this.imagTextField = new TextField();
    this.imagTextField.setId("imag");
    this.imagTextField.setMaxWidth(100);
    this.realTextField = new TextField();
    this.realTextField.setMaxWidth(100);
    this.realTextField.setId("real");

    this.imaginarySlider = createSlider(this.complex.getY0(), -1, 1.0, this.imagTextField);
    this.realSlider = createSlider(this.complex.getX0(), -1, 1.0, this.realTextField);
    this.textFieldListener(this.imagTextField);
    this.textFieldListener(this.realTextField);
  }

  /**
   * Creates a slider with a start value and a label to display the value of the slider.
   *
   * @param startValue The initial value of the complex number.
   * @param min        The minimum value of the slider.
   * @param max        The maximum value of the slider.
   * @param textField  The label to display the value of the slider.
   * @return the slider.
   */
  private Slider createSlider(double startValue, double min, double max, TextField textField) {
    Slider slider = new Slider(min, max, startValue); //starts at 0 just for construction.
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.5f);
    slider.setBlockIncrement(0.5f);
    slider.setMaxWidth(180);
    slider.setMinWidth(180);
    textField.textProperty().setValue(String.valueOf(startValue));
    slider.setId(textField.getText());
    this.sliderListener(slider, textField);
    return slider;
  }

  /**
   * Listens to the slider and updates the label with the value of the slider.
   *
   * @param slider    the slider to listen to.
   * @param textField the label to update.
   */
  private void sliderListener(Slider slider, TextField textField) {
    slider.valueProperty().addListener(
        (observable, oldValue, newValue) -> {
          try {
            controller.sliderSetComplexValue(slider, textField, newValue, this.complex);
            controller.changeTransform(this.getTransforms());
          } catch (Exception e) {
            this.isValueValid = false;
          }
        });
  }

  /**
   * Listens to the text field and updates the complex and the slider with the new values.
   *
   * @param field the text field to assign a listener to.
   */
  private void textFieldListener(TextField field) {
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        controller.complexSetValue(newValue, field, this.realSlider, this.imaginarySlider,
                                   this.complex);
        this.isValueValid = true;
      } catch (Exception e) {
        this.isValueValid = false;
      }
    });
  }

  /**
   * Creates a grid with sliders for the real and imaginary part of a complex number.
   *
   * @return the grid containing the sliders.
   */
  @Override
  public Node getFractalNode() {
    GridPane grid = new GridPane();
    Label real = new Label("Real:");
    real.getStyleClass().add("input-title");
    grid.add(real, 0, 0);
    grid.add(this.realSlider, 0, 1);
    grid.add(this.realTextField, 1, 1);

    Label imaginary = new Label("Imaginary:");
    imaginary.getStyleClass().add("input-title");
    grid.add(imaginary, 0, 2);
    grid.add(this.imaginarySlider, 0, 3);
    grid.add(this.imagTextField, 1, 3);
    return grid;
  }

  /**
   * Returns the transforms for the Julia set.
   *
   * @return the list of transforms.
   */
  @Override
  public List<Transform2D> getTransforms() {
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new JuliaTransform(complex, 1));
    transforms.add(new JuliaTransform(complex, -1));
    return transforms;
  }

  /**
   * Returns whether the value is valid or not.
   *
   * @return true if the value is valid, false otherwise.
   */
  @Override
  public boolean isValueValid() {

    return isValueValid;
  }
}
