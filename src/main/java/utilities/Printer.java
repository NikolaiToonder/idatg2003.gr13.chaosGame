package utilities;

import chaosgameclasses.ChaosCanvas;
import math.vectors.Vector2D;

/**
 * Class for printing to the terminal.
 */
public class Printer {

  /**
   * Prints the main menu to the terminal.
   *
   * @param path The current description location
   */
  public void printMainMenu(String path) {
    System.out.println("Welcome to the chaos game!");
    System.out.println("Current description location: " + path);
    System.out.println("1. Run chaos game");
    System.out.println("2. Change Transformation");
    System.out.println("3. Exit");
    System.out.println("Waiting for user input...");
  }

  /**
   * Prints the transformation menu to the terminal.
   */
  public void transformationChanging() {
    System.out.println("Enter the new transformation you want:");
    System.out.println("1. Sierpinski Triangle");
    System.out.println("2. Barnsley Transformation");
    System.out.println("3. Julia Transformation");
  }

  /**
   * Prints an error message to the terminal.
   */
  public void errorMessage() {
    System.out.println("Invalid input. Please try again.");
  }

  /**
   * Prints the canvas to the terminal.
   *
   * @param canvas the canvas to print.
   */
  public void printCanvasToTerminal(ChaosCanvas canvas) {
    for (int i = 0; i < canvas.getWidth(); i++) {
      for (int j = 0; j < canvas.getHeight(); j++) {
        if (canvas.getPixel(new Vector2D(i,j)) == 1) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
  }

  /**
   * Prints invalid steps error message.
   */
  public void printInvalidSteps() {
    System.out.println("Invalid number of steps. Please try again.");
  }

  /**
   * Makes it clear to the user that the program asks for a number of steps.
   */
  public void printGetNrOfSteps() {
    System.out.println("Enter the number of steps to run:");
  }

  /**
   * Error message for invalid path.
   */
  public void invalidPath() {
    System.out.println("Invalid path.");
  }
}
