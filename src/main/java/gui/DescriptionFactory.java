package gui;

import chaosgameclasses.ChaosGameDescription;
import java.io.IOException;

/**
 * Factory class to create different transformations.
 */
public class DescriptionFactory {

  /**
   * Factory method of the class.
   * @param chosenFractal a string to represent the fractal the user wants.
   *                      this will be chosen by a dropdown menu.
   * @return ChaosGameDescription of the fractal the user wants. Or null if the program doesn't
   * find anything
   */
  public ChaosGameDescription createAffine2D(String chosenFractal) throws IllegalArgumentException {
    try {
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
        case "Custom" -> {
          return new ChaosGameDescription("src/resources/customTransform.txt");
        }
        case "Snowflake" -> {
          return new ChaosGameDescription("src/resources/snowflakeTransform.txt");
        }
        default -> {
          return null;
        }
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error reading file");
    }
  }
}