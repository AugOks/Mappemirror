package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public class Vector2D {


  private double x0;
  private double y0;

  /**
   * Constructor.
   * initializes the fields.
   *
   * @param x1 the X value for the 2D vector.
   * @param y1 the Y value for the 2D vector.
   */
  public Vector2D(double x1, double y1) {
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
    //TODO: Needs validation
  }
  /**
   * Sets the Y value of the vector.
   *
   * @param y the value to set this Y to.
   */
  protected void setY0(double y) {
    this.y0 = y;
    //TODO: Needs validation
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
   * Adds together this vector and other vector then returns the combined vector.
   *
   * @param otherVector the vector to be added to this vector.
   * @return this vector added to other vector.
   */
  public Vector2D add(Vector2D otherVector) {
    //TODO: needs Junit testing
    this.x0 += otherVector.getX0();
    this.y0 += otherVector.getY0();
    return this;
  }

  /**
   * Subtracts otherVector from this Vector then returns this vector.
   *
   * @param otherVector the vector to be subtracted from this vector.
   * @return other vector subtracted from this vector.
   */
  public Vector2D subtract(Vector2D otherVector) {
    //TODO: needs Junit testing
    this.x0 -= otherVector.getX0();
    this.y0 -= otherVector.getY0();
    return this;
  }
}
