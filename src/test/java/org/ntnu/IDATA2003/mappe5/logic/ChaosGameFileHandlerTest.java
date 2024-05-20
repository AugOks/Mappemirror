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