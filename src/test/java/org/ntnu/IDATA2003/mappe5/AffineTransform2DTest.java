package org.ntnu.IDATA2003.mappe5;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.Box;
import org.junit.jupiter.api.Test;

//TODO Write JavaDoc for this test class.

class AffineTransform2DTest {

  /**
   * Test the instance method of class {@link AffineTransform2D}.
   * Test for the instance method of class with correct parameters.
   */
  @Test
    public void TestCreateInstanceOfAffineTransform2DValidInput(){
      Matrix2x2 matrix = new Matrix2x2(1,1,1,1);
      Vector2D vector = new Vector2D(1,1);
      AffineTransform2D AT2D = new AffineTransform2D(matrix,vector);
      assertNotNull(AT2D);
    }

  /**
   * Tests the creation of a AffineTransform object with a null matrix.
   * Expected to throw an {@link IllegalArgumentException}
   */
  @Test
  public void testAffineTransform2DConstructorWithNullMatrix(){
    Matrix2x2 nullMatrix = null;
    Vector2D vector = new Vector2D(1,1);
    try {
      AffineTransform2D testAffine = new AffineTransform2D(nullMatrix,vector);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }
  /**
   * Tests the creation of a AffineTransform object with a null vector.
   * Expected to throw an {@link IllegalArgumentException}
   */
  @Test
  public void testAffineTransform2DConstructorWithNullVector(){
    Matrix2x2 nullMatrix = new Matrix2x2(1,1,1,1);
    try {
      AffineTransform2D testAffine = new AffineTransform2D(nullMatrix,null);
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }


  /**
   * Test the method {@link AffineTransform2D#transform(Vector2D)}.
   * Expected to multiply the matrix with the vector and add the result to the vector.
   */
  @Test
  public void testTransformWithValidVector(){
    Matrix2x2 matrix = new Matrix2x2(1,1,1,1);
    Vector2D vector = new Vector2D(1,1);
    AffineTransform2D AT2D = new AffineTransform2D(matrix,vector);
    Vector2D transformedVector = AT2D.transform(new Vector2D(1,1));
    assertEquals(2, transformedVector.getX0());
    assertEquals(2, transformedVector.getY0());
  }

  @Test
  public void testTransformWithNullVector(){
    Matrix2x2 matrix = new Matrix2x2(1,1,1,1);
    Vector2D vector = new Vector2D(1,1);
    AffineTransform2D AT2D = new AffineTransform2D(matrix,vector);
    try {
      AT2D.transform(null);
      fail();
    } catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }




}