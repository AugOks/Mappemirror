package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public class Matrix2x2 {
  private double a00; // The first tuple in the matrix.
  private double a01; // The top right value in the matrix.
  private double a10; // The bottom left value in the matrix.
  private double a11; // The second tuple in the matrix.

  /**
   * Constructor.
   *
   * @param a00 the first tuple in the matrix.
   * @param a01 the top right value in the matrix.
   * @param a10 the bottom left value in the matrix.
   * @param a11 the second tuple in the matrix.
   */
  public Matrix2x2(double a00, double a01, double a10, double a11) {
    this.a00 = a00;
    this.a01 = a01;
    this.a10 = a10;
    this.a11 = a11;
  }

  /**
   * Performs matrix multiplication on this matrix with other 2D Vector.
   *
   * @param vector the vector to be multiplied with this matrix.
   * @return the vector product of the matrix multiplication.
   */
  public Vector2D multiply(Vector2D vector) {
    return  vector;
    //TODO Finish matrix multiplication method.
  }

}
