package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.List;
import javafx.scene.control.Alert;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;


public class InputNodeController {

  private ChaosGameControllerGui controller;
  private InputNode inputNode;
  private ChaosGameDescription currentDescription;


    public InputNodeController(ChaosGameControllerGui controllerGui,
                               ChaosGameDescription currentDescription, InputNode inputNode) {
        this.controller = controllerGui;
        this.currentDescription = currentDescription;

    }

    public void run(MinMaxCoordsNode minMaxCoordsNode, FractalInputNode fractalInputNode) {
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
    }

    public void changeDescription(ChaosGameDescription description) {
      this.currentDescription = description;
    }
    public void changeTransform(List<Transform2D> transforms) {
      currentDescription.setTransforms(transforms);
      controller.changeDescription(currentDescription);
    }
    public void changeMinMaxCoords(Vector2D minCoords, Vector2D maxCoords) {
      currentDescription.setMinCoords(minCoords);
      currentDescription.setMaxCoords(maxCoords);
      controller.changeDescription(currentDescription);
    }


}
