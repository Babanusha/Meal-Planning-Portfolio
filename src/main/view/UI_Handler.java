package view;

import static module.ApplicationSettings.COST_LIMITATION;
import static module.ApplicationSettings.DAY_INT_LIMITATION;
import static module.ApplicationSettings.MONTH_INT_LIMITATION;
import static module.ApplicationSettings.QUANTITY_LIMITATION;
import static module.ApplicationSettings.YEAR_INT_LIMITATION;

import controller.Validator;
import java.time.LocalDate;

public class UI_Handler {

  private final Reader reader;
  private final Printer printer;
  private final Validator validator;

  public UI_Handler(Reader reader, Printer printer, Validator validator) {
    this.reader = reader;
    this.printer = printer;
    this.validator = validator;
  }

  public int intHandler(int maximumIntValue) {
    int input = 0;
    boolean inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = reader.readInt();
        if (!validator.isBetweenZeroAndGivenValue(input, maximumIntValue)) {
          throw new Exception("Invalid input");
        } else {
          inputAccepted = true;
        }
      } catch (Exception e) {
        printer.invalidInputError();
        printer.printString("exception: " + e); //
        input = 0;
      }
    }
    return input;
  }

  public String stringHandler() {
    String input = null;
    boolean inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = reader.readString();
        if (!validator.validateString(input)) {
          throw new Exception("Invalid input");
        } else {
          inputAccepted = true;
        }
      } catch (Exception e) {
        printer.invalidInputError();
        printer.printString("exception: " + e); //
        input = null;
      }
    }
    return input;
  }





  public String promtForItemName( ) {
    printer.printString("Enter item name:");
    return stringHandler();
  }
  public int promtForItemQuantity( ) {
    printer.printString("Enter quantity:");
    return intHandler(QUANTITY_LIMITATION);
  }
  public String promtForQuantityUnit( ) {
    printer.printString("Enter quantity unit:");
    return stringHandler();
  }

  public int promtForCostOfItem( ) {
    printer.printString("Enter cost of item:");
    return intHandler(COST_LIMITATION);
  }
  public LocalDate promtForExpirationDate( ) {
    printer.printString("Enter expiration year:");
    int year = intHandler(YEAR_INT_LIMITATION);
    printer.printString("Enter expiration month:");
    int month = intHandler(MONTH_INT_LIMITATION);
    printer.printString("Enter expiration day:");
    int day = intHandler(DAY_INT_LIMITATION);

    return LocalDate.of(year, month, day);
  }






  public boolean yesOrNo(String questionONLY) {
    printer.printString(questionONLY + "  (yes/no or y/n)");
    return validator.checkIfYes(stringHandler());
  }
}
