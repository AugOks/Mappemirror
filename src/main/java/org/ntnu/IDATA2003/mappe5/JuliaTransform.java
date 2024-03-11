package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.

public class JuliaTransform implements Transform2D {
  Complex julia; //TODO find better name.
  int sign; // the sign of the complex number. Determines if it is the Conjugate.


  /**
   * Constructor.
   *
   * @param complex a complex vector.
   * @param sign    the sign of the complex vector. Must be -1 or 1.
   * @throws IllegalArgumentException
   */
  public JuliaTransform(Complex complex, int sign) {

    if (complex == null) {
      throw new IllegalArgumentException("complex is null");
    }
    if (!(sign == 1 || sign == -1)) {
      throw new IllegalArgumentException("Sign must be -1 or 1 ");
    }

    this.julia = new Complex(complex.getX0(), complex.getY0());
    this.sign = sign;
  }

  /**
   * Performs the julia transformation.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a transformed 2D vector.
   * @throws IllegalArgumentException
   */
  public Vector2D transform(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Vector point cannot be null");
    }
    Vector2D vector =  this.julia.sub(point);
    Complex complex = new Complex(vector.getX0(), vector.getY0());
    complex =  complex.sqrt();
    return  complex;
  }
}
