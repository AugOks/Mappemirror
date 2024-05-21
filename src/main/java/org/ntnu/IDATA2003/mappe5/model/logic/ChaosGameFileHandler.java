package org.ntnu.IDATA2003.mappe5.model.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.FailedToWriteToFileException;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.ResourceNotFoundException;


/**
 * Represents a class that handles the game files, including reading from and to a given file.
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
   * @throws NumberFormatException     if parsing doubles from string failed.
   */
  public ChaosGameDescription getcontentsOfFile(String path) throws ResourceNotFoundException {
    if (path == null) {
      throw new IllegalArgumentException("Path cannot be null");
    }
    Path pathOfFile = null;
    pathOfFile = Path.of(path);

    ArrayList<String> fileContent = new ArrayList<>();
    ArrayList<Transform2D> transforms;
    new Vector2D(0, 0);
    Vector2D minimumCoords;
    Vector2D maximumCoords;

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
   * Writes the details of the chaos game to a file, the file will have the name of the fractal.
   *
   * @param path        the path to the file to write to.
   * @param description the description of the chaos game to write to file.
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
      }

  }


}
