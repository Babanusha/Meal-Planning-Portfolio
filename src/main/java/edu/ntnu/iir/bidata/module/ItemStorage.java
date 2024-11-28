package edu.ntnu.iir.bidata.module;

import edu.ntnu.iir.bidata.view.Printer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStorage {

  private ArrayList<Item> itemContainer;
  private Item item;
  private Printer printer;



  public ItemStorage() {
    init();
  }




  public void init() {
    itemContainer = new ArrayList<>();
    printer = new Printer();
    testInit(); //FOR TESTING

  }



  private void testInit() {
    itemContainer = new ArrayList<>();
    // flytt til init
    itemContainer.add(new Item("Test1", 1, "liters"));
    itemContainer.add(new Item("Test2", 2, "pieces"));
    itemContainer.add(new Item("Test3", 3, "grams"));

    System.out.println("TEST");
    printAllItems();
    System.out.println("TEST DONE");
  }



  public void addItem(Item item) {
    itemContainer.add(item);
  }
  private void removeItem(Item item) {
    itemContainer.remove(item);
  }
  private ArrayList<Item> getItems() {
    return itemContainer;
  }

  public Iterator<Item> searchItems(String itemName) {
      return itemContainer.stream()
        .filter(item -> item.getName()
            .equalsIgnoreCase(itemName)).iterator();
  }

  private void printFilledListOfItems(List<Item> listOfItems) {
    for (Item item: listOfItems) {
      System.out.println("DO SOMETHING");
    }
  }

  private void printAllItems() {
    for (Item item : itemContainer) {
      printer.printItem(item);
    }

  }














}
