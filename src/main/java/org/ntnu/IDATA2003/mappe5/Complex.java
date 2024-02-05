package org.ntnu.IDATA2003.mappe5;

//TODO: Write JavaDoc for this class.
public class Complex extends Vector2D {


  public Complex(double img, double reale) {
    super(img, reale);
  }

  /**
   * finds the square root of a complex number.
   *
   * @return the square roof of the complex number.
   */
  public Complex sqrt() {
    Complex dummyComplex = new Complex(1, 1);
    return dummyComplex;
    //TODO: create square root of complex number method.
  }
}
