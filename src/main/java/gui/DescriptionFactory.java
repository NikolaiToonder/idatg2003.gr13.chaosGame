package gui;

import chaosgameclasses.ChaosGameDescription;

/**
 * Factory class for creating ChaosGameDescription objects.
 * Uses files to generate the description of the chaos game.

  * @version 1.0
  * @author Nikolai Engelsen Tønder, Mustafa Kees
 */
public class DescriptionFactory {
  public ChaosGameDescription createAffine2D(String chosenFractal) {
    switch (chosenFractal) {
      case "Barnsley" -> {
        return new ChaosGameDescription("src/resources/barnsleyTransform.txt");
      }
      case "Sierpinski" -> {
        return new ChaosGameDescription("src/resources/sierpinskiTriangle.txt");
      }
      case "Julia" -> {
        return new ChaosGameDescription("src/resources/juliaTransform.txt");
      }
      default -> {
        return null;
      }
    }
  }
}