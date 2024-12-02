package view;

import static module.ApplicationSettings.COST_LIMITATION;
import static module.ApplicationSettings.DAY_INT_LIMITATION;
import static module.ApplicationSettings.DEFAULT_INT_HANDLER_LIMIT;
import static module.ApplicationSettings.INT_GRAMS;
import static module.ApplicationSettings.INT_KILOGRAMS;
import static module.ApplicationSettings.INT_LITERS;
import static module.ApplicationSettings.INT_MILLIGRAMS;
import static module.ApplicationSettings.INT_MILLILITERS;

import static module.ApplicationSettings.MONTH_INT_LIMITATION;
import static module.ApplicationSettings.QUANTITY_LIMITATION;
import static module.ApplicationSettings.STRING_GRAMS;
import static module.ApplicationSettings.STRING_KILOGRAMS;
import static module.ApplicationSettings.STRING_LITERS;
import static module.ApplicationSettings.STRING_MILLIGRAMS;
import static module.ApplicationSettings.STRING_MILLILITERS;
import static module.ApplicationSettings.YEAR_INT_LIMITATION;


import controller.Validator;
import module.Item;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Iterator;

public class UserInterface {

  private final Reader reader;
  private final Printer printer;
  private final Validator validator;
  private final DecimalFormat decimalFormat;



  public UserInterface(Reader reader, Printer printer,
      Validator validator, DecimalFormat decimalFormat) {
    this.reader = reader;
    this.printer = printer;
    this.validator = validator;
    this.decimalFormat = decimalFormat;
  }

  public void printHomeMenu() {
    printer.homeMenu();
  }

  public double doubleHandler(int maximumIntValue) {
    double input = 0;
    boolean inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = reader.readDouble();
        if (validator.isBetweenZeroAndGivenValue((int) input, maximumIntValue)) {
          throw new Exception("Invalid input");
        } else {
          inputAccepted = true;
        }
      } catch (Exception e) {
        printInvalidInputError();
        printer.printString("exception: " + e); //
        input = 0;
      }
    }
    return input;
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
        printInvalidInputError();
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
      }
      catch(Exception e) {

        printInvalidInputError();
        printer.printString("exception: " + e); //
        input = null;
      }
    }
      return input;
    }


  public boolean confirmItemRemoval() {
    return yesOrNo("Is this the correct item to remove?");
  }
  public String promtForItemRemoval() {
    printer.printString("Enter name of item to remove:");
    return stringHandler();
  }



  public String promtForItemName( )  {
    printer.printString("Enter item name:");
    return stringHandler();
  }
  public int promtForQuantity( ) {
    printer.printString("Enter quantity:");
    return intHandler(QUANTITY_LIMITATION);
  }
  public String promtForQuantityUnit( ) {
    printer.printString("Enter quantity unit from list below:");
    printer.listQuantityUnitsTable(); //TODO: FILL
    return unitTableTranslator(intHandler(QUANTITY_LIMITATION));
  }

  private String unitTableTranslator(int userInput) {
    return switch (userInput) {
      case INT_KILOGRAMS -> STRING_KILOGRAMS;
      case INT_GRAMS -> STRING_GRAMS;
      case INT_MILLIGRAMS -> STRING_MILLIGRAMS;
      case INT_LITERS -> STRING_LITERS;
      case INT_MILLILITERS -> STRING_MILLILITERS;
      default -> {throw new IllegalStateException("Unexpected value: " + userInput); }
    };
  }

  public double promtForCostOfItem( ) {
    printer.printString("Enter cost of item:");
    return doubleHandler(COST_LIMITATION);
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



  public int promtWhatPartToEditInItem() {
    printer.itemEditMenu();
    return intHandler(DEFAULT_INT_HANDLER_LIMIT);
  }


  public boolean yesOrNo(String questionONLY) {
    printer.printString(questionONLY + "  (yes/no or y/n)");
    return validator.checkIfYes(stringHandler());
  }





  public void printNoItemsFound() {
    printer.printString("No items found");
  }

  public void printErrorWithException(Exception allExceptions) {
    printer.printError("Error: " + allExceptions); //TODO: CLEAN UP with specified error.
  }
  public void printInvalidInputError() {
    printer.printError("Invalid input, try again");
  }
  public void printExitMessage() {
    printer.printString("Exiting application");
  }









  private void printItemInFormat(Item inputItem, int itemNumerator) {
    String itemName = inputItem.getName();
    int quantity = inputItem.getQuantity();
    String quantityUnit = inputItem.getUnit();
    String formattedCost = decimalFormat.format(inputItem.getProductCost());
    LocalDate expirationDate = inputItem.getExpirationDate();

        //s = string, d = decimal, f = float
    String formattedString = String.format("%-5d\t%-14.14s\t%-8d\t%-11.11s\t%-11.11s\t%-16.16s",
        itemNumerator, itemName, quantity, quantityUnit, formattedCost, expirationDate);
    printer.printString(formattedString);
  }


  public void displayItemsInTable(Iterator<Item> foundItems) {
    printer.blankLine();
    //printer.printString("Items found:"); //TODO: EDIT LOCATION
    printer.itemTableFrame();
    int itemNumerator = 0;
    while (foundItems.hasNext()) {
      itemNumerator++;
      printItemInFormat(foundItems.next(), itemNumerator);

    }
    printer.blankLine();
  }

  public int promtMultipleItemsFoundChoice(Iterator<Item> itemIterator) {
    printer.printString("Multiple items found, choose item to edit:");
    displayItemsInTable(itemIterator);
    return intHandler(DEFAULT_INT_HANDLER_LIMIT);
  }


  public void displayCostOfItemsInFridge(double totalCost) {
    printer.printString("Total cost of items in fridge: " + decimalFormat.format(totalCost));
    printer.blankLine();
  }
}
