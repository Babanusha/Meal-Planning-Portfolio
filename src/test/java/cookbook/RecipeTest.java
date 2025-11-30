package cookbook;

import static settings.ApplicationSettings.STRING_PIECES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import fridge.Item;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeTest {

  Recipe recipeTester;

  @BeforeEach
  void setUp() {

    Item lettuce = new Item("lettuce", 1, STRING_PIECES);
    Item tomato = new Item("tomato", 1, STRING_PIECES);
    List<Item> saladIngredients = new ArrayList<>();
    saladIngredients.add(lettuce);
    saladIngredients.add(tomato);
    List<String> saladInstructions = new ArrayList<>();
    saladInstructions.add("Cut lettuce");
    saladInstructions.add("Cut tomato");
    saladInstructions.add("Cut cucumber");

    recipeTester = new Recipe("recipeTest", "Salad with lettuce", saladIngredients,
        saladInstructions);


  }

  @AfterEach
  void tearDown() {
    recipeTester = null;
  }

  @Test
  void getRecipeName() {
    assertEquals("recipeTest", recipeTester.getRecipeName(), "Positive getRecipeName() failed");
    assertNotEquals("hopefullyGoodGrades", recipeTester.getRecipeName(),
        "Negative getRecipeName() failed");
  }

  @Test
  void getRecipeDescription() {
    assertEquals("Salad with lettuce", recipeTester.getRecipeDescription(),
        "Positive getRecipeDescription() failed");
    assertNotEquals("hopefullyGoodGrades", recipeTester.getRecipeDescription(),
        "Negative getRecipeDescription() failed");
  }


  @Test
  void getRecipeIngredients() {
    assertEquals("lettuce", recipeTester.getRecipeIngredients().next().getName(),
        "Positive getRecipeIngredients() failed");
    assertNotEquals("tomato", recipeTester.getRecipeIngredients().next().getName(),
        "Negative getRecipeIngredients() failed");
  }

  @Test
  void getRecipeInstructions() {
    assertEquals("Cut lettuce", recipeTester.getRecipeInstructions().next(),
        "Positive getRecipeInstructions() failed");
    assertNotEquals("Cut tomato", recipeTester.getRecipeInstructions().next(),
        "Negative getRecipeInstructions() failed");
  }


}