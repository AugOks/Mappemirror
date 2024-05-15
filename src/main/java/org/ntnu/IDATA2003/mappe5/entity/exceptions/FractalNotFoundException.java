package org.ntnu.IDATA2003.mappe5.entity.exceptions;

public class FractalNotFoundException extends Exception{

  public FractalNotFoundException(String message){
    super(message);
  }

  public FractalNotFoundException(String message, Throwable cause){
    super(message, cause);
  }
}
