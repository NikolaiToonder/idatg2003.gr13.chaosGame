package model.utilities;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used to get input from the console. Will only be used when CLI is used.
 */
public class IsValidInput {

  /**
   * Checks if the input is a valid path.
   *
   * @param steps The input to check
   * @return True if the input is a valid path, false otherwise
   */
  public boolean isValidNumberOfSteps(String steps) {
    try {
      return Integer.parseInt(steps) > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
