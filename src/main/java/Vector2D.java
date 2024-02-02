public class Vector2D {


  private double x0;
  private double x1;

  /**
   * Constructor.
   * initializes the fields.
   * @param X1 the X value for the 2D vector.
   * @param X2  the Y value for the 2D vector.
   */
  public Vector2D(double X1, double X2){
    this.x0 = X1;
    this.x1 = X2;
  }

  /**
   * Returns the X value of the vector.
   * @return The X value of the vector.
   */
  public double getX0() {
    return this.x0;
  }

  /**
   * Returns the Y value of the vector.
   * @return the Y value of the vector.
   */
  public double getX1() {
    return  this.x1;
  }

  /**
   * Adds together this vector and other vector then returns the combined vector.
   * @param otherVector the vector to be added to this vector.
   * @return this vector added to other vector.
   */
  public Vector2D add(Vector2D otherVector){
    Vector2D dummyVector = new Vector2D(1,2);
    return dummyVector;
    //TODO: Fix this.
  }

  /**
   * Subtracts otherVector from this Vector then returns this vector.
   * @param otherVector the vector to be subtracted from this vector.
   * @return other vector subtracted from this vector.
   */
  public Vector2D subtract(Vector2D otherVector){
    Vector2D DummyVector = new Vector2D(1,2);
    return  DummyVector;
    //TODO: Fix this.
  }
}
