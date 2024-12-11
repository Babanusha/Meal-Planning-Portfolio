package fridge;

import static settings.ApplicationSettings.STRING_LITERS;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FridgeTest {

  Fridge fridgeTester = new Fridge();
  double cost = 0.0;
  @BeforeEach
  void setUp() {


    fridgeTester.createItemAndAddToFridge("Milk", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(2));
    fridgeTester.createItemAndAddToFridge("Milk", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(7));
    fridgeTester.createItemAndAddToFridge("Milk", 3, STRING_LITERS, 20.0, LocalDate.now().plusDays(3));
    fridgeTester.createItemAndAddToFridge("Milk", 4, STRING_LITERS, 20.0, LocalDate.now().plusDays(7));
    fridgeTester.createItemAndAddToFridge("Flour", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(5));
    fridgeTester.createItemAndAddToFridge("FourQuantityIGot", 4, STRING_LITERS, 20.0, LocalDate.now().plusDays(7));
    fridgeTester.createItemAndAddToFridge("SevenQuantityIGot", 7, STRING_LITERS, 20.0, LocalDate.now().plusDays(100));
    cost = 20.0* 24;  // cost times total quantity

  }
  @AfterEach
  void tearDown() {
    fridgeTester = null;
    cost = 0.0;
  }


  @Test
  void createItemAndAddToFridge() {

  // method done in setup.

    assertEquals(3, fridgeTester.retrieveNthOccurrenceOfItem(3,"milk").getQuantity(), "Not 3");
    assertNotEquals(2, fridgeTester.retrieveNthOccurrenceOfItem(3,"milk").getQuantity(), "is 3");
    assertNotEquals(4, fridgeTester.retrieveNthOccurrenceOfItem(3,"milk").getQuantity(), "is 3");
  }

  @Test
  void iterateOverFridge() {
    Iterator<Item> iteratedTest= fridgeTester.iterateOverFridge();
    ArrayList<Item> itemsTest = new ArrayList<>();
    while (iteratedTest.hasNext()) {
     itemsTest.add(iteratedTest.next());
    }
    assertEquals(7, itemsTest.size(), "Size of itemsTest is 7");
    assertNotEquals(6, itemsTest.size(), "Size of itemsTest is not 6");
  }


  @Test
  void searchForItem() {
    assertEquals(7, fridgeTester.searchForItem("SevenQuantityIGot").next().getQuantity(), "incorrect item found");
    assertNotEquals(4, fridgeTester.searchForItem("SevenQuantityIGot").next().getQuantity(), "quantity Matches");
  }

  @Test
  void retrieveNthOccurrenceOfItem() {
    assertEquals(3, fridgeTester.retrieveNthOccurrenceOfItem(3,"milk").getQuantity(), "Not 3"); //3rd occurrence of milk
    assertNotEquals(2, fridgeTester.retrieveNthOccurrenceOfItem(3,"milk").getQuantity(), "is 3");
    assertNotEquals(5, fridgeTester.retrieveNthOccurrenceOfItem(3,"milk").getQuantity(), "is 3");
  }

  @Test
  void calculateTotalCostOfItems() {
 double costOfItemsTest = fridgeTester.calculateTotalCostOfItems();
 assertEquals(480, costOfItemsTest, "Cost is not equal to sum of all items");
  assertNotEquals(0.0, costOfItemsTest, "Cost is 0.0");
  }

  @Test
  void calculateCostOfExpiredItems() {
    double costOfExpiredItemsTest = fridgeTester.calculateCostOfExpiredItems(LocalDate.now().plusDays(3)); //adds only the first item as expired.
    assertEquals(40, costOfExpiredItemsTest, "Cost of expired items is not 20");
    assertNotEquals(0, costOfExpiredItemsTest, "Cost of expired items is 0");
  }

  @Test
  void reduceQuantityOfItem() {
    fridgeTester.reduceQuantityOfItem(1,fridgeTester.iterateOverFridge().next());
    assertEquals(1, fridgeTester.retrieveNthOccurrenceOfItem(1,"milk").getQuantity(), "Quantity is not 1");
    assertNotEquals(0, fridgeTester.retrieveNthOccurrenceOfItem(1,"milk").getQuantity(), "Quantity is 0");
  }


}