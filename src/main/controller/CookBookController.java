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

public class CookBookController {

  private CookBook cookBook;
  private UserInterface userInterface;


  CookBookController(CookBook cookBook, UserInterface userInterface) {
    init(cookBook, userInterface);

  }

  private void init(CookBook cookBook, UserInterface userInterface) {
    this.cookBook = cookBook;
    this.userInterface = userInterface;
  }

  void openCookBookMenu(Iterator<Item> currentFridge) {
    boolean exitTrigger = false;
    while (!exitTrigger) {
      try {
        userInterface.printCookBookMenu();
        switch (userInterface.intHandler(SWITCH_CASE_LIMIT)) {
          case 1 -> createNewRecipe();
          // case 2 -> searchAndEditRecipeMenu(); //Todo: Implement
          case 3 -> displayAllRecipes();
          case 4 -> whatRecipesCanBeMade(currentFridge);

          case INT_EXIT -> exitTrigger = true;
          default -> throw new IllegalArgumentException(INVALID_INPUT);
        }
      } catch (Exception allExceptions) {
        userInterface.printIntErrorStandardResponse();
      }
    }
  }



  private void whatRecipesCanBeMade(Iterator<Item> iteratedFridge) {
    if (iteratedFridge.hasNext() && cookBook.notEmpty()) {
      userInterface.displayRecipesInTable(
          cookBook.isRecipeInFridge(iteratedFridge));
    } else {
      userInterface.printNoItemsFound();
    }
  }

  private void createNewRecipe() {
    String name = userInterface.promtForRecipeName();
    String description = userInterface.promtForRecipeDescription();
    List<Item> ingredientsNeeded = promtForRecipeIngredients();
    List<String> instructions = userInterface.promtForRecipeInstructions();
    cookBook.createRecipeAndAddToBook(name, description, ingredientsNeeded, instructions);
  }


  public List<Item> promtForRecipeIngredients() {
    userInterface.printEnterRecipeSpesifications();
    List<Item> listOfIngredients = new ArrayList<>();

    listOfIngredients.add(promtForRecipeIngredient()); //add first
    boolean addMoreIngredients = userInterface.promtAddMoreIngredients();

    while (addMoreIngredients) {
      listOfIngredients.add(promtForRecipeIngredient());
      addMoreIngredients = userInterface.promtAddMoreIngredients();
    }
    return listOfIngredients;
  }







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
