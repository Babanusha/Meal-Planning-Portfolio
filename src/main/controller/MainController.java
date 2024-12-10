package controller;

import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.STRING_DECIMALFORMAT;
import static settings.ApplicationSettings.SWITCH_CASE_LIMIT;

import cookbook.CookBook;
import fridge.Fridge;
import userInterface.Printer;
import userInterface.Reader;
import userInterface.UserInterface;
import userInterface.Validator;
import java.text.DecimalFormat;
import java.util.Scanner;

public class MainController {

  private UserInterface userInterface;
  private CookBookController cookBookController;
  private FridgeController fridgeController;

  public MainController() {
    initializeIndependentComponents();
    StartMainApplication();
  }

  private void initializeIndependentComponents() {
    Validator validator = new Validator();//Only Initiated in controller, not held by.
    Scanner scanner = new Scanner(System.in); //Only Initiated in controller, not held by.
    Reader reader = new Reader(scanner); //Only Initiated in controller, not held by.
    Printer printer = new Printer();  //Only Initiated in controller, not held by.
    DecimalFormat decimalFormat = new DecimalFormat(
        STRING_DECIMALFORMAT);  //Only Initiated in controller, not held by.

    userInterface = new UserInterface(reader, printer, validator, decimalFormat);

    CookBook cookBook = new CookBook();
    Fridge fridge = new Fridge();

    cookBookController = new CookBookController(cookBook, userInterface);
    fridgeController = new FridgeController(fridge, userInterface);


  }

  private void StartMainApplication() {
    boolean isApplicationOnline = true;
    userInterface.printWelcomeMessage();
    mainMenuSwitchCase(isApplicationOnline);
  }


  private void exit() {
    if (userInterface.promtExitMessage()) {
      System.exit(0);
    }
    mainMenuSwitchCase(true);
  }

  /**
   * Main menu switch case.
   *
   * @param programStatus boolean to keep the program running. Must correspond to UI.printers
   *                      homeMenu.
   */
  private void mainMenuSwitchCase(boolean programStatus) {
    while (programStatus) {
      try {
        userInterface.printHomeMenu(); //homeMenu i printer.
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {

          case 1 -> fridgeController.createNewItem();
          case 2 -> fridgeController.reduceAnItemInFridge();

          case 3 -> fridgeController.searchAndEditItemMenu();
          case 4 -> fridgeController.searchDisplayItemsThatExpireBeforeGivenDate();

          case 5 -> fridgeController.displayAllItemsInFridgeWithCost();
          case 6 -> fridgeController.displaySimplifiedFridge();

          case 7 -> cookBookController.openCookBookMenu(fridgeController.getFridgeIterator());
          case 8 -> fridgeController.fridgeSettings();

          case INT_EXIT -> programStatus = false;        //EXIT

          default -> throw new IllegalArgumentException("Invalid input");

        }
      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }

    }
    exit();  //SHOULD BE LAST OF METHOD
  }
}
