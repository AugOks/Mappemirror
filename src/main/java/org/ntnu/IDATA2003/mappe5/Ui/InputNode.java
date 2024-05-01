package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.scene.paint.Color;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;

/**
 * Represents the input box for the user interface regardless of what type of transform is active.
 * Holds an interface variable that contains the input fields for the fractal.
 */
public class InputNode {

  private GridPane inputNode; // The input node for the user interface.

  private ChaosGameDescription currentDescription; // The current description of the fractal.

  private MinMaxCoordsNode minMaxCoordsNode; // The min and max coordinates of the fractal.

  private FractalInputNode fractalInputNode; // The fractal input node.

  private int currentSteps; // The number of steps.
  private  InputNodeController controller;


  /**
   * Constructs the input node for the user interface.
   * Initializes the input node with a text field for the number of steps and a fractal input node.
   * @param description the description of the fractal to be drawn.
   * @param stepsInt the number of steps to be drawn.
   */
  public InputNode(ChaosGameDescription description, int stepsInt, ChaosGameControllerGui controller) {
    this.controller = new InputNodeController(controller, description, this);
    this.currentSteps = stepsInt;
    this.inputNode = new GridPane();
    this.inputNode.getStyleClass().add("inputNode");
    this.inputNode.setBackground(new Background(new BackgroundFill(Color.DARKGREY,
        null,null)));
    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.setMaxWidth(200);
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      this.currentSteps = Integer.parseInt(newValue);
    });
    this.inputNode.add(steps, 0, 0);
    boolean julia = this.findInstance(description);
    this.createInputNode(description, julia);
    Button runButton = new Button("Run");
    runButton.getStyleClass().add("button-rightPane");
    runButton.setOnAction(e -> {
        this.controller.run(minMaxCoordsNode, fractalInputNode);
        });

    this.inputNode.add(runButton, 0, 4);

  }


  /**
   * Creates the input node for the user interface, regardless of the type of fractal.
   *
   * @param description the description of the fractal to be drawn.
   */
  public void createInputNode(ChaosGameDescription description, boolean julia){
    this.minMaxCoordsNode = new MinMaxCoordsNode(description.getMinCoords(),
        description.getMaxCoords(), julia, controller);
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
   this.controller.changeDescription(description);
    this.currentSteps = steps;
    boolean julia = this.findInstance(description);
    this.createInputNode(description, julia);
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

  private boolean findInstance(ChaosGameDescription description){
    boolean julia = false;
    if (description.getTransform(0) instanceof AffineTransform2D){
     this.fractalInputNode =  new  AffineTransformNode(description);
    } else {
     this.fractalInputNode = new JuliaTransformNode(description, this.controller);
     julia = true;
    }
    return  julia;
  }
}
