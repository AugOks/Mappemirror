package org.ntnu.IDATA2003.mappe5.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

/**
 * A test class for the matrix2x2 Class.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link Matrix2x2#Matrix2x2(double, double, double, double)}
 *   <li> {@link Matrix2x2#Matrix2x2(Matrix2x2)}
 *   <li> {@link Matrix2x2#multiply(Vector2D)}
 *   <li> {@link Matrix2x2#matrixToString()}
 * </ul>
 */
class Matrix2x2Test {

  /**
   * Test the method {@link Matrix2x2#multiply(Vector2D)}.
   * Tests that the matrix multiplication performs as expected with known positive values.
   * Results were verified with Wolfram Alpha.
   */

  @Test
  public void testMatrix2x2MultiplicationWithPositiveArguments() {
    Matrix2x2 testMatrix = new Matrix2x2(1, 1, 1, 1);
    Vector2D testVector = new Vector2D(2, 2);
    Vector2D resultVector = new Vector2D(4, 4);
    Vector2D productVector;
    productVector = testMatrix.multiply(testVector);
    assertEquals(productVector.getX0(), resultVector.getX0());
    assertEquals(productVector.getY0(), resultVector.getY0());

  }

  /**
   * Test the method {@link Matrix2x2#multiply(Vector2D)}.
   * Tests that the matrix multiplication method performs as expected when a
   * null object is passed to the method
   */

  @Test
  public void testMatrixMultiplicationWithNullObject() {
    Matrix2x2 testMatrix = new Matrix2x2(1, 2, 3, 4);
    Vector2D testVector = null;
    try {
      testMatrix.multiply(testVector);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link Matrix2x2}.
   * Tests that the matrix2x2 constructor performs as expected when passed NaN values.
   */
  @Test
  public void testMatrixConstructorWithNaNValues() {
    try {
      Matrix2x2 matrixTest = new Matrix2x2(1 / 0, 1 / 0, 1 / 0, 1 / 0);
      fail();
    } catch (ArithmeticException a) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link Matrix2x2#matrixToString()}.
   * Tests that the method performs as expected when called.
   */
  @Test
  public void testMatrixToString() {
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    assertEquals("1.0, 1.0, 1.0, 1.0", matrix.matrixToString());
  }

  /**
   * Test the method {@link Matrix2x2#Matrix2x2(Matrix2x2)}.
   * Tests that the copy constructor performs as expected when passed.
   */
  @Test
  public void testMatrixCopyConstructor() {
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    Matrix2x2 copyMatrix = new Matrix2x2(matrix);
    assertEquals(matrix.getA(), copyMatrix.getA());
    assertEquals(matrix.getB(), copyMatrix.getB());
    assertEquals(matrix.getC(), copyMatrix.getC());
    assertEquals(matrix.getD(), copyMatrix.getD());
  }

}