package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.ChaosGame;
import org.ntnu.IDATA2003.mappe5.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.ChaosGameFileHandler;
import org.ntnu.IDATA2003.mappe5.Complex;
import org.ntnu.IDATA2003.mappe5.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.Transform2D;
import org.ntnu.IDATA2003.mappe5.Vector2D;

class ChaosGameFileHandlerTest {
  /**
   * A test class that tests that the file handler is able to parse lines of text as expected.
   * This class does not test file handling itself.
   */
  private ChaosGameFileHandler handler;
  private ChaosGameDescription description;
  @BeforeEach
  void init(){
    this.handler = new ChaosGameFileHandler();
    Vector2D minCoords = new Vector2D(-1.6, -1.0);
    Vector2D maxCoords = new Vector2D(1.6, 1.0);
    Complex c = new Complex(-0.74543, 0.11301);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c,1));
    transformList.add(new JuliaTransform(c,-1));

    this.description = new ChaosGameDescription(transformList, minCoords, maxCoords, "juliaTestFile");

  }

  /**
   * Tests creating a file and storing fractal data in that file.
   */
  @Test
  @Order(1)
  void testWriteToFile(){
    try {
      this.handler.writeToFile(description);
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * Tests reading a fractal from a file and printing that fractal if it was found.
   */
  @Test
  @Order(2)
  void testReadFromFile(){
    ChaosGameDescription description = null;
    try {
      description = this.handler.readFromFile("juliaTestFileOut");
      assertNotNull(description);
      ChaosGame game = new ChaosGame(description, 100, 100);
      game.runSteps(1000);
      int index_i = game.getCanvas().getHeight();
      int index_j = game.getCanvas().getWidth();
      int[][] canvas = game.getCanvas().getCanvasArray();
      ArrayList<String> canvasConsole = new ArrayList<>();
      String line = "";

      //TODO refactor this to use StringBuilder
      for (int i = 0; i < index_i; i++) {
        for (int j = 0; j < index_j; j++) {
          if (canvas[i][j] == 0) {
            line += "-";
          } else {
            line += "X";
          }
        }
        canvasConsole.add(line);
        line = "";
      }
      for (String s : canvasConsole) {
        System.out.println(s);
      }
    } catch (Exception e) {
      fail();
    }

  }

  /**
   * Tests reading from a file that does not exist.
   */
  @Test
  void testReadFromFileWithNegativeParameters(){
    try {
      this.handler.readFromFile("negativeValue");
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  /**
   * Tests writing to a file with negative parameters.

   */
  @Test
  void writeToFileWithNegativeParameters(){
    try {
      this.handler.writeToFile(null);
      fail();
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }



}