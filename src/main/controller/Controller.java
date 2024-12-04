package controller;

import static module.ApplicationSettings.DEFAULT_COST;
import static module.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static module.ApplicationSettings.INT_EXIT;
import static module.ApplicationSettings.INVALID_INPUT;
import static module.ApplicationSettings.STRING_DECIMALFORMAT;
import static module.ApplicationSettings.SWITCH_CASE_LIMIT;

import module.Fridge;
import module.Item;
import view.Printer;
import view.Reader;
import view.UserInterface;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;


public class Controller {


  private Fridge fridge;
  private UserInterface userInterface;


  /**
   * Constructor for the Controller class Initializes the ItemStorage, Handler, Printer, and
   * UI_Handler Starts the application
   */
  public Controller() {
    init();
    start();
  }

  /**
   * Initialising the controller with the necessary classes and objects.
   * <p>
   * Several classes are ONLY initiated in the controller, and not held by the controller.
   */

  private void init() {

    Validator validator = new Validator();//Only Initiated in controller, not held by.
    Scanner scanner = new Scanner(System.in); //Only Initiated in controller, not held by.
    Reader reader = new Reader(scanner); //Only Initiated in controller, not held by.
    Printer printer = new Printer();  //Only Initiated in controller, not held by.
    DecimalFormat decimalFormat = new DecimalFormat(
        STRING_DECIMALFORMAT);  //Only Initiated in controller, not held by.

    userInterface = new UserInterface(reader, printer, validator, decimalFormat);
    fridge = new Fridge();

  }


  /**
   * Starts the application and runs the main menu.
   */

  private void start() {
    boolean isApplicationOnline = true;
    mainMenuSwitchCase(isApplicationOnline);
  }

  /**
   * Exits the application.
   * <p>
   * Prompts user for confirmation before exiting.
   */
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

          case 1 -> createNewItem();
          case 2 -> searchAndEditItemMenu();
          case 3 -> displayAllItemsInFridge();
          case 4 -> displayItemsThatExpireBeforeGivenDate();

          case 8 -> fridgeSettings();
          case INT_EXIT -> programStatus = false;        //EXIT

          default -> throw new IllegalArgumentException("Invalid input");

        }
      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
    exit();
  }

  /**
   * Displays items that expire before a given date. Checks if there is any items returned form
   * fridge.
   */

  private void displayItemsThatExpireBeforeGivenDate() {
    LocalDate dateToCheck = userInterface.promtForExpirationDate();
    Iterator<Item> expiredItems = fridge.iterateExpiredItems(dateToCheck);
    if (expiredItems.hasNext()) {
      userInterface.displayItemsInTable(expiredItems);
      userInterface.displayCostOfItemsInFridge(fridge.calculateCostOfExpiredItems(dateToCheck));
    } else {
      userInterface.printNoItemsFound();
    }
  }

  /**
   * Fridge settings menu. Switch case with options.
   */
  private void fridgeSettings() {
    boolean exitTrigger = false;
    while (!exitTrigger) {
      try {
        userInterface.printSettingsMenu(); //settingsMenu i printer.
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {
          case 1 -> fridge.sortItemsAlphabetically();
          case 2 -> fridge.sortItemsByQuantityLeft();
          case 3 -> fridge.sortItemsByExpirationDate();
          case 4 -> userInterface.promtForNewCurrency();
          case INT_EXIT -> exitTrigger = true;

          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }
      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
  }

  /**
   * Creates a new item and adds it to the fridge.
   * <p>
   * Prompts user for name, quantity, quantity unit, cost, and expiration date. If user chooses to
   * add cost, the user is prompted for cost. If user chooses to add expiration date, the user is
   * prompted for expiration date.
   */
  private void createNewItem() {

    String name;
    int quantity;
    String quantityUnit;
    double cost = DEFAULT_COST;

    LocalDate expirationDate = DEFAULT_EXPIRATION_DATE;
    name = userInterface.promtForItemName();
    quantity = userInterface.promtForQuantity();
    quantityUnit = userInterface.promtForQuantityUnit();

    if (userInterface.yesOrNo("Do you want to add cost of item?")) { //TODO: move string
      cost = userInterface.promtForCostOfItem();
    }
    if (userInterface.yesOrNo("Do you want to add expiration date?")) {//TODO: move string
      expirationDate = userInterface.promtForExpirationDate();
    }
    fridge.createItemAndAddToFridge(name, quantity, quantityUnit,
        cost, expirationDate);
  }

  /**
   * Displays all items in the fridge. Retrieves and prints the calculated cost of all items in the
   * fridge.
   */
  private void displayAllItemsInFridge() {
    userInterface.displayItemsInTable(fridge.iterateOverFridge());
    userInterface.displayCostOfItemsInFridge(calculateCostOfFridge());
  }


  /**
   * Calculates the total cost of all items in the fridge.
   *
   * @return the total cost of all items in the fridge.
   */

  private double calculateCostOfFridge() {
    return fridge.calculateTotalCostOfItems();
  }

  /**
   * Searches for an item in the fridge by prompting item name. If the item is found, the user is
   * prompted to edit the item. if multiple items are found, the user is prompted to choose which
   * item to edit.
   */
  private void searchAndEditItemMenu() {
    displayAllItemsInFridge();
    String searchItem = userInterface.promtForItemName();
    int itemsFound = fridge.specificItemInFridgeCount(searchItem);

    switch (itemsFound) {
      case 0 -> userInterface.printNoItemsFound();
      case 1 -> editSingularItem(searchItem);
      default -> editMultipleItems(searchItem);
    }

  }

  /**
   * Method to make user explicitly choose between multiple items found in search.
   *
   * @param searchItem the item to search for. prompts the user to choose which item to edit from
   *                   number of new list.
   */
  private void editMultipleItems(String searchItem) {
    editItemSwitchCase(fridge.retrieveNthOccurenceOfItem(
        promtForSpecificItemToEditFromSearch(searchItem), searchItem));
  }

  /**
   * Edits a singular item.
   *
   * @param searchItem the item to search for. Prompts user to edit the item.
   */
  private void editSingularItem(String searchItem) {
    editItemSwitchCase(fridge.searchForItem(searchItem).next());
  }

  /**
   * Prompts user to choose which item to edit from a list of items.
   *
   * @param searchItem the item that has been searched for.
   * @return the index of the item to edit.
   */

  private int promtForSpecificItemToEditFromSearch(String searchItem) {
    return userInterface.promtMultipleItemsFoundChoice(fridge.searchForItem(searchItem));
  }

  /**
   * Switch case for editing an item.
   *
   * @param itemToEdit the item to edit.
   */

  private void editItemSwitchCase(Item itemToEdit) {
    int userChoice = userInterface.promtWhatPartToEditInItem();
    boolean editComplete = false;
    while (!editComplete && userChoice != INT_EXIT) { // 9 = exit
      try {
        switch (userChoice) {
          case 1 -> editComplete = editName(itemToEdit);
          case 2 -> editComplete = editQuantity(itemToEdit);
          case 3 -> editComplete = editQuantityUnit(itemToEdit);
          case 4 -> editComplete = editCostOfItem(itemToEdit);
          case 5 -> editComplete = editExpirationDate(itemToEdit);
          case 6 -> editComplete = removeItemFromFridge(itemToEdit);
          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }

      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
  }

  /**
   * Removes an item from the fridge.
   *
   * @param itemToEdit the item to remove.
   * @return true to indicate that the item has been removed.
   */
  private boolean removeItemFromFridge(Item itemToEdit) {
    fridge.removeItem(itemToEdit);
    displayAllItemsInFridge();
    return true; //Indicate a check for exit.
  }

  /**
   * Edits the expiration date of an item.
   *
   * @param itemToEdit the item to edit.
   * @return true to indicate that the expiration date has been edited.
   */
  private boolean editExpirationDate(Item itemToEdit) {
    return fridge.editItemExpirationDate(itemToEdit, userInterface.promtForExpirationDate());
  }

  /**
   * Edits the cost of an item.
   *
   * @param itemToEdit the item to edit.
   * @return true to indicate that the cost has been edited.
   */
  private boolean editCostOfItem(Item itemToEdit) {
    return fridge.editItemCost(itemToEdit, userInterface.promtForCostOfItem());
  }

  /**
   * Edits the quantity unit of an item.
   *
   * @param itemToEdit the item to edit.
   * @return true to indicate that the quantity unit has been edited.
   */
  private boolean editQuantityUnit(Item itemToEdit) {
    return fridge.editItemUnit(itemToEdit, userInterface.promtForQuantityUnit());
  }

  /**
   * Edits the quantity of an item.
   *
   * @param itemToEdit the item to edit.
   * @return true to indicate that the quantity has been edited.
   */
  private boolean editQuantity(Item itemToEdit) {
    return fridge.editItemQuantity(itemToEdit, userInterface.promtForQuantity());
  }

  /**
   * Edits the name of an item.
   *
   * @param itemToEdit the item to edit.
   * @return true to indicate that the name has been edited.
   */
  private boolean editName(Item itemToEdit) {
    return fridge.editItemName(itemToEdit, userInterface.promtForItemName());
  }


}

