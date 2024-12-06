package userInterface;

import static settings.ApplicationSettings.INVALID_INPUT;

import java.util.Scanner;

/**
 * Class for reading input from the user. Uses Java.util.Scanner for reading input.
 */
public class Reader {

  private final Scanner scanner;  //Scanner for reading input
  private boolean inputAccepted;  //Boolean for checking if input is accepted

  /**
   * Constructor for the Reader class initiates' scanner.
   */
  public Reader(Scanner scanner) {
    this.scanner = scanner;
  }

  /**
   * Method for reading a string from the user.
   *
   * @return String input from user.
   * @throws IllegalArgumentException back to UserInterface if input is invalid.
   */

  public String readString() throws IllegalArgumentException {
    inputAccepted = false;
    String input = "";
    while (!inputAccepted) {
      try {
        input = scanner.nextLine();
        inputAccepted = true;
      } catch (Exception ignored) {
        throw new IllegalArgumentException(INVALID_INPUT);
      }
    }
    return input;
  }

  /**
   * Method for reading an int from the user Method used so we don't have to parse double to int.
   *
   * @return Int input from user.
   * @throws IllegalArgumentException back to UserInterface if input is invalid.
   */

  public int readInt() throws IllegalArgumentException {
    int input = 0;
    inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = scanner.nextInt();
        scanner.nextLine(); //Consume newline
        inputAccepted = true;

      } catch (Exception e) {
        scanner.nextLine(); //Consume invalid input
        throw new IllegalArgumentException(INVALID_INPUT);
      }
    }
    return input;
  }

  /**
   * Method for reading a double from the user.
   *
   * @return Double input from user.
   * @throws IllegalArgumentException back to UserInterface if input is invalid.
   */

  public double readDouble() throws IllegalArgumentException {
    double input = 0;
    inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = scanner.nextDouble();
        scanner.nextLine(); //Consume newline
        inputAccepted = true;

      } catch (Exception e) {
        scanner.nextLine(); //Consume invalid input
        throw new IllegalArgumentException(INVALID_INPUT);
      }
    }
    return input;
  }


}
