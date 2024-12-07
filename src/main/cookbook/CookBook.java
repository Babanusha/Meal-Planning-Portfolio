package cookbook;

import static settings.ApplicationSettings.STRING_GRAMS;
import static settings.ApplicationSettings.STRING_LITERS;
import static settings.ApplicationSettings.STRING_PIECES;

import fridge.Item;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class CookBook {

  ArrayList<Recipe> cookBookArrayList;

  /**
   * Constructor for cookBook Initiates cookBook with an empty list of recipes.
   */
  public CookBook() {
    init();
    test(); //TODO: remove
  }

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


  private Recipe createRecipe(String recipeName, String recipeDescription,
      List<Item> recipeIngredients, List<String> recipeInstructions) {
    return new Recipe(recipeName, recipeDescription, recipeIngredients, recipeInstructions);
  }


  public void createRecipeAndAddToBook(String recipeName, String recipeDescription,
      List<Item> recipeIngredients, List<String> recipeInstructions) {

    addToRecipeBook(
        createRecipe(recipeName, recipeDescription, recipeIngredients, recipeInstructions));
  }

  public Iterator<Recipe> retrieveRecipeFromCookBook() {
    return cookBookArrayList.iterator();
  }


  private Iterator<Recipe> findRecipeByName(String recipeName) {
    return cookBookArrayList.stream().filter(recipe ->
        recipe.getRecipeName().equalsIgnoreCase(recipeName)).iterator();
  }

  public Iterator<Recipe> iterateOverCookBook() {
    return cookBookArrayList.iterator();
  }

  public int specificRecipeInCookBookCount(String searchRecipeByName) {
    return (int) cookBookArrayList.stream().filter(recipe ->
        recipe.getRecipeName().equalsIgnoreCase(searchRecipeByName)).count();
  }

  public boolean removeRecipe(Recipe recipeToEdit) {
    return cookBookArrayList.remove(recipeToEdit);
  }

  public void editInstructions(List<String> stringsList, Recipe recipeToEdit) {
    recipeToEdit.setRecipeInstructions(stringsList);
  }

  public void editIngredient(List<Item> itemsList, Recipe recipeToEdit) {
    recipeToEdit.setRecipeIngredients(itemsList);
  }

  public void editDescription(String newDescription, Recipe recipeToEdit) {
    recipeToEdit.setRecipeDescription(newDescription);
  }

  public void editName(String newName, Recipe recipeToEdit) {
    recipeToEdit.setRecipeName(newName);
  }

  public int getRecipeNumber(Recipe recipeToFindSpecifier) {
    return findRecipeNumberInList(recipeToFindSpecifier);
  }

  /**
   * If recipes are duplicated, this method will return the first instance of the recipe in the
   * list.
   *
   * @param recipeToEdit recipe to find in list
   * @return index of recipe in list
   */
  private int findRecipeNumberInList(Recipe recipeToEdit) {
    return IntStream.range(0, cookBookArrayList.size())
        .filter(i -> cookBookArrayList.get(i).equals(recipeToEdit))
        .findFirst()
        .orElse(-1);

  }

  public Recipe retrieveNthOccurenceOfRecipe(int nthOccurrenceToRetrieve) {
    return cookBookArrayList.get(nthOccurrenceToRetrieve);
  }

  public Item createIngredient(String name, int quantity, String unit) {
    return new Item(name, quantity, unit);
  }

// Recipe som har en liste av ingredienser
  // Ingredienser har navn, mengde og enhet
  // Kjøeskap har navn, mengde og enhet
  //Ta en recipe, sjekk om den har første ingrediens i kjøleskapet
  //Hvis ja, sjekk andre ingredienser
  //Hvis Nei, gå til neste recipe

  public Iterator<Recipe> isRecipeInFridge(Iterator<Item> fridgeItemsIterated) {
    List<Item> fridgeItemsList = iteratorToList(fridgeItemsIterated);
    List<Recipe> list = cookBookArrayList.stream().filter(recipe ->
        recipeIsInFridge(recipe, fridgeItemsList)).toList();
    return list.iterator();
  }

  public boolean recipeIsInFridge(Recipe recipeToCheck, List<Item> fridgeItems) {
    Iterator<Item> recipeIngredients = recipeToCheck.getRecipeIngredients();
    List<Item> recipeIngredientsList = iteratorToList(recipeIngredients);

    return recipeIngredientsList.stream().allMatch(recipeIngredient ->
        fridgeItems.stream().anyMatch(fridgeItem ->
            recipeIngredient.getName().equalsIgnoreCase(fridgeItem.getName()) &&
                recipeIngredient.getQuantity() <= fridgeItem.getQuantity()));
  }

  private List<Item> iteratorToList(Iterator<Item> recipeIngredients) {
    List<Item> list = new ArrayList<>();
    while (recipeIngredients.hasNext()) {
      list.add(recipeIngredients.next());
    }
    return list;
  }


  public boolean notEmpty() {
    return !cookBookArrayList.isEmpty();
  }
}
