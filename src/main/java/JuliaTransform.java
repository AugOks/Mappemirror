public class JuliaTransform extends  Transform2D {
  Complex point;
  int sign;

  public JuliaTransform(Complex point, int sign){
    this.point = point;
    this.sign = sign;
  }

  public Vector2D transform(Vector2D point){
    return point;
    //TODO: add body for transform.
  }
}
