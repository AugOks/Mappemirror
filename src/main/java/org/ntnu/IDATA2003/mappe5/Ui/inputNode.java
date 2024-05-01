package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;

/**
 * Represents the input box for the user interface regardless of what type of transform is active.
 * Holds an interface variable that contains the input fields for the fractal.
 */
public class inputNode {

  private GridPane inputNode; // The input node for the user interface.

  private ChaosGameDescription currentDescription; // The current description of the fractal.

  private ChaosGameControllerGui controllerGui;

  private MinMaxCoordsNode minMaxCoordsNode; // The min and max coordinates of the fractal.

  private FractalInputNode fractalInputNode; // The fractal input node.

  private int currentSteps; // The number of steps.

  /**
   * Constructs the input node for the user interface.
   * Initializes the input node with a text field for the number of steps and a fractal input node.
   *
   * @param description the description of the fractal to be drawn.
   * @param stepsInt the number of steps to be drawn.
   * @param controller the controller for the chaos game app.
   */
  public inputNode(ChaosGameDescription description, int stepsInt, ChaosGameControllerGui controller) {
    currentDescription = description;
    this.controllerGui = controller;
    this.currentSteps = stepsInt;
    this.inputNode = new GridPane();
    this.inputNode.getStyleClass().add("inputNode");

    // Adds a text field for the number of steps to the input node.
    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setMinWidth(250);
    steps.setText(String.valueOf(stepsInt));
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      this.currentSteps = Integer.parseInt(newValue);
    });

    this.findInstance(description);
    this.createInputNode(description);

    //Add a run button to the input node.
    Button runButton = new Button("Run");
    runButton.getStyleClass().add("button-rightPane");
    runButton.setOnAction(e -> {
      currentDescription.setTransforms(fractalInputNode.getTransforms());
      currentDescription.setMaxCoords(minMaxCoordsNode.getMaxCoords());
      currentDescription.setMinCoords(minMaxCoordsNode.getMinCoords());
      controller.changeDescription(currentDescription);
    });

    this.inputNode.add(steps, 0, 0);
    this.inputNode.add(new Label(" "), 0 ,1);
    // max/min coords
    this.inputNode.add(new Label(" "), 0 ,4);
    //Fractal input node
    this.inputNode.add(runButton, 0, 5);
  }


  /**
   * Creates the input node for the user interface, regardless of the type of fractal.
   *
   * @param description the description of the fractal to be drawn.
   */
  public void createInputNode(ChaosGameDescription description){
    this.minMaxCoordsNode = new MinMaxCoordsNode(description.getMinCoords(), description.getMaxCoords());
    this.inputNode.getChildren().removeIf(node -> node instanceof GridPane);

    this.inputNode.add(minMaxCoordsNode.getMinMaxNode(), 0,2);
    this.inputNode.add(fractalInputNode.getFractalNode(), 0 , 3);
  }


  /**
   * Returns the input node for the user interface.
   *
   * @return GridPane: the input node.
   */
  public GridPane getInputNode() {

    return this.inputNode;
  }

  /**
   * Changes the input node to a new input node.
   *
   * @param description the description of the fractal.
   */
  public void changeInputNode(ChaosGameDescription description, int steps){
    this.currentDescription = description;
    this.currentSteps = steps;
    this.findInstance(description);
    this.createInputNode(description);
  }
  /**
   * Returns the minimum coordinates of the fractal.
   *
   * @return the minimum coordinates.
   */
  public int getCurrentSteps() {

    return currentSteps;
  }

  /**
   * Finds the instance of the fractal input node.
   *
   * @param description the description of the fractal.
   */
  private void findInstance(ChaosGameDescription description){
    if (description.getTransform(0) instanceof AffineTransform2D){
     this.fractalInputNode =  new  AffineTransformNode(description);
    } else {
     this.fractalInputNode = new JuliaTransformNode(description);
    }
  }
}
