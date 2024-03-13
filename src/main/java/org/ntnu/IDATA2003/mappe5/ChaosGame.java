package org.ntnu.IDATA2003.mappe5;

import java.util.Random;

public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;
  private int[] maxCoords;

  public ChaosGame(ChaosGameDescription description, int width, int height){
    if (description == null){
      throw new IllegalArgumentException("Description cannot be null");
    }
    this.random = new Random();
    this.description = description;
    Vector2D maxCoords = new Vector2D(width, height);
    Vector2D minCoords = new Vector2D(0,0);
    this.canvas = new ChaosCanvas(width, height, minCoords, maxCoords);
    this.currentPoint = minCoords;

  }

  /**
   * Gets the canvas that the game is played on.
   *
   * @return the canvas of the game.
   */
  public ChaosCanvas getCanvas(){

    return canvas;
  }

  /**
   * Tells the game how many steps to run before halting.
   *
   * @param steps the amount of steps to be run before halting
   */
  public void runSteps(int steps){
    for (int i = 0; i <= steps-1; i++) {
      int dice = this.random.nextInt(2);    // throws a die for a random number
      Transform2D transf = this.description.getTransform(dice); // gets a random transform based on die
      Vector2D point = transf.transform(this.currentPoint); // transforms current position.
      canvas.putPixel(point); //Sets the results of the transformation as a pixel on the canvas
      this.currentPoint = point; //Sets the current pont to the result of the transformation
    }
    int indexi = this.canvas.getWidth();
    int indexj =  this.canvas.getHeight();
    int [][] canvas = this.canvas.getCanvasArray();
    for(int i = 0; i < indexj-100; i++){
      System.out.println();
      for(int j = 0; j < indexi-100; j++){
        System.out.print(canvas[i][j]);
      }

    }

  }
  private void setDescription(ChaosGameDescription description){

    this.description = description;
  }

  /* TODO: consider removing or refactoring.
  private void setCanvas(int height, int width){

    Vector2D minCoords= new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(width, height);
    this.canvas = new ChaosCanvas(width, height,minCoords, maxCoords );


  }*/
}
