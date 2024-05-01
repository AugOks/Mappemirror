package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;

/**
 * Represents the input box for the user interface regardless of what type of transform is active.
 * Holds an interface variable that contains the input fields for the fractal.
 */
public class inputNode {
  private GridPane inputNode;

  private ChaosGameDescription currentDescription;

  private ChaosGameControllerGui controllerGui;

  private MinMaxCoordsNode minMaxCoordsNode;

  private FractalInputNode fractalInputNode;
  private int currentSteps;

  /**
   * Constructs the input node for the user interface.
   * Initializes the input node with a textfield for the number of steps and a fractal input node.
   * @param description the description of the fractal to be drawn.
   * @param stepsInt the number of steps to be drawn.
   */
  public inputNode(ChaosGameDescription description, int stepsInt, ChaosGameControllerGui controller) {
    currentDescription = description;
    this.controllerGui = controller;
    this.currentSteps = stepsInt;
    this.inputNode = new GridPane();
    this.inputNode.setBackground(new Background(new BackgroundFill(Color.DARKGREY,
        null,null)));
    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.setMaxWidth(200);
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      //String newInput = textInputListener(newValue,oldValue);
      this.currentSteps = Integer.parseInt(newValue);
    });
    this.inputNode.add(steps, 0, 0);
    this.findInstance(description);
    this.createInputNode(description);
    Button runButton = new Button("Run");
    runButton.getStyleClass().add("button-rightPane");
    runButton.setOnAction(e -> {
      if (!minMaxCoordsNode.isValueIsChanged() && !fractalInputNode.isValueChanged()){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You have invalid values in min/max coordinates and fractal input.");
        alert.showAndWait();
      }
      else if(!minMaxCoordsNode.isValueIsChanged()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("One of the values in the min/max coordinates is invalid");
        alert.showAndWait();
      }
      else if (!fractalInputNode.isValueChanged()) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
       alert.setContentText("One of the values in the fractal input is invalid");
       alert.showAndWait();
      }

      if(fractalInputNode.isValueChanged() && minMaxCoordsNode.isValueIsChanged()) {
        currentDescription.setTransforms(fractalInputNode.getTransforms());
        currentDescription.setMaxCoords(minMaxCoordsNode.getMaxCoords());
        currentDescription.setMinCoords(minMaxCoordsNode.getMinCoords());
        controller.changeDescription(currentDescription);
      }
    });
    this.inputNode.add(new Label(" "), 0 ,3);
    this.inputNode.add(runButton, 0, 4);
  }


  /**
   * Creates the input node for the user interface, regardless of the type of fractal.
   * @param description the description of the fractal to be drawn.
   */
  public void createInputNode(ChaosGameDescription description){
    this.minMaxCoordsNode = new MinMaxCoordsNode(description.getMinCoords(), description.getMaxCoords());
    this.inputNode.getChildren().removeIf(node -> node instanceof GridPane);

    this.inputNode.add(minMaxCoordsNode.getMinMaxNode(), 0,1);
    this.inputNode.add(fractalInputNode.getFractalNode(), 0 , 2);
  }


  /**
   * Returns the input node for the user interface.
   * @return the input node.
   */
  public GridPane getInputNode() {
    return this.inputNode;
  }

  /**
   * Changes the input node to a new input node.
   * @param description
   */
  public void changeInputNode(ChaosGameDescription description, int steps){
    this.currentDescription = description;
    this.currentSteps = steps;
    this.findInstance(description);
    this.createInputNode(description);
  }
  /**
   * Returns the minimum coordinates of the fractal.
   * @return the minimum coordinates.
   */
  public int getCurrentSteps() {
    return currentSteps;
  }
  private void findInstance(ChaosGameDescription description){
    if (description.getTransform(0) instanceof AffineTransform2D){
     this.fractalInputNode =  new  AffineTransformNode(description);
    } else {
     this.fractalInputNode = new JuliaTransformNode(description);
    }
  }
}
