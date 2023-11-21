package edu.ntnu.stud;

import java.time.LocalTime;

/**
 * Represents a class that holds and keeps track of the current time in the application.
 *
 */

public class CurrentTime {

  /**
   * The current time in the application
   */
  public LocalTime currentTime;

  /**
   * Constructor, sets the currentTime
   *
   * @param hours the current hour
   * @param minutes the current minutes
   */
  public CurrentTime(int hours, int minutes){
    currentTime = LocalTime.of(hours, minutes);
  }

  /**
   * updates the current time to a new time
   * @param newTime the new time to update to
   */
  public void updateCurrentTime(LocalTime newTime){
    currentTime = newTime;
  }

  /**
   * Returns the current time.
   *
   * @return the current time.
   */
  public LocalTime returnCurrentTime(){
    return currentTime;
  }
}
