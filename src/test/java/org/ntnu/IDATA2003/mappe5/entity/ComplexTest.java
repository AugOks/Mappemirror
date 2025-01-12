package org.ntnu.IDATA2003.mappe5.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;


/**
 * This class tests the Complex class.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link Complex#sqrt()}
 *   <li> {@link Complex#Complex(double, double)}
 * </ul>
 */

class ComplexTest {

  /**
   * Test the method {@link Complex#sqrt()}.
   * This test is for a positive test of complex sqrt() with positive numbers.
   * This test bases it correct results of wolfram alpha.
   */

  @Test
  public void ComplexTestPositiveTestPositiveNumbers() {
    Complex posTest = new Complex(1, 1);
    Complex resultTest = posTest.sqrt();
    assertEquals(resultTest.getX0(), 1.09868411346781);
    assertEquals(resultTest.getY0(), 0.4550898605622274);
  }

  /**
   * Test the method {@link Complex#sqrt()}.
   * This test is for a positive test of complex sqrt() with negative numbers.
   * This test bases it correct results of wolfram alpha.
   */
  @Test
  public void ComplexTestPositiveTestNegativeNumbers() {
    Complex posTest = new Complex(-1, -1);
    Complex resultTest = posTest.sqrt();
    assertEquals(resultTest.getX0(), 0.455, 0.001);
    assertEquals(resultTest.getY0(), -1.098, 0.001);
  }


}