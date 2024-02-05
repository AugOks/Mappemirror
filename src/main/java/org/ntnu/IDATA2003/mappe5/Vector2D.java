package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public class Vector2D {


  private double x0;
  private double x1;

  /**
   * Constructor.
   * initializes the fields.
   *
   * @param x2 the X value for the 2D vector.
   * @param x3 the Y value for the 2D vector.
   */
  public Vector2D(double x2, double x3) {
    this.x0 = x2;
    this.x1 = x3;
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
   * Returns the Y value of the vector.
   *
   * @return the Y value of the vector.
   */
  public double getX1() {
    return this.x1;
  }

  /**
   * Adds together this vector and other vector then returns the combined vector.
   *
   * @param otherVector the vector to be added to this vector.
   * @return this vector added to other vector.
   */
  public Vector2D add(Vector2D otherVector) {
    this.x0 += otherVector.getX0();
    this.x1 += otherVector.getX1();
    return this;
  }

  /**
   * Subtracts otherVector from this Vector then returns this vector.
   *
   * @param otherVector the vector to be subtracted from this vector.
   * @return other vector subtracted from this vector.
   */
  public Vector2D subtract(Vector2D otherVector) {
    this.x0 -= otherVector.getX0();
    this.x1 -= otherVector.getX1();
    return this;
  }
}
