package org.ntnu.IDATA2003.mappe5.model.logic;

import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

/**
 * Represents a canvas where the fractals will be displayed.
 */

public class ChaosCanvas {

  private int[][] canvas; //The canvas of the game.
  private int width; //The width of the canvas.
  private int height; //The height of the canvas.
  private Vector2D minCoords; //The minimum coordinates of the canvas.
  private Vector2D maxCoords; //The maximum coordinates of the canvas.
  private AffineTransform2D transformCoordsToIndices;
  //The transformation from coordinates to indices.

  /**
   * Creates an instance of the canvas in chaos game.
   *
   * @param width     The width of the canvas.
   * @param height    The height of the canvas.
   * @param minCoords The minimum coords.
   * @param maxCoords The maximum coords.
   */
  public ChaosCanvas(int height, int width, Vector2D minCoords, Vector2D maxCoords) {
    setHeight(height);
    setWidth(width);
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
    setCoordsToIndices();
    makeCanvas();
  }

  /**
   * Gets a given pixel at the point coordinates on the canvas.
   *
   * @param point the coordinates of the pixel in the canvas.
   * @return the pixel at the coordinates.
   */
  public int getPixel(Vector2D point) {
    return 0;
  }

  /**
   * Returns the width of the canvas.
   *
   * @return width of canvas.
   */
  public int getWidth() {

    return width;
  }

  /**
   * Sets the Width of the canvas.
   *
   * @param width the width of the canvas.
   * @throws IllegalArgumentException if the width is less than 0.
   */
  private void setWidth(int width) {
    if (width < 0) {
      throw new IllegalArgumentException("width cannot be less than 0");
    }
    this.width = width;
  }

  /**
   * Returns the height of the canvas.
   *
   * @return height of canvas.
   */
  public int getHeight() {

    return height;
  }

  /**
   * Sets the height of the canvas.
   *
   * @param height height of canvas
   * @throws IllegalArgumentException if the height is less than 0.
   */
  private void setHeight(int height) {
    if (height < 0) {
      throw new IllegalArgumentException("height cannot be less than 0");
    }
    this.height = height;
  }

  /**
   * Puts a pixel at a given point coordinates on the canvas.
   *
   * @param point the coordinates of the pixel to put.
   */
  public void putPixel(Vector2D point) {
    Vector2D ijCoords = transformCoordsToIndices.transform(point);
    int i = (int) Math.round(ijCoords.getX0());
    int j = (int) Math.round(ijCoords.getY0());
    if (i >= 0 && i < this.height && j >= 0 && j < this.width && this.canvas[i][j] < 245) {
      this.canvas[i][j] += 10;
    }
  }

  /**
   * Gets the canvas of the game as a 2-dimensional int array.
   *
   * @return the 2-dimensional int array of the canvas.
   */
  public int[][] getCanvasArray() {

    return this.canvas.clone();
  }

  /**
   * Creates a canvas based on the width and height.
   */
  private void makeCanvas() {

    this.canvas = new int[this.height][this.width];
  }

  /**
   * Transforms the x,y coordinates of the fractal to indices for a two-dimensional array.
   * (i,j) = A*(x0,y0) + b
   */
  private void setCoordsToIndices() {
    double matrixValueB = (this.height - 1) / (this.minCoords.getY0() - this.maxCoords.getY0());
    double matrixValueC = (this.width - 1) / (this.maxCoords.getX0() - this.minCoords.getX0());
    Matrix2x2 matrixA = new Matrix2x2(0, matrixValueB, matrixValueC, 0);

    double vectorValueX = ((this.height - 1) * this.maxCoords.getY0()) / (this.maxCoords.getY0()
                                                                          - this.minCoords.getY0());
    double vectorValueY = ((this.width - 1) * this.minCoords.getX0()) / (this.minCoords.getX0()
                                                                         - this.maxCoords.getX0());
    Vector2D vectorB = new Vector2D(vectorValueX, vectorValueY);

    this.transformCoordsToIndices = new AffineTransform2D(matrixA, vectorB);
  }


  /**
   * Sets the max coordinates of the transform to the given coordinates.
   *
   * @param coords the coordinates to be set.
   */
  public void setMaxCoords(Vector2D coords) {

    this.maxCoords = coords;
  }

  /**
   * Sets the min coordinates of the transform to the given coordinates.
   *
   * @param coords the coordinates to be set.
   */
  public void setMinCoords(Vector2D coords) {

    this.minCoords = coords;
  }

  /**
   * Clears the canvas.
   */
  public void clear() {
    this.makeCanvas();
  }
}
