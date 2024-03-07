package utilities;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IsValidInput {

  public boolean isValidNumberOfSteps(String steps) {
    try {
      return Integer.parseInt(steps) > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
