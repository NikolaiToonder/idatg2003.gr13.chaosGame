package utilities;

import java.util.Scanner;

/**
 * This class is responsible for all user inputs in the program. It uses IsValidInput class to check
 * if the inputs are valid or not.
 *
 * @author Nikolai Engelsen Tønder
 * @version 1.0.0
 */
public class UserInput {

  Scanner lineScanner = new Scanner(System.in);
  IsValidInput isValidInput = new IsValidInput();

  /**
   * Method to get the user input for string. Checks if string is empty.
   *
   * @return input String
   */
  public String getInput() {
    String input = lineScanner.nextLine();
    while (input.isEmpty()) {
      input = lineScanner.nextLine();
    }
    return input;
  }

  /**
   * Method to get the user input for number of steps.
   *
   * @return number of steps
   */
  public int getNrOfSteps() {
    String input = lineScanner.nextLine();
    if (isValidInput.isValidNumberOfSteps(input)) {
      return Integer.parseInt(input);
    } else {
      return 0;
    }
  }


}
