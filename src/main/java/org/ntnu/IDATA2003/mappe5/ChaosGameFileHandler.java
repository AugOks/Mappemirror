package org.ntnu.IDATA2003.mappe5;

/**
 * Represents a class that handles the game files, including reading from and too a given file.
 */
public class ChaosGameFileHandler {

  /**
   * Empty constructor.
   */
  public ChaosGameFileHandler(){

 }

  /**
   * Reads in the contents of a given file.
   * @param path the URI of the given file.
   * @return the contents of the file as a chaosGameDescription object.
   */
 public ChaosGameDescription readFromFile(String path){
   //TODO complete this method.
   return  new ChaosGameDescription(null, null, null);
 }

  /**
   * Writes the details of the chaos game to a file.
   */
 public void writeToFile(ChaosGameDescription disc){
   //TODO: Complete this method.
 }

}
