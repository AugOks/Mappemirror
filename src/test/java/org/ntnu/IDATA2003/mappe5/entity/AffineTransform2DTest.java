package org.ntnu.IDATA2003.mappe5.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

/**
 * This class tests the AffineTransform2D class.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link AffineTransform2D#AffineTransform2D(Matrix2x2, Vector2D)}
 *   <li> {@link AffineTransform2D#transform(Vector2D)}
 * </ul>
 */

class AffineTransform2DTest {

  /**
   * Test the instance method of class {@link AffineTransform2D}.
   * Test for the instance method of class with correct parameters.
   */
  @Test
  public void TestCreateInstanceOfAffineTransform2DValidInput() {
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    Vector2D vector = new Vector2D(1, 1);
    AffineTransform2D AT2D = new AffineTransform2D(matrix, vector);
    assertNotNull(AT2D);
  }

  /**
   * Tests the creation of a AffineTransform object with a null matrix.
   * Expected to throw an {@link IllegalArgumentException}
   */
  @Test
  public void testAffineTransform2DConstructorWithNullMatrix() {
    Matrix2x2 nullMatrix = null;
    Vector2D vector = new Vector2D(1, 1);
    try {
      AffineTransform2D testAffine = new AffineTransform2D(nullMatrix, vector);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Tests the creation of a AffineTransform object with a null vector.
   * Expected to throw an {@link IllegalArgumentException}
   */
  @Test
  public void testAffineTransform2DConstructorWithNullVector() {
    Matrix2x2 nullMatrix = new Matrix2x2(1, 1, 1, 1);
    try {
      AffineTransform2D testAffine = new AffineTransform2D(nullMatrix, null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link AffineTransform2D#transform(Vector2D)}.
   * Expected to multiply the matrix with the vector and add the result to the vector.
   */
  @Test
  public void testTransformWithValidVector() {
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    Vector2D vector = new Vector2D(1, 1);
    AffineTransform2D AT2D = new AffineTransform2D(matrix, vector);
    Vector2D transformedVector = AT2D.transform(new Vector2D(1, 1));
    assertEquals(3, transformedVector.getX0());
    assertEquals(3, transformedVector.getY0());
  }

  /**
   * Test the method {@link AffineTransform2D#transform(Vector2D)}.
   * Negative test where the vector is Null.
   */
  @Test
  public void testTransformWithNullVector() {
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    Vector2D vector = new Vector2D(1, 1);
    AffineTransform2D AT2D = new AffineTransform2D(matrix, vector);
    try {
      AT2D.transform(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }


}