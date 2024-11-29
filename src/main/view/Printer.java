package view;

public class Printer {


  public void printString(String message) {
    System.out.println(message);
  }

  public void homeMenu() {
    printString("Fridge is open, choose an action:");

    printString("1. Add item to fridge");
    printString("2. Remove item from fridge");
    printString("3. List items in fridge (CALCULATE COST OF ITEMS)");
    printString("");
    printString("4. Edit item/item list");

    printString("9. Save and exit");
  }



  public void toExit() {
    printString("To exit: either type 'exit' or '9'");
  }

  public void printExitMessage() {
    printString("Exiting application");
  }

  public void LocalDateFormatError() {
    printError("Enter date in the format yyyy-mm-dd");
  }



  public void invalidInputError() {
    printError("Invalid input, try again");
  }

  public void printError(String stringToPrint) {
    System.err.println(stringToPrint);
  }

  public void printSkipLine() {
    printString("To skip this step, type 's' or 'skip' and press enter");
  }
}
