package fridge;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;


class ItemTest {

  private Item testItem;
  private String itemName;
  private int quantity;
  private String quantitySpecifier;
  private double productCost;
  private LocalDate expirationDateNotOut;


  @org.junit.jupiter.api.BeforeEach
  void setUp() {

    expirationDateNotOut = LocalDate.now().plusDays(50);
    itemName = "TestItem";
    quantity = 1;
    quantitySpecifier = "kg";
    productCost = 10;

    testItem = new Item(itemName,quantity,quantitySpecifier, productCost, expirationDateNotOut);
  }

  @org.junit.jupiter.api.AfterEach
  void tearDown() {
    testItem = null;
  }

  @org.junit.jupiter.api.Test
  void getName() {
    Assertions.assertEquals(testItem.getName(), "TestItem", "Positive getName() failed");
    Assertions.assertNotEquals("hopefullyGoodGrades" , testItem.getName(), "Negative getName() failed");
  }

  @org.junit.jupiter.api.Test
  void getQuantity() {
    Assertions.assertEquals(testItem.getQuantity(), quantity, "Positive getQuantity() failed");
    Assertions.assertNotEquals(2, testItem.getQuantity(), "Negative getQuantity() failed");
  }

  @org.junit.jupiter.api.Test
  void getQuantitySpecifier() {
    Assertions.assertEquals(testItem.getUnit(), quantitySpecifier, "Positive getQuantitySpecifier() failed");
    Assertions.assertNotEquals("privateMethodsAreSecure", testItem.getUnit(), "Negative getQuantitySpecifier() failed");
  }

  @org.junit.jupiter.api.Test
  void getProductCost() {
    Assertions.assertEquals(testItem.getProductCost(), productCost, "Positive getProductCost() failed");
    Assertions.assertNotEquals(200, testItem.getProductCost(), "Negative getProductCost() failed");
  }

  @org.junit.jupiter.api.Test
  void getExpirationDate() {
    LocalDate invalidTime = LocalDate.of(2021, 10, 10);
    Assertions.assertEquals(testItem.getExpirationDate(), expirationDateNotOut, "Positive getExpirationDate() failed");
    Assertions.assertNotEquals(invalidTime, testItem.getExpirationDate(), "Negative getExpirationDate() failed");
  }

}
