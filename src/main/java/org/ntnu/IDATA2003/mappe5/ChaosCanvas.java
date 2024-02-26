package org.ntnu.IDATA2003.mappe5;

public class ChaosCanvas {

  private int[][] canvas;
  private int width;
  private int height;
  private Vector2D minCords;
  private Vector2D maxCords;
  private AffineTransform2D transformCoordsToIndices;

  public ChaosCanvas(int width, int height, Vector2D minCords, Vector2D maxCords){

  }

  public int getPixel(Vector2D point){
    return 0;
  }

  public void putPixel(Vector2D point){
    ;
  }

  public int[][] getCanvasArray(){
    return null;
  }

  public void clear(){
    ;
  }
}
