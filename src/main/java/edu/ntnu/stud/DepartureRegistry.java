package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * <h6>represents a registry of train departures.</h6>
 * <p>Holds a Hashmap that acts as the main registry which
 * is manipulated by this class. Using a Hashmap is useful because it lets us quickly and easily
 * sort a list based on departures, as well as easily find a departure based on the train number.
 * In this case the train number acts as the key to the departure object, when the registry only
 * holds departures within a 24hr period this is fine as no train number is duplicated. However,
 * if more than one day was to be represented in the registry, a more unique identifier would be
 * necessary as trains often have identical departures every day.</p>
 */
public class DepartureRegistry {

  private HashMap<Integer, TrainDeparture> depRegistry;


  public DepartureRegistry() {
    depRegistry = new HashMap<>();
    this.fillRegWithDummies();
  }


  /**
   * finds a train by its unique train number.
   *
   * @param trainNumber the unique train number to search on.
   * @return returns the index of the train in the registry
   * @throws IndexOutOfBoundsException if no train found
   */
  public TrainDeparture findTrainByNumber(int trainNumber) throws IndexOutOfBoundsException {
    TrainDeparture foundTrain = depRegistry.get(trainNumber);
    if (foundTrain == null){
      throw new IndexOutOfBoundsException("No train found");
    }
    return foundTrain;
  }

  /**
   * Checks if a train already exists in the registry
   * @param trainNr The train to search by
   * @return true if train is duplicate, false if it's not.
   */
  public boolean checkIfTrainDuplicate(int trainNr){
    boolean isDuplicate = false;
    if (depRegistry.containsKey(trainNr)){
      isDuplicate = true;
    }
    return isDuplicate;
  }
/*
  /**
   * Adds a new departure to the registry. New departure cannot have the same train number as
   * an existing departure.
   *
   * @param dest    The destination of the train.
   * @param track   the track the train will arrive at.
   * @param trainNr the unique number of the train.
   * @param line    what line the train runs on.
   * @param depTime the time of departure.
   * @return returns a boolean based on if the departure was successfully added or not;
   *
  public boolean addDeparture(LocalTime depTime, String line, int trainNr, int track, String dest) {
    boolean success;
    if (departureValidation(depTime, line, trainNr, track, dest)) {

      TrainDeparture departure = new TrainDeparture(depTime, line, trainNr, track, dest);
      depRegistry.put(trainNr, departure);
      success = true;
    } else {
      success = false;
    }
    return success;
  }
*/
  /**
   * Adds a departure object to registry if it succeeds validation.
   *
   * @param departure the complete departure object to be added.
   * @return true or false based on the outcome.
   */
  public boolean addDeparture(TrainDeparture departure) {
    boolean success;
    if (departureValidation(departure)) {
      depRegistry.put(departure.getTrainNr(), departure);
      success = true;
    } else {
      success = false;
    }
    return success;
  }

  /*
   * adds a new departure to the registry with delay.
   *
   * @param dest    The destination of the train.
   * @param track   the track the train will arrive at.
   * @param trainNr the unique number of the train.
   * @param line    what line the train runs on.
   * @param depTime the time of departure.
   * @param delay   the amount of time the train is delayed by.
   *
  public boolean addDepartureWithDelay(LocalTime depTime, String line, int trainNr, int track
      , String dest, LocalTime delay) {
    boolean success = false;

    if (departureValidation(depTime, line, trainNr, track, dest) && delay != null) {
      TrainDeparture departure = new TrainDeparture(depTime, line, trainNr, track, dest, delay);
      depRegistry.put(trainNr, departure);
      success = true;
    }
    return success;
  }
*/
  /**
   * Removes the Departures that are before the new current time.
   *
   * @param currentTime The new, updated current time.
   */
  public void removePreviousDeparture(LocalTime currentTime) {
    Iterator<TrainDeparture> departures = depRegistry.values().iterator();

    while (departures.hasNext()) {
      TrainDeparture tempDep = departures.next();
      if (tempDep.compareTo(currentTime) < 0) {
        departures.remove();
      }
    }
  }

  /**
   * Finds all departures who have the destination given by user.
   * Returns an empty Arraylist if nothing found.
   *
   * @param destination the user given destination which is searched on.
   * @return a list of all departures with the given destination. Does not return null
   */
  public List<TrainDeparture> findDepByDest(String destination) {
    List<TrainDeparture> destinationlist = new ArrayList<>();
    String destCase = destination.toLowerCase();

    this.depRegistry.values().forEach(departures -> {
      if (destCase.equalsIgnoreCase(departures.getDestination())) {
        destinationlist.add(departures);
      }
    });
    return destinationlist;
  }

  /**
   * Fils the registry with temporary dummmy objects for testing purposes.
   */
  private void fillRegWithDummies() {
    LocalTime time = LocalTime.of(10, 30);
    LocalTime time2 = LocalTime.of(10, 50);
    LocalTime time3 = LocalTime.of(10, 40);
    LocalTime time4 = LocalTime.of(10, 20);
    int trainNumber1 = 20;
    int trainNumber2 = 30;
    int trainNumber3 = 320;
    int trainNumber4 = 520;
    TrainDeparture test = new TrainDeparture(time, "L1", trainNumber1, 1,
        "Drammen");
    TrainDeparture test2 = new TrainDeparture(time2, "L2", trainNumber2, 2,
        "oslo");
    TrainDeparture test3 = new TrainDeparture(time3, "L3", trainNumber3, 3,
        "oslo");
    TrainDeparture test4 = new TrainDeparture(time4, "L4", trainNumber4, 4,
        "Gøteborg");
    depRegistry.put(trainNumber1, test);
    depRegistry.put(trainNumber2, test2);
    depRegistry.put(trainNumber3, test3);
    depRegistry.put(trainNumber4, test4);
  }

  /**
   * returns all train departures as an iterator.
   *
   * @return all departures as an iterator
   */
  public Iterator<TrainDeparture> getAllDepartures() {
    Iterator<TrainDeparture> it = depRegistry.values().iterator();
    return it;
  }

  /**
   * Returns the size of the registry as an int.
   *
   * @return the size of the registry.
   */
  public int getSizeOfRegistry() {
    return depRegistry.size();
  }

  /**
   * Sorts all departures by their original departure time.
   *
   * @return a sorted list of departures.
   */
  public List<TrainDeparture> getSortedDeparturesByTime() {
    List<TrainDeparture> sortedDepartures = new ArrayList<>(this.depRegistry.values());

    sortedDepartures.sort(
        (TrainDeparture train1, TrainDeparture train2) -> train1.compareTo(train2)
    );
    return sortedDepartures;
  }
    /**
     * Sorts all departures based on their actual departure time, including any delay if any.
     * returns
     */
  public List<TrainDeparture> getSortedDeparturesWithDelay() {
    List<TrainDeparture> sortedDepartures = new ArrayList<>(this.depRegistry.values());

    sortedDepartures.sort(
        (TrainDeparture train1, TrainDeparture train2) ->
            train1.compareTo(train2.getDelayedDeparture())
    );
    return sortedDepartures;
  }


  /**
   * Adds delay to a given departure based on parameters.
   *
   * @param trainNr The train number of the departure to add delay too
   * @param delay Amount of delay the departure is experiencing.
   * @return Boolean success if the operation failed or succeeded.
   */
  public boolean addDelayToTrain(int trainNr, LocalTime delay) {
    boolean success;
    TrainDeparture foundTrain = depRegistry.get(trainNr);
    if (foundTrain != null && delay != null) {
      foundTrain.setDelay(delay);
      success = true;
    } else {
      success = false;
    }
    return success;
  }

  /**
   * Checks if a given string is "good" by making sure it is not null, empty, blank or filled with
   * default value for incorrectly created departure.
   *
   * @param gString the string to be validated.
   * @return true or false based on the success of the operation.
   */
  public boolean stringIsGood(String gString) {
    boolean good = false;
    if (gString != null && !gString.isBlank() && !gString.equalsIgnoreCase("ERROR")) {
      good = true;
    }
    return good;
  }

  /*
   * Validates the fields of a given departure using stringIsGood method and manual validation of
   * fields.
   *
   * @param depTime The departure time of the train.
   * @param line    What line the train follows.
   * @param trainNr the unique number of the train.
   * @param track   what track the train runs on.
   * @param dest    the trains final destination.
   * @return true or false based on if anything failed validation or not.
   *
  public boolean departureValidation(LocalTime depTime, String line, int trainNr, int track
      , String dest) {
    boolean validated = false;
    if (!depRegistry.containsKey(trainNr)
        && stringIsGood(line)
        && stringIsGood(dest)
        && trainNr > 0
        && track > 0) {
      validated = true;
    }
    return validated;
  }
*/
  /**
   * Validates the fields of a given departure using stringIsGood method and manual validation of
   * fields.
   *
   * @param departure the complete departure to be validated.
   * @return true or false based on if anything failed validation or not.
   */
  public boolean departureValidation(TrainDeparture departure) {
    boolean validated = false;
    if (!depRegistry.containsKey(departure.getTrainNr())
        && stringIsGood(departure.getLine())
        && stringIsGood(departure.getDestination())
        && departure.getTrainNr() > 0
        && departure.getTrack() > 0) {
      validated = true;
    }
    return validated;
  }
}
