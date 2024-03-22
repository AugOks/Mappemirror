package org.ntnu.IDATA2003.mappe5;


/**
 * A class that represents an affine transformation in 2D space.
 */
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
   * Returns the matrix of the transformation.
   *
   * @return the matrix of the transformation.
   */
  public Matrix2x2 getMatrix() {
    return matrix;
  }

  /**
   * Returns the vector of the transformation.
   *
   * @return the vector of the transformation.
   */
  public Vector2D getVector() {
    return vector;
  }

  /**
   * Transforms the matrix and the vector to a string.
   *
   * @return String of the matrix and the vector.
   */
  @Override
  public String transformToString() {
    return this.matrix.matrixToString() + ", " + vector.toString();
  }

  /**
   * Performs an Affine transformation to the matrix and the vector.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a vector point in 2D space.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Vector point is null");
    }
    Vector2D vectorNew = this.matrix.multiply(point);
    return vectorNew.add(this.vector);

  }

}
