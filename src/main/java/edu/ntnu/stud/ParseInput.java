package edu.ntnu.stud;

import java.time.LocalTime;
import java.util.Scanner;

public class ParseInput {

  private Scanner scanner;

  public ParseInput() {
    scanner = new Scanner(System.in);
  }
  /**
   * Gets time from user as a string and then uses Integer wrapper class to parse it to ints,
   * which is what LocalTime requires to create temporal objects.
   * Checks that user input is within a 24hr format.
   *
   * @return a LocalTime object
   */
  public LocalTime getTimeFromUser() {
    LocalTime timeObject = null;
    while (timeObject == null) {

      String time = scanner.next();
      String[] numbers = time.split(":");

      int hours = Integer.parseInt(numbers[0]);
      int minutes = Integer.parseInt(numbers[1]);

      if (hours >= 0 && hours < 24 && minutes >= 0 && minutes < 60) {
        timeObject = LocalTime.of(hours, minutes);

      }
    }
    return timeObject;
  }

  /**
   * Takes in an int from user and returns it
   * @return an int from the user
   */
  public int parseInt() {
    int output = -1;
    if (scanner.hasNextInt()) {
      output = scanner.nextInt();
    }
    return output;
  }

  /**
   * Takes in a string from user and returns it
   * @return a string from the user
   */
  public String parseStr(){
    String output = "";
    if(scanner.hasNext()){
      output = scanner.next();
    }
    return output;
  }

  /**
   * Takes in a string from user and checks if it is a valid string for a destination.
   * It is assumed that no destination will have numbers in their name.
   * In the future it would be prudent to check a registry of valid destinations here.
   * @return a string that contains no numbers.
   */
  public String parseDest(){
    String output = "";
    if (scanner.hasNext()){
      output = scanner.next();
      if(!output.matches("[A-z]+")){
        output = "";
      }
    }
    return output;
  }
}
