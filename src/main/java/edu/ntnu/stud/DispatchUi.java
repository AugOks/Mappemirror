package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


/**
 * represents the UI of the dispatch application
 * Holds a train registry of DepartureRegistry
 * Holds current time as a LocalTime object.
 */
public class DispatchUi {
  private final static char SHOW_DEPARTURES = 's';
  private final static char ADD_NEW_DEPARTURE = 'a';
  private final static char CHANGE_TRACK = 'c';
  private final static char ADD_DELAY = 'd';
  private final static char FIND_DEP_BY_NUMBER = 'f';
  private final static char FIND_DEP_BY_DEST = 'b';
  private final static char UPDATE_TIME = 't';
  private final static char EXIT = 'x';
  private static final char SHOW_DELAYED_DEPARTURES = 'k';
  /**
   * Copy of the current time.
   */
  private CurrentTime currentTime;
  /**
   * A registry holding all departures.
   */
  private DepartureRegistry depRegistry;

  private ParseInput parser;

  /**
   * Constructor
   */
  public DispatchUi() {
    depRegistry = new DepartureRegistry();
    parser = new ParseInput();

  }

  /**
   * Creates the welcome splash message.
   */

  private void displayWelcomeText() {
    System.out.println("Welcome to the train dispatch app, Version: 0.7");
    System.out.println("This application allows you to do everything needed to manage train "
        + "departures:");
  }

  /**
   * creates the main menu and shows the options to the user.
   *
   * @return a char character for use in switch case.
   */
  private char displayMenu() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("--------------------------------------------------------------------");
    System.out.println("options:");
    System.out.println("Show all departures - S");
    System.out.println("Show all departures with delay - K");
    System.out.println("Add new train departure - A");
    System.out.println("Change the track of an incoming departure - C");
    System.out.println("Add delay to a departure - D");
    System.out.println("Find a departure based on train number - F");
    System.out.println("Find a departure based on destination - B");
    System.out.println("Update the current time - T");
    System.out.println("exit the app - X");
    String tempString = scanner.next();
    return tempString.toLowerCase().charAt(0);
    //Turning string into char, makes it easier should the user
    //accidentally type 2 characters.
  }

  /**
   * Start method: displays welcome splash message, displays the menu and starts a switch case
   * for all options.
   */
  public void start() {
    boolean finished = false;
    char choice;
    displayWelcomeText();

    while (!finished) {
      choice = displayMenu();

      switch (choice) {
        case SHOW_DEPARTURES:
          System.out.println("Train departures: \n");
          this.printDepartures((depRegistry.getSortedDeparturesByTime().iterator()));
          break;
        case SHOW_DELAYED_DEPARTURES:
          System.out.println("Train departures: \n");
          this.printDepartures((depRegistry.getSortedDeparturesWithDelay().iterator()));
        case ADD_NEW_DEPARTURE:
          this.addNewDeparture();
          break;
        case CHANGE_TRACK:
          this.changeTrack();
          break;
        case ADD_DELAY:
          this.addDelayToTrain();
          break;
        case FIND_DEP_BY_NUMBER:
          this.findDepartureFromNumber();
          break;
        case FIND_DEP_BY_DEST:
          this.printSelectedDepartures();
          break;
        case UPDATE_TIME:
          updateTime();
          break;
        case EXIT:
          System.out.println("goodbye!");
          finished = true;
          break;
        default:
          System.out.println("invalid choice");
      }
    }
  }

  public void init() {
  }

  public void addDelayToTrain(){
    Scanner scanner = new Scanner(System.in);
    System.out.println("the train number for the train you want to add delay to: ");
    int trainNr = parser.parseInt();

    System.out.println("The amount of delay to add to the departure: ");
    LocalTime delay =parser.getTimeFromUser();

    if (depRegistry.addDelayToTrain(trainNr, delay)){
      System.out.println("Delay successfully added");
    }
    else{
      if (delay == null){
        System.out.println("invalid input in delay");
      }
      else {
        System.out.println("Adding delay failed");
      }
    }
  }

  /**
   * Find a given departure based the train number for that departure.
   */
  private void findDepartureFromNumber(){
    System.out.println("the train number for the departure you wish to find: ");
    int trainNr = parser.parseInt();
    this.departureDisplayFormatting();
    try {
      this.printDeparture(this.depRegistry.findTrainByNumber(trainNr));
    }catch (IndexOutOfBoundsException e){
      System.out.println(e.getMessage());
    }
  }

  /**
   * Updates the current time to a new time, and then removes all departed departures.
   */
  private void updateTime(){
    System.out.println("What is the current time? (format 00:00): ");
    currentTime.updateCurrentTime(parser.getTimeFromUser());
    depRegistry.removePreviousDeparture(currentTime.returnCurrentTime());
  }

  /**
   * Finds departures based on destination and prints a list of those departures.
   *
   */
  private void printSelectedDepartures() {
    System.out.print("please write the destination you wish to search by: ");
    Scanner scanner = new Scanner(System.in);
    List<TrainDeparture> departures = this.findDepartureByDestination();
    if (!departures.isEmpty()) {
      Iterator<TrainDeparture> it = departures.iterator();
      this.printDepartures(it);
    }
    else{
      System.out.println("there are currently no trains going to that destination");
    }
  }

  /**
   * Adds new departure from user in terminal. Does rudimentary validation of input and informs
   * user if adding departures failed or succeeded based on if the validation succeeded.
   */
  public void addNewDeparture() {
    boolean success;
    System.out.println("New train departure\n");

    System.out.println("train number: ");
    int trainNr = parser.parseInt();
    if (!depRegistry.checkIfTrainDuplicate(trainNr)) {

      System.out.println("Departure time: ");
      LocalTime depTime = parser.getTimeFromUser();

      System.out.println("Train Line: ");
      String line = parser.parseStr();

      System.out.println("Track number: ");
      int track = parser.parseInt();
      System.out.println("Destination: ");
      String dest = parser.parseDest();

      success = depRegistry.addDeparture(new TrainDeparture(depTime, line, trainNr, track, dest));
      if (success){
        System.out.println("Departure was successfully added");
      }else{
        System.out.println("Something went wrong when adding the departure, please try again");
      }

    }else {
      System.out.println("Departure with this train number already exists");
    }
  }

  /**
   * Returns a list of all destinations matching the search.
   *
   * @return A list of all destinations matching the search
   */

  private List<TrainDeparture> findDepartureByDestination() {

    List<TrainDeparture> departures = null;
    String input = parser.parseDest();

    if (!input.isEmpty()) {

      departures = depRegistry.findDepByDest(input);

    } else {
      System.out.println("Invalid input, please only use alphabetical characters");

    }
    if (departures == null || departures.isEmpty()) {
      System.out.println("No matching departures with given destination found");
    } else {

    }
    return departures;
  }

  /**
   * Changes the trains current track to a new one assigned by the user in terminal.
   */
  private void changeTrack() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Train number for the train whom you wish to change the track: ");
    int trainNumber = parser.parseInt();
    if (trainNumber > 0) {

      try {
        TrainDeparture tempDep = depRegistry.findTrainByNumber(trainNumber);
        System.out.println(
            "Departure successfully found, current track is: " + tempDep.getTrack() +
                " What track would you like to change it to?: ");
        int track = parser.parseInt();
        if (track > 0) {
          if (tempDep.setTrack(track)) {
            System.out.println("Track successfully changed");
          } else {
            System.out.println("Track changed failed, enter a valid number");
          }
        } else {
          System.out.println("invalid train track, please write a valid number");
        }
      }catch (IndexOutOfBoundsException e){
        System.out.println(e.getMessage());
      }
      }
  }

  /**
   * A line that displays the departure formatting.
   */
  private void departureDisplayFormatting(){
    System.out.println("Departure" + '\t' + "track" + '\t' + "Train" + '\t' + "line" + '\t' +
        "destination");
  }

  /**
   * prints a single departure to terminal
   * @param departure the departure to be printed.
   */
  private void printDeparture( TrainDeparture departure) {

      System.out.print(departure.getDelayedDeparture());
      System.out.print("" + '\t' + '\t');
      System.out.print(departure.getTrack());
      System.out.print("" + '\t' + '\t');
      System.out.print(departure.getTrainNr());
      System.out.print('\t');
      System.out.print('\t' + departure.getLine());
      System.out.println("" + '\t' + '\t' + departure.getDestination());

    }
  /**
   * Prints all departures in a given iterator.
   *
   * @param depList The Iterator to be printed.
   */
  private void printDepartures(Iterator<TrainDeparture> depList) {
    this.departureDisplayFormatting();

    Iterator<TrainDeparture> departures = depList;
    while (departures.hasNext()) {
      TrainDeparture tempDep = departures.next();
      printDeparture(tempDep);
    }
  }
}
