package cookbook;

import static settings.ApplicationSettings.STRING_GRAMS;
import static settings.ApplicationSettings.STRING_LITERS;
import static settings.ApplicationSettings.STRING_PIECES;
import static org.junit.jupiter.api.Assertions.*;

import fridge.Fridge;
import fridge.Item;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CookBookTest {
  CookBook cookBookTester = new CookBook();
  Recipe pastaWeDontHaveTestRecipe;
  Fridge testFridge = new Fridge();

  @BeforeEach
  void setUp() {//first item cant be made, second can.
    Item lettuce3 = new Item("Lettuce", 3, STRING_PIECES);
    Item tomatoSauce = new Item("TomatoSauce", 1, STRING_LITERS);
    List<Item> pastaIngredients = new ArrayList<>();
    pastaIngredients.add(lettuce3);
    pastaIngredients.add(tomatoSauce);

    List<String> pastaInstructions = new ArrayList<>();
    pastaInstructions.add("Boil pasta");
    pastaInstructions.add("Add tomato sauce");
    cookBookTester.createRecipeAndAddToBook("PastaDontHave", "Pasta with tomato sauce", pastaIngredients,
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
    cookBookTester.createRecipeAndAddToBook("PizzaWeHave", "Pizza with cheese", pizzaIngredients,
        pizzaInstructions);

     pastaWeDontHaveTestRecipe = new Recipe("PastaDontHave", "Pasta with tomato sauce", pastaIngredients,
        pastaInstructions);

    testFridge.createItemAndAddToFridge("Pizza", 200, STRING_PIECES, 20.0, null);
    testFridge.createItemAndAddToFridge("Tomato", 100, STRING_LITERS, 20.0, null);
    testFridge.createItemAndAddToFridge("Cheese", 40, STRING_GRAMS, 20.0, null);
  }

  @AfterEach
  void tearDown() {
    cookBookTester = null;
    pastaWeDontHaveTestRecipe = null;
  }


  @Test
  void isAnyRecipeInFridge() { // uses recipeIsInFridge() method

   Iterator<Recipe> iteratorOfRecipiesWeHaveTest = cookBookTester.isAnyRecipeInFridge(testFridge.iterateOverFridge());
    ArrayList<Recipe> recipesWeHaveTest = new ArrayList<>();
    while (iteratorOfRecipiesWeHaveTest.hasNext()) {
      recipesWeHaveTest.add(iteratorOfRecipiesWeHaveTest.next());
    }
    assertEquals(1, recipesWeHaveTest.size(), "Size of recipesWeHaveTest is 1");
    assertNotEquals(0, recipesWeHaveTest.size(), "Size of recipesWeHaveTest is not 0");
    assertEquals("PizzaWeHave", recipesWeHaveTest.get(0).getRecipeName(), "Recipe is not PizzaWeHave");
  }


  @Test
  void getRecipeNames() {
    Iterator<String> recipeNames = cookBookTester.getRecipeNames();
    ArrayList<String> names = new ArrayList<>();
    while (recipeNames.hasNext()) {
      names.add(recipeNames.next());
    }
    assertEquals(2, names.size(), "Size of names is 2");
    assertNotEquals(1, names.size(), "Size of names is not 1");
  }

  @Test
  void canSpecificRecipeBeMade() {
    boolean canBeMade = cookBookTester.canSpecificRecipeBeMade(1, testFridge.iterateOverFridge());
    assertTrue(canBeMade, "Recipe can be made");
    canBeMade = cookBookTester.canSpecificRecipeBeMade(0, testFridge.iterateOverFridge());
    assertFalse(canBeMade, "Recipe can not be made");
  }
}