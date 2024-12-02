package controller;

import static module.ApplicationSettings.DEFAULT_COST;
import static module.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static module.ApplicationSettings.INT_EXIT;
import static module.ApplicationSettings.STRING_DECIMALFORMAT;
import static module.ApplicationSettings.SWITCH_CASE_LIMIT;

import module.Item;
import module.Fridge;
import view.Reader;
import view.Printer;
import view.UserInterface;
import java.text.DecimalFormat;
import java.time.LocalDate;


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

    fridge = new Fridge();
    Validator validator = new Validator();

    //Only Initiated in controller, not held by.
    Reader reader = new Reader();
    Printer printer = new Printer();
    DecimalFormat decimalFormat = new DecimalFormat(STRING_DECIMALFORMAT);

    userInterface = new UserInterface(reader, printer, validator, decimalFormat);
  }




  private void start() {
    boolean isApplicationOnline = true; //If time, make persistent storage


    mainMenuSwitchCase(isApplicationOnline);
  }

  private void exit() {
    userInterface.printExitMessage();

    //TODO: Make it save to a file if time + persistent storage?
  }

  private void mainMenuSwitchCase(boolean programStatus) {
    while (programStatus) {
      try {
        userInterface.printHomeMenu();
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {

          case 1 -> createNewItem();
          case 2 -> editItemMenu();
          case 3 -> displayAllItemsInFridge();

          case INT_EXIT -> programStatus = false;        //EXIT

          default -> userInterface.printInvalidInputError(); //UI_HANDLER?
        }
      } catch (Exception allExceptions) {
        userInterface.printErrorWithException(allExceptions);
      }
    }
    exit();
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
    if (userInterface.yesOrNo("Do you want to add cost of item?")) {
      cost = userInterface.promtForCostOfItem();
    }
    if (userInterface.yesOrNo("Do you want to add expiration date?")) {
      expirationDate = userInterface.promtForExpirationDate();
    }
    fridge.createItemAndAddToFridge(name, quantity, quantityUnit,
        cost, expirationDate);
  }

  private void displayAllItemsInFridge() {
    userInterface.displayItemsInTable(fridge.iterateOverFridge());
    userInterface.displayCostOfItemsInFridge(CalculateCostOfFrige());
  }


  private double CalculateCostOfFrige() {
   return fridge.calculateTotalCostOfItems();
  }
  private void editItemMenu() {
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

  private void editItemSwitchCase(Item itemToEdit) { //TODO:BOOLEAN RETURN TO CONFIRM EDIT
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


          default -> userInterface.printInvalidInputError();
        }

      } catch (Exception allExceptions) {
        userInterface.printErrorWithException(allExceptions);
      }
  }





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

