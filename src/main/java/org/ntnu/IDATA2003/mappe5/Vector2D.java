package org.ntnu.IDATA2003.mappe5;

//TODO: Write more JavaDoc for this class?

/**
 * Represents a vector in 2D space.
 * This class currently does not have any constraints as any valid double number
 * would be acceptable, perhaps an upper or lower bound is necessary at some point.
 */
public class Vector2D {

  private double x0;
  private double y0;


  /**
   * Constructor.
   * initializes the fields.
   *
   * @param x1 the X value for the 2D vector.
   * @param y1 the Y value for the 2D vector.
   * @throws ArithmeticException
   */
  public Vector2D(double x1, double y1) {
    if (Double.isNaN(x1) || Double.isNaN(y1)) {
      throw new ArithmeticException("one of the paramaters is not a number");
    }
    this.x0 = x1;
    this.y0 = y1;
  }

  /**
   * Returns the X value of the vector.
   *
   * @return The X value of the vector.
   */
  public double getX0() {

    return this.x0;
  }

  /**
   * Sets the X value of the vector.
   *
   * @param x the value to set this x to.
   */
  protected void setX0(double x) {

    this.x0 = x;
  }

  /**
   * Returns the Y value of the vector.
   *
   * @return the Y value of the vector.
   */
  public double getY0() {

    return this.y0;
  }

  /**
   * Sets the Y value of the vector.
   *
   * @param y the value to set this Y to.
   */
  protected void setY0(double y) {

    this.y0 = y;
  }

  /**
   * Adds together this vector and other vector then returns the combined vector.
   *
   * @param otherVector the vector to be added to this vector.
   * @return this vector added to other vector.
   * @throws IllegalArgumentException
   */
  public Vector2D add(Vector2D otherVector) {

    if (otherVector == null) {
      throw new IllegalArgumentException("Vector is null");
    }

    double xValue = this.x0 + otherVector.getX0();
    double yValue = this.y0 + otherVector.getY0();
    Vector2D returnVector = new Vector2D(xValue, yValue);
    return returnVector;
  }

  /**
   * Subtracts otherVector from this Vector then returns this vector.
   *
   * @param otherVector the vector to be subtracted from this vector.
   * @return other vector subtracted from this vector.
   * @throws IllegalArgumentException
   */
  public Vector2D sub(Vector2D otherVector) {
    //TODO: needs Junit testing
    if (otherVector == null) {
      throw new IllegalArgumentException("Vector is null");
    }
    double xValue = this.x0 - otherVector.getX0();
    double yValue = this.y0 - otherVector.getY0();
    Vector2D returnVector = new Vector2D(xValue, yValue);
    return returnVector;

  }
}
