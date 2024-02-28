package org.ntnu.IDATA2003.mappe5;

import java.util.Random;

public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;

  public ChaosGame(ChaosGameDescription description, int width, int height){

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

  }
  private void setDescription(ChaosGameDescription description){
    this.description = description;
  }
  private void setCanvas(int height, int width){

    Vector2D minCoords= new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(width, height);
    this.canvas = new ChaosCanvas(width, height,minCoords, maxCoords );
  }
}
