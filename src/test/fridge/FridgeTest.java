package fridge;

import static settings.ApplicationSettings.STRING_LITERS;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class FridgeTest {

  Fridge fridgeTester = new Fridge();

  @BeforeEach
  void setUp() {
    fridgeTester.createItemAndAddToFridge("Milk", 1, STRING_LITERS, 20.0, LocalDate.now().plusDays(2));
    fridgeTester.createItemAndAddToFridge("Milk", 3, STRING_LITERS, 20.0, LocalDate.now().plusDays(7));
    fridgeTester.createItemAndAddToFridge("Milk", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(3));
    fridgeTester.createItemAndAddToFridge("Milk", 1, STRING_LITERS, 20.0, LocalDate.now().plusDays(7));
    fridgeTester.createItemAndAddToFridge("Flour", 2, STRING_LITERS, 20.0, LocalDate.now().plusDays(5));
    fridgeTester.createItemAndAddToFridge("Potato", 4, STRING_LITERS, 20.0, LocalDate.now().plusDays(7));
    fridgeTester.createItemAndAddToFridge("Bacon", 7, STRING_LITERS, 20.0, LocalDate.now().plusDays(100));
  }

  @AfterEach
  void tearDown() {
    fridgeTester = null;
  }






  }

