package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.List;
import javafx.scene.control.Alert;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;

/**
 * Controller for the input nodes in the GUI.
 */
public class InputNodeController {

  private ChaosGameControllerGui controller;
  private InputNode inputNode;
  private ChaosGameDescription currentDescription;

  /**
   * Constructor for the InputNodeController class.
   *
   * @param controllerGui the controller for the GUI.
   * @param currentDescription the current description of the game.
   * @param inputNode the input node for the GUI.
   */

    public InputNodeController(ChaosGameControllerGui controllerGui,
                               ChaosGameDescription currentDescription, InputNode inputNode) {
        this.controller = controllerGui;
        this.currentDescription = currentDescription;

    }

  /**
   * Runs the fractal with the given min/max coordinates and fractal input.
   * @param minMaxCoordsNode the min/max coordinates node of the fractal.
   * @param fractalInputNode the fractal input node.
   */
  public void run(MinMaxCoordsNode minMaxCoordsNode, FractalInputNode fractalInputNode) {
      if (!minMaxCoordsNode.isValueValid() && !fractalInputNode.isValueValid()){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("You have invalid values in min/max coordinates and fractal input.");
        alert.showAndWait();
      }
      else if(!minMaxCoordsNode.isValueValid()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("One of the values in the min/max coordinates is invalid");
        alert.showAndWait();
      }
      else if (!fractalInputNode.isValueValid()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("One of the values in the fractal input is invalid");
        alert.showAndWait();
      }

      if(fractalInputNode.isValueValid() || minMaxCoordsNode.isValueValid()) {
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

}
