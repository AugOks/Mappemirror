package org.ntnu.IDATA2003.mappe5;

/**
 * Represents a canvas where the fractals will be displayed.
 */

public class ChaosCanvas {

  private int[][] canvas;
  private int width;
  private int height;
  private Vector2D minCoords;
  private Vector2D maxCoords;
  private AffineTransform2D transformCoordsToIndices;

  /**
   * Creates an instance of the chaos game itself.
   *
   * @param width The width of the canvas.
   * @param height The height of the canvas.
   * @param minCoords The minimum coords.
   * @param maxCoords The maximum coords.
   */
  public ChaosCanvas(int width, int height, Vector2D minCoords, Vector2D maxCoords){
    setWidth(width);
    setHeight(height);
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
    setCoordsToIndices();
    makeCanvas();

  }

  /**
   * Sets the Width of the canvas.
   * 
   * @param width the width of the canvas.
   */
  private void setWidth(int width){
    //TODO add gard for width
    this.width = width;
  }

  /**
   * Sets the height of the canvas.
   * @param height height of canvas
   */
  private void setHeight(int height){
    //TODO add gard for height
    this.height = height;
  }

  /**
   * Gets a given pixel at the point coordinates on the canvas.
   * 
   * @param point the coordinates of the pixel in the canvas.
   * @return the pixel at the coordinates.
   */
  public int getPixel(Vector2D point){
    //TODO: Fix this method.
    return 0;
  }

  /**
   * Puts a pixel at a given point coordinates on the canvas.
   *
   * @param point the coordinates of the pixel to put.
   */
  public void putPixel(Vector2D point){
    ;
  }

  /**
   * Gets the canvas of the game as a 2-dimensional int array.
   *
   * @return the 2-dimensional int array of the canvas.
   */
  public int[][] getCanvasArray(){
    return this.canvas;
  }

  /**
   * Creates a canvas based on the width and height.
   *
   */
  public void makeCanvas(){
    this.canvas = new int[this.height][this.width];
  }

  /**
   * Transforms the x,y coordinates of the fractal to indices for a two-dimensional array.
   * (i,j) = A*(x0,y0) + B
   */
  private void setCoordsToIndices(){
    double Ab1 = (this.height-1)/(this.minCoords.getY0() - this.maxCoords.getY0());
    double Ac1 = (this.width -1)/(this.maxCoords.getX0() - this.minCoords.getX0());
    Matrix2x2 matrixA = new Matrix2x2(0,Ab1,Ac1,0);

    double Bx = ((this.height-1)*this.maxCoords.getY0()) / (this.maxCoords.getY0() -
        this.minCoords.getY0());
    double By = ((this.width -1) *this.minCoords.getX0()) / (this.minCoords.getX0() -
        this.maxCoords.getX0());
    Vector2D vectorB = new Vector2D(Bx, By);

    this.transformCoordsToIndices = new AffineTransform2D(matrixA, vectorB);
  }

  /**
   * Takes in X and Y coordinates and returns the pixel position of those coordinates.
   *
   * @param x0 the X value of the coordinates.
   * @param y0 the Y value of the coordinates.
   * @return the indices of the pixel as an int array.
   */
  public int[] transformCoordsToIndices(double x0, double y0){
    int[] xy = new int[2];
    Vector2D coordinates = new Vector2D( x0, y0);
    Vector2D indices = this.transformCoordsToIndices.transform(coordinates);
    xy[0] = (int) Math.round(indices.getX0());
    xy[1] = (int) Math.round(indices.getY0());
    return xy;
  }
  /**
   * Sets the max coordinates of the transform?
   * TODO: fix javadoc
   *
   * @param coords the coordinates to be set.
   */
  public  void setMaxCoords(Vector2D coords){
    this.maxCoords = coords;
  }
  /**
   * Sets the min coordinates of the transform?
   * TODO: fix this javadoc.
   *
   * @param coords the coordinates to be set.
   */
  public void setMinCoords(Vector2D coords){
    this.minCoords = coords;
  }

  /**
   * Clears the canvas.
   *
   */
  public void clear(){
    ;
  }
}
