package controller;

import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.INVALID_INPUT;
import static settings.ApplicationSettings.SWITCH_CASE_LIMIT;

import cookbook.CookBook;
import fridge.Item;
import userinterface.UserInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Controller class for the CookBook. Handles the interaction between the CookBook and the UserInterface.
 */
public class CookBookController {

  private CookBook cookBook; // CookBook object
  private UserInterface userInterface; // UserInterface object


  /**
   * Constructor for the CookBookController class.
   * Contains independency injection of CookBook and UserInterface.
   * @param cookBook CookBook object
   * @param userInterface UserInterface object
   */
  CookBookController(CookBook cookBook, UserInterface userInterface) {
    init(cookBook, userInterface);

  }

  /**
   *  Method for initiating the CookBookController.
   * @param cookBook CookBook object
   * @param userInterface UserInterface object
   */
  private void init(CookBook cookBook, UserInterface userInterface) {
    this.cookBook = cookBook;
    this.userInterface = userInterface;
  }

  /**
   * Method for opening the CookBook menu.
   * @param currentFridge Iterator for the current fridge.
   *                      Used to check what recipes can be made with the current fridge content.
   */
  void openCookBookMenu(Iterator<Item> currentFridge) {
    boolean exitTrigger = false;
    while (!exitTrigger) {
      try {
        userInterface.printCookBookMenu();
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {
          case 1 -> createNewRecipe();
         // case 5 -> displayAllRecipesNameOnly();
          // case 6 -> seeRecipeByName();
          case 2 -> displayAllRecipesFullDetail();
          case 3 -> checkIfSpecificRecipeCanBeMade(currentFridge);
          case 4 -> seeAllRecipesThatCanBeMade(currentFridge);

          case INT_EXIT -> exitTrigger = true;
          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }
      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
  }

  /**
   * Method for checking if a specific recipe can be made with the current fridge content.
   * User is prompted with list of recipes (names) and asked to choose a recipe.
   * The method then checks if the recipe can be made with the current fridge content.
   * The user is informed.
   * NB! User prompt is taken in, and subtracted by 1 to match the index of the recipe in the list.
   * @param currentFridge Iterator for the current fridge, iterated in when entering cookBook.
   */
  private void checkIfSpecificRecipeCanBeMade(Iterator<Item> currentFridge) {
    if (cookBook.notEmpty() && currentFridge.hasNext()) {
      userInterface.displayRecipeByNameAndNumberInTable(cookBook.getRecipeNames());
      boolean canBeMade = cookBook.canSpecificRecipeBeMade(
          (userInterface.promtForRecipeNumber() - 1)  //Subtract 1 to match index
          , currentFridge); //Finds first recipe that fits.
      userInterface.printRecipeCanBeMadeAnswer(canBeMade);
    } else {
      userInterface.printNoItemsFound();
    }
  }



  /**
   * Method for checking what recipes can be made with the current fridge content.
   * @param iteratedFridge Iterated fridge content, to cross check.
   */
  private void seeAllRecipesThatCanBeMade(Iterator<Item> iteratedFridge) {
    if (iteratedFridge.hasNext() && cookBook.notEmpty()) {
      userInterface.displayRecipesInTable(
          cookBook.isAnyRecipeInFridge(iteratedFridge));
    } else {
      userInterface.printNoItemsFound();
    }
  }

  /**
   * Method for creating a new recipe.
   * Gathers Recipe Information into simple parts and uses CookBook to create a new Recipe.
   */

  private void createNewRecipe() {
    String name = userInterface.promtForRecipeName();
    String description = userInterface.promtForRecipeDescription();
    List<Item> ingredientsNeeded = promtForRecipeIngredients();
    List<String> instructions = writeIteratorToList(userInterface.promtForRecipeInstructions());
    cookBook.createRecipeAndAddToBook(name, description, ingredientsNeeded, instructions);
  }
  private List<String> writeIteratorToList(Iterator<String> stringListIterator) {
    List<String> stringList = new ArrayList<>();
    stringListIterator.forEachRemaining(stringList::add);
    return stringList;
  }

  /**
   * Method for prompting the user for recipe ingredients.
   * @return List of Items, representing the ingredients needed for the recipe.
   */

  private List<Item> promtForRecipeIngredients() {
    userInterface.printEnterRecipeSpecifications(); //Prints out the prompt
    List<Item> listOfIngredients = new ArrayList<>(); //List to store ingredients

    listOfIngredients.add(promtForRecipeIngredient()); //add first
    boolean addMoreIngredients = userInterface.promtAddMoreIngredients();

    while (addMoreIngredients) { //Optional to add more than one.
      listOfIngredients.add(promtForRecipeIngredient());
      addMoreIngredients = userInterface.promtAddMoreIngredients();
    }
    return listOfIngredients; //Return list of ingredients
  }

  /**
   * Method for displaying all recipes in the cookBook.
   * Uses CookBook to iterate over the cookBook and display the recipes in a table.
   */
  private void displayAllRecipesFullDetail() {
    userInterface.displayRecipesInTable(cookBook.iterateOverCookBook());
  }

  /**
   * Method for prompting the user for a recipe ingredient.
   * Builds an Item object with cookBook.createIngredient() and returns it.
   * @see CookBook#createIngredient(String, int, String)
   * @return Item object representing the ingredient.
   */

  private Item promtForRecipeIngredient() {
    String itemName = userInterface.promtForItemName();
    int ingredientQuantity = userInterface.promtForQuantity();
    String quantityUnit = userInterface.promtForQuantityUnit();
    return cookBook.createIngredient(itemName, ingredientQuantity, quantityUnit);
  }
}
