package org.ntnu.IDATA2003.mappe5.entity.exceptions;

public class FailedToWriteToFileException extends Exception{

      public FailedToWriteToFileException(String message){
     super(message);
      }

      public FailedToWriteToFileException(String message, Throwable cause){
     super(message, cause);
      }
}
