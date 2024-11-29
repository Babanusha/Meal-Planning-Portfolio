package controller;

import static module.ApplicationSettings.MAX_INT_LENGTH;

public class Validator {


  public boolean isBetweenZeroAndGivenValue(int numberToCheck, int maxIntValue) {
    return numberToCheck <= maxIntValue && String.valueOf(numberToCheck).length() < MAX_INT_LENGTH;
  }


  public boolean validateString(String input) {
    int maxLengthOfStringInput = 100;
    String regex = "^[a-zA-Z0-9]*$";
  return input != null && !input.trim().isEmpty() &&
      input.length() < maxLengthOfStringInput && input.matches(regex);
  }

  public boolean checkIfYes(String input) {
    return input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y") || input.equalsIgnoreCase("ye");
  }



}
