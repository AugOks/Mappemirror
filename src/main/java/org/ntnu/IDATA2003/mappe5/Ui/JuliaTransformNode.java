package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;

/**
 * Represents the input field for a Julia transformation.
 */
public class JuliaTransformNode implements FractalInputNode {
  private Slider imaginarySlider;
  private Slider realSlider;
  private TextField imagTextField;
  private TextField realTextField;
  private Complex complex;

  /**
   * Constructor for the JuliaSliderBox class.
   *
   * @param description the description for the current game.
   */
  public JuliaTransformNode(ChaosGameDescription description){
    JuliaTransform julia  = (JuliaTransform) description.getTransform(0);
    this.complex = julia.getComplex();
    this.imagTextField = new TextField();
    this.imagTextField.setId("imag");
    this.imagTextField.setMaxWidth(70);
    this.realTextField = new TextField();
    this.realTextField.setMaxWidth(70);
    this.realTextField.setId("real");

    this.imaginarySlider = createSlider(this.complex.getY0(),-1, 1.0,this.imagTextField);
    this.realSlider = createSlider(this.complex.getX0(),-1, 1.0,  this.realTextField);
    this.textFieldListener(this.imagTextField);
    this.textFieldListener(this.realTextField);
  }

  /**
   * Creates a slider with a start value and a label to display the value of the slider.
   *
   * @param startValue The initial value of the complex number.
   * @param min The minimum value of the slider.
   * @param max The maximum value of the slider.
   * @param textField The label to display the value of the slider.
   * @return the slider.
   */
  private Slider createSlider(double startValue,double min, double max, TextField textField) {
    Slider slider = new Slider(min, max,startValue); //starts at 0 just for construction.
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.25f);
    slider.setBlockIncrement(0.1f);
    textField.textProperty().setValue(String.valueOf(startValue));
    slider.setId(textField.getText());
    this.sliderListener(slider,textField);
    return slider;
  }
  /**
   * Listens to the slider and updates the label with the value of the slider.
   *
   * @param slider the slider to listen to.
   * @param textField the label to update.
   */
  private void sliderListener(Slider slider, TextField textField){
    slider.valueProperty().addListener(
        (observable, oldValue, newValue) -> {
          String displayValue =  String.valueOf(newValue);
          try {
            if (slider.getId().equalsIgnoreCase("real")) {
              this.complex.setX0((double) newValue);
            } else {
              this.complex.setY0((double) newValue);
            }
            textField.setText(displayValue);
          }catch (Exception e){
            ;//TODO Maybe fix this
          }
        });
  }

  /**
   * Listens to the text field and updates the complex and the slider with the new values.
   *
   * @param field the text field to assign a listener to.
   */
  private void textFieldListener(TextField field){
    field.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        double value = Double.parseDouble(newValue);
        if (field.getId().equalsIgnoreCase("real")) {
          this.complex.setX0(value);
          realSlider.setValue(value);
        } else {
          this.complex.setY0(value);
          imaginarySlider.setValue(value);
        }
      }catch (Exception e){
        ;//TODO Maybe fix this
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
    grid.add(new Label("Real:"), 0, 0);
    grid.add(this.realSlider, 1, 0);
    grid.add(new Label("Value: "), 2, 0);
    grid.add(this.realTextField, 3, 0);
    grid.add(new Label("Imaginary:"), 0, 1);
    grid.add(this.imaginarySlider, 1, 1);
    grid.add(new Label("value: "), 2 ,1);
    grid.add(this.imagTextField, 3, 1);
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
    return  transforms;
  }
}
