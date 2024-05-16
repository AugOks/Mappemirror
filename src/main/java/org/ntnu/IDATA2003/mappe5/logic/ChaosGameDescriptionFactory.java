package org.ntnu.IDATA2003.mappe5.logic;

import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

//TODO wright javadoc for this chaos game description factory
/**
 * A factory implementation for creating pre-defined fractals for the chaos game.
 */

public class ChaosGameDescriptionFactory {
  /**
   * An enum containing all currently available pre-defined fractals the factory can output.
   */
  public enum Fractals {
    JULIA, BARNSLEY, SIERPINSKI, SPIDERWEB, SQUARE, PENTAGON, KOCHCURVE, DRAGONFIRE, BLANKJULIA,
    BLANKAFFINE
  }

  /**
   * Takes an input and creates a fractal based on the name given.
   *
   * @param fractal the name of the fractal to create.
   * @return null if no fractal with the given name could be found,
   * otherwise a complete chaos game description for the given fractal
   */
  public ChaosGameDescription createDescription(Fractals fractal){

    ChaosGameDescription description = null;
    switch (fractal){
      case BARNSLEY:
        description = this.createBarnsleyFern();
        break;

      case JULIA:
        description = this.createJulia();
        break;

      case SIERPINSKI:
        description = this.createSierpinksi();
        break;

      case SPIDERWEB:
        description = this.createSpiderweb();
        break;

      case SQUARE:
        description = this.createSquare();
        break;

      case PENTAGON:
        description = this.createPentagon();
        break;

      case KOCHCURVE:
        description = this.createKochCurve();
        break;

      case DRAGONFIRE:
        description = this.createDragonFire();
        break;

      case BLANKJULIA:
        description = this.createBlankJulia();
        break;

      case BLANKAFFINE:
       // description = this.createBlankAffine(numberTransforms);
        break;

      default:
        break;
    }
    return description;
  }

  /**
   * Creates a chaos game description based on a pre-defined Julia set.
   *
   * @return the complete chaos game description for the Julia set.
   */
  private ChaosGameDescription createJulia(){

    Vector2D minCoords = new Vector2D(-1.6, -1);
    Vector2D maxCoords = new Vector2D(1.6, 1);
    Complex c = new Complex(-0.74543, 0.11301);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c, 1));
    transformList.add(new JuliaTransform(c, -1));

    return new ChaosGameDescription(transformList, minCoords, maxCoords, "Julia");
  }

  /**
   * Creates a Sierpinski triangle based on pre-defined values.
   *
   * @return the complete chaos game description for the Sierpinski triangle.
   */
  private ChaosGameDescription createSierpinksi() {
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    Matrix2x2 matrix = new Matrix2x2(.5, 0, 0, .5);
    Vector2D firstVector = new Vector2D(0,0);
    Vector2D secondVector = new Vector2D(.25,.5);
    Vector2D thirdVector = new Vector2D(.5,0);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrix, firstVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), secondVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), thirdVector));
    return new  ChaosGameDescription(transforms, minCoords,maxCoords, "Sierpinski");
  }
  /**
   * Creates a Barnsley fern based on pre-defined values.
   *
   * @return the complete chaos game description for the Barnsley fern.
   */
  private  ChaosGameDescription createBarnsleyFern(){
    Vector2D minCoords = new Vector2D(-2.65, 0);
    Vector2D maxCoords = new Vector2D(2.65, 10);
    Matrix2x2 firstMatrix = new Matrix2x2(0, 0, 0, .16);
    Matrix2x2 secondMatrix = new Matrix2x2(.85, .04, -.04, .85);
    Matrix2x2 thirdMatrix = new Matrix2x2(.2, -.26, .23, .22);
    Matrix2x2 fourthMatrix = new Matrix2x2(-.15, .28, .26, .24);
    Vector2D firstVector = new Vector2D(0,0);
    Vector2D secondVector = new Vector2D(0, 1.6);
    Vector2D thirdVector = new Vector2D(0,1.6);
    Vector2D fourthVector = new Vector2D(0,.44);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(secondMatrix, secondVector));
    transforms.add(new AffineTransform2D(thirdMatrix, thirdVector));
    transforms.add(new AffineTransform2D(fourthMatrix, fourthVector));
    return new  ChaosGameDescription(transforms, minCoords,maxCoords, "Sierpinski");
  }

  /**
   * Creates a SpiderNet based on pre-defined values.
   *
   * @return the complete chaos game description for the SpiderNet.
   */
  private ChaosGameDescription createSpiderweb(){
    Vector2D minCoords = new Vector2D(-150, -150);
    Vector2D maxCoords = new Vector2D(150, 150);
    Matrix2x2 firstMatrix = new Matrix2x2(.4, 0, 0, .4);
    Matrix2x2 secondMatrix = new Matrix2x2(.4, -0.5, 0.5, .4);
    Matrix2x2 thirdMatrix =  new Matrix2x2(.4, 0.5, -0.5, .4);
    Vector2D firstVector = new Vector2D(-160,0);
    Vector2D secondVector = new Vector2D(160,0);
    Vector2D thirdVector = new Vector2D(0,0);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(firstMatrix, secondVector));
    transforms.add(new AffineTransform2D(secondMatrix, thirdVector));
    transforms.add(new AffineTransform2D(thirdMatrix, thirdVector));
    return new  ChaosGameDescription(transforms, minCoords,maxCoords, "SpiderNet");
  }

  /**
   * Creates a Square based on pre-defined values.
   *
   * @return the complete chaos game description for the Square.
   */
  private ChaosGameDescription createSquare() {
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    Matrix2x2 matrix = new Matrix2x2(.3333, 0, 0, .3333);
    Vector2D firstVector = new Vector2D(0, 150);
    Vector2D secondVector = new Vector2D(0, -150);
    Vector2D thirdVector = new Vector2D(150, 0);
    Vector2D fourthVector = new Vector2D(-150, 0);
    Vector2D fifthVector = new Vector2D(150, -150);
    Vector2D sixthVector = new Vector2D(-150, 150);
    Vector2D seventhVector = new Vector2D(150, 150);
    Vector2D eightVector = new Vector2D(-150, -150);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrix, firstVector));
    transforms.add(new AffineTransform2D(matrix, secondVector));
    transforms.add(new AffineTransform2D(matrix, thirdVector));
    transforms.add(new AffineTransform2D(matrix, fourthVector));
    transforms.add(new AffineTransform2D(matrix, fifthVector));
    transforms.add(new AffineTransform2D(matrix, sixthVector));
    transforms.add(new AffineTransform2D(matrix, seventhVector));
    transforms.add(new AffineTransform2D(matrix, eightVector));
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "Square");
  }

  /**
   * Creates a Pentagon based on pre-defined values.
   *
   * @return the complete chaos game description for the Pentagon.
   */
  private ChaosGameDescription createPentagon() {
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    Matrix2x2 matrix = new Matrix2x2(.38, 0, 0, .38);
    Vector2D firstVector = new Vector2D(100, 0);
    Vector2D secondVector = new Vector2D(30.9017, 95.1057);
    Vector2D thirdVector = new Vector2D(-80.9017, 58.7785);
    Vector2D fourthVector = new Vector2D(-80.9017, -58.7785);
    Vector2D fifthVector = new Vector2D(30.9017, -95.1057);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrix, firstVector));
    transforms.add(new AffineTransform2D(matrix, secondVector));
    transforms.add(new AffineTransform2D(matrix, thirdVector));
    transforms.add(new AffineTransform2D(matrix, fourthVector));
    transforms.add(new AffineTransform2D(matrix, fifthVector));
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "Pentagon");
  }

  /**
   * Creates a Snowflake based on pre-defined values.
   *
   * @return the complete chaos game description for the Snowflake.
   */
  private ChaosGameDescription createKochCurve() {
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    Matrix2x2 firstMatrix = new Matrix2x2(.3333, 0, 0, .3333);
    Matrix2x2 secondMatrix = new Matrix2x2(0.16667, -0.288675, 0.288675, 0.16667);
    Matrix2x2 thirdMatrix = new Matrix2x2(0.16667, 0.288675, -0.288675, 0.16667);
    Vector2D firstVector = new Vector2D(-200, 0);
    Vector2D secondVector = new Vector2D(200, 0);
    Vector2D thirdVector = new Vector2D(-50, 86.6024);
    Vector2D fourthVector = new Vector2D(50, 86.6024);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(firstMatrix, secondVector));
    transforms.add(new AffineTransform2D(secondMatrix, thirdVector));
    transforms.add(new AffineTransform2D(thirdMatrix, fourthVector));
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "KochCurve");
  }

  /**
   * Creates a DragonFire based on pre-defined values.
   *
   * @return the complete chaos game description for the DragonFire.
   */
  private ChaosGameDescription createDragonFire(){
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    Matrix2x2 firstMatrix = new Matrix2x2(.5, 0, 0, 0.8);
    Matrix2x2 secondMatrix = new Matrix2x2(0.5, 0.2, -0.2, 0.5);
    Matrix2x2 thirdMatrix = new Matrix2x2(0.5, -0.2, 0.2, 0.5);
    Vector2D firstVector = new Vector2D(0, 50);
    Vector2D secondVector = new Vector2D(-100, -100);
    Vector2D thirdVector = new Vector2D(100, -100);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(secondMatrix, secondVector));
    transforms.add(new AffineTransform2D(thirdMatrix, thirdVector));
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "DragonFire");
  }

  private ChaosGameDescription createBlankJulia(){
    Vector2D minCoords = new Vector2D(0,0);
    Vector2D maxCoords = new Vector2D(0.1, 0.1);
    Complex c = new Complex(0,0);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c, 1));
    transformList.add(new JuliaTransform(c, -1));

    return new ChaosGameDescription(transformList, minCoords, maxCoords, "BlankJulia");
  }

  private ChaosGameDescription createBlankAffine(int numberTransforms){
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(0.1, 0.1);
    List<Transform2D> transforms = new ArrayList<>();
    for (int i=0; i< numberTransforms; i++){
      Matrix2x2 Matrix = new Matrix2x2(0, 0, 0, 0);
      Vector2D Vector = new Vector2D(0, 0);
      transforms.add(new AffineTransform2D(Matrix, Vector));
    }
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "BlankAffine");
  }
}


