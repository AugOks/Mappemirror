package org.ntnu.IDATA2003.mappe5.model.logic;

import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.model.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.model.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;

//TODO wright javadoc for this chaos game description factory

/**
 * A factory implementation for creating pre-defined fractals for the chaos game.
 */

public class ChaosGameDescriptionFactory {
  /**
   * Takes an input and creates a fractal based on the name given.
   *
   * @param fractal the name of the fractal to create.
   * @return a complete chaos game description for the given fractal.
   */
  public ChaosGameDescription createDescription(Fractals fractal) {

    ChaosGameDescription description = null;
    switch (fractal) {
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

      default:
        break;
    }
    return description;
  }

  /**
   * Creates a fractal based on the name given and the number of transforms.
   *
   * @param fractal          the name of the fractal to create.
   * @param numberTransforms the number of transforms to create.
   * @return a complete chaos game description for the given fractal.
   */
  public ChaosGameDescription createDescription(Fractals fractal, int numberTransforms) {

    ChaosGameDescription description = null;
    if (fractal == Fractals.BLANKAFFINE) {
      description = this.createBlankAffine(numberTransforms);
    }
    return description;
  }

  /**
   * Creates a chaos game description based on a pre-defined Julia set.
   *
   * @return the complete chaos game description for the Julia set.
   */
  private ChaosGameDescription createJulia() {

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
    Matrix2x2 matrix = new Matrix2x2(.5, 0, 0, .5);
    Vector2D firstVector = new Vector2D(0, 0);
    Vector2D secondVector = new Vector2D(.25, .5);
    Vector2D thirdVector = new Vector2D(.5, 0);
    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(matrix, firstVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), secondVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), thirdVector));
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(1, 1);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "Sierpinski");
  }

  /**
   * Creates a Barnsley fern based on pre-defined values.
   *
   * @return the complete chaos game description for the Barnsley fern.
   */
  private ChaosGameDescription createBarnsleyFern() {

    Matrix2x2 firstMatrix = new Matrix2x2(0, 0, 0, .16);
    Matrix2x2 secondMatrix = new Matrix2x2(.85, .04, -.04, .85);
    Matrix2x2 thirdMatrix = new Matrix2x2(.2, -.26, .23, .22);
    Matrix2x2 fourthMatrix = new Matrix2x2(-.15, .28, .26, .24);
    Vector2D firstVector = new Vector2D(0, 0);
    Vector2D secondVector = new Vector2D(0, 1.6);
    Vector2D thirdVector = new Vector2D(0, 1.6);
    Vector2D fourthVector = new Vector2D(0, .44);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(secondMatrix, secondVector));
    transforms.add(new AffineTransform2D(thirdMatrix, thirdVector));
    transforms.add(new AffineTransform2D(fourthMatrix, fourthVector));
    Vector2D minCoords = new Vector2D(-2.65, 0);
    Vector2D maxCoords = new Vector2D(2.65, 10);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "Sierpinski");
  }

  /**
   * Creates a SpiderNet based on pre-defined values.
   *
   * @return the complete chaos game description for the SpiderNet.
   */
  private ChaosGameDescription createSpiderweb() {

    Matrix2x2 firstMatrix = new Matrix2x2(.4, 0, 0, .4);
    Matrix2x2 secondMatrix = new Matrix2x2(.4, -0.5, 0.5, .4);
    Matrix2x2 thirdMatrix = new Matrix2x2(.4, 0.5, -0.5, .4);
    Vector2D firstVector = new Vector2D(-160, 0);
    Vector2D secondVector = new Vector2D(160, 0);
    Vector2D thirdVector = new Vector2D(0, 0);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(firstMatrix), secondVector));
    transforms.add(new AffineTransform2D(secondMatrix, thirdVector));
    transforms.add(new AffineTransform2D(thirdMatrix, new Vector2D(thirdVector)));
    Vector2D minCoords = new Vector2D(-150, -150);
    Vector2D maxCoords = new Vector2D(150, 150);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "SpiderNet");
  }

  /**
   * Creates a Square based on pre-defined values.
   *
   * @return the complete chaos game description for the Square.
   */
  private ChaosGameDescription createSquare() {

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
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), secondVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), thirdVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), fourthVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), fifthVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), sixthVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), seventhVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(matrix), eightVector));
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "Square");
  }

  /**
   * Creates a Pentagon based on pre-defined values.
   *
   * @return the complete chaos game description for the Pentagon.
   */
  private ChaosGameDescription createPentagon() {

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
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "Pentagon");
  }

  /**
   * Creates a Snowflake based on pre-defined values.
   *
   * @return the complete chaos game description for the Snowflake.
   */
  private ChaosGameDescription createKochCurve() {

    Matrix2x2 firstMatrix = new Matrix2x2(.3333, 0, 0, .3333);
    Matrix2x2 secondMatrix = new Matrix2x2(0.16667, -0.288675, 0.288675, 0.16667);
    Matrix2x2 thirdMatrix = new Matrix2x2(0.16667, 0.288675, -0.288675, 0.16667);
    Vector2D firstVector = new Vector2D(-200, 0);
    Vector2D secondVector = new Vector2D(200, 0);
    Vector2D thirdVector = new Vector2D(-50, 86.6024);
    Vector2D fourthVector = new Vector2D(50, 86.6024);

    List<Transform2D> transforms = new ArrayList<>();
    transforms.add(new AffineTransform2D(firstMatrix, firstVector));
    transforms.add(new AffineTransform2D(new Matrix2x2(firstMatrix), secondVector));
    transforms.add(new AffineTransform2D(secondMatrix, thirdVector));
    transforms.add(new AffineTransform2D(thirdMatrix, fourthVector));
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "KochCurve");
  }

  /**
   * Creates a DragonFire based on pre-defined values.
   *
   * @return the complete chaos game description for the DragonFire.
   */
  private ChaosGameDescription createDragonFire() {

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
    Vector2D minCoords = new Vector2D(-200, -200);
    Vector2D maxCoords = new Vector2D(200, 200);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "DragonFire");
  }

  /**
   * Creates a blank Julia set based on pre-defined values.
   *
   * @return the complete chaos game description for the BlankJulia.
   */
  private ChaosGameDescription createBlankJulia() {
    Complex c = new Complex(0, 0);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c, 1));
    transformList.add(new JuliaTransform(c, -1));
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(0.1, 0.1);
    return new ChaosGameDescription(transformList, minCoords, maxCoords, "BlankJulia");
  }

  /**
   * Creates a blank affine set based on pre-defined values.
   *
   * @param numberTransforms the number of transforms to create.
   * @return the complete chaos game description for the BlankAffine.
   */
  private ChaosGameDescription createBlankAffine(int numberTransforms) {

    List<Transform2D> transforms = new ArrayList<>();
    for (int i = 0; i < numberTransforms; i++) {
      Matrix2x2 matrix = new Matrix2x2(0, 0, 0, 0);
      Vector2D vector = new Vector2D(0, 0);
      transforms.add(new AffineTransform2D(new Matrix2x2(matrix), new Vector2D(vector)));
    }
    Vector2D minCoords = new Vector2D(0, 0);
    Vector2D maxCoords = new Vector2D(0.1, 0.1);
    return new ChaosGameDescription(transforms, minCoords, maxCoords, "BlankAffine");
  }

  /**
   * An enum containing all currently available pre-defined fractals the factory can output.
   */
  public enum Fractals {
    JULIA, BARNSLEY, SIERPINSKI, SPIDERWEB, SQUARE, PENTAGON, KOCHCURVE, DRAGONFIRE, BLANKJULIA,
    BLANKAFFINE
  }
}


