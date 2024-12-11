package controller;

import static settings.ApplicationSettings.DEFAULT_COST;
import static settings.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.INVALID_INPUT;
import static settings.ApplicationSettings.SWITCH_CASE_LIMIT;

import fridge.Fridge;
import fridge.Item;
import userinterface.UserInterface;
import java.time.LocalDate;
import java.util.Iterator;

/**
 * Controller class for the Fridge. Handles the interaction between the Fridge and the
 * UserInterface.
 */

public class FridgeController {

  private Fridge fridge; // Fridge object
  private UserInterface userInterface; // UserInterface object


  /**
   * Constructor for the FridgeController class. Contains independency injection of CookBook and
   * UserInterface.
   *
   * @param fridge        Fridge object
   * @param userInterface UserInterface object
   */
  public FridgeController(Fridge fridge, UserInterface userInterface) {
    init(fridge, userInterface);
  }


  /**
   * Method for initiating the FridgeController.
   *
   * @param fridge        Fridge object
   * @param userInterface UserInterface object
   */
  private void init(Fridge fridge, UserInterface userInterface) {
    this.fridge = fridge;
    this.userInterface = userInterface;
  }


  /**
   * Method for displaying a simplified version of the fridge. Simplified meaning the quantities of
   * items of same name are summed up.
   */

  void displaySimplifiedFridge() {
    userInterface.displayItemsInTable(fridge.retrieveShortenListOfItems());
  }

  /**
   * Method for reducing the quantity of an item. If there is no items in the fridge, method does
   * nothing. Contains a null check for the returned item list. Gives feedback to the user if the
   * item is not in the fridge, or if the item is depleted.
   */
  void reduceAnItemInFridge() {

    userInterface.promptUserForWhatItemToRemove();
    Item itemToReduce = searchForItemByNameInFridge(); //Search for item.
    if (itemToReduce != null) { //null check
      int quantityToReduceWith = userInterface.promtForQuantityToReduceWIth(); // Promt for quantity.
      if (fridge.reduceQuantityOfItem(quantityToReduceWith,
          itemToReduce)) { //Returns if item is depleted
        userInterface.printItemRemovedCauseNoneLeft();
      }
    }
  }

  /**
   * Method for searching and editing an item in the fridge. Package private since use is only
   * within the package. Throws an exception making user turn to menu if item is not found. (with
   * feedback).
   */
  void searchAndEditItemMenu() {
    Item itemToEdit = searchForItemByNameInFridge(); //if null is returned/item not found, it returns to menu.
    if (itemToEdit != null) { //Double check.
      editItemSwitchCase(itemToEdit); //Edit item menu with item to edit.
    }
  }


  /**
   * Method for searching and displaying items that expire before the searched date. The user is
   * prompted for a date to check against. If no items are found, a message is printed to the user.
   * If items are found, the items are displayed in a table and the total cost of the items is
   * printed.
   */


  void searchDisplayItemsThatExpireBeforeGivenDate() {
    LocalDate dateToCheck = userInterface.promtForExpirationDate(); //Promt for date.
    Iterator<Item> expiredItems = fridge.iterateExpiredItems(
        dateToCheck); //Iterate over expired items by expiration given.
    if (expiredItems.hasNext()) { //If items are found.
      userInterface.displayItemsInTable(expiredItems);
      userInterface.displayCostOfItemsInFridge(fridge.calculateCostOfExpiredItems(dateToCheck));
    } else {
      userInterface.printNoItemsFound();
    }
  }


  /**
   * Fridge settings menu. Switch case with options. Prompts user for choice of sorting fridge by,
   * currency, or exit.
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
   * Prompts user for name, quantity, quantity unit. If user chooses to add cost, the user is
   * prompted for cost. If user chooses to add expiration date, the user is prompted for expiration
   * date.
   * <p>
   * non-mandatory item details are given default values from ApplicationSettings.
   */
  void createNewItem() {

    // Default values for non-mandatory item details.
    double cost = DEFAULT_COST;
    LocalDate expirationDate = DEFAULT_EXPIRATION_DATE;

    //Promt for mandatory item details.
    String name = userInterface.promtForItemName(); //Promt for name.
    int quantity = userInterface.promtForQuantity(); //Promt for quantity.
    String quantityUnit = userInterface.promtForQuantityUnit(); //Promt for quantity unit.

    if (userInterface.yesOrNoAddCostOfItem()) { //Optional cost.
      cost = userInterface.promtForCostOfItem();
    }
    if (userInterface.yesOrNoAddExpirationDate()) { //Optional expiration date.
      expirationDate = userInterface.promtForExpirationDate();
    }
    fridge.createItemAndAddToFridge(name, quantity, quantityUnit,
        //Uses fridge to create an add item
        cost, expirationDate);                                  //to fridge
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


  /**
   * Method for finding an item in the fridge by Item name. Displays all items in the fridge and
   * prompts user for item name. Uses a switch case to handle the different outcomes of the search.
   * If 0 items found, an exception is thrown and caught. (causing feedback and return to menu).
   *
   * @return the item found by the search.
   */
  private Item searchForItemByNameInFridge() {
    displayAllItemsInFridgeWithCost();
    Item itemFromFridge = null; //Item to return.
    try {
      String searchItem = userInterface.promtForItemName(); //Promt for item name.
      int itemsFound = fridge.specificItemInFridgeCount(searchItem); //Count items found.

      switch (itemsFound) { // use items found to determine next step.
        case 0 -> throw new IllegalArgumentException("No items found"); //No items, throw.
        case 1 -> itemFromFridge = retrieveUniqeFirstItem(searchItem); //One item, retrieve.
        default -> itemFromFridge = promtMultipleFound(searchItem); //Multiple items, promt further.
      }
    } catch (Exception allExceptions) { //Currently catches all, should use logger if improved.
      userInterface.printNoItemsFound(); //Feedback to user.
      throw new IllegalArgumentException("throw user back to main menu.");
    }
    return itemFromFridge; //Searched Item.
  }

  /**
   * Retrieves the first item found by search in the fridge.
   *
   * @param searchItem the item to search for.
   * @return the first item found.
   */
  private Item retrieveUniqeFirstItem(String searchItem) {
    return fridge.searchForItem(searchItem).next();
  }

  /**
   * Prompts user to choose which item to edit from a list of items. Displays all options found by
   * search term for user to choose. Retrieves the nth occurrence of item // The item corresponding
   * to the index user returns.
   *
   * @param searchItem the item that has been searched for.
   * @return the item to edit.
   */
  private Item promtMultipleFound(String searchItem) {
    return fridge.retrieveNthOccurrenceOfItem(
        promtForSpecificItemToEditFromSearch(searchItem), searchItem);
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

