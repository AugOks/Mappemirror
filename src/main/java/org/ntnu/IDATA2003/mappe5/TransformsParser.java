package org.ntnu.IDATA2003.mappe5;

import java.util.ArrayList;
import java.util.List;

public class TransformsParser {

  /**
   * Parses the affine transforms from the file content.
   *
   * @param fileContent the content of the file.
   * @return a list of affine transforms.
   */
  public List<Transform2D> parseAffineTransforms(List<String> fileContent) {
    if (fileContent == null || fileContent.get(2) == null){
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
   * @param dirtyString the string to be cleaned of spaces.
   * @return the clean string.
   */
  public String cleanString(String dirtyString){
    return dirtyString.replaceAll("\\s", "");
  }

  /**
   * Parses a julia complex string containing one complex number into two distinct transforms with inverted signs.
   * @param juliaComplex the String containing the Julia transform values.
   * @return the list of Julia transforms.
   */
  public List<JuliaTransform> parseJuliaTransforms(String juliaComplex) {
    if (juliaComplex == null || juliaComplex.isBlank()){
      throw new IllegalArgumentException("string must have contents");
    }
    ArrayList<JuliaTransform> transforms = new ArrayList<>();
    juliaComplex = cleanString(juliaComplex);
    String[] juliaValues = juliaComplex.split(",");
    if (juliaValues.length != 2){
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
    if(content == null || content.isBlank()){
      throw new IllegalArgumentException("Vector string must have content");
    }
    String[] maxCoords = content.split(",");
    if (maxCoords.length != 2){
      throw new IllegalArgumentException("Too many values in vector string");
    }
    double x = Double.parseDouble(maxCoords[0]);
    double y = Double.parseDouble(maxCoords[1]);
    return new Vector2D(x, y);

  }

}
