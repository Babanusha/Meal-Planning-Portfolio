package cookbook;

import static settings.ApplicationSettings.STRING_GRAMS;
import static settings.ApplicationSettings.STRING_LITERS;
import static settings.ApplicationSettings.STRING_PIECES;

import fridge.Item;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class for the cookBook. Contains a list of recipes.
 * The cookBook can add recipes, iterate over recipes, retrieve a specific recipe, check if a recipe
 * is in the fridge, and check if the cookBook is empty.
 */
public class CookBook {

  ArrayList<Recipe> cookBookArrayList; // List of recipes in cookBook

  /**
   * Constructor for cookBook Initiates cookBook with an empty list of recipes.
   */
  public CookBook() {
    init();
    //test(); //TESTKLASSE FOR Å LEGGE TIL RECIPE I COOKBOOK
  }

  /**
   * Test method for adding recipes to cookBook.
   * Used for testing purposes.
   * Left in code for censoring purposes.
   */
  private void test() {

    ///*******
    Item lettuce = new Item("l", 1, STRING_PIECES);
    Item tomato = new Item("t", 1, STRING_PIECES);
    Item cucumber = new Item("c", 1, STRING_PIECES);
    List<Item> saladIngredients = new ArrayList<>();
    saladIngredients.add(lettuce);
    saladIngredients.add(tomato);
    saladIngredients.add(cucumber);

    List<String> saladInstructions = new ArrayList<>();
    saladInstructions.add("Cut lettuce");
    saladInstructions.add("Cut tomato");
    saladInstructions.add("Cut cucumber");

    createRecipeAndAddToBook("recipeWeHave", "Salad with lettuce", saladIngredients,
        saladInstructions);

    //******
    Item lettuce3 = new Item("Lettuce", 3, STRING_PIECES);
    Item tomatoSauce = new Item("TomatoSauce", 1, STRING_LITERS);
    List<Item> pastaIngredients = new ArrayList<>();
    pastaIngredients.add(lettuce3);
    pastaIngredients.add(tomatoSauce);

    List<String> pastaInstructions = new ArrayList<>();
    pastaInstructions.add("Boil pasta");
    pastaInstructions.add("Add tomato sauce");
    createRecipeAndAddToBook("PastaDontHave", "Pasta with tomato sauce", pastaIngredients,
        pastaInstructions);

    ////****
    Item pizzaDough = new Item("Pizza", 1, STRING_PIECES);
    Item pizzaSauce = new Item("Tomato", 1, STRING_LITERS);
    Item cheese = new Item("Cheese", 40, STRING_GRAMS);

    List<Item> pizzaIngredients = new ArrayList<>();

    pizzaIngredients.add(pizzaDough);
    pizzaIngredients.add(pizzaSauce);
    pizzaIngredients.add(cheese);

    List<String> pizzaInstructions = new ArrayList<>();
    pizzaInstructions.add("Roll out pizza dough");
    pizzaInstructions.add("Add tomato sauce");
    pizzaInstructions.add("Add cheese");
    createRecipeAndAddToBook("PizzaWeHave", "Pizza with cheese", pizzaIngredients,
        pizzaInstructions);


  }

  /**
   * Initiates the cookBook with an empty list of recipes.
   */
  private void init() {
    cookBookArrayList = new ArrayList<>();
  }


  /**
   * Adds a recipe to the cookBook.
   *
   * @param recipeToAdd to add to cookBook
   */

  private void addToRecipeBook(Recipe recipeToAdd) {
    cookBookArrayList.add(recipeToAdd);
  }

  /**
   * Creates a recipe with the given parameters using Recipe constructor.
   * @param recipeName name of the recipe
   * @param recipeDescription description of the recipe
   * @param recipeIngredients ingredients of the recipe
   * @param recipeInstructions instructions of the recipe
   * @return a recipe with the given parameters.
   */
  private Recipe createRecipe(String recipeName, String recipeDescription,
      List<Item> recipeIngredients, List<String> recipeInstructions) {
    return new Recipe(recipeName, recipeDescription, recipeIngredients, recipeInstructions);
  }


  /**
   * Creates a recipe with the given parameters and adds it to the cookBook.
   * @param recipeName name of the recipe
   * @param recipeDescription description of the recipe
   * @param recipeIngredients ingredients of the recipe
   * @param recipeInstructions  instructions of the recipe
   */
  public void createRecipeAndAddToBook(String recipeName, String recipeDescription,
      List<Item> recipeIngredients, List<String> recipeInstructions) {

    addToRecipeBook(
        createRecipe(recipeName, recipeDescription, recipeIngredients, recipeInstructions));
  }

  /**
   * Iterates over the cookBook and returns an iterator of recipes.
   * @return iterator of recipes in cookBook
   */

  public Iterator<Recipe> iterateOverCookBook() {
    return cookBookArrayList.iterator();
  }

  /**
   * Retrieves the nth occurrence of a recipe in the cookBook.
   * @param nthOccurrenceToRetrieve the occurrence to retrieve
   * @return  the nth occurrence of a recipe in the cookBook
   */
  public Recipe retrieveNthOccurenceOfRecipe(int nthOccurrenceToRetrieve) {
    return cookBookArrayList.get(nthOccurrenceToRetrieve);
  }

  /**
   * Creates an ingredient with the given parameters.
   * Ingredient is created as an Item object.
   * @param name name of the ingredient
   * @param quantity quantity of the ingredient
   * @param unit unit of the ingredient
   * @return an ingredient with the given parameters
   */
  public Item createIngredient(String name, int quantity, String unit) {
    return new Item(name, quantity, unit);
  }

  /**
   * Checks if there is any recipe in the CookBook List that currently can be made, from the
   * retrieved fridge items.
   * @param fridgeItemsIterated fridge items to check against
   * @return iterator of recipes that can be made
   */

  public Iterator<Recipe> isAnyRecipeInFridge(Iterator<Item> fridgeItemsIterated) {
    List<Item> fridgeItemsList = iteratorToList(fridgeItemsIterated);
    List<Recipe> list = cookBookArrayList.stream().filter(recipe ->
        recipeIsInFridge(recipe, fridgeItemsList)).toList();
    return list.iterator();
  }

  /**
   * Checks if a recipe is in the fridge.
   * Does this by cross checking every ingredient in the recipe with the fridge items.
   *
   *
   * @param recipeToCheck recipe to check
   * @param fridgeItems fridge items to check against
   * @return true if recipe is in fridge, false otherwise
   */
  public boolean recipeIsInFridge(Recipe recipeToCheck, List<Item> fridgeItems) {
    List<Item> recipeIngredientsList = iteratorToList(recipeToCheck.getRecipeIngredients());

    return recipeIngredientsList.stream().allMatch(recipeIngredient -> //All recipe ingredients must match
        fridgeItems.stream().anyMatch(fridgeItem -> //checks all ingredients for a match
            recipeIngredient.getName().equalsIgnoreCase(fridgeItem.getName()) && //name must match
                recipeIngredient.getQuantity() <= fridgeItem.getQuantity())); //quantity must be enough
  }

  /**
   * Converts an iterator to a list.
   * @param recipeIngredients iterator to convert
   * @return list of items
   */

  private List<Item> iteratorToList(Iterator<Item> recipeIngredients) {
    List<Item> list = new ArrayList<>();
    while (recipeIngredients.hasNext()) {
      list.add(recipeIngredients.next());
    }
    return list;
  }

  /**
   * Checks if the cookBook is empty.
   * @return true if cookBook is not empty, false otherwise
   */

  public boolean notEmpty() {
    return !cookBookArrayList.isEmpty();
  }

  /**
   * Gets the recipe names in the cookBook.
   * @return iterator of recipe names
   */
  public Iterator<String> getRecipeNames() {
    return cookBookArrayList.stream().map(Recipe::getRecipeName).iterator(); // maps all recipe names to iterator stream.
  }

  /**
   * Checks if a specific recipe can be made with the given fridge content.
   * @param recipeNumberInList the number of the recipe in the cookBook.
   * @param currentFridge fridge content to check against
   * @return true if recipe can be made, false otherwise
   */
  public boolean canSpecificRecipeBeMade(int recipeNumberInList, Iterator<Item> currentFridge) {
    Recipe recipeToCheck = retrieveNthOccurenceOfRecipe(recipeNumberInList);
    return recipeIsInFridge(recipeToCheck, iteratorToList(currentFridge));
  }
}
