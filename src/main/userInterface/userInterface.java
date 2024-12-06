package userInterface;

import static settings.ApplicationSettings.COST_LIMITATION;
import static settings.ApplicationSettings.CURRENCY_EUR;
import static settings.ApplicationSettings.CURRENCY_GBP;
import static settings.ApplicationSettings.CURRENCY_NOK;
import static settings.ApplicationSettings.CURRENCY_USD;
import static settings.ApplicationSettings.DAY_INT_LIMITATION;
import static settings.ApplicationSettings.DEFAULT_INT_HANDLER_LIMIT;
import static settings.ApplicationSettings.INT_GRAMS;
import static settings.ApplicationSettings.INT_KILOGRAMS;
import static settings.ApplicationSettings.INT_LITERS;
import static settings.ApplicationSettings.INT_MILLIGRAMS;
import static settings.ApplicationSettings.INT_MILLILITERS;
import static settings.ApplicationSettings.INVALID_INPUT;
import static settings.ApplicationSettings.MONTH_INT_LIMITATION;
import static settings.ApplicationSettings.QUANTITY_LIMITATION;
import static settings.ApplicationSettings.STRING_GRAMS;
import static settings.ApplicationSettings.STRING_HANDLER_LIMIT_FOR_NAME;
import static settings.ApplicationSettings.STRING_HANDLER_LIMIT_FOR_YES_NO;
import static settings.ApplicationSettings.STRING_KILOGRAMS;
import static settings.ApplicationSettings.STRING_LITERS;
import static settings.ApplicationSettings.STRING_MILLIGRAMS;
import static settings.ApplicationSettings.STRING_PIECES;
import static settings.ApplicationSettings.STRING_NOT_GIVEN;
import static settings.ApplicationSettings.YEAR_INT_LIMITATION;

import module.fridge.Item;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Iterator;

/**
 * Class for handling user input/output. Uses Reader, Printer, Validator and DecimalFormat classes.
 */
public class User_interface {

  private final Reader reader;
  private final Printer printer;
  private final Validator validator;
  private final DecimalFormat decimalFormat;
  private String currentCurrency = CURRENCY_NOK; //Default currency

  /**
   * Constructor for the UserInterface class. Independency Injection for Reader, Printer, Validator
   * and DecimalFormat.
   *
   * @param reader        , Reader class for reading user input.
   * @param printer       , Printer class for printing output to user.
   * @param validator     , Validator class for validating user input.
   * @param decimalFormat , DecimalFormat class for formatting values to correct length.
   */

  public User_interface(Reader reader, Printer printer,
      Validator validator, DecimalFormat decimalFormat) {
    this.reader = reader;
    this.printer = printer;
    this.validator = validator;
    this.decimalFormat = decimalFormat;
  }

  /**
   * Method for handling double values. Uses Reader class.
   *
   * @param maximumIntValue , maximum allowed value for the double input.
   * @return double , returns the validated double value from user.
   * @throws IllegalArgumentException if input is not within allowed parameters. (0 -
   *                                  maximumIntValue)
   */

  public double doubleHandler(int maximumIntValue) {
    double input = 0;
    boolean inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = reader.readDouble();
        if (!validator.isDoubleBetweenZeroAndGivenValue(input, maximumIntValue)) {
          throw new IllegalArgumentException(INVALID_INPUT);
        }

        inputAccepted = true;
      } catch (Exception e) {
        doubleErrorStandardResponse();
        input = 0;
      }
    }
    return input;
  }

  /**
   * Method for handling standard response for "double" input errors.
   */
  private void doubleErrorStandardResponse() {
    printer.invalidInputError();
    printer.correctedFormatForDoubleHandler();
  }

  /**
   * Method for handling integer values.
   *
   * @param maximumIntValue , maximum allowed value for the integer input.
   * @return int , returns the validated integer value from user.
   */
  public int intHandler(int maximumIntValue) {
    int input = 0;
    boolean inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = readAndValidateInt(maximumIntValue);
        inputAccepted = true;
      } catch (Exception e) {
        printer.intErrorStandardResponse();
      }
    }
    return input;
  }

  /**
   * Method for reading and validating integer values.
   *
   * @param maximumIntValue , maximum allowed value for the integer input.
   * @return int , returns the validated integer value from user.
   * @throws IllegalArgumentException ,if input is not within allowed parameters.
   */
  private int readAndValidateInt(int maximumIntValue) throws IllegalArgumentException {
    int input = reader.readInt();
    if (!validator.isBetweenZeroAndGivenValue(input, maximumIntValue)) {
      throw new IllegalArgumentException(INVALID_INPUT);
    }
    return input;
  }


  /**
   * Handling string values retrieved from user.
   *
   * @return , returns the validated string value from user.
   */

  public String stringHandler(int maxLengthOfString) {
    String input = null;
    boolean inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = readAndValidateString(maxLengthOfString);
        inputAccepted = true;
      } catch (Exception e) {
        printer.stringErrorStandardResponse();
      }
    }
    return input;
  }

  /**
   * Method for reading and validating string values.
   *
   * @return , returns the validated string value from user.
   * @throws IllegalArgumentException ,if input does not match the regex pattern.
   */

  private String readAndValidateString(int maxLengthOfString) throws IllegalArgumentException {
    String input = reader.readString();
    if (!validator.validateString(input, maxLengthOfString)) {
      throw new IllegalArgumentException(INVALID_INPUT);
    }
    return input;
  }

  /**
   * Prompts user for item name. Uses printer class for printing promt to user.
   *
   * @return , stringHandler returns the validated integer value from user.
   */

  public String promtForItemName() {
    printer.printString("Enter item name:");
    return stringHandler(STRING_HANDLER_LIMIT_FOR_NAME);
  }

  /**
   * Prompts user for quantity of item. Uses printer class for printing promt to user.
   *
   * @return , intHandler returns the validated integer value from user.
   */

  public int promtForQuantity() {
    printer.printString("Enter quantity:");
    return intHandler(QUANTITY_LIMITATION);
  }


  /**
   * Prompts user for quantity unit of item. User is given a list of options to choose from. Uses
   * printer class for printing promt to user. Catches IllegalArgumentException thrown by
   * getQuantityUnitFromInput. Gives feedback to user.
   *
   * @return , stringHandler returns the validated integer value from user.
   * @throws IllegalArgumentException if input is not within allowed parameters.
   */
  public String promtForQuantityUnit() {
    String quantityUnit = null;
    boolean valueAccepted = false;
    while (!valueAccepted) {
      printer.printString("Enter quantity unit from list below:");
      printer.listQuantityUnitsTable();
      try {
        quantityUnit = getQuantityUnitFromInput(); //Can Throw IllegalArgumentException
        valueAccepted = true; //Only reached if no exception is thrown.
      } catch (Exception e) {
        printer.intErrorStandardResponse();
      }
    }
    return quantityUnit;
  }

  /**
   * Translates user input to quantity unit.
   *
   * @return , returns the quantity unit in string format.
   * @throws IllegalArgumentException if input is not within allowed parameters.
   */

  private String getQuantityUnitFromInput() throws IllegalArgumentException {
    int userInput = intHandler(DEFAULT_INT_HANDLER_LIMIT);
    return switch (userInput) {
      case INT_KILOGRAMS -> STRING_KILOGRAMS;
      case INT_GRAMS -> STRING_GRAMS;
      case INT_MILLIGRAMS -> STRING_MILLIGRAMS;
      case INT_LITERS -> STRING_LITERS;
      case INT_MILLILITERS -> STRING_PIECES;
      default -> throw new IllegalArgumentException(
          INVALID_INPUT); // Throws exception to promtForQuantityUnit.
    };
  }


  /**
   * Prompts user for cost of item. Uses printer class for printing promt to user.
   *
   * @return , doubleHandler returns the validated double value from user.
   */

  public double promtForCostOfItem() {
    printer.printString("Enter cost of item:");
    return doubleHandler(COST_LIMITATION);
  }

  /**
   * Prompts user for expiration date. Uses printer class for printing promt to user. Prompts and
   * retrieves year, month and day from user.
   *
   * @return , returns the validated LocalDate value from user.
   */
  public LocalDate promtForExpirationDate() {
    printer.printString("Enter expiration year:");
    printer.printError("Year must be between 1 and 9998");
    int year = intHandler(YEAR_INT_LIMITATION);
    printer.printString("Enter expiration month:");
    printer.printError("Month must be between 1 and 12");
    int month = intHandler(MONTH_INT_LIMITATION);
    printer.printString("Enter expiration day:");
    printer.printError("Day must be between 1 and 31");
    int day = intHandler(DAY_INT_LIMITATION);

    return LocalDate.of(year, month, day);
  }
  /**
   * Prompts user for what part of item to edit. Uses printer class for printing promt to user.
   *
   * @return , intHandler returns the validated integer value from user.
   */

  public int promtWhatPartToEditInItem() {
    printer.editItemMenu();
    return intHandler(DEFAULT_INT_HANDLER_LIMIT);
  }

  /**
   * Prompts user for a question that requires a yes or no answer. Uses printer class for printing
   * promt to user. Uses validator class for validating user input, (Checks if yes).
   *
   * @param questionOnly , the question to be asked.
   * @return , returns the validated boolean value from user.
   */
  public boolean yesOrNo(String questionOnly) {
    printer.printString(questionOnly + "  (yes/no or y/n)");
    return validator.checkIfYes(stringHandler(STRING_HANDLER_LIMIT_FOR_YES_NO));
  }


  /**
   * Prints a message to user that no items were found.
   */
  public void printNoItemsFound() {
    printer.printString("No items found");
  }


  /**
   * Asks the user if they want to exit the program.
   *
   * @return a boolean symbolising the users choice. (yes/no)
   */
  public boolean promtExitMessage() {
    return yesOrNo("Do you want to exit? No data will be saved.");
  }

  /**
   * formats and prints the item in a table format. Values currently not listed doesn't print. Cost
   * is formatted to 2 decimal max.
   *
   * @param inputItem     , the item to be printed.
   * @param itemNumerator , the item number in the list.
   */

  private void printItemInFormat(Item inputItem, int itemNumerator) {
    String itemName = inputItem.getName();
    int quantity = inputItem.getQuantity();
    String quantityUnit = inputItem.getUnit();
    String validatedCost = retrieveCostFormattedToString(inputItem.getProductCost());
    String validatedExpirationDate = retrieveExpirationDateToString(inputItem.getExpirationDate());

    //s = string, d = decimal, f = float
    String formattedString = String.format("%-5d\t%-14.14s\t%-8d\t%-11.11s\t%-11.11s\t%-16.16s",
        itemNumerator, itemName, quantity, quantityUnit, validatedCost, validatedExpirationDate);
    printer.printString(formattedString);
  }

  /**
   * Retrieves the cost of an item and formats it to a string. Uses decimalFormat for formatting the
   * cost to 2 decimals. If the cost is "default value", AKA not given it will format to
   * STRING_NOT_GIVEN.
   *
   * @param inputCost , the item to retrieve the cost from.
   * @return , returns the formatted cost in string format.
   */
  private String retrieveCostFormattedToString(Double inputCost) {
    String cost = STRING_NOT_GIVEN;
    if (!validator.costIsDefaultValue(inputCost)) {
      cost = decimalFormat.format(inputCost);
    }
    return cost;
  }

  /**
   * Retrieves the expiration date of an item and formats it to a string. If the expiration date is
   * If date is "default value", AKA not given it will format to STRING_NOT_GIVEN.
   *
   * @param inputDate , the item to retrieve the expiration date from.
   * @return , returns the formatted expiration date in string format.
   */
  private String retrieveExpirationDateToString(LocalDate inputDate) {
    String formattedDate = STRING_NOT_GIVEN;
    if (!validator.isAfterDateLimit(inputDate)) {
      formattedDate = String.format("%d-%02d-%02d",
          inputDate.getYear(), inputDate.getMonthValue(), inputDate.getDayOfMonth());
    }
    return formattedDate;
  }

  /**
   * Prints items iterated in. Uses printer class for printing to user. Includes a itemNumerator to
   * count the items.
   *
   * @param foundItems , the items to be printed.
   */

  public void displayItemsInTable(Iterator<Item> foundItems) {
    if (!foundItems.hasNext()) {
      printNoItemsFound();
      return;
    }
    printer.blankLine();
    printer.itemTableFrame();
    int itemNumerator = 0;
    while (foundItems.hasNext()) {
      itemNumerator++;
      printItemInFormat(foundItems.next(), itemNumerator);

    }
    printer.blankLine();
  }

  /**
   * Prompts user for a choice when multiple items are found.
   *
   * @param itemIterator , iterated items to choose from.
   * @return , returns the validated integer value from user, symbolising the users choice of item.
   */
  public int promtMultipleItemsFoundChoice(Iterator<Item> itemIterator) {
    printer.printString("Multiple items found, choose item by typing the items number.:");
    displayItemsInTable(itemIterator);
    return intHandler(DEFAULT_INT_HANDLER_LIMIT);
  }


  /**
   * Prints the total cost of all items in fridge. Uses DecimalFormat to format the cost to 2
   * decimals. Uses printer class for printing to user.
   *
   * @param totalCost , the total cost of all items in fridge.
   */

  public void displayCostOfItemsInFridge(double totalCost) {
    printer.printString("  Total cost of items in fridge: "
        + decimalFormat.format(totalCost) + " " + currentCurrency);
    printer.blankLine();
  }

  /*
   * Redirects to printer method
   * Prints the edit fridge/settings menu
   */
  public void printSettingsMenu() {
    printer.settingsMenu();
  }


  /**
   * Redirects to printer method Prints the home menu.
   */

  public void printHomeMenu() {
    printer.homeMenu();
  }

  //CURRENCY

  /**
   * Prompts user for new currency. Gives user a currencyMenu to choose from. Uses printer class for
   * printing promt to user. Changes: currentCurrency to the chosen currency. Currently available
   * currencies: NOK, USD, EUR, GBP.
   */
  public void promtForNewCurrency() {
    boolean exitTriggered = false;
    while (!exitTriggered) {
      try {
        printer.currencyMenu();
        switch (intHandler(DEFAULT_INT_HANDLER_LIMIT)) {
          case 1 -> exitTriggered = setCurrencyNok();
          case 2 -> exitTriggered = setCurrencyEuro();
          case 3 -> exitTriggered = setCurrencyUsd();
          case 4 -> exitTriggered = setCurrencyGbp();
          case 9 -> exitTriggered = true;   //EXIT
          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }
      } catch (Exception allExceptions) {
        printer.intErrorStandardResponse();
      }
    }
  }

  /**
   * Changes the current currency to USD.
   *
   * @return , returns true to exit the currency menu.
   */
  private boolean setCurrencyUsd() {
    currentCurrency = CURRENCY_USD;
    return true;
  }

  /**
   * Changes the current currency to GBP.
   *
   * @return , returns true to exit the currency menu.
   */

  private boolean setCurrencyGbp() {
    currentCurrency = CURRENCY_GBP;
    return true;
  }

  /**
   * Changes the current currency to NOK.
   *
   * @return , returns true to exit the currency menu.
   */

  private boolean setCurrencyNok() {
    currentCurrency = CURRENCY_NOK;
    return true;
  }

  /**
   * Changes the current currency to EURO.
   *
   * @return , returns true to exit the currency menu.
   */
  private boolean setCurrencyEuro() {
    currentCurrency = CURRENCY_EUR;
    return true;
  }

  /**
   * Prints standard feedback for int error. Redirects to printer method.
   */
  public void printIntErrorStandardResponse() { //redirects to printer
    printer.intErrorStandardResponse();
  }

  public String promtForRecipeName() {
    printer.printString("Enter recipe name:");
    return stringHandler(STRING_HANDLER_LIMIT_FOR_NAME);
  }
}
