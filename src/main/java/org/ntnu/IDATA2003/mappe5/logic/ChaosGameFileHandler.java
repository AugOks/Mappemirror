package org.ntnu.IDATA2003.mappe5.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.FailedToWriteToFileException;
import org.ntnu.IDATA2003.mappe5.entity.exceptions.ResourceNotFoundException;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;


/**
 * Represents a class that handles the game files, including reading from and too a given file.
 */
public class ChaosGameFileHandler {

  ArrayList<ChaosGameDescription> chaosGames;

  TransformsParser parser;

  /**
   * Empty constructor.
   */
  public ChaosGameFileHandler() {
    chaosGames = new ArrayList<>();
    parser = new TransformsParser();

  }

  /**
   * Reads in the contents of a given file.
   *
   * @param path the path to the file to read.
   * @return the contents of the file as a chaosGameDescription object.
    * @throws ResourceNotFoundException if the file could not be found.
   * @throws NumberFormatException    if parsing doubles from string failed.
   */
  public ChaosGameDescription getcontentsOfFile(String path) throws ResourceNotFoundException {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    Path pathOfFile = null;
      pathOfFile = Path.of(path);

    ArrayList<String> fileContent = new ArrayList<>();
    ArrayList<Transform2D> transforms;
    Vector2D minimumCoords = new Vector2D(0, 0);
    Vector2D maximumCoords = new Vector2D(0, 0);

    try (BufferedReader reader = Files.newBufferedReader(pathOfFile)) {
      String text;
      //Removes comments if any on each line of the values
      while ((text = reader.readLine()) != null) {
        String[] textDelimit = text.split("#");
        fileContent.add(textDelimit[0]);
      }
      if (fileContent.get(3) == null) {
        throw new NumberFormatException("Could not read the transforms from the file.");
      }
      minimumCoords = parser.getVectorFromString(fileContent.get(1));
      maximumCoords = parser.getVectorFromString(fileContent.get(2));
      transforms = new ArrayList<>(parser.getTransformsFromStrings(fileContent));

    } catch (IOException e) {
     throw new ResourceNotFoundException("Could not read the file " + e.getMessage());
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Could not parse doubles from string ");
    }
      String name = path.split(".txt")[0];
    ChaosGameDescription game = new ChaosGameDescription(transforms, minimumCoords, maximumCoords,
        name);
    this.chaosGames.add(game);
    return game;
  }

  /**
   * Reads the contents of a file with a given fractal name.w
   * @param fractal the name of the fractal to read from file.
   * @return the chaos game description of the fractal.
   * @throws ResourceNotFoundException
   */
  public ChaosGameDescription readFromFileWithFractalName(String fractal)
      throws ResourceNotFoundException {

    if (Objects.requireNonNull(getClass().getClassLoader().getResource(fractal + ".txt"))
        .toExternalForm().isBlank()){
      throw new ResourceNotFoundException("Could not find fractal file with this name");
    }
    String filePath;
    try {
      filePath = (Objects.requireNonNull(
          getClass().getClassLoader().getResource(fractal + ".txt")))
          .toExternalForm().replace("file:/", "");
    } catch (NullPointerException e) {
      throw new ResourceNotFoundException("Could not find the file" + e.getMessage());
    }
    return getcontentsOfFile(filePath);
  }
  /**
   * Writes the details of the chaos game to a file, the file will have the name of the fractal but
   * if no name is given a new file name with a random number will be generated to avoid any file
   * reader issues.
   */
  public void writeToFile(String path, ChaosGameDescription description)
      throws FailedToWriteToFileException {
    if (description == null) {
    throw new IllegalArgumentException("Description cannot be null");
  }
    Path pathOfFile = Path.of(path + ".txt");

    try (BufferedWriter output = Files.newBufferedWriter(pathOfFile)) {
      List<String> chaosGameInfo = parser.getChaosGameInfoAsString(description);

      output.write(
          chaosGameInfo.get(1) + chaosGameInfo.get(2) + chaosGameInfo.get(3)
              + chaosGameInfo.get(4));

    } catch (IOException e) {
      throw new FailedToWriteToFileException("something went wrong when writing to file");
      //TODO needs to be fixed?
    }

  }




}
