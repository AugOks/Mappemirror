package org.ntnu.IDATA2003.mappe5.model.entity.exceptions;

/**
 * Represents an exception that is thrown when the application fails to find a resource.
 */
public class ResourceNotFoundException extends RuntimeException {

  /**
   * Constructor for the exception.
   *
   * @param message the message to be displayed when the exception is thrown.
   */
  public ResourceNotFoundException(String message) {

    super(message);
  }

  /**
   * Constructor for the exception.
   *
   * @param message the message to be displayed when the exception is thrown.
   * @param cause   the cause of the exception.
   */
  public ResourceNotFoundException(String message, Throwable cause) {

    super(message, cause);
  }
}
