package org.ntnu.IDATA2003.mappe5;

/**
 * Represents a vector/coordinates in 2D space.
 * This class currently does not have any constraints as any valid double number
 * would be acceptable, perhaps an upper or lower bound is necessary at some point.
 */

public class Vector2D {

  private double x0; //The x0 value of the vector
  private double y0; //The x1 value of the vector

  /**
   * Constructor.
   * initializes the fields.
   *
   * @param x1 the X0 value for the 2D vector.
   * @param y1 the X1 value for the 2D vector.
   */
  public Vector2D(double x1, double y1) {
    if (Double.isNaN(x1) || Double.isNaN(y1)) {
      throw new ArithmeticException("One of the parameters is not a number");
    }
    this.x0 = x1;
    this.y0 = y1;
  }

  /**
   * Returns the X0 value of the vector.
   *
   * @return The X0 value of the vector.
   */
  public double getX0() {

    return this.x0;
  }

  /**
   * Sets the X0 value of the vector.
   *
   * @param x0 the X0 value of the vector.
   */
  protected void setX0(double x0) {

    this.x0 = x0;
  }

  /**
   * Returns the X1 value of the vector.
   *
   * @return the X1 value of the vector.
   */
  public double getY0() {

    return this.y0;
  }

  /**
   * Sets the Y value of the vector.
   *
   * @param x1 the X1 value of the vector.
   */
  protected void setY0(double x1) {

    this.y0 = x1;
  }

  /**
   * Adds together two vectors then returns the product.
   *
   * @param otherVector the vector to be added to this vector.
   * @return this vector added to other vector.
   */
  public Vector2D add(Vector2D otherVector) {
    if (otherVector == null) {
      throw new IllegalArgumentException("Vector is null");
    }
    double xValue = this.x0 + otherVector.getX0();
    double yValue = this.y0 + otherVector.getY0();
    return new Vector2D(xValue, yValue);
  }

  /**
   * Subtracts otherVector from this Vector then returns this vector.
   *
   * @param otherVector the vector to be subtracted from this vector.
   * @return other vector subtracted from this vector.
   */
  public Vector2D sub(Vector2D otherVector) {
    if (otherVector == null) {
      throw new IllegalArgumentException("Vector is null");
    }
    double xValue = this.x0 - otherVector.getX0();
    double yValue = this.y0 - otherVector.getY0();
    return new Vector2D(xValue, yValue);

  }
}
