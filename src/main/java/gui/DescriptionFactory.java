package gui;

import chaosgameclasses.ChaosGameDescription;
import java.util.List;
import matrix.Matrix2x2;
import transformations.AffineTransform2D;
import transformations.Transform2D;
import vectors.Vector2D;

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