package model.factory;

import model.chaosgameclasses.ChaosGameDescription;

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
          return new ChaosGameDescription("src/main/resources/barnsleyTransform.txt");
        }
        case "Sierpinski" -> {
          return new ChaosGameDescription("src/main/resources/sierpinskiTriangle.txt");
        }
        case "Julia" -> {
          return new ChaosGameDescription("src/main/resources/juliaTransform.txt");
        }
        case "Custom" -> {
          return new ChaosGameDescription("src/main/resources/customTransform.txt");
        }
        case "Snowflake" -> {
          return new ChaosGameDescription("src/main/resources/snowflakeTransform.txt");
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