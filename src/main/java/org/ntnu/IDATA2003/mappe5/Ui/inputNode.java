package org.ntnu.IDATA2003.mappe5.Ui;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;

/**
 * Represents the input box for the user interface regardless of what type of transform is active.
 */
public class inputNode {
  private VBox inputNode;

  private MinMaxCoordsNode minMaxCoordsNode;

  private FractalInputNode fractalInputNode;
  private int currentSteps;

  public inputNode(ChaosGameDescription description, int stepsInt) {
    this.inputNode = new VBox();
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
    this.inputNode.getChildren().add(steps);
    if (description.getTransform(0) instanceof AffineTransform2D) {
      this.fractalInputNode = new AffineTransformNode(description);
    } else {
      this.fractalInputNode = new JuliaTransformNode(description);
    }
    this.createInputNode(description);

  }

  public void createInputNode(ChaosGameDescription description){
    this.minMaxCoordsNode = new MinMaxCoordsNode(description.getMinCoords(), description.getMaxCoords());
    this.inputNode.getChildren().add(minMaxCoordsNode.getMinMaxNode());
    this.inputNode.getChildren().add(fractalInputNode.getFractalNode());
  }

  public VBox getInputNode() {
    return this.inputNode;
  }

  public int getCurrentSteps() {
    return currentSteps;
  }
}
