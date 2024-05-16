package org.ntnu.IDATA2003.mappe5.entity.exceptions;

/**
 * Represents an exception that is thrown when the application fails to write to a file.
 */
public class FailedToWriteToFileException extends Exception {

  public FailedToWriteToFileException(String message) {
    super(message);
  }

  public FailedToWriteToFileException(String message, Throwable cause) {
    super(message, cause);
  }
}
