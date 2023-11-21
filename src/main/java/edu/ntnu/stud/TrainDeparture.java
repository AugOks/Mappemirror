package edu.ntnu.stud;

import java.time.LocalTime;


/**
 * The Train departure class represents a train departure and holds the values for the train
 * departure.
 *
 * <p>The class holds onto the departure time as a LocalTime object, which is useful because of
 * LocalTimes pre-made methods that allow comparisons, maximum and minimum time checking, and a
 * slew of other useful checking and validation options.
 * What line the departure runs on is kept and is stored as a String because of the nature of line
 * identifiers, IE a mix of letters and numbers.
 * Train number is kept as an int, because it is assumed that train numbers do not contain letters
 * however, it might be possible for train numbers to contain letters, such as with buses.
 * Which track the train departs from is a simple int as that is what is specified in the assignment
 * The delay the train is experiencing is kept as a LocalTime object for ease of use
 * when working with the departure time.
 * The destination is kept as a string, as destinations are usually written names.
 * </p>
 * <p>While the assignment asks us to specify which fields do not require mutator methods,
 * I have made mutator methods for all fields for ease of validation and expandability.
 * However, not all methods are public, specifically, departureTime, line and train number.
 * the departure time is not allowed to be changed as this the original departure time is to be kept
 * for posterity, if delayed time is necessary, the getDelayedDeparture method is to be used.
 * The line should not be changed. A train does not change line unless at end of station, which is
 * outside the scope of this assignment as this is a departure app and not a train app.
 * the number of the train is not changeable, as this is the unique identifier, if a train needs to
 * change this, a new departure object needs to be made.</p>
 * <p>Delay, Destination and track are mutable. Delay and track are obvious as these can easily vary
 * depending on circumstances.
 * Destination is a bit trickier as there are a few cases in which changing the destination might
 * be necessary, such as if there is an obstruction on the track and the train has to stop earlier.
 * Because of this, I have chosen to make destination mutable, however, future versions of the app
 * needs to validate that the new destination is on the current line and is not before the current
 * stop.
 * The class initially does not accept negative values for all ints and does not accept empty, blank
 * or null strings for String fields.</p>
 */
public class TrainDeparture {
  /**
   * time left to departure.
   * represented with localtime object.
   */
  private LocalTime departureTime;
  /**
   * The line the train follows.
   */
  private String line;
  /**
   * A unique (per day) number of the train.
   */
  private int trainNr;
  /**
   * Which track the train runs on.
   */
  private int track;
  /**
   * the delay of the train if any.
   */
  private LocalTime delay;
  /**
   * the destination of the train.
   */
  private String destination;

  /**
   * Constructs a departure object with given parameters.
   *
   * @param depTime     the time of departure.
   * @param trainLine   the line the train follows.
   * @param trainNumber the unique train number identifier.
   * @param trainTrack  the track that the train stops at.
   * @param delay       the delay (in seconds) of the train.
   * @param destination the final destination of the train.
   */
  public TrainDeparture(LocalTime depTime, String trainLine, int trainNumber, int trainTrack,
                        String destination, LocalTime delay) {
    setDepartureTime(depTime);
    setLine(trainLine);
    setTrainNr(trainNumber);
    setTrack(trainTrack);
    setDelay(delay);
    setDestination(destination);

  }

  /**
   * Constructs a departure object with given parameters without delay parameter.
   *
   * @param depTime     the time of departure.
   * @param trainLine   the line the train follows.
   * @param trainNumber the unique train number identifier.
   * @param trainTrack  the track that the train stops at.
   * @param destination the destination the train will arrive at.
   */
  public TrainDeparture(LocalTime depTime, String trainLine, int trainNumber, int trainTrack,
                        String destination) {
    setDepartureTime(depTime);
    setLine(trainLine);
    setTrainNr(trainNumber);
    setTrack(trainTrack);
    setDestination(destination);
  }

  /**
   * returns the departure time of the train.
   *
   * @return the departure time as a localTime object.
   */
  public LocalTime getDepartureTime() {
    return this.departureTime;
  }

  /**
   * sets the departure time for the train. Sets the departure to 00:00 if null object is passed
   * to method.
   *
   * @param depTime the time of departure given by the user as a localTime object.
   */
  private void setDepartureTime(LocalTime depTime) {
    if (depTime != null) {
      this.departureTime = depTime;
    } else {
      this.departureTime = LocalTime.of(0, 0);
    }
  }

  /**
   * Returns the line the train runs on.
   *
   * @return the line as a string.
   */
  public String getLine() {
    return this.line;
  }

  /**
   * set the line that the train runs on.
   *
   * @param trainLine cannot be an empty or blank string.
   */
  private void setLine(String trainLine) {

    if (trainLine == null || trainLine.isEmpty() || trainLine.isBlank()) {
      this.line = "ERROR";
    } else {
      this.line = trainLine;
    }
  }

  /**
   * returns the unique number of the train.
   *
   * @return the trains number as an int.
   */
  public int getTrainNr() {
    return this.trainNr;
  }

  /**
   * sets the unique number of the train. Must be checked that it does not coincide with another
   * train number.
   *
   * @param trainNumber cannot be a negative number.
   */
  private void setTrainNr(int trainNumber) {
    if (trainNumber > 0) {
      this.trainNr = trainNumber;
    } else {
      this.trainNr = 0;
    }
  }

  /**
   * returns the amount of time the train is delayed by.
   *
   * @return the delay of the train in seconds as an int.
   */
  public LocalTime getDelay() {
    return this.delay;
  }

  /**
   * sets the delay of the train, if any.
   *
   * @param timeDelay cannot be a negative number.
   */
  public void setDelay(LocalTime timeDelay) {
    this.delay = timeDelay;
  }

  /**
   * Returns the track the train runs on.
   *
   * @return the track as an int.
   */
  public int getTrack() {
    return this.track;
  }

  /**
   * returns the destination of the train.
   *
   * @return the destination the train will arrive at.
   */
  public String getDestination() {
    return this.destination;
  }

  /**
   * sets the destination of the train.
   *
   * @param dest cannot be a blank, empty or null string, needs to be on the line the train
   *             is running and not before the current stop.
   */
  public void setDestination(String dest) {
    if (dest == null || dest.isBlank() || dest.isEmpty()) {
      this.destination = "ERROR";
    } else {
      this.destination = dest;
    }
  }

  /**
   * returns the departure time with the delay included. To be used when showing the expected
   * departure time in the UI.
   *
   * @return LocalTime object of the departure time with delay.
   */
  public LocalTime getDelayedDeparture() {
    LocalTime temp;
    if (delay != null) {
      LocalTime delayedDeparture = this.departureTime;

      temp = delayedDeparture.plusHours(this.delay.getHour()).plusMinutes(this.delay.getMinute());
    } else {
      temp = this.departureTime;
    }

    return temp;
  }

  /**
   * sets the track number.
   *
   * @param trackNr cannot be a negative number.
   */
  public boolean setTrack(int trackNr) {
    boolean success;
    if (trackNr > 0) {
      this.track = trackNr;
      success = true;
    } else {
      success = false;
      this.track = 0;
    }
    return success;
  }

  /**
   * Compares the delayed departure time field with other departure time.
   *
   * @param otherTime the time to compare to
   * @return the difference between the times as a comperator.
   */

  public int compareTo(LocalTime otherTime) {
    return this.getDelayedDeparture().compareTo(otherTime);
  }

  /**
   * Compares two train departure times and returns a comparator value for the purpose of sorting.
   *
   * @param otherTrain the object to be compared.
   * @return
   */
  public int compareTo(TrainDeparture otherTrain) {
    return this.getDepartureTime().compareTo(otherTrain.getDepartureTime());
  }
}
