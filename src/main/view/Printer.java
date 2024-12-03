package view;

import static module.ApplicationSettings.INT_EXIT;
import static module.ApplicationSettings.STRING_GRAMS;
import static module.ApplicationSettings.STRING_KILOGRAMS;
import static module.ApplicationSettings.STRING_LITERS;
import static module.ApplicationSettings.STRING_MILLIGRAMS;
import static module.ApplicationSettings.STRING_MILLILITERS;
import static module.ApplicationSettings.CURRENCY_EUR;
import static module.ApplicationSettings.CURRENCY_GBP;
import static module.ApplicationSettings.CURRENCY_NOK;
import static module.ApplicationSettings.CURRENCY_USD;

public class Printer {


  public void printString(String message) {
    System.out.println(message);
  }
  public void printError(String stringToPrint) {
    System.err.println(stringToPrint);
  }

  //______________________________________-


  public void homeMenu() {
    blankLine();
    printString("Fridge is open, choose an action:");
    printString("1. Add item to fridge");
    printString("2. Search fridge, edit/remove possible.");
    printString("3. List items in fridge & calculate cost");
    printString("4. See items that expire before a given date");
    blankLine();
    printString("8. Fridge settings");
    printString(INT_EXIT + ". Exit Application");
  }

  public void settingsMenu() {
    printString("Edit fridge:");
    printString("1. Sort items alphabetically");
    printString("2. Sort items by quantity - ascending");
    printString("3. Sort items by earliest expiration date");
    blankLine();
    printString("4. Choose new currency type");
    blankLine();
    printString(INT_EXIT + ". Back to main menu");
  }

  public void editItemMenu() {
    printString("Edit item:");
    printString("1. Edit name");
    printString("2. Edit quantity");
    printString("3. Edit unit");
    printString("4. Edit cost");
    printString("5. Edit expiration date");
    printString("6. Remove item from fridge");
    blankLine();
    printString(INT_EXIT + ". Back to edit menu");
  }


  public void blankLine() {
    printString("");
  }







  public void listQuantityUnitsTable() {

    printString("1. " + STRING_KILOGRAMS);
    printString("2. " + STRING_GRAMS);
    printString("3. " + STRING_MILLIGRAMS);
    printString("4. " + STRING_LITERS);
    printString("5. " + STRING_MILLILITERS);
  }


  public void itemTableFrame() {
    printString(String.format("%-5s\t%-13s\t%-11s\t%-10s\t%-10s\t%-16s",
        "Nr", "Name", "Quantity", "Unit", "Cost", "Expiration Date"));
  }

  public void currencyMenu() {
    printString("Please choose currency:");
    printString("1. " + CURRENCY_NOK);
    printString("2. " + CURRENCY_EUR);
    printString("3. " + CURRENCY_USD);
    printString("4. " + CURRENCY_GBP);
    blankLine();
    printString(INT_EXIT + ". Exit");
  }

  public void correctedFormatForIntHandler() {
    printString("Please enter a number corresponding to the given list");
  }
  public void invalidInputError() {
    printError("Invalid input, try again");
  }

  public void correctedFormatForDoubleHandler() {
    printString("Please enter a number, two decimals allowed");
  }

  public void correctedFormatForStringHandler() {
    printString("Please enter letters and numbers, special characters not allowed");
  }


  /**
   * Standard feedback message for string input errors.
   */

  public void stringErrorStandardResponse() {
  invalidInputError();
  correctedFormatForStringHandler();
  }
  /**
   * Standard user feedback for "int" input errors.
   */
  public void intErrorStandardResponse() {
    invalidInputError();
    correctedFormatForIntHandler();
  }
}
