package org.ntnu.IDATA2003.mappe5.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;
import org.ntnu.IDATA2003.mappe5.model.entity.JuliaTransform;
import org.ntnu.IDATA2003.mappe5.model.entity.Transform2D;
import org.ntnu.IDATA2003.mappe5.model.entity.Vector2D;
import org.ntnu.IDATA2003.mappe5.model.entity.exceptions.FailedToWriteToFileException;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGame;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescription;
import org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameFileHandler;

class ChaosGameFileHandlerTest {
  /**
   * A test class that tests that the file handler is able to parse lines of text as expected.
   * This class does not test file handling itself.
   */
  private ChaosGameFileHandler handler;
  private ChaosGameDescription description;

  @BeforeEach
  void init() {
    this.handler = new ChaosGameFileHandler();
    Vector2D minCoords = new Vector2D(-1.6, -1.0);
    Vector2D maxCoords = new Vector2D(1.6, 1.0);
    Complex c = new Complex(-0.74543, 0.11301);
    List<Transform2D> transformList = new ArrayList<>();
    transformList.add(new JuliaTransform(c, 1));
    transformList.add(new JuliaTransform(c, -1));

    this.description =
        new ChaosGameDescription(transformList, minCoords, maxCoords, "juliaset");

  }

  /**
   * Tests creating a file and storing fractal data in that file.
   */
  @Test
  @Order(1)
  void testWriteToFile() {
    try {
      this.handler.writeToFile("testFile", description);
    } catch (Exception e) {
      fail();
    }
  }

  /**
   * Tests reading a fractal from a file and printing that fractal if it was found.
   */
  //TODO this test is not working as intended
  @Test
  @Order(2)
  void testReadFromFile() {
    ChaosGameDescription description = null;
    try {
      description = this.handler.readFromFileWithFractalName("julia_set");
      assertNotNull(description);
      ChaosGame game = new ChaosGame(description, 100, 300);
      game.runSteps(1000);
      int index_i = game.getCanvas().getHeight();
      int index_j = game.getCanvas().getWidth();
      int[][] canvas = game.getCanvas().getCanvasArray();
      ArrayList<String> canvasConsole = new ArrayList<>();
      StringBuilder line = new StringBuilder();

      for (int i = 0; i < index_i; i++) {
        for (int j = 0; j < index_j; j++) {
          if (canvas[i][j] == 0) {
            line.append("-");
          } else {
            line.append("X");
          }
        }
        canvasConsole.add(line.toString());
        line = new StringBuilder();
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
  void testReadFromFileWithNegativeParameters() {
    try {
      this.handler.readFromFileWithFractalName("negativeValue");
      fail();
    } catch (Exception e) {
      assertTrue(true);
    }
  }

  /**
   * Tests writing to a file with negative parameters.
   */
  @Test
  void writeToFileWithNegativeParameters() {
    try {
      this.handler.writeToFile(null, null);
      fail();
    } catch (FailedToWriteToFileException | IllegalArgumentException e) {
      assertTrue(true);
    }
  }


}