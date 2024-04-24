package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import org.ntnu.IDATA2003.mappe5.entity.Complex;

public class JuliaSliderBox {
  private Slider imaginarySlider;
  private Label imaginaryLabel;
  private Slider realSlider;
  private Label realLabel;

  /**
   * Constructor for the JuliaSliderBox class.
   * @param complex the complex number to be represented.
   */
  public JuliaSliderBox(Complex complex) {
    this.imaginaryLabel = new Label();
    this.realLabel = new Label();
    this.imaginarySlider = this.createSlider(complex.getX0(),-1, 1.0,this.imaginaryLabel);
    this.realSlider = createSlider(complex.getY0(),-1, 1.0,  this.realLabel);

  }

  /**
   * Creates a slider with a start value and a label to display the value of the slider.
   * @param startValue The initial value of the complex number.
   * @param min The minimum value of the slider.
   * @param max The maximum value of the slider.
   * @param label The label to display the value of the slider.
   * @return the slider.
   */
  private Slider createSlider(double startValue,double min, double max, Label label) {
    Slider slider = new Slider(min, max,0); //starts at 0 just for construction.
    slider.setShowTickLabels(true);
    slider.setShowTickMarks(true);
    slider.setMajorTickUnit(0.25f);
    slider.setBlockIncrement(0.1f);
    this.sliderListener(slider,label);
    slider.setValue(startValue);
    return slider;
  }
  /**
   * Listens to the slider and updates the label with the value of the slider.
   * @param slider the slider to listen to.
   * @param label the label to update.
   */
  private void sliderListener(Slider slider, Label label){
    slider.valueProperty().addListener(
        (observable, oldValue, newValue) -> {
          String displayValue = String.format("Value:  %.2f", newValue);
          label.setText(displayValue);
        });
  }

  /**
   * Creates a grid with sliders for the real and imaginary part of a complex number.
   * @return the grid containing the sliders.
   */
  public GridPane getSliderGrid(){
    GridPane grid = new GridPane();
    grid.add(new Label("Real:"), 0, 0);
    grid.add(this.realSlider, 1, 0);
    grid.add(this.realLabel, 2, 0);
    grid.add(new Label("Imaginary:"), 0, 1);
    grid.add(this.imaginarySlider, 1, 1);
    grid.add(this.imaginaryLabel, 2, 1);
    return grid;
  }

}
