package view;

import module.Item;

public class Printer {


  public void printString(String message) {
    System.out.println(message);
  }

  public void homeMenu() {
    printString("Fridge is open, choose an action:");
    printString("1. Add item");
    printString("2. Remove item");
    printString("3. List items");
  }


  public void printItem(Item item) {
    if (item.getUnit() != null) {
      printString("Item: " + item.getName() + " Quantity: " +
          item.getQuantity() + " Unit: " + item.getUnit());
    }
  }

  public void invalidInput() {
    printString("Invalid input, try again");
  }
}
