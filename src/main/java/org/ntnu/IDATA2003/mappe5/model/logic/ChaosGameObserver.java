package org.ntnu.IDATA2003.mappe5.model.logic;

/**
 * An interface defining a set of methods that any object that would like to
 * subscribe to changes in objects of the chaosGame, must implement.
 */
public interface ChaosGameObserver {

  /**
   * Called whenever the observed object needs to signal to its observers that a change
   * has occurred.
   */
  void update();
}
