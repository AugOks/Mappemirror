package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
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
   */
  public Vector2D multiply(Vector2D vector) {
    //TODO needs Junit testing
    Vector2D matrixProduct;
    double firstMulti = this.a * vector.getX0();
    double secondMulti = this.c * vector.getX0();
    double thirdMulti = this.b * vector.getY0();
    double fourthMulti = this.d * vector.getY0();
    matrixProduct = new Vector2D(firstMulti + secondMulti, thirdMulti + fourthMulti);
    return matrixProduct;
  }

}
