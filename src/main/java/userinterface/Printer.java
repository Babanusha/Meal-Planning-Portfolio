package userinterface;

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

/**
 * Printer Class used to print string output to user. Uses System.out & System.err
 */
public class Printer {


  /**
   * Prints a string to the console.
   *
   * @param message the message to print.
   */
  public void printString(String message) {
    System.out.println(message);
  }

  /**
   * Prints an error message to the console.
   *
   * @param stringToPrint the error message to print.
   */
  public void printError(String stringToPrint) {
    System.err.println(stringToPrint);
  }

  //______________________________________-

  /**
   * prints welcome message.
   */
  public void welcomeMessage() {
    blankLine();
    printString("Welcome to the fridge application!");
  }

  /**
   * prints home menu.
   */
  public void homeMenu() {
    blankLine();
    printString("Fridge is open, choose an action:");

    printString("1. Add item to fridge");
    printString("2. Take out parts of an item");

    printString("3. Search fridge, edit/remove possible.");
    printString("4. Search expire before a given date");
    blankLine();
    printString("5. List items in fridge & calculate cost");
    printString("6. Display a simplified fridge overview");
    blankLine();
    printString("7. Open cookBook");
    blankLine();
    printString("8. Fridge settings");
    printString(INT_EXIT + ". Exit Application");
  }

  /**
   * prints settings menu.
   */
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

  /**
   * prints edit item menu.
   */
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

  /**
   * Prints a blank line to the console.
   */

  public void blankLine() {
    printString("");
  }


  /**
   * Prints a list of quantity units to the console.
   */
  public void listQuantityUnitsTable() {

    printString("1. " + STRING_KILOGRAMS);
    printString("2. " + STRING_GRAMS);
    printString("3. " + STRING_MILLIGRAMS);
    printString("4. " + STRING_LITERS);
    printString("5. " + STRING_PIECES);
  }

  //ea = each

  /**
   * Prints the item table frame to the console.
   */
  public void itemTableFrame() {
    printString(String.format("%-5s\t%-13s\t%-11s\t%-10s\t%-10s\t%-16s",
        "Nr", "Name", "Quantity", "Unit", "Cost 'ea' ", "Expiration Date"));
  }

  /**
   * Prints the currency menu to the console.
   */
  public void currencyMenu() {
    printString("Please choose currency:");
    printString("1. " + CURRENCY_NOK);
    printString("2. " + CURRENCY_EUR);
    printString("3. " + CURRENCY_USD);
    printString("4. " + CURRENCY_GBP);
    blankLine();
    printString(INT_EXIT + ". Exit");
  }

  /**
   * Prints the standard feedback message for "int" input errors.
   */
  public void correctedFormatForIntHandler() {
    printString("Please enter a number corresponding to the given list");
  }

  /**
   * Prints the standard feedback message for invalid input errors.
   */
  public void invalidInputError() {
    printError("Invalid input, try again");
  }

  /**
   * Prints the standard feedback message for "double" input errors.
   */
  public void correctedFormatForDoubleHandler() {
    printString("Please enter a number, two decimals allowed");
  }

  /**
   * Prints the standard feedback message for string input errors.
   */
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

  /**
   * Prints the cookBook menu to the console.
   */
  public void cookBookMenu() {
    printString("Welcome to cookBook! Where we help you manage your recipes.");
    printString("To continue please choose an action from below:");
    blankLine();
    printString("1. Create a new recipe");
    printString("2. See all recipes by name only");
    printString("3. Search for recipes by name");
    printString("4. See all recipes with full detail.");
    blankLine();
    printString("5. Check if recipe can be made with your items");
    printString("6. See all recipes that can be made");
    blankLine();
    printString(INT_EXIT + ". Exit cookBook");
  }

  /**
   * Prints the manual for writing a recipe to the console.
   */
  public void recipeManual() {

    printString("Write instructions sequentially, one line at a time.");
    printString("When one step is finished, press enter before writing the next step.");
    printString("We will automatically add the step number for you.");
    blankLine();
    printError("!! When complete: type 'done' and press enter.");
  }


  /**
   * prints ingredient table formatted.
   */
  public void printIngredientTable() {
    printString("Ingredients:");
    blankLine();
    printString(String.format("%-5s\t%-13s\t%-11s\t%-10s",
        "Nr", "Name", "Quantity", "Unit"));
  }


  /**
   * Prints response for found recipe.
   */
  public void foundRecipe() {
    blankLine();
    printString("Recipe found:");
  }
}
