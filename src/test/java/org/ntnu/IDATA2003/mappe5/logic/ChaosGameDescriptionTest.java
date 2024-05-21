package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;

/**
 * This class tests the ChaosGameDescription class.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link ChaosGameDescription#ChaosGameDescription(List, Vector2D, Vector2D, String)}
 *   <li> {@link ChaosGameDescription#getTransform(int)}
 *   <li> {@link ChaosGameDescription#setTransforms(List)}
 *   <li> {@link ChaosGameDescription#getTransformSize()}
 * </ul>
 */
class ChaosGameDescriptionTest {

  /**
   * Test the instance method of class {@link ChaosGameDescription}.
   * Test for the instance method of class with valid input.
   */
  @Test
  public void testInstanceWithValidInput() {
    Vector2D minCoords = new Vector2D(0, 0);
    Matrix2x2 matrix = new Matrix2x2(1, 1, 1, 1);
    Vector2D maxCoords = new Vector2D(2, 2);
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrix, minCoords));
    transforms.add(new AffineTransform2D(matrix, maxCoords));
    ChaosGameDescription chaosGameDescription = new ChaosGameDescription(transforms, minCoords,
                                                                         maxCoords, "test");

    assertNotNull(chaosGameDescription);
    assertEquals(chaosGameDescription.getMinCoords(), minCoords);
    assertEquals(chaosGameDescription.getMaxCoords(), maxCoords);
    assertEquals(chaosGameDescription.getName(), "test");
    assertEquals(chaosGameDescription.getTransform(0), transforms.get(0));
    assertEquals(chaosGameDescription.getTransform(1), transforms.get(1));
    assertEquals(chaosGameDescription.getTransformSize(), transforms.size());
    assertEquals(chaosGameDescription.getAllTransforms(), transforms);
  }

  /**
   * Test the instance method of class {@link ChaosGameDescription#setTransforms(List)}.
   * Test for the instance method of class with null transforms.
   */
  @Test
  public void testInstanceWithNullTransforms() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(2, 2);
    ;
    assertThrows(IllegalArgumentException.class,
                 () -> new ChaosGameDescription(null, minCoords, maxCoords, "test"));
  }

  /**
   * Test the instance method of class {@link ChaosGameDescription#getTransform(int)}.
   */
  @Test
  public void testGetTransformWithIndexOutOfBounds() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(2, 2);
    Matrix2x2 matrixTransform = new Matrix2x2(1, 1, 1, 1);
    Vector2D vectorTest = new Vector2D(1, 1);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrixTransform, vectorTest));
    ChaosGameDescription chaosGameDescription = new ChaosGameDescription(transforms, minCoords,
                                                                         maxCoords, "test");

    assertThrows(IndexOutOfBoundsException.class, () -> chaosGameDescription.getTransform(2));
  }

  /**
   * Test the instance method of class {@link ChaosGameDescription#getTransformSize()}.
   * Test for the instance method of class with valid input.
   */
  @Test
  public void testGetTransformSize() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(2, 2);
    Matrix2x2 matrixTransform = new Matrix2x2(1, 1, 1, 1);
    Vector2D vectorTest = new Vector2D(1, 1);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrixTransform, vectorTest));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrixTransform), new Vector2D(vectorTest)));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrixTransform), new Vector2D(vectorTest)));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrixTransform), new Vector2D(vectorTest)));
    ChaosGameDescription chaosGameDescription = new ChaosGameDescription(transforms, minCoords,
                                                                         maxCoords, "test");

    assertEquals(chaosGameDescription.getTransformSize(), 4);
  }
}
