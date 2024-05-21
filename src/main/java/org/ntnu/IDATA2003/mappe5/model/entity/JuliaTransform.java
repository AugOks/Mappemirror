package org.ntnu.IDATA2003.mappe5.model.entity;

/**
 * A class that represents a julia transformation in 2D space.
 * This class transform a 2D vector using the julia transformation.
 */

public class JuliaTransform implements Transform2D {
  Complex julia; // the complex number of the julia transformation.
  int sign; // the sign of the complex number. Determines if it is the Conjugate.


  /**
   * Constructor for julia transformation.
   *
   * @param complex a complex vector.
   * @param sign    the sign of the complex vector. Must be -1 or 1.
   */
  public JuliaTransform(Complex complex, int sign) {

    if (complex == null) {
      throw new IllegalArgumentException("Complex is null");
    }
    if (!(sign == 1 || sign == -1)) {
      throw new IllegalArgumentException("Sign must be -1 or 1 ");
    }
    this.julia = new Complex(complex.getX0(), complex.getY0());
    this.sign = sign;
  }

  public String transformToString() {

    return julia.getX0() + ", " + julia.getY0();
  }

  /**
   * Performs the julia transformation.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a transformed 2D vector.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Vector point cannot be null");
    }
    Vector2D vector = point.sub(this.julia);
    Complex complex = new Complex(vector.getX0(), vector.getY0());
    complex = complex.sqrt();
    complex.scalar(sign);
    return complex;
  }

  /**
   * Gets the complex number of the julia transformation.
   *
   * @return the complex number.
   */
  public Complex getComplex() {
    return julia;
  }

  /**
   * Sets the complex number of the julia transformation.
   *
   * @param complex the complex number.
   */
  public void setComplex(Complex complex) {
    this.julia = complex;
  }

}
