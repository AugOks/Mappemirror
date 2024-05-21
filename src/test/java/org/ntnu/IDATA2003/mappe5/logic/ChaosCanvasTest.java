package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescriptionFactory;

/**
 * Tests the ChaosCanvas class {@link ChaosCanvas}.
 * This class tests the following methods:
 * <ul>
 *   <li> {@link ChaosCanvas#ChaosCanvas(int, int, Vector2D, Vector2D)}
 *   <li> {@link ChaosCanvas#putPixel(Vector2D)}
 * </ul>
 */
class ChaosCanvasTest {
  int height;
  int width;

  /**
   * Test the instance method of class {@link ChaosCanvas}.
   */
  private Vector2D minCoords;
  private Vector2D maxCoords;

  /**
   * Initializes the instance variables for the tests.
   */
  @BeforeEach
  void init() {
    this.minCoords = new Vector2D(1, 1);
    this.maxCoords = new Vector2D(10, 10);
    this.height = 10;
    this.width = 10;
  }

  /**
   * Test creating an instance of the canvas in chaos game with a valid input.
   * {@link ChaosCanvas}
   */
  @Test
  void testChaosCanvasCreateInstanceValidInput() {
    minCoords = new Vector2D(0, 0);
    maxCoords = new Vector2D(1, 1);
    height = 100;
    width = 200;
    ChaosCanvas chaosCanvas = new ChaosCanvas(height, width, minCoords, maxCoords);
    assertNotNull(chaosCanvas);
    assertEquals(chaosCanvas.getHeight(), height);
    assertEquals(chaosCanvas.getWidth(), width);
  }

  /**
   * Test the putPixel method of class {@link ChaosCanvas#putPixel(Vector2D)}.
   * Using valid input.
   */
  @Test
  void testPutPixelWithValidInput() {
    ChaosCanvas chaosCanvas = new ChaosCanvas(height, width, minCoords, maxCoords);
    int i = 2;
    int j = 2;
    Vector2D vector = new Vector2D(i, j);
    try {
      chaosCanvas.putPixel(vector);
      int[][] canvas = chaosCanvas.getCanvasArray();
      assertEquals(canvas[8][1], 10);
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * Test the putPixel method of class {@link ChaosCanvas#putPixel(Vector2D)}.
   * Using invalid input.
   */
  @Test
  void testPutPixelWithInvalidInput() {
    ChaosCanvas chaosCanvas = new ChaosCanvas(height, width, minCoords, maxCoords);
    int i = 2;
    int j = 2;
    Vector2D vector = new Vector2D(i, j);
    chaosCanvas.putPixel(vector);
    int[][] canvas = chaosCanvas.getCanvasArray();
    assertEquals(canvas[8][1], 10);
  }

  /**
   * Test the getHeight method of class {@link ChaosCanvas#putPixel(Vector2D)}.
   */
  @Test
  void testPutPixelWithJuliaTransform() {
    ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();
    ChaosGameDescription description = factory.createDescription(
        ChaosGameDescriptionFactory.Fractals.JULIA);
    ChaosGame game = new ChaosGame(description, height, width);
    ChaosCanvas canvas = game.getCanvas();
    Vector2D testPoint = new Vector2D(-1.59, -0.95);
    canvas.putPixel(testPoint);
    int[][] canvasArray = canvas.getCanvasArray();
    assertEquals(canvasArray[9][0], 10);
  }


}