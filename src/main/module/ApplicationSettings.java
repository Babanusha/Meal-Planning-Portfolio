package module;

import java.time.LocalDate;

public class ApplicationSettings {
  // Constants for the application

  //User Interface
  public static final int QUANTITY_LIMITATION = 9999999;
  public static final int COST_LIMITATION = 9999999;
  public static final int DAY_INT_LIMITATION = 31;
  public static final int MONTH_INT_LIMITATION = 12;
  public static final int YEAR_INT_LIMITATION = 9998; //9999 is reserved for default expiration date

  public static final int SWITCH_CASE_LIMIT = 99;
  public static final int DEFAULT_INT_HANDLER_LIMIT = 99; //Default value for intHandler maximum value input.

  //Controller
  public static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.of(9999, 12, 31);

  public static final int DEFAULT_COST = -1;

  //VALIDATION:


  private ApplicationSettings() {
    // Prevent instantiation
  }


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
  public static final String STRING_MILLILITERS = "Milliliters";




  public static final String STRING_DECIMALFORMAT = "0.##"; //Decimal format for double values

  public static final int INT_EXIT = 9;
  public static final String EXIT = "exit";
  public static final String CANCELLED = "cancelled";

}