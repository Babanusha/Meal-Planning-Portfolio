package cookbook;

import fridge.Item;
import java.util.Iterator;
import java.util.List;

public class Recipe {

  private String recipeName;
  private String recipeDescription;
  private List<Item> recipeIngredients;
  private List<String> recipeInstructions;


  public Recipe(String recipeName, String recipeDescription, List<Item> recipeIngredients,
      List<String> recipeInstructions) {
    setRecipeName(recipeName);
    setRecipeDescription(recipeDescription);
    setRecipeIngredients(recipeIngredients);
    setRecipeInstructions(recipeInstructions);
  }

  public String getRecipeName() {
    return recipeName;
  }

  void setRecipeName(String recipeName) {
    this.recipeName = recipeName;
  }

  public String getRecipeDescription() {
    return recipeDescription;
  }

  void setRecipeDescription(String recipeDescription) {
    this.recipeDescription = recipeDescription;
  }

  public Iterator<Item> getRecipeIngredients() {
    return recipeIngredients.iterator();
  }

  void setRecipeIngredients(List<Item> recipeIngredients) {
    this.recipeIngredients = recipeIngredients;
  }

  public Iterator<String> getRecipeInstructions() {
    return recipeInstructions.iterator();
  }

  void setRecipeInstructions(List<String> recipeInstructions) {
    this.recipeInstructions = recipeInstructions;
  }
}
