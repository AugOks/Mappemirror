package org.ntnu.IDATA2003.mappe5;
import  java.lang.Math;

/**
 * A class that represents a complex number.
 * <p> The class extends the Vector2D class and consists of a real and imaginary number
 * <p> Adds a method sqrt() to find the square root of a complex number.</p>
 */
public class Complex extends Vector2D {

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
    double sqrt = Math.sqrt(x * x + y * y);           //finds the length of the vector.
    double firstOperation =  Math.sqrt(0.5*(sqrt + x));//finds the real number of the vector.
    double secondOperation = Math.sqrt(0.5*(sqrt - x)); //finds the imaginary number for the vector.
    this.setX0(firstOperation);
    this.setY0(secondOperation);
    return this;
  }

}
