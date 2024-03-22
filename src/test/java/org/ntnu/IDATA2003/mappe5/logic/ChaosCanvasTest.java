package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.ChaosCanvas;
import org.ntnu.IDATA2003.mappe5.ChaosGame;
import org.ntnu.IDATA2003.mappe5.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.ChaosGameFileHandler;
import org.ntnu.IDATA2003.mappe5.Complex;
import org.ntnu.IDATA2003.mappe5.Vector2D;

/**
 * Tests the ChaosCanvas class {@link ChaosCanvas}.
 */
class ChaosCanvasTest {
  /**
   * Test the instance method of class {@link ChaosCanvas}.
   */
  private Vector2D minCoords;
  private Vector2D maxCoords;
  int height;
  int width;

  /**
   * Initializes the instance variables for the tests.
   */
  @BeforeEach
  void init(){
     this.minCoords = new Vector2D(1, 1);
     this.maxCoords = new Vector2D(10, 10);
     this.height =10;
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
    height =100;
    width = 200;
    ChaosCanvas chaosCanvas = new ChaosCanvas(height,width,minCoords,maxCoords);
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
    ChaosCanvas chaosCanvas = new ChaosCanvas(height,width,minCoords,maxCoords);
    int i = 2;
    int j = 2;
    Vector2D vector = new Vector2D(i,j);
    chaosCanvas.putPixel(vector);
    //int[] pos = chaosCanvas.transformCoordsToIndices(vector);
    int[][] canvas = chaosCanvas.getCanvasArray();
    assertEquals(canvas[8][1],1);
  }

  /**
   * Test the putPixel method of class {@link ChaosCanvas#putPixel(Vector2D)}.
   * Using invalid input.
   */
  @Test
  void testPutPixelWithInvalidInput() {
    ChaosCanvas chaosCanvas = new ChaosCanvas(height,width,minCoords,maxCoords);
    int i = 2;
    int j = 2;
    Vector2D vector = new Vector2D(i,j);
    chaosCanvas.putPixel(vector);
    int[][] canvas = chaosCanvas.getCanvasArray();
    assertEquals(canvas[8][1],1);
  }
  @Test
  void testPutPixelWithJuliaTransform(){
    ChaosGameFileHandler handler = new ChaosGameFileHandler();
    ChaosGameDescription description  = handler.readFromFile("juliaset");
    ChaosGame game = new ChaosGame(description, height, width);
    ChaosCanvas canvas =  game.getCanvas();
    Vector2D testPoint = new Vector2D(-1.59, -0.95);
    canvas.putPixel(testPoint);
    canvas.transformCoordsToIndices(testPoint);
    int[][] canvasArray = canvas.getCanvasArray();
    assertEquals(canvasArray[9][0], 1);
  }
}