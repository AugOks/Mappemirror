package org.ntnu.IDATA2003.mappe5.view;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.ntnu.IDATA2003.mappe5.controller.ChaosGameControllerGui;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.AnimationFailedException;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.model.logic.DancePartyFactory;

/**
 * Represents an animation class for the ChaosGameGui.
 * The class contains methods for animating fractals.
 * It is currently is using threads to accomplish simultaneous animation and drawing without
 * impacting performance.
 */
public class ChaosGameAnimations {
  private final ChaosGameControllerGui controller; //the controller for the ChaosGameGui.
  private final DancePartyFactory factory;  //the factory for creating descriptions.
  private ChaosGameDescription currentDescription; //the current description of the fractal.
  private MediaPlayer mediaPlayer; //the media player for playing music.
  private boolean isDancing = false; //a boolean to check if the fractal is dancing.

  /**
   * Constructor for the ChaosGameAnimations class.
   *
   * @param description the description of the fractal.
   * @param controller  the controller for the ChaosGameGui.
   */
  public ChaosGameAnimations(ChaosGameDescription description, ChaosGameControllerGui controller) {
    this.currentDescription = description;
    this.controller = controller;
    this.factory = new DancePartyFactory();
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
    Duration frameGap = Duration.millis(300);
    Duration frameTime = Duration.ZERO;
    for (int i = 1; i <= 35; i++) {
      frameTime = frameTime.add(frameGap);
      int finalI = i;
      frames.add(new KeyFrame(frameTime, e -> this.danceMoves(finalI)));
    }
    int cycleCount = 2;
    timeLine.setCycleCount(cycleCount);
    timeLine.play();
    Media sound = new Media((getClass().getResource("/danceParty.mp3")).toExternalForm()
        .toString());
    mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.setVolume(0.8);
    mediaPlayer.setStopTime(timeLine.getCycleDuration().multiply(cycleCount));
    mediaPlayer.play();

  }

  /**
   * Changes the description of the fractal based on the dance move.
   *
   * @param danceMove the dance move to be performed.
   */
  private void danceMoves(int danceMove) {
    this.currentDescription = this.factory.createDanceMoves(danceMove, this.currentDescription);
    this.setTheColor(danceMove);
    controller.changeDescription(this.currentDescription);
  }

  private void setTheColor(int danceMove) {
    int color = danceMove % 7;
    switch (color) {
      case 1:
        this.controller.setColorChoiceC(Color.RED);
        break;
      case 2:
        this.controller.setColorChoiceC(Color.ORANGE);
        break;
      case 3:
        this.controller.setColorChoiceC(Color.YELLOW);
        break;
      case 4:
        this.controller.setColorChoiceC(Color.GREEN);
        break;
      case 5:
        this.controller.setColorChoiceC(Color.BLUE);
        break;
      case 6:
        this.controller.setColorChoiceC(Color.INDIGO);
        break;
      default:
        this.controller.setColorChoiceC(Color.VIOLET);
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
   * @return true if the animation is successful.
   * @throws AnimationFailedException if the animation fails.
   *                                  source [1]
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
      AtomicInteger colorChoice = new AtomicInteger();
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
          colorChoice.getAndIncrement();
          setTheColor(colorChoice.get());
          controller.changeDescription(currentDescription);
        });
      }
    });
    slideThread.start();            //starts the animation.
  }
}

