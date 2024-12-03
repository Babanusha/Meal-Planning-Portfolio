package controller;

import static module.ApplicationSettings.DEFAULT_COST;
import static module.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static module.ApplicationSettings.INT_EXIT;
import static module.ApplicationSettings.INVALID_INPUT;
import static module.ApplicationSettings.STRING_DECIMALFORMAT;
import static module.ApplicationSettings.SWITCH_CASE_LIMIT;

import module.Item;
import module.Fridge;
import view.Reader;
import view.Printer;
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


  public void init() {


    Validator validator = new Validator();//Only Initiated in controller, not held by.
    Scanner scanner = new Scanner(System.in); //Only Initiated in controller, not held by.
    Reader reader = new Reader(scanner); //Only Initiated in controller, not held by.
    Printer printer = new Printer();  //Only Initiated in controller, not held by.
    DecimalFormat decimalFormat = new DecimalFormat(STRING_DECIMALFORMAT);  //Only Initiated in controller, not held by.

    userInterface = new UserInterface(reader, printer, validator, decimalFormat);
    fridge = new Fridge();

  }




  private void start() {
    boolean isApplicationOnline = true; //If time, make persistent storage
    mainMenuSwitchCase(isApplicationOnline);
  }

  private void exit() { //TODO: Make it save to a file if time + persistent storage?
    if (userInterface.promtExitMessage()) {
      System.exit(0);
    }
    mainMenuSwitchCase(true);
  }

  private void mainMenuSwitchCase(boolean programStatus) {
    while (programStatus) {
      try {
        userInterface.printHomeMenu(); //homeMenu i printer.
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {

          case 1 -> createNewItem();
          case 2 -> searchAndEditItemMenu();
          case 3-> displayAllItemsInFridge();
          case 4-> displayItemsThatExpireBeforeGivenDate();

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
  private void displayItemsThatExpireBeforeGivenDate() {
    LocalDate dateToCheck = userInterface.promtForExpirationDate();
    Iterator<Item> expiredItems= fridge.iterateExpiredItems(dateToCheck);
    if (expiredItems.hasNext()){
      userInterface.displayItemsInTable(expiredItems);
      userInterface.displayCostOfItemsInFridge(fridge.calculateCostOfExpiredItems(dateToCheck));
    } else {
      userInterface.printNoItemsFound();
    }

  }

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

  private void displayAllItemsInFridge() {
    userInterface.displayItemsInTable(fridge.iterateOverFridge());
    userInterface.displayCostOfItemsInFridge(calculateCostOfFridge());
  }


  private double calculateCostOfFridge() {
   return fridge.calculateTotalCostOfItems();
  }
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
  private void editMultipleItems(String searchItem) {
    editItemSwitchCase(fridge.retrieveNthOccurenceOfItem(
        promtForSpecificItemToEditFromSearch(searchItem),searchItem));
  }

  private void editSingularItem(String searchItem) {
    editItemSwitchCase(fridge.searchForItem(searchItem).next());
  }

  private int promtForSpecificItemToEditFromSearch(String searchItem) {
   return userInterface.promtMultipleItemsFoundChoice(fridge.searchForItem(searchItem));
  }

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

  private boolean removeItemFromFridge(Item itemToEdit) {
    fridge.removeItem(itemToEdit);
    displayAllItemsInFridge();
    return true;
  }

  private boolean editExpirationDate(Item itemToEdit) {
    return fridge.editItemExpirationDate(itemToEdit, userInterface.promtForExpirationDate());
  }

  private boolean editCostOfItem(Item itemToEdit) {
    return fridge.editItemCost(itemToEdit, userInterface.promtForCostOfItem());
  }

  private boolean editQuantityUnit(Item itemToEdit) {
    return fridge.editItemUnit(itemToEdit, userInterface.promtForQuantityUnit());
  }

  private boolean editQuantity(Item itemToEdit) {
    return fridge.editItemQuantity(itemToEdit, userInterface.promtForQuantity());
  }

  private boolean editName(Item itemToEdit) {
    return fridge.editItemName(itemToEdit, userInterface.promtForItemName());
  }


}

