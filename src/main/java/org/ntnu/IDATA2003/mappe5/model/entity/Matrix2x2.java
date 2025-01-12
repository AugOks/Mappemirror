package org.ntnu.IDATA2003.mappe5.model.entity;

/**
 * Represents a 2x2 matrix containing 4 double values.
 * Matrix2x2 = [[a, b], [c, d]].
 * This class does not have any constraints as any valid double number.
 * Has a function for multiplying the matrix with a 2D vector.
 */
public class Matrix2x2 {
  private double a; // The first tuple in the matrix.
  private double b; // The top right value in the matrix.
  private double c; // The bottom left value in the matrix.
  private double d; // The second tuple in the matrix.

  /**
   * Constructor.
   *
   * @param a1 the first tuple in the matrix.
   * @param b1 the top right value in the matrix.
   * @param c1 the bottom left value in the matrix.
   * @param d1 the second tuple in the matrix.
   */
  public Matrix2x2(double a1, double b1, double c1, double d1) {
    this.setA(a1);
    this.setB(b1);
    this.setC(c1);
    this.setD(d1);
  }

  /**
   * Constructor.
   * Initializes the fields based on the matrix.
   * Essentially a copy constructor.
   *
   * @param matrix the matrix to be copied.
   */
  public Matrix2x2(Matrix2x2 matrix) {
    this.a = matrix.getA();
    this.b = matrix.getB();
    this.c = matrix.getC();
    this.d = matrix.getD();
  }

  /**
   * Performs matrix multiplication on this matrix with other 2D Vector.
   *
   * @param vector the vector to be multiplied with this matrix.
   * @return the vector product of the matrix multiplication.
   */
  public Vector2D multiply(Vector2D vector) {
    if (vector == null) {
      throw new IllegalArgumentException("Vector is null");
    }
    Vector2D matrixProduct;
    double firstMulti = this.a * vector.getX0();
    double secondMulti = this.b * vector.getY0();
    double thirdMulti = this.c * vector.getX0();
    double fourthMulti = this.d * vector.getY0();
    matrixProduct = new Vector2D(firstMulti + secondMulti, thirdMulti + fourthMulti);
    return matrixProduct;
  }

  /**
   * Converts the matrix to a string.
   *
   * @return the matrix as a string.
   */
  public String matrixToString() {

    return this.a + ", " + this.b + ", " + this.c + ", " + this.d;
  }

  /**
   * Gets the value of the top left element in the matrix.
   *
   * @return the value of the top left element in the matrix.
   */
  public double getA() {

    return a;
  }

  /**
   * Sets the value of the top left element in the matrix.
   *
   * @param value the value to be set.
   */
  public void setA(double value) {

    this.a = value;
  }

  /**
   * Gets the value of the top right element in the matrix.
   *
   * @return the value of the top right element in the matrix.
   */
  public double getB() {

    return b;
  }

  /**
   * sets the value of the top right element in the matrix.
   *
   * @param value the value to be set.
   */
  public void setB(double value) {

    this.b = value;
  }

  /**
   * Gets the value of the bottom left element in the matrix.
   *
   * @return the value of the bottom left element in the matrix.
   */
  public double getC() {

    return c;
  }

  /**
   * sets the value of the bottom left element in the matrix.
   *
   * @param value the value to be set.
   */
  public void setC(double value) {

    this.c = value;
  }

  /**
   * Gets the value of the bottom right element in the matrix.
   *
   * @return the value of the bottom right element in the matrix.
   */
  public double getD() {

    return d;
  }

  /**
   * sets the value of the bottom right element in the matrix.
   *
   * @param value the value to be set.
   */
  public void setD(double value) {

    this.d = value;

  }
}
