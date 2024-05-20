package org.ntnu.IDATA2003.mappe5.model.logic;

import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

/**
 * Represents a class that takes in values and parses them into usable transforms and vectors.
 */
public class TransformsParser {

  /**
   * Parses the affine transforms from the file content.
   *
   * @param fileContent the content of the file.
   * @return a list of affine transforms.
   */
  public List<Transform2D> parseAffineTransforms(List<String> fileContent) {
    if (fileContent == null || fileContent.get(2) == null) {
      throw new IllegalArgumentException("This file is missing content. Could not parse");
    }
    ArrayList<Double> transfValues
        = new ArrayList<>();              //Matrix value, shortened for legibility
    List<Transform2D> affineTransf = new ArrayList<>();
    for (int i = 0; i < fileContent.size(); i++) {
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
   *
   * @param dirtyString the string to be cleaned of spaces.
   * @return the clean string.
   */
  public String cleanString(String dirtyString) {
    return dirtyString.replaceAll("\\s", "");
  }

  /**
   * Parses a julia complex string containing one complex number into two distinct transforms with
   * inverted signs.
   *
   * @param juliaComplex the String containing the Julia transform values.
   * @return the list of Julia transforms.
   */
  public List<JuliaTransform> parseJuliaTransforms(String juliaComplex) {
    if (juliaComplex == null || juliaComplex.isBlank()) {
      throw new IllegalArgumentException("string must have contents");
    }
    ArrayList<JuliaTransform> transforms = new ArrayList<>();
    juliaComplex = cleanString(juliaComplex);
    String[] juliaValues = juliaComplex.split(",");
    if (juliaValues.length != 2) {
      throw new IllegalArgumentException("Invalid number of values in the string");
    }
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
  public Vector2D getVectorFromString(String content) {
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("Vector string must have content");
    }
    String[] maxCoords = content.split(",");
    if (maxCoords.length != 2) {
      throw new IllegalArgumentException("Too many values in vector string");
    }
    double x = Double.parseDouble(maxCoords[0]);
    double y = Double.parseDouble(maxCoords[1]);
    return new Vector2D(x, y);

  }

  /**
   * Gets all transforms read in from file and returns it as a list of transform2Ds.
   *
   * @param fileContent The content of the file to be turned into transforms.
   * @return The list of transforms.
   */
  public List<Transform2D> getTransformsFromStrings(ArrayList<String> fileContent) {
    ArrayList<Transform2D> transforms = new ArrayList<>();
    String typeOfTransf = cleanString(fileContent.getFirst());
    List<String> parseString = fileContent.subList(3, fileContent.size());
    if (typeOfTransf.equals("Affine2D")) {
      transforms.addAll(parseAffineTransforms(parseString));
    }
    if (typeOfTransf.equals("Julia")) {
      transforms.addAll(parseJuliaTransforms(fileContent.get(3)));
    }
    return transforms;
  }

  /**
   * Writes all the affine matrices to a singular continuous string with a CSV format,
   * used to write to file.
   *
   * @param transforms The list of transforms to be turned into strings.
   * @return The string of all the transforms.
   */
  public String writeAffineToString(List<Transform2D> transforms) {
    StringBuilder matrices = new StringBuilder();
    int index = 1;
    for (Transform2D transf : transforms) {
      String matrixValues = "";
      matrixValues = transf.transformToString() + "# transform number: " + index + '\n';
      index++;
      matrices.append(matrixValues);
    }
    return matrices.toString();
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
  public List<String> getChaosGameInfoAsString(ChaosGameDescription description) {
    if (description == null) {
      throw new IllegalArgumentException("description cannot be null");
    }

    String transform =
        description.getTransform(0).getClass().getSimpleName()
            .replace("Transform", "");
    String compare = transform;
    transform += "           # type of transform" + '\n';
    String minCoords =
        description.getMinCoords().getX0() + ", " + description.getMinCoords().getY0();
    minCoords += "           # lower left coordinates" + '\n';
    String maxCoords =
        description.getMaxCoords().getX0() + ", " + description.getMaxCoords().getY0();
    maxCoords += "           # upper right coordinates" + '\n';
    ArrayList<String> transformInfo = new ArrayList<>();

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


}
