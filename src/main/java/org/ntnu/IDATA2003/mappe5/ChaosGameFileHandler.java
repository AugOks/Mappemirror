package org.ntnu.IDATA2003.mappe5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.ntnu.IDATA2003.mappe5.Ui.ChaosGameUi;

/**
 * Represents a class that handles the game files, including reading from and too a given file.
 */
public class ChaosGameFileHandler {

  ArrayList<ChaosGameDescription> chaosGames;

  /**
   * Empty constructor.
   */
  public ChaosGameFileHandler() {
    chaosGames = new ArrayList<>();

  }

  /**
   * Reads in the contents of a given file.
   *
   * @param fractal the name of the file without exstension.
   * @return the contents of the file as a chaosGameDescription object.
   * @throws  IllegalArgumentException if no file with the given fractal name could be found.
   * @throws NumberFormatException if parsing doubles from string failed.
   */
  public ChaosGameDescription readFromFile(String fractal)  {
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
      //TODO add check for file contents
      while ((text = reader.readLine()) != null) {  //Removes comments if any on each line of the values.
        String[] textDelimit = text.split("#");
        fileContent.add(textDelimit[0]);
      }
      minimumCoords = this.getVectorFromFileContents(fileContent.get(1));
      maximumCoords = this.getVectorFromFileContents(fileContent.get(2));
      transforms.addAll(this.getTransformsFromFileContents(fileContent));

    } catch (IOException e) {
      throw new IllegalArgumentException("File with this name could not be found " + e.getMessage());
    } catch (NumberFormatException e) {
      throw new NumberFormatException("Could not parse doubles from string ");
    }catch (IndexOutOfBoundsException e){
      //TODO do something here
    }

    ChaosGameDescription game = new ChaosGameDescription(transforms, minimumCoords, maximumCoords,
                                                         fractal);
    this.chaosGames.add(game);
    return game;
  }

  /**
   * Gets all transforms read in from file and returns it as a list of transform2Ds.
   * @param fileContent The content of the file to be turned into transforms.
   * @return The list of transforms.
   */

  private ArrayList<Transform2D> getTransformsFromFileContents(ArrayList<String> fileContent){
    ArrayList<Transform2D> transforms = new ArrayList<>();
    String typeOfTransf =  this.cleanString(fileContent.get(0));
    if (typeOfTransf.equals("Affine2D")) {
      transforms.addAll(parseAffineTransforms(fileContent));
    }
    if (typeOfTransf.equals("Julia")) {
      transforms.addAll(this.parseJuliaTransforms(fileContent.get(3)));
    }
    return transforms;
  }

  /**
   * Writes the details of the chaos game to a file, the file will have the name of the fractal but if no name is given
   * a new file name with a random number will be generated to avoid any file reader issues.
   */
  public void writeToFile(ChaosGameDescription description) {
    Path pathOfFile;
    if(description == null ){
      throw new IllegalArgumentException("one of the values in the description is null");
    }

    if ( !description.getName().isBlank() ) {
      pathOfFile = Path.of(description.getName() + "out.txt");
    } else {
      Random rand = new Random();
      rand.nextInt(100);       //Generates a random name to give to the nameless fractal.
      pathOfFile = Path.of("fractal" + rand + ".txt");
    }
    try (BufferedWriter output = Files.newBufferedWriter(pathOfFile)) {

      List<String> chaosGameInfo = this.getChaosGameInfoAsString(description);

      output.write(
          chaosGameInfo.get(1) + chaosGameInfo.get(2) + chaosGameInfo.get(3) + chaosGameInfo.get(4));

    } catch (IOException e) {

    }

  }

  /**
   * Takes a @ChaosGameDescription, gets all the transforms and parses them to a string depending
   * on which type of transform is relevant.
   * @param description The chaos game description to be
   * @return
   */


  /**
   * Turns the information of the chaosgame description into writable strings and returns it.
   * <ul>
   *   <li>The first element of the list is a un-appended name of the transform, used to compare what type of transform
   *   that is being read.</li>
   *   <li>Second element is the name of the transform that will have more text appended, unsuitable for comparing.</li>
   *   <li>Third element is the minimum coordinates of the transformation.</li>
   *   <li>Fourth element is the maximum coordinates of the transformation.</li>
   * </ul>
   * @param description the chaos game description to be turned into strings.
   * @return
   */
  private ArrayList<String> getChaosGameInfoAsString(ChaosGameDescription description) {
    //TODO use stringbuilder instead.
    ArrayList<String> transformInfo = new ArrayList<>();
    String transform = description.getTransform(0).getClass().getSimpleName().replaceAll("Transform", "");
    String compare = new String(transform);
    transform += "           # type of transform" + '\n';
    String minCoords = description.getMinCoords().getX0() + ", " + description.getMinCoords().getY0();
    minCoords += "           # lower left coordinates" + '\n';
    String maxCoords = description.getMaxCoords().getX0() + ", " + description.getMaxCoords().getY0();
    maxCoords += "           # upper right coordinates" + '\n';
    transformInfo.add(compare);
    transformInfo.add(transform);
    transformInfo.add(minCoords);
    transformInfo.add(maxCoords);
    String transformsAsStrings = "";

    if (compare.equals("Affine2D")) {
      transformsAsStrings = this.writeAffineToString(description.getAllTransforms());
    }
    else if (compare.equals("Julia")) {
      transformsAsStrings = description.getTransform(0).transformToString() +
          " # real and imaginary part of constant c";
    }
    transformInfo.add(transformsAsStrings);
    return transformInfo;
  }

  /**
   * Writes all the affine matrices to a singular continuous string with a CSV format, used to write to file.
   * @param transforms The list of transforms to be turned into strings.
   * @return
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

  /**
   * Parses the affine transforms from the file content.
   *
   * @param fileContent the content of the file.
   * @return a list of affine transforms.
   */
  public List<AffineTransform2D> parseAffineTransforms(ArrayList<String> fileContent) {
    ArrayList<Double> transfValues
        = new ArrayList<>();              //Matrix value, shortened for legibility
    //TODO: make sure fileContent has the right content
    List<AffineTransform2D> affineTransf = new ArrayList<>();
    for (int i = 3; i < fileContent.size(); i++) {
      String[] transforms = fileContent.get(i).split(",");
      for (String value : transforms) {
        transfValues.add(Double.parseDouble(value));
      }

      affineTransf.add(new AffineTransform2D(
          new Matrix2x2(transfValues.get(0), transfValues.get(1), transfValues.get(2),
                        transfValues.get(3)), // first four values contain the matrix
          new Vector2D(transfValues.get(4),
                       transfValues.get(5))));  // last two values contain the vector.
      transfValues.clear();
    }
    return affineTransf;
  }

  /**
   * Takes in a dirty string filled with spaces and removes them.
   * @param dirtyString the string to be cleaned of spaces.
   * @return the clean string.
   */
  private String cleanString(String dirtyString){
    return dirtyString.replaceAll("\\s", "");
  }

  /**
   * Parses a julia complex string containing one complex number into two distinct transforms with inverted signs.
   * @param juliaComplex the String containing the Julia transform values.
   * @return the list of Julia transforms.
   */
  private List<JuliaTransform> parseJuliaTransforms(String juliaComplex) {
    ArrayList<JuliaTransform> transforms = new ArrayList<>();
    juliaComplex = cleanString(juliaComplex);
    String[] juliaValues = juliaComplex.split(",");
    double real = Double.parseDouble(juliaValues[0]);
    double imag = Double.parseDouble(juliaValues[1]);

    Complex complex = new Complex(real, imag);
    transforms.add(new JuliaTransform(complex, 1));
    transforms.add(new JuliaTransform(complex, -1));

    return transforms;
  }

  /**
   * Gets a line of text, delimits it on ',' and returns the two values as a vector2D.
   *
   * @param content the string to be parsed.
   * @return A vector2D containing the values from the string.
   */
  private Vector2D getVectorFromFileContents(String content) {

    String[] maxCoords = content.split(",");
   double x = Double.parseDouble(maxCoords[0]);
   double y = Double.parseDouble(maxCoords[1]);
    return new Vector2D(x, y);

  }
}
