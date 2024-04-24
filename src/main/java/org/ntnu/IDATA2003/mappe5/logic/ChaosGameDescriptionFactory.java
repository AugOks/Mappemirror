package org.ntnu.IDATA2003.mappe5.logic;

import java.util.ArrayList;
import java.util.List;
import org.ntnu.IDATA2003.mappe5.entity.AffineTransform2D;
import org.ntnu.IDATA2003.mappe5.entity.Complex;
import org.ntnu.IDATA2003.mappe5.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.entity.Matrix2x2;
import org.ntnu.IDATA2003.mappe5.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.entity.Vector2D;

//TODO right javadoc for this chaos game description factory

/**
 * A factory implementation for creating pre-defined fractals for the chaos game.
 */


public class ChaosGameDescriptionFactory {
  /**
   * An enum containing all currently available pre-defined fractals the factory can output.
   */
  public enum Fractals {
    JULIA, BARNSLEY, SIERPINSKI
  }

  /**
   * takes an input and creates a fractal based on the name given.
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

      default:
        break;
    }
    return description;
  }

  /**
   * Creates a chaos game description based on a pre-defined Julia set.
   * @return the complete chaos game description for the Julia set.
   */
  private ChaosGameDescription createJulia(){

    Vector2D minCoords = new Vector2D(-1.6, -1.0);
    Vector2D maxCoords = new Vector2D(1.6, 1.0);
    Complex c = new Complex(-0.74543, 0.11301);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c, 1));
    transformList.add(new JuliaTransform(c, -1));

    return new ChaosGameDescription(transformList, minCoords, maxCoords, "Julia");
  }

  /**
   * Creates a Sierpinski triangle based on pre-defined values.
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
    transforms.add(new AffineTransform2D(matrix, secondVector));
    transforms.add(new AffineTransform2D(matrix, thirdVector));
    return new  ChaosGameDescription(transforms, minCoords,maxCoords, "Sierpinski");
  }
  /**
   * Creates a Barnsley fern based on pre-defined values.
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

}


