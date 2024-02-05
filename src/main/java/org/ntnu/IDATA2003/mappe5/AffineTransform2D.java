package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public class AffineTransform2D implements Transform2D {
  private Matrix2x2 matrix;
  private Vector2D vector;

  /**
   * Constructor.
   *
   * @param matrix  the matrix for the transformation.
   * @param vectorV the vector to be used in the transformation.
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vectorV) {
    this.matrix = matrix;
    this.vector = vectorV;
  }

  /**
   * Performs an Affine transformation to the matrix and the vector.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a vector point in 2D space.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    this.matrix.multiply(this.vector);
    this.vector.add(point);
    return this.vector;
  }
}
