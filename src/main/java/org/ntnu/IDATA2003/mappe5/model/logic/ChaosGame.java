package org.ntnu.IDATA2003.mappe5.model.logic;

import java.util.ArrayList;
import java.util.Random;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;


/**
 * The chaos game controls the main logic of the application.
 * It's responsible for creating an instance of the description and canvas needed.
 */
public class ChaosGame {
  private ChaosCanvas canvas; // The canvas for the fractals in the app.
  private ChaosGameDescription description; //The description of the chaosGame.
  private Vector2D currentPoint; //The current point
  private Random random;
  private ArrayList<ChaosGameObserver> subscribers;
  private int currentHeight;
  private int currentWidth;

  /**
   * Creates an instance of ChaosGame and initializes fields with values.
   *
   * @param description The description of the fractal to be made.
   * @param width       The canvas width.
   * @param height      The canvas height.
   */

  public ChaosGame(ChaosGameDescription description, int height, int width) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }
    if (width < 1 || height < 1) {
      throw new IllegalArgumentException("The canvas cannot have size smaller than 1x1");
    }
    this.random = new Random();
    this.currentHeight = height;
    this.currentWidth = width;
    this.subscribers = new ArrayList<>();
    this.setDescription(description, currentHeight, currentWidth);
    Vector2D maxCoords = this.description.getMaxCoords();
    Vector2D minCoords = this.description.getMinCoords();
    this.canvas = new ChaosCanvas(height, width, minCoords, maxCoords);
    this.currentPoint = new Vector2D(0, 0);

  }

  /**
   * Gets the canvas that the game is played on.
   *
   * @return the canvas of the game.
   */
  public ChaosCanvas getCanvas() {

    return this.canvas;
  }

  /**
   * Sets the canvas of the game.
   */
  private void setCanvas() {
    this.canvas =
        new ChaosCanvas(this.currentHeight, this.currentWidth, this.description.getMinCoords(),
            this.description.getMaxCoords());
  }

  /**
   * Gets the description of the game being played.
   *
   * @return the description of the game.
   */
  public ChaosGameDescription getDescription() {

    return this.description;
  }

  /**
   * Adds a new subscriber to obeserve the changes in this class.
   *
   * @param subscriber The new subscriber to be added to the list.
   * @throws IllegalArgumentException if the subscriber is a null object.
   */
  public void addSubscriber(ChaosGameObserver subscriber) {
    if (subscriber == null) {
      throw new IllegalArgumentException("new subscriber cannot be null");
    }
    this.subscribers.add(subscriber);
  }

  private void updateSubscriber() {
    for (ChaosGameObserver sub : this.subscribers) {
      this.canvas.clear();
      sub.update();
    }
  }

  /**
   * Tells the game how many steps to run before halting.
   * Starts by transforming the current point using one of the transformations,
   * Then it places the transformed point on the canvas and sets the current point to the result.
   *
   * @param steps the amount of steps to be run before halting
   */
  public void runSteps(int steps) {
    if (steps < 1) {
      throw new IllegalArgumentException("Steps cannot be less than 1");
    }
    for (int i = 0; i < steps; i++) {
      int dice = this.random.nextInt(
          this.description.getTransformSize());    // throws a die for a random number
      Transform2D transform = this.description.getTransform(
          dice); // gets a random transform based on die
      Vector2D point = transform.transform(this.currentPoint); // transforms current position.
      canvas.putPixel(point); //Sets the results of the transformation as a pixel on the canvas
      this.currentPoint = point; //Sets the current pont to the result of the transformation
    }

  }

  /**
   * Sets the description of the game.
   *
   * @param description the new description of the game.
   * @throws IllegalArgumentException if the description is a null object.
   */
  public void setDescription(ChaosGameDescription description, int height, int width) {
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }
    currentWidth = width;
    currentHeight = height;
    this.description = description;
    this.setCanvas();
    this.updateSubscriber();
  }

}
