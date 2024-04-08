package org.ntnu.IDATA2003.mappe5.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
   * @param fractal the name of the file without exstension.
   * @return the contents of the file as a chaosGameDescription object.
   * @throws IllegalArgumentException if no file with the given fractal name could be found.
   * @throws NumberFormatException    if parsing doubles from string failed.
   */
  public ChaosGameDescription readFromFile(String fractal) {
    URL url = getClass().getClassLoader().getResource(fractal + ".txt");
    Path pathOfFile = null;
    try {
      pathOfFile = Path.of(url.toURI());
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
    ArrayList<String> fileContent = new ArrayList<>();
    ArrayList<Transform2D> transforms = new ArrayList<>();
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
      transforms.addAll(this.getTransformsFromStrings(fileContent));

    } catch (IOException e) {
      throw new IllegalArgumentException(
          "File with this name could not be found " + e.getMessage());
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Could not parse doubles from string ");
    } catch (IndexOutOfBoundsException e) {
      //TODO do something here
    }

    ChaosGameDescription game = new ChaosGameDescription(transforms, minimumCoords, maximumCoords,
        fractal);
    this.chaosGames.add(game);
    return game;
  }

  /**
   * Gets all transforms read in from file and returns it as a list of transform2Ds.
   *
   * @param fileContent The content of the file to be turned into transforms.
   * @return The list of transforms.
   */
  private ArrayList<Transform2D> getTransformsFromStrings(ArrayList<String> fileContent) {
    ArrayList<Transform2D> transforms = new ArrayList<>();
    String typeOfTransf = parser.cleanString(fileContent.getFirst());
    List<String> parseString = fileContent.subList(3, fileContent.size());
    if (typeOfTransf.equals("Affine2D")) {
      transforms.addAll(parser.parseAffineTransforms(parseString));
    }
    if (typeOfTransf.equals("Julia")) {
      transforms.addAll(parser.parseJuliaTransforms(fileContent.get(3)));
    }
    return transforms;
  }

  /**
   * Writes the details of the chaos game to a file, the file will have the name of the fractal but
   * if no name is given a new file name with a random number will be generated to avoid any file
   * reader issues.
   */
  public void writeToFile(ChaosGameDescription description) {
    Path pathOfFile;
    if (description == null) {
      throw new IllegalArgumentException("Description cannot be null");
    }

    if (!description.getName().isBlank()) {
      pathOfFile = Path.of(description.getName() + "Out.txt");
    } else {
      Random rand = new Random();
      rand.nextInt(100);       //Generates a random name to give to the nameless fractal.
      pathOfFile = Path.of("fractal" + rand + ".txt");
    }
    try (BufferedWriter output = Files.newBufferedWriter(pathOfFile)) {

      List<String> chaosGameInfo = this.getChaosGameInfoAsString(description);

      output.write(
          chaosGameInfo.get(1) + chaosGameInfo.get(2) + chaosGameInfo.get(3)
              + chaosGameInfo.get(4));

    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to file " + e.getMessage());
      //TODO needs to be fixed?
    }

  }


  /**
   * Turns the information of the ChaosGame description into writable strings and returns it.
   * <ul>
   *   <li>The first element of the list is a un-appended name of the transform, used to compare
   *   what type of transform that is being read.</li>
   *   <li>Second element is the name of the transform that will have more text appended, unsuitable
   *   for comparing.</li>
   *   <li>Third element is the minimum coordinates of the transformation.</li>
   *   <li>Fourth element is the maximum coordinates of the transformation.</li>
   * </ul>
   *
   * @param description the chaos game description to be turned into strings.
   * @return transformInfo
   */
  private ArrayList<String> getChaosGameInfoAsString(ChaosGameDescription description) {
    //TODO use stringbuilder instead.
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }
    ArrayList<String> transformInfo = new ArrayList<>();
    String transform =
        description.getTransform(0).getClass().getSimpleName()
            .replaceAll("Transform", "");
    String compare = new String(transform);
    transform += "           # type of transform" + '\n';
    String minCoords =
        description.getMinCoords().getX0() + ", " + description.getMinCoords().getY0();
    minCoords += "           # lower left coordinates" + '\n';
    String maxCoords =
        description.getMaxCoords().getX0() + ", " + description.getMaxCoords().getY0();
    maxCoords += "           # upper right coordinates" + '\n';
    transformInfo.add(compare);
    transformInfo.add(transform);
    transformInfo.add(minCoords);
    transformInfo.add(maxCoords);
    String transformsAsStrings = "";

    if (compare.equals("Affine2D")) {
      transformsAsStrings = this.writeAffineToString(description.getAllTransforms());
    } else if (compare.equals("Julia")) {
      transformsAsStrings = description.getTransform(0).transformToString()
          + " # real and imaginary part of constant c";
    }
    transformInfo.add(transformsAsStrings);
    return transformInfo;
  }

  /**
   * Writes all the affine matrices to a singular continuous string with a CSV format,
   * used to write to file.
   *
   * @param transforms The list of transforms to be turned into strings.
   * @return The string of all the transforms.
   */
  private String writeAffineToString(List<Transform2D> transforms) {
    String matrices = "";
    int index = 1;
    for (Transform2D transf : transforms) {
      String matrixValues = "";
      matrixValues = transf.transformToString() + "# transform number: " + index + '\n';
      index++;
      matrices += matrixValues;
    }
    return matrices;
  }

}
