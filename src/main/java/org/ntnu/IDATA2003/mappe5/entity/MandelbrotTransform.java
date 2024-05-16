package org.ntnu.IDATA2003.mappe5.entity;

//TODO get this class to work
//TODO add java doc to this class
public class MandelbrotTransform implements Transform2D {

private Complex mandelbrot;

  public MandelbrotTransform(Complex complex){
    this.mandelbrot = new Complex(complex.getX0(), complex.getY0());
  }

  /**
   * Performs the mandelbrot transformation.
   *
   * @param point A point in a 2D vector space. //no idea if this is correct.
   * @return a transformed 2D vector.
   */
  @Override
  public Vector2D transform(Vector2D point) {
    if (point == null) {
      throw new IllegalArgumentException("Vector point cannot be null");
    }
    Vector2D vector = point.sub(this.mandelbrot);
    Complex complexInput = new Complex(vector.getX0(), vector.getY0());
    mandelbrotMath(complexInput,2);
    return null;
  }

  @Override
  public String transformToString() {

    return mandelbrot.getX0() + " " + mandelbrot.getY0();
  }

  public static int mandelbrotMath(Complex z0, int max) {
    Complex z = z0;
    for (int t = 0; t < max; t++) {
     // if (z.abs() > 2.0) return t;
     // z = (Complex) z.times(z).add(z0);
      ;
    }
    return max;
  }
}
