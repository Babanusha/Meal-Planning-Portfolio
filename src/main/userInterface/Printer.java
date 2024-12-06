package userInterface;

import static settings.ApplicationSettings.CURRENCY_EUR;
import static settings.ApplicationSettings.CURRENCY_GBP;
import static settings.ApplicationSettings.CURRENCY_NOK;
import static settings.ApplicationSettings.CURRENCY_USD;
import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.STRING_GRAMS;
import static settings.ApplicationSettings.STRING_KILOGRAMS;
import static settings.ApplicationSettings.STRING_LITERS;
import static settings.ApplicationSettings.STRING_MILLIGRAMS;
import static settings.ApplicationSettings.STRING_PIECES;


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
    printString("2. Take out parts of an item");
    printString("3. Search fridge, edit/remove possible.");
    printString("4. List items in fridge & calculate cost");
    printString("5. Search expire before a given date");
    blankLine();
    printString("6. Open cookBook");
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
    printString("5. " + STRING_PIECES);
  }

  //ea = each

  public void itemTableFrame() {
    printString(String.format("%-5s\t%-13s\t%-11s\t%-10s\t%-10s\t%-16s",
        "Nr", "Name", "Quantity", "Unit", "Cost 'ea' ", "Expiration Date"));
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

  public void cookBookMenu() {
    printString("Welcome to cookBook! Where we help you manage your recipes.");
    printString("To continue please choose an action from below:");
    printString("1. Create a new recipe");
    printString("2. search for a recipe - edit/remove possible");
    printString("3. See all recipes");
    blankLine();
    printString(INT_EXIT + ". Exit cookBook");
  }

  public void recipeManual() {

    printString("Write instructions sequentially, one line at a time.");
    printString("When one step is finished, press enter before writing the next step.");
    printError("When complete: type 'done' and press enter.");
  }


  public void editRecipeMenu() {
    printString("Edit recipe:");
    printString("1. Edit name");
    printString("2. Edit description");
    printString("3. Edit ingredients");
    printString("4. Edit instructions");
    printString("5. Remove recipe");
    blankLine();
    printString(INT_EXIT + ". Back to cookBook menu");
  }


  public void reduceItemQuantity() {
    printString("Enter the amount you want to reduce the quantity by:");
  }
}
