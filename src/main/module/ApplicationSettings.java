package module;

import java.time.LocalDate;

public class ApplicationSettings {
  // Constants for the application

  //User Interface
  public static final int QUANTITY_LIMITATION = 9999999;
  public static final int COST_LIMITATION = 9999999;
  public static final int DAY_INT_LIMITATION = 31;
  public static final int MONTH_INT_LIMITATION = 12;
  public static final int YEAR_INT_LIMITATION = 9999;
  public static final int SWITCH_CASE_LIMIT = 99;

  //Controller
  public static final LocalDate DEFAULT_EXPIRATION_DATE = LocalDate.of(9999, 12, 31);

  public static final int DEFAULT_COST = -1;



  //VALIDATION:

  public static final int MAX_INT_LENGTH = 11; //

  private ApplicationSettings() {
    // Prevent instantiation
  }
}