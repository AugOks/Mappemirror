package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public abstract class Transform2D {

  /**
   * Abstract method to be inherited.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return the transformed vector.
   */
  public abstract Vector2D transform(Vector2D point);

}