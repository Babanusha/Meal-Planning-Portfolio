package controller;



public class Validator {


  public boolean isBetweenZeroAndGivenValue(int numberToCheck, int maxIntValue) {
  return numberToCheck > 0 && numberToCheck <= maxIntValue;
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

  public boolean isDoubleAccepted(double input, int maximumIntValue) {
    return input > 0 && input <= maximumIntValue;
  }
}
