package org.ntnu.IDATA2003.mappe5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
   * @param fractal the URI of the given file of the right fractal.
   * @return the contents of the file as a chaosGameDescription object.
   */
 public ChaosGameDescription readFromFile(String fractal){
   //TODO complete this method.
   Path pathOfFile =  Path.of( fractal + ".txt");
   ArrayList<String> fileContent = new ArrayList<>();
   ArrayList<Transform2D> transforms = new ArrayList<>();
   Vector2D minimumCoords = new Vector2D(0,0);
   Vector2D maximumCoords = new Vector2D(0,0);

try (BufferedReader reader = Files.newBufferedReader(pathOfFile)) {
     String text;

     while ((text = reader.readLine()) != null){
       String[] textDelimit = text.split("#");
       fileContent.add(textDelimit[0]);
     }
     minimumCoords = this.getVectorFromFileContents(fileContent.get(1));

     maximumCoords =  this.getVectorFromFileContents(fileContent.get(2));

     String typeOfTransf = fileContent.get(0).replaceAll("\\s", "");
     if (typeOfTransf.equals("Affine2D")){
     transforms.addAll(parseAffineTransforms(fileContent));
     }

   }catch (IOException e){
     System.out.println("error while reading file\n" + e.getMessage());
   }catch (NumberFormatException e){
     System.out.println("double could not be parsed " + e.getMessage() );
   }
   return  new ChaosGameDescription(transforms, minimumCoords, maximumCoords);
 }

  /**
   * Writes the details of the chaos game to a file.
   */
 public void writeToFile(ChaosGameDescription disc){
   //TODO: Complete this method.
 }

  /**
   * Parses the affine transforms from the file content.
   * @param fileContent the content of the file.
   * @return a list of affine transforms.
   */
 private List<AffineTransform2D> parseAffineTransforms(ArrayList<String> fileContent){
   ArrayList<Matrix2x2> matrices = new ArrayList<>();
   ArrayList<Double> MV = new ArrayList<>();              //Matrix value, shortened for legibility
   List<AffineTransform2D> affineTransf = new ArrayList<>();
   for(int i = 3; i < fileContent.size(); i++){
     String[] transforms = fileContent.get(i).split(",");
     for (String value : transforms){
       MV.add(Double.parseDouble(value));
     }

     affineTransf.add(new AffineTransform2D(
         new Matrix2x2(MV.get(0),MV.get(1), MV.get(2), MV.get(3)), // first four values contain the matrix
         new Vector2D(MV.get(4), MV.get(5))));  // last two values contain the vector.
     MV.clear();
   }
   return affineTransf;
 }

  /**
   * Gets a line of text, delimits it on ',' and returns the two values as a vector2D.
   * @param content the string to be parsed.
   * @return A vector2D containing the values from the string.
   */

 private Vector2D getVectorFromFileContents(String content){
   double x = 0;
   double y = 0;
   String [] maxCoords = content.split(",");
   x = Double.parseDouble(maxCoords[0]);
   y = Double.parseDouble(maxCoords[1]);
   return new Vector2D(x,y);

 }
}
