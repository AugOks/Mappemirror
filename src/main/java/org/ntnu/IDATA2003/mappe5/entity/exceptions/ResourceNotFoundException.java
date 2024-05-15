package org.ntnu.IDATA2003.mappe5.entity.exceptions;

public class ResourceNotFoundException extends RuntimeException{

      public ResourceNotFoundException(String message){
     super(message);
      }

      public ResourceNotFoundException(String message, Throwable cause){
     super(message, cause);
      }
}
