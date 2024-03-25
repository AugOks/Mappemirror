package org.ntnu.IDATA2003.mappe5.logic;

import java.util.List;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

/**
 * Description creates the information for ChaosGame important for initializing the canvas and the
 * transformations used.
 */

public class ChaosGameDescription {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;
  private String name;


  /**
   * Create an instance of a ChaosGameDescription with a list of transformations and min/mas coords
   * of the canvas.
   *
   * @param transforms a list of transformations from transform2D
   * @param minCoords  a 2D vector of the minimum coordinates
   * @param maxCoords  a 2D vector of the maximum coordinates
   */
  public ChaosGameDescription(List<Transform2D> transforms, Vector2D minCoords, Vector2D maxCoords,
                              String name) {
    setTransforms(transforms);
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
    setName(name);

  }

  /**
   * Returns the name of the fractal if one exists.
   *
   * @return the name of the fractal.
   */
  public String getName() {
    return this.name;
  }

  /**
   * sets the name of the Fractal if one is applicable.
   *
   * @param name the name of the fractal.
   */
  private void setName(String name) {
    if (name == null) {
      name = "";
    }
    this.name = name;
  }

  /**
   * Returns the max coords.
   *
   * @return max coords
   */
  public Vector2D getMaxCoords() {

    return this.maxCoords;
  }

  /**
   * Sets the max coordinates of the canvas.
   *
   * @param maxCoords the coordinates to be set.
   */
  public void setMaxCoords(Vector2D maxCoords) {
    if (maxCoords == null) {
      throw new IllegalArgumentException("Max coordinates cannot be null");
    }
    this.maxCoords = maxCoords;
  }

  /**
   * Returns the min coords.
   *
   * @return min coords
   */
  public Vector2D getMinCoords() {

    return this.minCoords;
  }

  /**
   * Sets the min coordinates of the canvas.
   *
   * @param minCoords the coordinates to be set.
   */
  public void setMinCoords(Vector2D minCoords) {
    if (minCoords == null) {
      throw new IllegalArgumentException("MinCoords cannot be null");
    }
    this.minCoords = minCoords;
  }

  /**
   * Returns the transformation corresponding to the given index.
   *
   * @param index representing the transformation wanted.
   * @return the transformation corresponding to the given index.
   * @throws IndexOutOfBoundsException if the index is negative or larger than the amount of
   *                                   transforms.
   */
  public Transform2D getTransform(int index) {
    if (index < 0 || index >= this.getTransformSize()) {
      throw new IndexOutOfBoundsException("index cannot be negative or larger than the amount "
          + "transforms.");
    }
    return this.transforms.get(index);
  }

  /**
   * Returns all transforms in memory.
   *
   * @return all transforms.
   */
  public List<Transform2D> getAllTransforms() {

    return this.transforms;
  }

  /**
   * Returns the amount of transforms this description holds onto.
   *
   * @return the amount of transforms as an int.
   */
  public int getTransformSize() {

    return this.transforms.size();
  }

  /**
   * Sets a list of transformations of the chaos games.
   *
   * @param transforms the transformations available.
   * @throws IllegalArgumentException if the list is null.
   */
  public void setTransforms(List<Transform2D> transforms) {
    if (transforms == null) {
      throw new IllegalArgumentException("Transforms list cannot be null");
    }
    this.transforms = transforms;

  }
}
