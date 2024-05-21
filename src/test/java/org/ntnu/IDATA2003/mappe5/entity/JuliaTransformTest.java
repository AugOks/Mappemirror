package org.ntnu.IDATA2003.mappe5.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;


/**
 * This class tests the JuliaTransform class.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link JuliaTransform#JuliaTransform(Complex, int)}
 *   <li> {@link JuliaTransform#transform(Vector2D)}
 *   <li> {@link JuliaTransform#transformToString()}
 *   <li> {@link JuliaTransform#getComplex()}
 *   <li> {@link JuliaTransform#JuliaTransform(Complex, int)}
 * </ul>
 */

class JuliaTransformTest {

  /**
   * Test the method {@link JuliaTransform#transform(Vector2D)}.
   * This test is for a positive test of julia transform with positive numbers and sign.
   * This test bases it correct results of wolfram alpha.
   */

  @Test
  public void testJuliaTransformWithPositiveArgumentsPositiveSign() {
    Complex complexTest = new Complex(.3, .6);

    JuliaTransform juliaTest = new JuliaTransform(complexTest, 1);
    Vector2D testVector = new Vector2D(0.4, 0.2);

    testVector = juliaTest.transform(testVector);
    assertEquals(0.5061178531536732, testVector.getX0());
    assertEquals(-0.3951648786024423, testVector.getY0());

  }

  /**
   * Test the method {@link JuliaTransform#transform(Vector2D)}.
   * This test is for a positive test of julia transform with negative numbers and positive sign.
   * This test bases it correct results of wolfram alpha.
   */

  @Test
  public void testJuliaTransformWithPositiveArgumentsNegativeNumbers() {
    Complex complexTest = new Complex(.3, .6);

    JuliaTransform juliaTest = new JuliaTransform(complexTest, -1);
    Vector2D testVector = new Vector2D(0.4, 0.2);

    testVector = juliaTest.transform(testVector);
    assertEquals(-0.5061178531536732, testVector.getX0());
    assertEquals(0.3951648786024423, testVector.getY0());

  }

  /**
   * Test the method {@link JuliaTransform(Complex, int)}.
   * Tests that the constructor performs as expected when passed a null object.
   * Expected to throw an {@link IllegalArgumentException}
   */
  @Test
  public void testJuliaTransformConstructorWithNullComplex() {
    try {
      JuliaTransform juliaTest = new JuliaTransform(null, 1);
      fail();
    } catch (IllegalArgumentException e) {
      if (e.getMessage().equals("complex is null")) {
        assertTrue(true);
      }
    }
  }

  /**
   * Tests that the constructor performs as expected when passed an invalid sign value.
   * Expected to throw an {@link IllegalArgumentException}
   */
  @Test
  public void testJuliaTransformWithInvalidSign() {
    try {
      Complex testComplex = new Complex(1, 1);
      JuliaTransform juliaTest = new JuliaTransform(testComplex, 2);
      fail();
    } catch (IllegalArgumentException e) {
      if (e.getMessage().equals("Sign must be -1 or 1 ")) {
        assertTrue(true);
      }
    }
  }

  /**
   * Tests that the transform method performs as expected when passed a null object.
   */
  @Test
  public void testJuliaTransformTransformationWithNullObject() {
    try {
      Complex testComplex = new Complex(1, 1);
      Vector2D testVector = null;
      JuliaTransform juliaTest = new JuliaTransform(testComplex, 1);
      juliaTest.transform(testVector);

      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link JuliaTransform#transformToString()}.
   * This test is for a positive test of julia transform with positive numbers and sign.
   */
  @Test
  public void testTransformToString() {
    Complex complexTest = new Complex(0.3, 0.6);

    JuliaTransform juliaTest = new JuliaTransform(complexTest, 1);
    assertEquals("0.3, 0.6", juliaTest.transformToString());
  }

  /**
   * Test the method {@link JuliaTransform#getComplex()}.
   * This test is for a positive test of julia transform with positive numbers and sign.
   */
  @Test
  public void testGetComplex() {
    Complex complexTest = new Complex(.3, .6);

    JuliaTransform juliaTest = new JuliaTransform(complexTest, 1);
    assertEquals(complexTest.getX0(), juliaTest.getComplex().getX0());
    assertEquals(complexTest.getY0(), juliaTest.getComplex().getY0());
  }

}