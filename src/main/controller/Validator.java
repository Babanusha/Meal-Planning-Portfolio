package controller;


import static module.ApplicationSettings.MAXIMUM_LENGTH_OF_STRING_INPUT;

public class Validator {


  /**
   * Method for checking if a number is between 0 and a given value.
   *
   * @param numberToCheck number to check
   * @param maxIntValue   maximum value for number
   * @return true if number is between 0 and maxIntValue, false otherwise
   */
  public boolean isBetweenZeroAndGivenValue(int numberToCheck, int maxIntValue) {
    return numberToCheck > 0 && numberToCheck <= maxIntValue;
  }

  /**
   * "   * Validates a string input. Checks if input is not null, not empty, less than
   * "MAXIMUM_LENGTH_OF_STRING_INPUT" characters and
   *
   * @param input string to validate
   * @return true if input is valid, false otherwise
   */

  public boolean validateString(String input) {
    int maxLengthOfStringInput = MAXIMUM_LENGTH_OF_STRING_INPUT;
    String regex = "^[a-zA-Z0-9]*$";
    return input != null && !input.trim().isEmpty() &&
        input.length() < maxLengthOfStringInput && input.matches(regex);
  }

  /**
   * Method for checking if a string input is "yes", "y" or "ye".
   *
   * @param input string to check
   * @return true if input is "yes", "y" or "ye", false otherwise
   */

  public boolean checkIfYes(String input) {
    return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y") || input.equalsIgnoreCase(
        "ye");
  }


  /**
   * Method to check if a double is between 0 and a given value.
   *
   * @param input         double to check
   * @param givenMaxValue maximum value for input
   * @return true if input is between 0 and givenMaxValue, false otherwise
   */
  public boolean isDoubleBetweenZeroAndGivenValue(double input, int givenMaxValue) {
    return input > 0 && input <= givenMaxValue;
  }
}
