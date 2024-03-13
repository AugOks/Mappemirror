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
   * @throws IllegalArgumentException
   */
  public AffineTransform2D(Matrix2x2 matrix, Vector2D vectorV) {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix is null");
    }
    this.matrix = matrix;
    if (vectorV == null) {
      throw new IllegalArgumentException("Vector is null");
    }
    this.vector = vectorV;
  }

  /**
   * Performs an Affine transformation to the matrix and the vector.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a vector point in 2D space.
   * @throws IllegalArgumentException
   */
  @Override
  public Vector2D transform(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Vector point is null");
    }
    Vector2D vectorNew =  this.matrix.multiply(point);
    return vectorNew.add(this.vector);
  }
}
