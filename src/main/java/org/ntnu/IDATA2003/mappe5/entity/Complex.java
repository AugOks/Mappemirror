package org.ntnu.IDATA2003.mappe5.entity;

/**
 * A class that represents a complex number.
 *
 * <p>The class extends the Vector2D class and consists of a real and imaginary number
 * <p>Adds a method sqrt() to find the square root of a complex number.</p>
 */
public class Complex extends Vector2D {

  /**
   * Constructor.
   *
   * @param real the real number of the complex value
   * @param imag the imaginary number of the complex value
   */
  public Complex(double real, double imag) {

    super(real, imag);
  }

  /**
   * Finds the square root of a complex number.
   *
   * @return the square roof of the complex number.
   */
  public Complex sqrt() {
    double x = this.getX0();
    double y = this.getY0();
    double sqrt = Math.sqrt(x * x + y * y); //finds the length of the vector.
    double firstOperation = Math.sqrt(0.5 * (sqrt + x)); //finds the real number of the vector.
    double secondOperation = Math.signum(y) * Math.sqrt(0.5 * (sqrt - x));
    //finds the imaginary number for the vector.

    return new Complex(firstOperation, secondOperation);
  }

  /**
   * Sets the Y0 value (imaginary value) of the vector.
   *
   * @param y0 the Y0 value of the vector.
   */
  public void setY0(double y0) {

    super.setY0(y0);
  }

}
