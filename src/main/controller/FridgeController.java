package controller;

import static settings.ApplicationSettings.DEFAULT_COST;
import static settings.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.INVALID_INPUT;
import static settings.ApplicationSettings.SWITCH_CASE_LIMIT;

import fridge.Fridge;
import fridge.Item;
import userInterface.UserInterface;
import java.time.LocalDate;
import java.util.Iterator;


public class FridgeController {


  private Fridge fridge;
  private UserInterface userInterface;


  /**
   * Constructor for the Controller class Initializes the ItemStorage, Handler, Printer, and
   * UI_Handler Starts the application
   */
  public FridgeController(Fridge fridge, UserInterface userInterface) {
    init(fridge, userInterface);
  }


  private void init(Fridge fridge, UserInterface userInterface) {
    this.fridge = fridge;
    this.userInterface = userInterface;
  }


  /**
   * Starts the application and runs the main menu.
   */


  void displaySimplifiedFridge() {
    userInterface.displayItemsInTable(fridge.retrieveShortenListOfItems());
  }

  void reduceAnItemInFridge() {
    userInterface.promptUserForWhatItemToRemove();
    Item itemToReduce = searchForItemNameInFridge();
    if (itemToReduce != null) {
      int quantityToReduceWith = userInterface.promtForQuantityToReduceWIth();
      if (fridge.reduceQuantityOfItem(quantityToReduceWith, itemToReduce)) {
        userInterface.printItemRemovedCauseNoneLeft(); //TODO: you cant take out more items then you have.
      }
    }
  }

  void searchAndEditItemMenu() {
    Item itemToEdit = searchForItemNameInFridge();
    if (itemToEdit != null) {
      editSingularItem(itemToEdit);
    }
  }







  /**
   * Displays items that expire before a given date. Checks if there is any items returned form
   * fridge.
   */


  void searchDisplayItemsThatExpireBeforeGivenDate() {
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
  void fridgeSettings() {
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
  void createNewItem() {

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
  void displayAllItemsInFridgeWithCost() {
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


  private Item searchForItemNameInFridge() {
    displayAllItemsInFridgeWithCost();
    Item itemFromFridge = null;
    try {
      String searchItem = userInterface.promtForItemName();
      int itemsFound = fridge.specificItemInFridgeCount(searchItem);
      switch (itemsFound) {
        case 0 -> throw new IllegalArgumentException("No items found");
        case 1 -> itemFromFridge = retrieveUniqeFirstItem(searchItem);
        default -> itemFromFridge = promtMultipleFound(searchItem);
      }
    } catch (Exception allExceptions) { //Currently catches all, should use logger if improved.
      userInterface.printNoItemsFound();
    }

    return itemFromFridge;
  }

  private Item retrieveUniqeFirstItem(String searchItem) {
    return fridge.searchForItem(searchItem).next();
  }

  private Item promtMultipleFound(String searchItem) {
    return fridge.retrieveNthOccurenceOfItem(
        promtForSpecificItemToEditFromSearch(searchItem), searchItem);
  }

  /**
   * Method to make user explicitly choose between multiple items found in search.
   *
   * @param searchItem the item to search for. prompts the user to choose which item to edit from
   *                   number of new list.
   */
  private void editMultipleFoundItems(String searchItem) {
    editItemSwitchCase(promtMultipleFound(searchItem));
  }

  /**
   * Edits a singular item.
   */
  private void editSingularItem(Item itemToEdit) {
    editItemSwitchCase(itemToEdit); //TODO: fix javadoc
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
    boolean removedStatus = fridge.removeItem(itemToEdit);
    displayAllItemsInFridgeWithCost();
    return removedStatus; //Indicate a check for exit.
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


  public Iterator<Item> getFridgeIterator() {
    return fridge.iterateOverFridge();
  }
}

