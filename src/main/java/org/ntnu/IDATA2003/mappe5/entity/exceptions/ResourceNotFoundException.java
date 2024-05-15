package org.ntnu.IDATA2003.mappe5.entity.exceptions;

/**
 * Represents an exception that is thrown when the application fails to find a resource.

 */
public class ResourceNotFoundException extends RuntimeException{

      public ResourceNotFoundException(String message){
     super(message);
      }

      public ResourceNotFoundException(String message, Throwable cause){
     super(message, cause);
      }
}
