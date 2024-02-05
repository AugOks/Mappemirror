package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public class JuliaTransform implements Transform2D {
  Complex julia; //TODO find better name.
  int sign; //This must be -1 or 1

  /**
   * Constructor.
   *
   * @param point a complex vector point.
   * @param sign the sign of the complex vector.
   */
  public JuliaTransform(Complex point, int sign) {
    this.julia = point;
    this.sign = sign;
  }

  /**
   * performs the julia transformation.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a transformed 2D vector.
   */
  public Vector2D transform(Vector2D point) {
    this.julia.setX0(this.julia.getX0() - point.getX0());
    this.julia.setY0(this.julia.getY0() - point.getY0() * sign);
    return julia.sqrt();

  }
}
