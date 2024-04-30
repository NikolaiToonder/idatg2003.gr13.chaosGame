package gui;

import chaosgameclasses.ChaosGameDescription;


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