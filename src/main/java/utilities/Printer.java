package utilities;

import chaosGameClasses.ChaosCanvas;
import vectors.Vector2D;

public class Printer {

  public void printMainMenu(String path) {
    System.out.println("Welcome to the chaos game!");
    System.out.println("Current description location: " + path);
    System.out.println("1. Run chaos game");
    System.out.println("2. Change Transformation");
    System.out.println("2. Exit");
    System.out.println("Waiting for user input...");
  }
  public void transformationChanging() {
    System.out.println("Enter the new transformation you want:");
    System.out.println("1. Sierpinski Triangle");
    System.out.println("2. Barnsley Transformation");
    System.out.println("3. Julia Transformation");
  }
  public void errorMessage() {
    System.out.println("Invalid input. Please try again.");
  }
  public void printCanvasToTerminal(ChaosCanvas canvas) {
    for (int i = 0; i < canvas.getWidth() - 1; i++) {
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
  public void printInvalidSteps() {
    System.out.println("Invalid number of steps. Please try again.");
  }
  public void printGetNrOfSteps() {
    System.out.println("Enter the number of steps to run:");
  }
  public void invalidPath() {
    System.out.println("Invalid path.");
  }
}
