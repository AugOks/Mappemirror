package org.ntnu.IDATA2003.mappe5.Ui;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

  private VBox inputNode; // The input node for the user interface.

  private GridPane inputNodeGrid; // The input node for the user interface.

  private ChaosGameDescription currentDescription; // The current description of the fractal.

  private MinMaxCoordsNode minMaxCoordsNode; // The min and max coordinates of the fractal.

  private FractalInputNode fractalInputNode; // The fractal input node.

  private int currentSteps; // The number of steps.
  private boolean sliders;
  private final InputNodeController controller;


  /**
   * Constructs the input node for the user interface.
   * Initializes the input node with a text field for the number of steps and a fractal input node.
   *
   * @param description the description of the fractal to be drawn.
   * @param stepsInt the number of steps to be drawn.
   */
  public InputNode(ChaosGameDescription description, int stepsInt, ChaosGameControllerGui controller) {
    this.controller = new InputNodeController(controller, description, this);
    this.currentSteps = stepsInt;
    this.inputNodeGrid = new GridPane();
    this.inputNode = new VBox(inputNodeGrid);

    this.inputNodeGrid.getStyleClass().add("inputNode");
    this.inputNodeGrid.setBackground(new Background(new BackgroundFill(Color.DARKGREY,
        null,null)));

    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.setMaxWidth(275);
    steps.setMinWidth(275);
    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      this.currentSteps = Integer.parseInt(newValue);
    });
    Label runStepsLabel = new Label("Run steps:");
    runStepsLabel.getStyleClass().add("input-title");
    this.inputNodeGrid.add(runStepsLabel, 0, 0);
    this.inputNodeGrid.add(steps, 0, 1);
    this.inputNodeGrid.add(new Label(""),0,2);

    this.findInstance(description);
    this.createInputNode(description, false);
    this.createRunButton();
  }


  /**
   * Creates the input node for the user interface, regardless of the type of fractal.
   *
   * @param description the description of the fractal to be drawn.
   */
  public void createInputNode(ChaosGameDescription description, boolean slidersOnOff){
    this.sliders = slidersOnOff;
    this.minMaxCoordsNode = new MinMaxCoordsNode(description.getMinCoords(),
        description.getMaxCoords(), controller);
    this.inputNodeGrid.getChildren().removeIf(node -> node instanceof GridPane);
    this.inputNodeGrid.add(minMaxCoordsNode.getMinMaxNode(this.sliders),0,3);
    this.inputNodeGrid.add(fractalInputNode.getFractalNode(),0,4);
  }


  /**
   * Returns the input node for the user interface.
   *
   * @return GridPane: the input node.
   */
  public VBox getInputNode() {
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
    this.findInstance(description);
    this.createInputNode(description, this.sliders);
  }
  /**
   * returns the current steps.
   *
   * @return The amount of steps to run.
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
     this.fractalInputNode = new JuliaTransformNode(description, this.controller);
    }
  }

  private void createRunButton(){
    Button runButton = new Button("Run");
    runButton.getStyleClass().add("button-rightPane");
    runButton.setOnAction(e -> {
      this.controller.run(minMaxCoordsNode, fractalInputNode);
    });
    HBox runButtonBox = new HBox(runButton);
    runButtonBox.setAlignment(Pos.CENTER);
    this.inputNodeGrid.add(runButtonBox,0,5);
  }
}
