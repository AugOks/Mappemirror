package org.ntnu.IDATA2003.mappe5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

//TODO: Write JavaDoc for this test class.

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
    Complex resultTest =  posTest.sqrt();
    assertEquals(resultTest.getX0(), 0.455,0.001);
    assertEquals(resultTest.getY0(), -1.098,0.001);
  }

  /**
   * Tests that the constructor performs as expected when giving it NaN values.
   */
  @Test
  public void complexTestConstructorWithNaNValues() {
    try {
      Complex testComplex = new Complex(1 / 0, 1 / 0);
      fail();
    } catch (ArithmeticException a) {
      assertTrue(true);
    }
  }

}