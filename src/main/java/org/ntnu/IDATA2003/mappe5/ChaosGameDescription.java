package org.ntnu.IDATA2003.mappe5;
import java.util.List;

//TODO add javadoc for this class
/**
 * bla bla bla.
 */

public class ChaosGameDescription {


  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

 //TODO write better javadoc her too

  /**
   *
   * @param transforms a list of transformations from transform2D
   * @param minCoords a 2D vector of the minimum coordinates
   * @param maxCoords a 2D vector of the maximum coordinates
   */
  public ChaosGameDescription(List<Transform2D> transforms,Vector2D minCoords, Vector2D maxCoords ){
    setTransforms(transforms);
    setMaxCoords(maxCoords);
    setMinCoords(minCoords);
  }

  /**
   * Returns the max coords.
   * @return max coords
   */
  public Vector2D getMaxCoords(){

    return this.maxCoords;
  }

  /**
   * Returns the min coords.
   * @return min coords
   */
  public Vector2D getMinCoords() {

    return this.minCoords;
  }

  public Transform2D getTransform(int index){
    if (index < 0){
      throw new IndexOutOfBoundsException("index cannot be negative");
    }
     return this.transforms.get(index);
  }

  /**
   * Sets the min coordinates of the transform?
   * TODO: fix this javadoc.
   *
   * @param coords the coordinates to be set.
   */
  public void setMinCoords(Vector2D coords){
    this.minCoords = coords;
  }

  /**
   * Sets the max coordinates of the transform?
   * TODO: fix javadoc
   *
   * @param coords the coordinates to be set.
   */
  public  void setMaxCoords(Vector2D coords){
    this.maxCoords = coords;
  }

  /**
   * Sets the transforms of the chaos games.
   *
   * @param transforms the transforms to be set.
   */
  public  void setTransforms(List<Transform2D> transforms){
    this.transforms = transforms;
  }
}
