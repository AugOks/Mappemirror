package org.ntnu.IDATA2003.mappe5.view;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.IDATA2003.mappe5.controller.ChaosGameControllerGui;
import org.ntnu.IDATA2003.mappe5.controller.InputNodeController;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;

/**
 * Represents the input box for the user interface regardless of what type of transform is active.
 * Holds an interface variable that contains the input fields for the fractal.
 */
public class InputNode {

  private final InputNodeController controller;
  private final GridPane inputNode; // The input node for the user interface.
  private ChaosGameDescription currentDescription; // The current description of the fractal.
  private MinMaxCoordsNode minMaxCoordsNode; // The min and max coordinates of the fractal.
  private FractalInputNode fractalInputNode; // The fractal input node.
  private int currentSteps; // The number of steps.
  private boolean sliders;


  /**
   * Constructs the input node for the user interface.
   * Initializes the input node with a text field for the number of steps and a fractal input node.
   *
   * @param description the description of the fractal to be drawn.
   * @param stepsInt    the number of steps to be drawn.
   */
  public InputNode(ChaosGameDescription description, int stepsInt,
                   ChaosGameControllerGui controller) {
    this.controller = new InputNodeController(controller, description, this);
    this.currentSteps = stepsInt;
    this.inputNode = new GridPane();
    this.runWhenEnterPressed(inputNode);
    this.inputNode.getStyleClass().add("inputNode");
    TextField steps = new TextField();
    steps.setPromptText("Steps");
    steps.setText(String.valueOf(stepsInt));
    steps.setMaxWidth(275);
    steps.setMinWidth(275);
    Label runStepsLabel = new Label("Run steps:");
    runStepsLabel.getStyleClass().add("input-title");
    this.inputNode.add(runStepsLabel, 0, 0);
    this.inputNode.add(steps, 0, 1);
    this.inputNode.add(new Label(""), 0, 2);

    this.findInstance(description);
    this.createInputNode(description, false);
    this.createRunButton();

    steps.textProperty().addListener((observable, oldValue, newValue) -> {
      try {
        this.currentSteps = Integer.parseInt(newValue);
      } catch (NumberFormatException e) {
        steps.setText(oldValue);
      }
    });
    steps.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ENTER)) {
        this.currentSteps = Integer.parseInt(steps.getText());
      }
    });
  }

  /**
   * Runs the fractal when the enter key is pressed.
   *
   * @param node the node to run the fractal.
   */
  private void runWhenEnterPressed(Node node) {
    node.setOnKeyPressed(e -> {
      if (e.getCode().equals(KeyCode.ENTER)) {
        this.controller.run(minMaxCoordsNode, fractalInputNode);
      }
    });
  }

  /**
   * Creates the input node for the user interface, regardless of the type of fractal.
   *
   * @param description the description of the fractal to be drawn.
   */
  public void createInputNode(ChaosGameDescription description, boolean slidersOnOff) {
    this.sliders = slidersOnOff;
    this.minMaxCoordsNode = new MinMaxCoordsNode(description.getMinCoords(),
                                                 description.getMaxCoords(), controller);
    this.inputNode.getChildren().removeIf(node -> node instanceof GridPane);
    this.inputNode.add(minMaxCoordsNode.getMinMaxNode(this.sliders), 0, 3);
    this.inputNode.add(fractalInputNode.getFractalNode(), 0, 4);
  }


  /**
   * Returns the input node for the user interface.
   *
   * @return GridPane: the input node.
   */
  public VBox getInputNode() {
    return new VBox(this.inputNode);
  }

  /**
   * Changes the input node to a new input node.
   *
   * @param description the description of the fractal.
   */
  public void changeInputNode(ChaosGameDescription description, int steps) {
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

  private void findInstance(ChaosGameDescription description) {
    if (description.getTransform(0) instanceof AffineTransform2D) {
      this.fractalInputNode = new AffineTransformNode(description, this.controller);
    } else {
      this.fractalInputNode = new JuliaTransformNode(description, this.controller);
    }
  }

  private void createRunButton() {
    Button runButton = new Button("Run");
    runButton.getStyleClass().add("button-rightPane");
    runButton.setOnAction(e -> {
      this.controller.run(minMaxCoordsNode, fractalInputNode);
    });
    HBox runButtonBox = new HBox(runButton);
    runButtonBox.setAlignment(Pos.CENTER);
    this.inputNode.add(new Label(""), 0, 5);
    this.inputNode.add(runButtonBox, 0, 6);
  }
}
