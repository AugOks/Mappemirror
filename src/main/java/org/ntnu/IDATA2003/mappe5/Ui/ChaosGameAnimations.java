package org.ntnu.IDATA2003.mappe5.Ui;

import static org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory.Fractals.JULIA;

import java.util.Collection;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.logic.ChaosGameDescriptionFactory;

public class ChaosGameAnimations {
  private ChaosGameDescription currentDescription;
  private ChaosGameControllerGui controller;
  private ChaosGameDescription startDescription;
  private ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();

  public ChaosGameAnimations(ChaosGameDescription description) {
    this.currentDescription = description;
    this.startDescription = description;
    this.factory = new ChaosGameDescriptionFactory();
  }

  public void danceParty(ChaosGameControllerGui controller) {
    this.controller = controller;
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


}
