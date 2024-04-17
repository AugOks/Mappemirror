package org.ntnu.IDATA2003.mappe5.entity;

public class PixelOutOfBoundsException extends Exception{

  public PixelOutOfBoundsException(String message){
    super(message);
  }

  public PixelOutOfBoundsException(String message, Throwable cause){
    super(message, cause);
  }
}
