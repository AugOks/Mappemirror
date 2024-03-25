package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.Transform2D;
import org.ntnu.IDATA2003.mappe5.TransformsParser;
import org.ntnu.IDATA2003.mappe5.Vector2D;

/**
 * Tests the TransformsParser class.
 */

class TransformsParserTest {

  TransformsParser parser = new TransformsParser();

  /**
   * tests {@link  TransformsParser#parseAffineTransforms(List)}
   * Tests parsing affine transforms with valid input.
   */

  @Test
  void testParseAffineTransformsWithValidInput() {
    List<String> content = new ArrayList<>();
    content.add(".5, 0, 0, .5, 0, 0 ");
    content.add(".5, 0, 0, .5, .25, .5");
    content.add(".5, 0, 0, .5, .5, 0  ");
    List<Transform2D> transforms = parser.parseAffineTransforms(content);
    assertEquals(3, transforms.size());
  }

  /**
   * Test {@link TransformsParser#cleanString(String)}
   * Tests clean string method.
   */
  @Test
  void testCleanString(){
    String dirtyString = "  1 2 3 4 5 6  ";
    String cleanString = parser.cleanString(dirtyString);
    assertEquals("123456", cleanString);
  }

  /**
   * tests {@link TransformsParser#parseJuliaTransforms(String)}
   * tests parsing julia transforms with valid input.
   */
  @Test
  void testParseJuliaTransformsWithValidInput(){
    String juliaComplex = "-0.74543, 0.11301";
    List<JuliaTransform> transforms = parser.parseJuliaTransforms(juliaComplex);
    assertEquals(2, transforms.size());
  }

  /**
   * tests {@link TransformsParser#parseJuliaTransforms(String)}
   * Tests parsing julia transforms with invalid input.
   * @
   */
  @Test
  void testParseJuliaTransformsWithInvalidInput(){
    String juliaComplex = "-0.74543, 0.11301, 0.1";
    assertThrows(IllegalArgumentException.class, () -> parser.parseJuliaTransforms(juliaComplex));
  }

  /**
   * Test {@link TransformsParser#getVectorFromString(String)}
   * Tests getting a vector from a string with valid input.
   */
  @Test
  void testGetVectorFromStrings(){
    String vectorValues = "1, 2";
    Vector2D vector = parser.getVectorFromString(vectorValues);
    assertEquals(1, vector.getX0());
    assertEquals(2,vector.getY0());
  }

  /**
   * Test {@link TransformsParser#parseAffineTransforms(List)}
   * Tests parsing affine transforms with invalid input.
   */
  @Test
  void testParseAffineTransformsWithInvalidInput(){
    try {
      parser.parseAffineTransforms(null);
      fail();
    } catch (IllegalArgumentException e) {
     assertTrue(true);
    }
  }

  /**
   * Test {@link TransformsParser#parseJuliaTransforms(String)}
   * Tests parsing julia transforms with invalid input.
   */
  @Test
  void testParseJuliaTransformsWithInvalidInputNull(){
    try {
      parser.parseJuliaTransforms(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Test {@link TransformsParser#getVectorFromString(String)}
   * Tests getting a vector from a string with invalid input.
   */
  @Test
  void testGetVectorFromStringWithNullInput(){
    try {
      parser.getVectorFromString(null);
        fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

  /**
   * Test {@link TransformsParser#getVectorFromString(String)}
   * Tests getting a vector from a string with invalid input.
   */
  @Test
  void TestGetVectorFromStringWithInvalidInput(){
    try {
      parser.getVectorFromString("1, 2, 3");
      fail();
    }catch (IllegalArgumentException e){
      assertTrue(true);
    }
  }

}