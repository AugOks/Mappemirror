package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

class ChaosGameDescriptionTest {

  /**
   * Test the instance method of class {@link ChaosGameDescription}.
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
}
