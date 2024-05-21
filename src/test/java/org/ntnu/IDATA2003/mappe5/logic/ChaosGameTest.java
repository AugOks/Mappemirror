package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;

/**
 * This class tests the class {@link ChaosGame}.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link ChaosGame#ChaosGame(ChaosGameDescription, int, int)}
 *   <li> {@link ChaosGame#runSteps(int)}
 * </ul>
 */
class ChaosGameTest {

  private int height;
  private int width;
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private Matrix2x2 matrix;
  private List<Transform2D> listOfTransformation;
  private ChaosGameDescription chaosGameDescription;


  /**
   * Sets the value of the different parameters before each test.
   */
  @BeforeEach
  public void instance() {
    this.height = 30;
    this.width = 70;
    this.minCoords = new Vector2D(1, 1);
    this.matrix = new Matrix2x2(1, 1, 1, 1);
    this.maxCoords = new Vector2D(2, 2);
    listOfTransformation = new ArrayList<>();
    this.listOfTransformation.add(new AffineTransform2D(matrix, minCoords));
    this.listOfTransformation.add(new AffineTransform2D(matrix, maxCoords));
    this.chaosGameDescription = new ChaosGameDescription(listOfTransformation,
                                                         minCoords, maxCoords, "test");
  }


  /**
   * Positive test of the Instance of the class {@link ChaosGame}
   */
  @Test
  public void testInstanceOfChaosGameWithValidInput() {
    ChaosGame chaosGame = new ChaosGame(chaosGameDescription, height, width);
    assertNotNull(chaosGame);
  }

  /**
   * Negative test of the Instance of the class {@link ChaosGame}
   * The height is set to a negative value.
   */
  @Test
  public void testInstanceOfChaosGameWithInvalidHeight() {
    this.height = -10;
    try {
      ChaosGame chaosGame = new ChaosGame(chaosGameDescription, height, width);
      fail();
    } catch (IllegalArgumentException a) {
      assertTrue(true);
    }
  }

  /**
   * Negative test of the Instance of the class {@link ChaosGame}.
   * The width is set to a negative value.
   */
  @Test
  public void testInstanceOfChaosGameWithInvalidWidth() {
    this.width = -10;
    try {
      ChaosGame chaosGame = new ChaosGame(chaosGameDescription, height, width);
      fail();
    } catch (IllegalArgumentException a) {
      assertTrue(true);
    }
  }

}