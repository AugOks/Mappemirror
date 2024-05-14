package org.ntnu.IDATA2003.mappe5.Ui;

import java.util.List;
import javafx.scene.Node;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;

/**
 * Interface for the input nodes for the fractal. This is used to get the node and the transforms
 * without the program knowing what kind of fractal it is.
 */

public interface FractalInputNode {
  /**
   * Returns the fractal node.
   * @return the fractal node.
   */
  public Node getFractalNode();

  /**
   * Returns the transforms for the fractal.
   * @return the transforms for the fractal.
   */
  public List<Transform2D> getTransforms();
  /**
   * Checks if the value is valid.
   * @return true if the value is valid, false otherwise.
   */
  public boolean isValueValid();
}
