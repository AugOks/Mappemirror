package org.ntnu.IDATA2003.mappe5.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

/**
 * Tests the Vector2D class {@link Vector2D}..
 * Tests the following methods:
 * <ul>
 *   <li> {@link Vector2D#Vector2D(double, double)}
 *   <li> {@link Vector2D#sub(Vector2D)}
 *   <li> {@link Vector2D#add(Vector2D)}
 *   <li> {@link Vector2D#scalar(int)}
 * </ul>
 */

class Vector2DTest {

  /**
   * Test the method {@link Vector2D#Vector2D(double, double)}.
   * Test for the Vector2D constructor with positive parameters.
   */
  @Test
  public void testVector2DConstructorWithPositiveParameters() {
    Vector2D testVector = new Vector2D(1, 2);
    assertEquals(1, testVector.getX0());
    assertEquals(2, testVector.getY0());
  }

  /**
   * Test the method {@link Vector2D#add(Vector2D)}.
   * Test for the Vector2D addition method with positive parameters.
   */
  @Test
  public void testVector2DAdditionWithPositiveParameters() {
    Vector2D testVector1 = new Vector2D(1, 2);
    Vector2D testVector2 = new Vector2D(2, 1);
    Vector2D resultVector = new Vector2D(3, 3);

    Vector2D actualResults = testVector1.add(testVector2);
    assertEquals(actualResults.getX0(), resultVector.getX0());
    assertEquals(actualResults.getY0(), resultVector.getY0());
  }

  /**
   * Test the method {@link Vector2D#sub(Vector2D)}.
   * Test for the Vector2D subtraction method with positive parameters.
   */
  @Test
  public void testVector2DSubtractionWithPositiveParameters() {
    Vector2D testVector1 = new Vector2D(4, 4);
    Vector2D testVector2 = new Vector2D(1, 1);
    Vector2D resultVector = new Vector2D(3, 3);

    Vector2D actualResults = testVector1.sub(testVector2);
    assertEquals(actualResults.getX0(), resultVector.getX0());
    assertEquals(actualResults.getY0(), resultVector.getY0());
  }

  /**
   * Test the method {@link Vector2D#add(Vector2D)}.
   * Test for the Vector2D addition method with one null vector.
   */
  @Test
  public void testVector2DAdditionNullVector() {
    Vector2D testVector = new Vector2D(4, 4);
    try {
      testVector.add(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link Vector2D#sub(Vector2D)}.
   * Test for the Vector2D subtraction method with one null vector.
   */
  @Test
  public void testVector2DSubtractionNullVector() {
    Vector2D testVector = new Vector2D(4, 4);
    try {
      testVector.sub(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link Vector2D#add(Vector2D)}.
   * Test for the Vector2D addition method with one null vector.
   */
  @Test
  public void testVector2DAddition2NullVector() {
    Vector2D testVector = null;
    try {
      testVector.add(null);
      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link Vector2D#sub(Vector2D)}.
   * Test for the Vector2D subtraction method with one null vector.
   */
  @Test
  public void testVector2DSubtraction2NullVector() {
    Vector2D testVector = null;
    try {
      testVector.sub(null);
      fail();
    } catch (NullPointerException e) {
      assertTrue(true);
    }
  }

  /**
   * Test the method {@link Vector2D#scalar(int)}.
   * Test for the Vector2D scalar method.
   */
  @Test
  public void testVector2DScalar() {
    Vector2D testVector = new Vector2D(4, 4);
    testVector.scalar(2);
    assertEquals(8, testVector.getX0());
    assertEquals(8, testVector.getY0());
  }

}