package org.ntnu.IDATA2003.mappe5;

import java.util.ArrayList;
import java.util.List;

public class ChaosGameMainApp {
  public static void main(String[] args) {
    Matrix2x2 Matrix = new Matrix2x2(.5,0,0,.5);
    Vector2D firstVector = new Vector2D(0,0);
    AffineTransform2D firstTransf = new AffineTransform2D(Matrix,firstVector);
    Vector2D secondVector = new Vector2D(.25,.5);
    AffineTransform2D secondTransf = new AffineTransform2D(Matrix,secondVector);
    Vector2D thirdVector = new Vector2D(.5,0);
    AffineTransform2D thirdTransf = new AffineTransform2D(Matrix,thirdVector);

    Complex juliaComplex = new Complex(-0.74543, 0.11301);
    JuliaTransform fourthTransf = new JuliaTransform(juliaComplex, 1);

    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(firstTransf);
    transformList.add(secondTransf);
    transformList.add(thirdTransf);
    transformList.add(fourthTransf);
    Vector2D minCoords = new Vector2D(0,0);
    Vector2D maxCoords = new Vector2D(1,1);
    ChaosGameDescription  description = new ChaosGameDescription(transformList, minCoords,maxCoords);

    ChaosGame mainGame = new ChaosGame(description, 100, 100);
    mainGame.runSteps(20);

  }
}
