package org.ntnu.IDATA2003.mappe5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a class that handles the game files, including reading from and too a given file.
 */
public class ChaosGameFileHandler {

  ArrayList<ChaosGameDescription> chaosGames;

  /**
   * Empty constructor.
   */
  public ChaosGameFileHandler(){
    chaosGames = new ArrayList<>();

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

   ChaosGameDescription game = new ChaosGameDescription(transforms, minimumCoords, maximumCoords, fractal);
  this.chaosGames.add(game);
  return  game;
 }

  /**
   *
   */

 public void writeAllGamesToFile(){
   for(ChaosGameDescription games : this.chaosGames){
     writeToFile(games);
   }
 }


  /**
   * Writes the details of the chaos game to a file.
   */
 public void writeToFile(ChaosGameDescription disc){
   Path pathOfFile;
   if (disc.getName() != null || !disc.getName().isBlank() || !disc.getName().isEmpty()) {
     pathOfFile = Path.of(disc.getName() + "out.txt");
   }else{
     Random rand = new Random();
     rand.nextInt(100);                                 //Generates a random name to give to the nameless fractal.
     pathOfFile = Path.of("fractal"+ rand+ ".txt");
   }
   try(BufferedWriter output = Files.newBufferedWriter(pathOfFile)) {

     String transformsAsStrings = "";
     String transform = disc.getTransform(0).getClass().getSimpleName().replaceAll("Transform", "");
     String compare = new String(transform);
     transform += "           # type of transform" + '\n';
     String minCoords = disc.getMinCoords().getX0() + ", " + disc.getMinCoords().getY0();
     minCoords += "           # lower left coordinates"+ '\n';
     String maxCoords = disc.getMaxCoords().getX0() + ", " + disc.getMaxCoords().getY0();
     maxCoords += "           # upper right coordinates" + '\n';

     if (compare.equals("Affine2D")){
       transformsAsStrings = this.writeAffineToString(disc.getAllTransforms());
     }
     else if(compare.equals("Julia")){
      transformsAsStrings = disc.getTransform(0).transformToString() +" # real and imaginary part of complex c";
     }
     output.write(transform + minCoords + maxCoords + transformsAsStrings);

   }catch (IOException e){
     System.out.println("Failed to write to file " + e.getMessage() );
   }

 }
 private String writeAffineToString(List<Transform2D> transforms){
   String matrices = "";
   int index = 1;
   for (Transform2D transf : transforms){
     String matrixValues = "";
     matrixValues = transf.transformToString()+ "# transform number: " + index + '\n';
     index++;
     matrices += matrixValues;
     }
   return matrices;
 }

  /**
   * Parses the affine transforms from the file content.
   * @param fileContent the content of the file.
   * @return a list of affine transforms.
   */
 private List<AffineTransform2D> parseAffineTransforms(ArrayList<String> fileContent){
   ArrayList<Matrix2x2> matrices = new ArrayList<>();
   ArrayList<Double> transfValues = new ArrayList<>();              //Matrix value, shortened for legibility
   List<AffineTransform2D> affineTransf = new ArrayList<>();
   for(int i = 3; i < fileContent.size(); i++){
     String[] transforms = fileContent.get(i).split(",");
     for (String value : transforms){
       transfValues.add(Double.parseDouble(value));
     }

     affineTransf.add(new AffineTransform2D(
         new Matrix2x2(transfValues.get(0),transfValues.get(1), transfValues.get(2), transfValues.get(3)), // first four values contain the matrix
         new Vector2D(transfValues.get(4), transfValues.get(5))));  // last two values contain the vector.
     transfValues.clear();
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
