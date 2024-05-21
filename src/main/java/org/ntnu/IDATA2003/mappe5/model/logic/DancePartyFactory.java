package org.ntnu.IDATA2003.mappe5.model.logic;

import static org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescriptionFactory.Fractals.*;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;

/**
 * Factory for creating dance moves.
 */
public class DancePartyFactory {

  private final ChaosGameDescriptionFactory factory = new ChaosGameDescriptionFactory();

  /**
   * Creates a dance move based on the dance index.
   *
   * @param danceIndex the index of the dance move.
   * @param description the description of the fractal.
   * @return the description of the fractal.
   */
  public ChaosGameDescription createDanceMoves(int danceIndex, ChaosGameDescription description){
    switch (danceIndex) {
      case 1,35:
        description = this.factory.createDescription(JULIA);
        description.setMinCoords(new Complex(-6.0, -5.0));
        description.setMaxCoords(new Complex(6.0, 5.0));
        break;
      case 2:
        description.setMinCoords(new Complex(-5.0, -4.0));
        description.setMaxCoords(new Complex(5.0, 4.0));
        break;
      case 3:
        description.setMinCoords(new Complex(-4.0, -3.0));
        description.setMaxCoords(new Complex(4.0, 3.0));
        break;
      case 4:
        description.setMinCoords(new Complex(-3.0, -2.0));
        description.setMaxCoords(new Complex(3.0, 2.0));
        break;
      case 5:
        description.setMinCoords(new Complex(-2.0, -1.0));
        description.setMaxCoords(new Complex(2.0, 1.0));
        break;
      case 6:
        description.setMinCoords(new Complex(-1.6, -1.0));
        description.setMaxCoords(new Complex(1.6, 1.0));
        break;
      case 7, 14:
        description.setMinCoords(new Complex(-2.5, -1));
        break;
      case 8, 13:
        description.setMinCoords(new Complex(-3.5, -1));
        break;
      case 9, 12:
        description.setMinCoords(new Complex(-4.5, -1));
        break;
      case 10:
        description.setMinCoords(new Complex(-5, -1));
        break;
      case 11:
        description.setMinCoords(new Complex(-5.5, -1));
        break;
      case 15, 25:
        description = this.factory.createDescription(SIERPINSKI);
        break;
      case 16, 23:
        description.setMinCoords(new Complex(-2.0, 0.0));
        break;
      case 17:
        description.setMaxCoords(new Complex(2.0, 1.0));
        break;
      case 18:
        description.setMaxCoords(new Complex(3.0, 1.0));
        description.setMinCoords(new Complex(-1.0, 0.0));
        break;
      case 19:
        description.setMinCoords(new Complex(0.0, 0.0));
        break;
      case 20:
        description.setMinCoords(new Complex(0.0, 0.0));
        description.setMaxCoords(new Complex(4.0, 1.0));
        break;
      case 21:
        description.setMaxCoords(new Complex(4.0, 2.0));
        break;
      case 22:
        description.setMinCoords(new Complex(-1.0, 0.0));
        break;
      case 24:
        description.setMinCoords(new Complex(-3.0, 0.0));
        break;
      case 26,34:
        description = factory.createDescription(PENTAGON);
        break;
      case 27,33:
        description.setMinCoords(new Complex(-150.0, -150.0));
        description.setMaxCoords(new Complex(150.0, 150.0));
        break;
      case 28,32:
        description.setMinCoords(new Complex(-125.0, -125.0));
        description.setMaxCoords(new Complex(125.0, 125.0));
        break;
      case 29,31:
        description.setMinCoords(new Complex(-100.0, -100.0));
        description.setMaxCoords(new Complex(100.0, 100.0));
        break;
      case 30:
        description.setMinCoords(new Complex(-75.0, -75.0));
        description.setMaxCoords(new Complex(75.0, 75.0));
        break;
      default:
        break;
    }
    return description;
  }



}
