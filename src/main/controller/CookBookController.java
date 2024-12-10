package controller;

import static settings.ApplicationSettings.INT_EXIT;
import static settings.ApplicationSettings.INVALID_INPUT;
import static settings.ApplicationSettings.SWITCH_CASE_LIMIT;

import cookbook.CookBook;
import cookbook.Recipe;
import fridge.Item;
import userInterface.UserInterface;
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
          // case 2 -> searchAndEditRecipeMenu(); //Todo: Implement
          case 3 -> displayAllRecipes();
          case 4 -> checkIfSpecificRecipeCanBeMade(currentFridge);
          case 5 -> seeAllRecipesThatCanBeMade(currentFridge);

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
   * NB! user prompt is taken in, and subtracted by 1 to match the index of the recipe in the list.
   * @param currentFridge Iterator for the current fridge, iterated in when entering cookBook.
   */
  private void checkIfSpecificRecipeCanBeMade(Iterator<Item> currentFridge) {
    userInterface.displayRecipeByNameAndNumberInTable(cookBook.getRecipeNames());
    boolean canBeMade = cookBook.canSpecificRecipeBeMade((userInterface.promtForRecipeNumber() -1)  //Subtract 1 to match index
        , currentFridge); //Finds first recipe that fits.
    userInterface.printRecipeCanBeMadeAnswer(canBeMade);
  }



  /**
   * Method for checking what recipes can be made with the current fridge content.
   * @param iteratedFridge Iterated fridge content, to cross check.
   */
  private void seeAllRecipesThatCanBeMade(Iterator<Item> iteratedFridge) {
    if (iteratedFridge.hasNext() && cookBook.notEmpty()) {
      userInterface.displayRecipesInTable(
          cookBook.isRecipeInFridge(iteratedFridge));
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
    List<String> instructions = userInterface.promtForRecipeInstructions();
    cookBook.createRecipeAndAddToBook(name, description, ingredientsNeeded, instructions);
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







//TODO: Implement edit recipe or remove recipeEDIT METHOD from CookBookController
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
    if (userInterface.promtForConfirmation()) {
      removeConfirmation = cookBook.removeRecipe(recipeToEdit);

    }
    return removeConfirmation;
  }

  private boolean editRecipeInstructions(Recipe recipeToEdit) {
    userInterface.printRecipeInstructions(recipeToEdit.getRecipeInstructions());
    cookBook.editInstructions(userInterface.promtForRecipeInstructions(), recipeToEdit);
    return true;
  }

  private boolean editRecipeIngredients(Recipe recipeToEdit) {
    userInterface.printRecipeIngredients(recipeToEdit.getRecipeIngredients());
    cookBook.editIngredient(promtForRecipeIngredients(), recipeToEdit);
    return true;
  }

  private boolean editRecipeDescription(Recipe recipeToEdit) {
    userInterface.printRecipeDescription(recipeToEdit.getRecipeDescription());
    cookBook.editDescription(userInterface.promtForRecipeDescription(), recipeToEdit);
    return true;
  }

  private boolean editRecipeName(Recipe recipeToEdit) {
    int recipeNumber = cookBook.getRecipeNumber(recipeToEdit);
    userInterface.printRecipeName(recipeNumber, recipeToEdit.getRecipeName());

    return false;
  }

  private void displayAllRecipes() {
    userInterface.displayRecipesInTable(cookBook.iterateOverCookBook());
  }


  private Item promtForRecipeIngredient() {
    String itemName = userInterface.promtForItemName();
    int ingredientQuantity = userInterface.promtForQuantity();
    String quantityUnit = userInterface.promtForQuantityUnit();
    return cookBook.createIngredient(itemName, ingredientQuantity, quantityUnit);
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


}
