package settings;

import java.time.LocalDate;

/**
 * Class for ApplicationSettings, which contains constants for the application. Constants are used
 * for limiting input, setting default values.
 */
public class ApplicationSettings {

  private ApplicationSettings() {
    // Prevent instantiation.
  }

  //User Interface
  public static final int QUANTITY_LIMITATION = 9999999;
  public static final int COST_LIMITATION = 9999999;
  public static final int DAY_INT_LIMITATION = 31;
  public static final int MONTH_INT_LIMITATION = 12;
  public static final int YEAR_INT_LIMITATION = 9998; //9999 is reserved for default expiration date
  public static final int SWITCH_CASE_LIMIT = 99;

  //HANDLER LIMITATIONS
  public static final int DEFAULT_INT_HANDLER_LIMIT = 999; //Default value for intHandler maximum value input.


  public static final int STRING_HANDLER_LIMIT_FOR_YES_NO = 5; //Yes or No can be up to x characters long.

  public static final int STRING_HANDLER_LIMIT_FOR_NAME = 20; //Item name can be up to x characters long.
  public static final int STRING_HANDLER_LIMIT_FOR_DESCRIPTION = 200; //Description can be up to x characters long.
  public static final int STRING_HANDLER_LIMIT_FOR_INSTRUCTIONS = 200; //Instructions can be up to x characters long.

  public static final String TEN_BLANK_SPACES = "          "; //10 blank spaces

  public static final String STRING_LITERAL_NOT_GIVEN = "Not given";
  //Controller
  public static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.of(9999, 12, 31);
  public static final int DEFAULT_COST = -1;
  //Quantity Unit Specifiers
  public static final int INT_KILOGRAMS = 1;
  public static final int INT_GRAMS = 2;
  public static final int INT_MILLIGRAMS = 3;
  public static final int INT_LITERS = 4;
  public static final int INT_MILLILITERS = 5;

  public static final String STRING_KILOGRAMS = "Kilograms";
  public static final String STRING_GRAMS = "Grams";
  public static final String STRING_MILLIGRAMS = "Milligrams";
  public static final String STRING_LITERS = "Liters";
  public static final String STRING_PIECES = "Pieces";
  public static final String STRING_DECIMALFORMAT = "0.##"; //Decimal format for double values
  public static final int INT_EXIT = 9;

  public static final String INVALID_INPUT = "Invalid input";
  //ISO CODE STRING FOR CURRENCY
  public static final String CURRENCY_NOK = "Kr (NOK)";
  public static final String CURRENCY_USD = "$ (USD)";
  public static final String CURRENCY_EUR = "€ (EUR)";
  public static final String CURRENCY_GBP = "£ (GBP)";


}