package org.ntnu.IDATA2003.mappe5.model.entity.exceptions;

/**
 * Represents an exception that is thrown when the application fails to animate the fractal.
 */
public class AnimationFailedException extends RuntimeException {

  /**
   * Constructor for the exception.
   *
   * @param message the message to be displayed when the exception is thrown.
   */
  public AnimationFailedException(String message) {

    super(message);
  }

  /**
   * Constructor for the exception.
   *
   * @param message the message to be displayed when the exception is thrown.
   * @param cause   the cause of the exception.
   */
  public AnimationFailedException(String message, Throwable cause) {

    super(message, cause);
  }
}
