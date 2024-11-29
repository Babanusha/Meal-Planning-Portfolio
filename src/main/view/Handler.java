package view;
import controller.Validator;
import java.util.Scanner;

public class Handler {

  Scanner scanner;
  boolean inputAccepted;
  Printer printer;
  Validator validator;





  public Handler(Validator validator) {
    scanner = new Scanner(System.in);
    printer = new Printer();
    this.validator = validator;

  }


  public String getStringInput() {
    inputAccepted = false;
    String input = "";
    while (!inputAccepted) {
      try {
        input = scanner.nextLine();
        if (validator.validateString(input)) {
          inputAccepted = true;
        } else {
          throw new Exception("Format is: [a-zA-Z0-9]"); //TODO: RYDD OPP/tydeligjør
        }
      } catch (Exception e) {
        printer.invalidInput();
        printer.printString("exception: " + e);
      }
    } return input;
  }

  public int getIntInput() {
    int input = 0;
    inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = scanner.nextInt();
        scanner.nextLine(); //Consume newline
        inputAccepted = true;

      } catch (Exception e) {
        printer.invalidInput();
        scanner.nextLine(); //Consume invalid input
      }
    } return input;
  }


}
