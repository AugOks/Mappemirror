package org.ntnu.IDATA2003.mappe5.entity.exceptions;

/**
 * Represents an exception that is thrown when the application fails to animate the fractal.
 */
public class AnimationFailedException extends RuntimeException {

  public AnimationFailedException(String message) {
    super(message);
  }

  public AnimationFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
