package org.ntnu.IDATA2003.mappe5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * A test class for the matrix2x2 Class.
 */
class Matrix2x2Test {

  /**
   * Tests that the matrix multiplication performs as expected with known positive values.
   * Results were verified with Wolfram Alpha.
   */

  @Test
  public void testMatrix2x2MultiplicationWithPositiveArguments(){
    Matrix2x2 testMatrix = new Matrix2x2(1,1,1,1);
    Vector2D testVector = new Vector2D(2,2);
    Vector2D resultVector = new Vector2D(4,4);
    Vector2D productVector;
    productVector = testMatrix.multiply(testVector);
    assertEquals(productVector.getX0(), resultVector.getX0());
    assertEquals(productVector.getY0(), resultVector.getY0());

  }

  /**
   * Tests that the matrix multiplication method performs as expected when a
   * null object is passed to the method
   */

  @Test
  public  void testMatrixMultiplicationWithNullObject(){
    Matrix2x2 testMatrix = new Matrix2x2(1,2,3,4);
    Vector2D testVector = null;
    try {
      testMatrix.multiply(testVector);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

  /**
   * Tests that the matrix2x2 constructor performs as expected when passed NaN values.
   */
  @Test
  public  void testMatrixConstructorWithNaNValues(){
    try {
      Matrix2x2 matrixTest = new Matrix2x2(1 / 0, 1 / 0, 1 / 0, 1 / 0);
      fail();
    }catch (ArithmeticException a){
      assertTrue(true);
    }
  }



}