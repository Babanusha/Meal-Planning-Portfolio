package controller;

import static settings.ApplicationSettings.DEFAULT_COST;
import static settings.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.INVALID_INPUT;
import static settings.ApplicationSettings.STRING_DECIMALFORMAT;
import static settings.ApplicationSettings.SWITCH_CASE_LIMIT;

import cookbook.CookBook;
import cookbook.Recipe;
import fridge.Fridge;
import fridge.Item;
import userInterface.Printer;
import userInterface.Reader;
import userInterface.userInterface;
import userInterface.Validator;
import java.text.DecimalFormat;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Controller {

  private CookBook cookBook;
  private Fridge fridge;
  private userInterface.userInterface userInterface;


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


    userInterface = new userInterface(reader, printer, validator, decimalFormat);

    cookBook = new CookBook();
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
          case 2 -> reduceAnItemInFridge();
          case 3 -> searchAndEditItemMenu();
          case 4 -> displayAllItemsInFridge();
          case 5 -> displayItemsThatExpireBeforeGivenDate();


          case 6 -> openCookBookMenu();

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

  private void reduceAnItemInFridge() {
    userInterface.promptUserForWhatItemToRemove();
    Item itemToReduce = searchForItemNameInFridge();
    if (itemToReduce != null) {
      int quantityToReduceWith = userInterface.promtForQuantityToReduceWIth();
      if (fridge.reduceQuantityOfItem(quantityToReduceWith, itemToReduce)){
        userInterface.printItemRemovedCauseNoneLeft(); //TODO: you cant take out more items then you have.
      }
    }
  }

  private void searchAndEditItemMenu() {
    Item itemToEdit = searchForItemNameInFridge();
    if (itemToEdit != null) {
      editSingularItem(itemToEdit);
    }
  }


  private void openCookBookMenu() {
    boolean exitTrigger = false;
    while (!exitTrigger) {
      try {
        userInterface.printCookBookMenu();
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {
          case 1 -> createNewRecipe();
          // case 2 -> searchAndEditRecipeMenu(); //Todo: Implement
          case 3 -> displayAllRecipes();

          case INT_EXIT -> exitTrigger = true;
          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }
      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
  }

  private void createNewRecipe() {
    String name = userInterface.promtForRecipeName();
    String description = userInterface.promtForRecipeDescription();
    List<Item> ingredientsNeeded = promtForRecipeIngredients();
    List<String> instructions = userInterface.promtForRecipeInstructions();
    cookBook.createRecipeAndAddToBook(name, description, ingredientsNeeded,instructions);
  }


  public List<Item> promtForRecipeIngredients() {
    userInterface.printEnterRecipeSpesifications();
    List<Item> listOfIngredients= new ArrayList<>();

    listOfIngredients.add(promtForRecipeIngredient()); //add first
    boolean addMoreIngredients = userInterface.promtAddMoreIngredients();

    while (addMoreIngredients) {
      listOfIngredients.add(promtForRecipeIngredient());
      addMoreIngredients = userInterface.promtAddMoreIngredients();
    }
    return listOfIngredients;
  }


/*
  private void searchAndEditRecipeMenu() {
    String recipeNameToFind = userInterface.promtForRecipeName();
    int numberOfRecipesFound = cookBook.specificRecipeInCookBookCount(recipeNameToFind);

    switch (numberOfRecipesFound) {
      case 0 -> userInterface.printNoItemsFound(); //Found none
      case 1 -> editSingularRecipe(recipeNameToFind);   //found 1
      default -> findSingularRecipeFromList(recipeNameToFind); //found multiple
    }
  }

  private void findSingularRecipeFromList(String recipeNameToFind) {
    editRecipeSwitchCase(cookBook.retrieveNthOccurenceOfRecipe(
        promtForSpecificIRecipeToEditFromSearch(recipeNameToFind)));
  }

  private String promtForSpecificIRecipeToEditFromSearch(String recipeNameToFind) {
    userInterface.printRecipeName(cookBook.retrieveRecipeFromCookBook());
    return userInterface.promtForRecipeNumberFromMultipleRecipes();
  }

  */


  private void editRecipeSwitchCase(Recipe recipe) {
    boolean editComplete = false;
    while (!editComplete) {

      try {
        int userChoice = userInterface.promtWhatPartToEditInRecipe();
        switch (userChoice) {
          case 1 -> editComplete = editRecipeName(recipe);
          case 2 -> editComplete = editRecipeDescription(recipe);
          case 3 -> editComplete = editRecipeIngredients(recipe);
          case 4 -> editComplete = editRecipeInstructions(recipe);
          case 5 -> editComplete = removeRecipeFromCookBook(recipe);

          case INT_EXIT -> editComplete = true; //EXIT

          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }

      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
  }

  private boolean removeRecipeFromCookBook(Recipe recipeToEdit) {
    boolean removeConfirmation = false;
    if (userInterface.promtForConfirmation()){
      removeConfirmation = cookBook.removeRecipe(recipeToEdit);

    }
    return removeConfirmation;
  }

  private boolean editRecipeInstructions(Recipe recipeToEdit) {
    userInterface.printRecipeInstructions(recipeToEdit.getRecipeInstructions());
    cookBook.editInstructions(userInterface.promtForRecipeInstructions(),recipeToEdit);
    return true; //Future improvements: checkEdit;
  }

  private boolean editRecipeIngredients(Recipe recipeToEdit) {
    userInterface.printRecipeIngredients(recipeToEdit.getRecipeIngredients());
    cookBook.editIngredient(promtForRecipeIngredients(),recipeToEdit);
    return true;
  }

  private boolean editRecipeDescription(Recipe recipeToEdit) {
    userInterface.printRecipeDescription(recipeToEdit.getRecipeDescription());
    cookBook.editDescription(userInterface.promtForRecipeDescription(),recipeToEdit);
    return true;
  }

  private boolean editRecipeName(Recipe recipeToEdit) {
    int recipeNumber = cookBook.getRecipeNumber(recipeToEdit);
    userInterface.printRecipeName(recipeNumber,recipeToEdit.getRecipeName());


    return false;
  }

  private void displayAllRecipes() {
    userInterface.displayRecipesInTable(cookBook.iterateOverCookBook());
  }


  ///////// BORDER BETWEEN FRIDGE AND COOKBOOK ////////// //TODO: Move to separate class.




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

  private Item promtForRecipeIngredient() {
    String itemName = userInterface.promtForItemName();
    int ingredientQuantity = userInterface.promtForQuantity();
    String quantityUnit = userInterface.promtForQuantityUnit();
    return cookBook.createIngredient(itemName, ingredientQuantity, quantityUnit);
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


  private Item searchForItemNameInFridge() {
    displayAllItemsInFridge();
    Item itemFromFridge = null;
    try {
      String searchItem = userInterface.promtForItemName();
      int itemsFound = fridge.specificItemInFridgeCount(searchItem);
      switch (itemsFound) {
        case 0 -> throw new IllegalArgumentException("No items found");
        case 1 -> itemFromFridge = retrieveUniqeFirstItem(searchItem);
        default -> itemFromFridge = promtMultipleFound(searchItem);
      }
    }
    catch(Exception allExceptions) { //Currently catches all, should use logger if improved.
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
   *
   *
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
    displayAllItemsInFridge();
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


}

