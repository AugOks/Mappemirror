//TODO: Write JavaDoc for this class.
public class JuliaTransform extends Transform2D {
  Complex point;
  int sign;

  /**
   * Constructor.
   *
   * @param point a complex vector point.
   * @param sign the sign of the complex vector.
   */
  public JuliaTransform(Complex point, int sign) {
    this.point = point;
    this.sign = sign;
  }

  /**
   * performs the julia transformation.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a transformed 2D vector.
   */
  public Vector2D transform(Vector2D point) {
    return point;
    //TODO: add body for transform.
  }
}
