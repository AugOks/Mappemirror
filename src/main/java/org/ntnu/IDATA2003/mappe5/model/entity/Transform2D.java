package org.ntnu.IDATA2003.mappe5.model.entity;

/**
 * Interface for 2D transformations.
 */
public interface Transform2D {

  /**
   * Interface method to be implemented.
   *
   * @param point A point in a 2D vector space.
   * @return the transformed vector.
   */
  public Vector2D transform(Vector2D point);

  /**
   * Interface method to be implemented.
   *
   * @return the transformed vector as a string.
   */
  public String transformToString();


}
