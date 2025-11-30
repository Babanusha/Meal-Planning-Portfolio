package cookbook;

import fridge.Item;
import java.util.Iterator;
import java.util.List;

/**
 * Class for the Recipe. Contains a recipe name, description, ingredients and instructions. The
 * class can get and set the recipe name, description, ingredients and instructions.
 */
public class Recipe {

  private String recipeName; //name of the recipe
  private String recipeDescription; //description of the recipe
  private List<Item> recipeIngredients; //ingredients of the recipe
  private List<String> recipeInstructions; //instructions of the recipe


  /**
   * Constructor for the Recipe class. Uses setters to set the recipe name, description, ingredients
   * and instructions.
   *
   * @param recipeName         name of the recipe
   * @param recipeDescription  description of the recipe
   * @param recipeIngredients  ingredients of the recipe
   * @param recipeInstructions instructions of the recipe
   */
  public Recipe(String recipeName, String recipeDescription, List<Item> recipeIngredients,
      List<String> recipeInstructions) {
    setRecipeName(recipeName);
    setRecipeDescription(recipeDescription);
    setRecipeIngredients(recipeIngredients);
    setRecipeInstructions(recipeInstructions);
  }

  /**
   * Getter for the recipe name.
   *
   * @return the recipe name
   */
  public String getRecipeName() {
    return recipeName;
  }

  /**
   * Setter for the recipe name.
   *
   * @param recipeName the name of the recipe
   */
  private void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  /**
   * Getter for the recipe description.
   *
   * @return the recipe description
   */
  public String getRecipeDescription() {
    return recipeDescription;
  }

  /**
   * Setter for the recipe description.
   *
   * @param recipeDescription the description of the recipe
   */
  private void setRecipeDescription(String recipeDescription) {
    this.recipeDescription = recipeDescription;
  }

  /**
   * Getter for the recipe ingredients.
   *
   * @return the recipe ingredients iterated.
   */
  public Iterator<Item> getRecipeIngredients() {
    return recipeIngredients.iterator();
  }

  /**
   * Setter for the recipe ingredients.
   *
   * @param recipeIngredients the ingredients of the recipe
   */
  private void setRecipeIngredients(List<Item> recipeIngredients) {
    this.recipeIngredients = recipeIngredients;
  }

  /**
   * Getter for the recipe instructions.
   *
   * @return the recipe instructions iterated.
   */
  public Iterator<String> getRecipeInstructions() {
    return recipeInstructions.iterator();
  }

  /**
   * Setter for the recipe instructions.
   *
   * @param recipeInstructions the instructions of the recipe
   */
  private void setRecipeInstructions(List<String> recipeInstructions) {
    this.recipeInstructions = recipeInstructions;
  }
}
