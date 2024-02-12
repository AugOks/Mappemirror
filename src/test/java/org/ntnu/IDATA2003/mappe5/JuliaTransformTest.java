package org.ntnu.IDATA2003.mappe5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

//TODO: Write JavaDoc for this test class.

class JuliaTransformTest {

  /**
   * Test the method {@link JuliaTransform#transform(Vector2D)}.
   * This test is for a positive test of julia transform with positive numbers and sign.
   * This test bases it correct results of wolfram alpha.
   */

  @Test
  public void testJuliaTransformWithPositiveArgumentsPositiveSign(){
    Complex complexTest = new Complex(2,2);

    JuliaTransform juliaTest = new JuliaTransform(complexTest,1);
    Vector2D testVector = new Vector2D(1,1);

    testVector =  juliaTest.transform(testVector);
   assertEquals(1.09868411346781, testVector.getX0());
    assertEquals(0.4550898605622274, testVector.getY0());

  }

  /**
   * Test the method {@link JuliaTransform#transform(Vector2D)}.
   * This test is for a positive test of julia transform with negative numbers and positive sign.
   * This test bases it correct results of wolfram alpha.
   */

  @Test
  public void testJuliaTransformWithPositiveArgumentsNegativeNumbers(){
    Complex complexTest = new Complex(2,2);

    JuliaTransform juliaTest = new JuliaTransform(complexTest,1);
    Vector2D testVector = new Vector2D(3,3);

    testVector =  juliaTest.transform(testVector);
    assertEquals(0.4550898605622274, testVector.getX0());
    assertEquals(1.09868411346781, testVector.getY0());
  }
}