package org.ntnu.IDATA2003.mappe5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;


class JuliaTransformTest {
  @Test
  public void testJuliaTransformWithPositiveArguments(){
    Complex complexTest = new Complex(2,2);

    JuliaTransform juliaTest = new JuliaTransform(complexTest,-1);
    Vector2D testVector = new Vector2D(1,1);

    testVector =  juliaTest.transform(testVector);
    System.out.println(testVector.getX0() + "  i" + testVector.getY0());



  }
}