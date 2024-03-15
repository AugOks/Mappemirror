package org.ntnu.IDATA2003.mappe5;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class ChaosGame {
  private ChaosCanvas canvas;
  private ChaosGameDescription description;
  private Vector2D currentPoint;
  private Random random;

  /**
   *  Creates an instance of ChaosGame and initializes fields with values.
   * @param description The description of the fractal to be made.
   * @param width The canvas width.
   * @param height The canvas height.
   */
  public ChaosGame(ChaosGameDescription description, int height, int width){
    if (description == null){
      throw new IllegalArgumentException("Description cannot be null");
    }
    if (width < 1 || height < 1){
      throw new IllegalArgumentException("The canvas cannot have size smaller than 1x1");
    }
    this.random = new Random();
    this.description = description;
    Vector2D maxCoords = this.description.getMaxCoords();
    Vector2D minCoords = this.description.getMinCoords();
    this.canvas = new ChaosCanvas(height, width, minCoords, maxCoords);
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
    for (int i = 0; i < steps; i++) {
      int dice = this.random.nextInt(3);    // throws a die for a random number
      Transform2D transf = this.description.getTransform(dice); // gets a random transform based on die
      Vector2D point = transf.transform(this.currentPoint); // transforms current position.
      canvas.putPixel(point); //Sets the results of the transformation as a pixel on the canvas
      //System.out.println(point.getX0()+" "+ point.getY0());
      this.currentPoint = point; //Sets the current pont to the result of the transformation
    }
    int indexi = this.canvas.getHeight();
    int indexj =  this.canvas.getWidth();
    int[][] canvas = this.canvas.getCanvasArray();
    ArrayList<String> canvasConsole = new ArrayList<>();
    String line="";
    for(int i = 0; i < indexi; i++){
      for(int j = 0; j < indexj; j++){
        if(canvas[i][j]==0){
          line += "-";
        } else{
          line+="X";
        }
      }
      canvasConsole.add(line);
      line="";
    }
    for (String s : canvasConsole) {
      System.out.println(s);
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
