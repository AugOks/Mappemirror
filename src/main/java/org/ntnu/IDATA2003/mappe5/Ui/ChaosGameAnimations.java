package org.ntnu.IDATA2003.mappe5.Ui;

import static org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory.Fractals.JULIA;

import java.util.Collection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.AnimationFailedException;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;

public class ChaosGameAnimations {
  private ChaosGameDescription currentDescription;
  private ChaosGameControllerGui controller;
  private ChaosGameDescription startDescription;
  private ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();

  public ChaosGameAnimations(ChaosGameDescription description, ChaosGameControllerGui controller) {
    this.currentDescription = description;
    this.startDescription = description;
    this.controller = controller;
    this.factory = new ChaosGameDescriptionFactory();
  }

  public void danceParty() {
    this.controller.changeDescription(this.currentDescription);
    this.danceAnimation(this.currentDescription);
  }

  //https://stackoverflow.com/questions/46570494/javafx-changing-the-image-of-an-imageview-using-timeline-doesnt-work
  private void danceAnimation(ChaosGameDescription description){
    Timeline timeLine = new Timeline();
    Collection<KeyFrame> frames = timeLine.getKeyFrames();
    Duration frameGap = Duration.millis(500);
    Duration frameTime = Duration.ZERO ;
    for (int i = 1; i < 5; i++){
      frameTime = frameTime.add(frameGap);
      int finalI = i;
      frames.add(new KeyFrame(frameTime, e -> this.danceMoves(finalI)));
    }
    timeLine.setCycleCount(3);
    timeLine.play();
  }

  private void danceMoves(int danceMove){
    switch(danceMove){
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
   * Animates the Julia set by changing the complex number of the JuliaTransform.
   * @param description the description of the Julia set.
   * @throws AnimationFailedException if the animation fails.
   */
  public void juliaSliderAnimation(ChaosGameDescription description){
    Thread slideThread = new Thread(() -> {
     JuliaTransform transform1 = (JuliaTransform) description.getTransform(0);
     JuliaTransform transform2 = (JuliaTransform) description.getTransform(1);
      Complex complex1 = transform1.getComplex();
      complex1.setY0(-1.0);
      complex1.setX0(-1.0);
      Complex complex2 = transform2.getComplex();
      complex2.setX0(-1.0);
      complex2.setY0(-1.0);
      while(complex1.getX0() < 1 && complex1.getY0() < 1){
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
           throw new AnimationFailedException("Failed to animate the Julia set");
        }
        Platform.runLater(() -> {
          complex1.setX0(complex1.getX0() + 0.05);
          complex1.setY0(complex1.getY0() + 0.05);
          complex2.setX0(complex2.getX0() + 0.05);
          complex2.setY0(complex2.getY0() + 0.05);
          controller.changeDescription(currentDescription);
        });
      }
    });
    slideThread.start();
  }


}
