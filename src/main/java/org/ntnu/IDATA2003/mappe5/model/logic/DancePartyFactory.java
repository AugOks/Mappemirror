package org.ntnu.IDATA2003.mappe5.model.logic;

import static org.ntnu.IDATA2003.mappe5.model.logic.ChaosGameDescriptionFactory.Fractals.*;
import org.ntnu.IDATA2003.mappe5.model.entity.Complex;

public class DancePartyFactory {

  private ChaosGameDescriptionFactory factory;
  public DancePartyFactory() {
    this.factory = new ChaosGameDescriptionFactory();
  }
  public ChaosGameDescription createDanceMoves(int danceIndex, ChaosGameDescription description){
    switch (danceIndex) {
      case 1:
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
      case 7:
        description.setMinCoords(new Complex(-2.5, -1));
        break;
      case 8:
        description.setMinCoords(new Complex(-3.5, -1));
        break;
      case 9:
        description.setMinCoords(new Complex(-4.5, -1));
        break;
      case 10:
        description.setMinCoords(new Complex(-5, -1));
        break;
      case 11:
        description.setMinCoords(new Complex(-5.5, -1));
        break;
      case 12:
        description.setMinCoords(new Complex(-4.5, -1));
        break;
      case 13:
        description.setMinCoords(new Complex(-3.5, -1));
        break;
      case 14:
        description.setMinCoords(new Complex(-2.5, -1));
        break;
      case 15:
        description.setMinCoords(new Complex(-1.5, -1));
        break;
      default:
        break;
    }
    return description;
  }



}
