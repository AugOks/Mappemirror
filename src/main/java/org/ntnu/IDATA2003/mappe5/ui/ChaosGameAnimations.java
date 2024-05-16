package org.ntnu.IDATA2003.mappe5.ui;

import static org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory.Fractals.JULIA;

import java.util.Collection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.AnimationFailedException;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;

/**
 * Represents an animation class for the ChaosGameGui.
 * The class contains methods for animating fractals.
 * It is currently is using threads to accomplish simultaneous animation and drawing without
 * impacting performance.
 */
public class ChaosGameAnimations {
  private final ChaosGameControllerGui controller;
  private final ChaosGameDescriptionFactory factory;
  private ChaosGameDescription currentDescription;
  private ChaosGameDescription startDescription;

  /**
   * Constructor for the ChaosGameAnimations class.
   *
   * @param description the description of the fractal.
   * @param controller  the controller for the ChaosGameGui.
   */
  public ChaosGameAnimations(ChaosGameDescription description, ChaosGameControllerGui controller) {
    this.currentDescription = description;
    this.startDescription = description;
    this.controller = controller;
    this.factory = new ChaosGameDescriptionFactory();
  }

  /**
   * Exposes a method for choosing the dance party animation.
   */
  public void danceParty() {
    this.controller.changeDescription(this.currentDescription);
    this.danceAnimation();
  }

  //https://stackoverflow.com/questions/46570494/javafx-changing-the-image-of-an-imageview-using-timeline-doesnt-work

  /**
   * Animates the fractal by changing the min and max coordinates of the fractal.
   */
  private void danceAnimation() {
    Timeline timeLine = new Timeline();
    Collection<KeyFrame> frames = timeLine.getKeyFrames();
    Duration frameGap = Duration.millis(500);
    Duration frameTime = Duration.ZERO;
    for (int i = 1; i < 5; i++) {
      frameTime = frameTime.add(frameGap);
      int finalI = i;
      frames.add(new KeyFrame(frameTime, e -> this.danceMoves(finalI)));
    }
    timeLine.setCycleCount(3);
    timeLine.play();
  }

  /**
   * Changes the description of the fractal based on the dance move.
   *
   * @param danceMove the dance move to be performed.
   */
  private void danceMoves(int danceMove) {
    switch (danceMove) {
      case 1:
        this.currentDescription = this.factory.createDescription(JULIA);
        this.controller.changeDescription(this.currentDescription);
        break;
      case 2:
        this.currentDescription.getMinCoords().setX0(-3.0);
        this.controller.changeDescription(this.currentDescription);
        break;
      case 3:
        this.currentDescription.getMinCoords().setY0(-3.0);
        this.controller.changeDescription(this.currentDescription);
        break;
      case 4:
        this.currentDescription.getMaxCoords().setX0(3.0);
        this.controller.changeDescription(this.currentDescription);
        break;
      case 5:
        this.currentDescription.getMaxCoords().setY0(3.0);
        this.controller.changeDescription(this.currentDescription);
        break;
      default:
        break;
    }
  }

  /**
   * Exposes a method for choosing the Julia set animation based on which type is wanted.
   *
   * @param choice the type of animation to be chosen.
   */
  public void chooseJuliaAnimation(String choice) {
    switch (choice) {
      case "normal":
        this.juliaSliderAnimation(this.currentDescription, -1, -1, true, true);
        break;
      case "wacky":
        this.juliaSliderAnimation(this.currentDescription, 0.39, -1.0,
            false, true);
        break;
      default:
        break;
    }
  }

  /**
   * Animates the Julia set by changing the complex number of the JuliaTransform.
   *
   * @param description the description of the Julia set.
   * @throws AnimationFailedException if the animation fails.
   */
  private void juliaSliderAnimation(ChaosGameDescription description, double x0, double y0,
                                    boolean deltaX, boolean deltaY) {
    Thread slideThread = new Thread(() -> { //creates a new thread to perform the animation on
      JuliaTransform transform1 = (JuliaTransform) description.getTransform(0);
      JuliaTransform transform2 = (JuliaTransform) description.getTransform(1);
      Complex complex1 = transform1.getComplex();
      complex1.setX0(x0);
      complex1.setY0(y0);
      Complex complex2 = transform2.getComplex();
      complex2.setX0(x0);
      complex2.setY0(y0);
      while (complex1.getX0() < 1 && complex1.getY0() < 1) { //looping while the values are not
        try {                                             // at the max
          Thread.sleep(100);         //pauses the animation at set intervals to give
        } catch (InterruptedException e) { // a smooth transition
          throw new AnimationFailedException("Failed to animate the Julia set");
          //throws a new exception if the animation fails
        }
        Platform.runLater(() -> { //Updates the values in the complex incrementally.
          // Controls the thread that is changing the values.
          if (deltaY) {
            complex1.setY0(complex1.getY0() + 0.05);
            complex2.setY0(complex2.getY0() + 0.05);
          }
          if (deltaX) {
            complex1.setX0(complex1.getX0() + 0.05);
            complex2.setX0(complex2.getX0() + 0.05);
          }
          controller.changeDescription(currentDescription);
        });
      }
    });
    slideThread.start();            //starts the animation.
  }

}
