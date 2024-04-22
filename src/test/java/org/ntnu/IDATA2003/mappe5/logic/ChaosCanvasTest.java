package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.entity.PixelOutOfBoundsException;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

/**
 * Tests the ChaosCanvas class {@link ChaosCanvas}.
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
      //int[] pos = chaosCanvas.transformCoordsToIndices(vector);
      int[][] canvas = chaosCanvas.getCanvasArray();
      assertEquals(canvas[8][1], 1);
    }catch (PixelOutOfBoundsException p){
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
    try {
      chaosCanvas.putPixel(vector);
    } catch (PixelOutOfBoundsException e) {
      fail();
    }
    int[][] canvas = chaosCanvas.getCanvasArray();
    assertEquals(canvas[8][1], 1);
  }

  @Test
  void testPutPixelWithJuliaTransform() {
    ChaosGameFileHandler handler = new ChaosGameFileHandler();
    ChaosGameDescription description = handler.readFromFileWithFractalName("juliaset");
    ChaosGame game = new ChaosGame(description, height, width);
    ChaosCanvas canvas = game.getCanvas();
    Vector2D testPoint = new Vector2D(-1.59, -0.95);
    try {
      canvas.putPixel(testPoint);
    } catch (PixelOutOfBoundsException e) {
      fail();
    }
    canvas.transformCoordsToIndices(testPoint);
    int[][] canvasArray = canvas.getCanvasArray();
    assertEquals(canvasArray[9][0], 1);
  }
}