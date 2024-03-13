package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.


/**
 * Represents a 2x2 matrix containing 4 double values.
 * Currently, does not have any constraints, perhaps a lower and/or upper bound is necessary.
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
    this.a = a1;
    this.b = b1;
    this.c = c1;
    this.d = d1;

  }

  /**
   * Performs matrix multiplication on this matrix with other 2D Vector.
   *
   * @param vector the vector to be multiplied with this matrix.
   * @return the vector product of the matrix multiplication.
   * @throws IllegalArgumentException
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
   * sets the value of the first tuple in the matrix.
   *
   * @param value the value to be set.
   */
  public void setA(double value) {
    this.a = value;
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
   * sets the value of the bottom left element in the matrix.
   *
   * @param value the value to be set.
   */
  public void setC(double value) {
    this.c = value;
  }

  /**
   * sets the value of the second tuple in the matrix.
   *
   * @param value the value to be set.
   */
  public void setD(double value) {
    this.d = value;

  }

}
