package module;

import static module.ApplicationSettings.STRING_LITERS;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FridgeTest {

  Fridge fridgeTester = new Fridge();
  ArrayList<Item> listOfItemsTest = new ArrayList<>();

  @BeforeEach
  void setUp() {
    fridgeTester.addItemToFridge(new Item("Milk", 1, STRING_LITERS, 20.0, LocalDate.now().plusDays(2)));
    fridgeTester.addItemToFridge(new Item("Milk", 3, STRING_LITERS, 20.0, LocalDate.now().plusDays(7)));
    fridgeTester.addItemToFridge(new Item("Milk", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(3)));
    fridgeTester.addItemToFridge(new Item("Milk", 1, STRING_LITERS, 20.0, LocalDate.now().plusDays(7)));
    fridgeTester.addItemToFridge(new Item("Flour", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(5)));
    fridgeTester.addItemToFridge(new Item("Potato", 4, STRING_LITERS, 20.0, LocalDate.now().plusDays(7)));
    fridgeTester.addItemToFridge(new Item("Bacon", 7, STRING_LITERS, 20.0, LocalDate.now().plusDays(100)));
  }

  @AfterEach
  void tearDown() {
    fridgeTester = null;
  }

  @Test
  void addItemToFridge() {
    fridgeTester.addItemToFridge(new Item("Milk", 1, STRING_LITERS, 20.0, LocalDate.now().plusDays(7)));
    assertEquals(8, fridgeTester.getSizeOfFridge(), "The size of the fridge should be 8");
    assertNotEquals(7, fridgeTester.getSizeOfFridge(), "The size of the fridge should not be 7");
  }

  @Test
  void searchItems() {
    Iterator<Item> searchResult = fridgeTester.searchItems("Milk");
    searchResult.forEachRemaining(item -> assertEquals("Milk", item.getName(), "The item should be Milk"));
  }




  }

