package module;

import static module.ApplicationSettings.STRING_KILOGRAMS;
import static module.ApplicationSettings.STRING_LITERS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class Fridge {


  private ArrayList<Item> listOfItems;

                       ///TODO: make item search case insensitive  ss SS
  public Fridge() {
    init();
    testInit();
  }

  private void  testInit() { //TODO: Remove before completion
    listOfItems.add(new Item("Milk", 1, STRING_LITERS, 20.03034, LocalDate.now().plusDays(2)));
    listOfItems.add(new Item("Milk", 3, STRING_LITERS, 40.3402, LocalDate.now().plusDays(7)));
    listOfItems.add(new Item("Bacon", 2, STRING_KILOGRAMS, 20.03032, LocalDate.now().plusDays(3)));
    listOfItems.add(new Item("Kenguru", 1, STRING_LITERS, 30, LocalDate.now().plusDays(7)));

  }
  private void init() {
    listOfItems = new ArrayList<>();
  }

  public void createItemAndAddToFridge(String name, int quantity, String unit,
      double productCost, LocalDate expirationDate) {
    Item item = new Item(name, quantity, unit, productCost, expirationDate);
    addItemToFridge(item);
  }

  public Iterator<Item> iterateOverFridge() {
    return listOfItems.iterator();
  }

  private void addItemToFridge(Item item) {
    listOfItems.add(item);
  }


  public boolean removeItem(Item item) {
    return listOfItems.remove(item);
  }


  public Iterator<Item> searchForItem(String searchItem) {
      return listOfItems.stream()
        .filter(filteredListOfItems -> filteredListOfItems.getName()
            .equalsIgnoreCase(searchItem)).iterator();
  }


  public void sortItemsByExpirationDate() {
    listOfItems.sort(Comparator.comparing(Item::getExpirationDate));
  }

  public void sortItemsByQuantityLeft() {
    listOfItems.sort(Comparator.comparingInt(Item::getQuantity));
  }

  public void sortItemsAlphabetically() {
    listOfItems.sort(Comparator.comparing(Item::getName));
  }

  public boolean editItemName(Item item, String newName) {
    item.setName(newName);
    return true;
  }
  public boolean editItemQuantity(Item item, int newQuantity) {
    item.setQuantity(newQuantity);
    return true;
  }
  public boolean editItemUnit(Item item, String newUnit) {
    item.setUnit(newUnit);
    return true;
  }
  public boolean editItemCost(Item item, double newCost) {
    item.setProductCost(newCost);
    return true;
  }
  public boolean editItemExpirationDate(Item item, LocalDate newDate) {
    item.setExpirationDate(newDate);
    return true;
  }


  public int specificItemInFridgeCount(String searchItem) {
    int count = 0;
    for (Item itemToCount : listOfItems) {
      if (itemToCount.getName().equalsIgnoreCase(searchItem)) { // case in-sensitive. and includes names not fully written.
        count++;
      }
    }
    return count;
  }


  public Item retrieveNthOccurenceOfItem(int itemNumber, String searchItem) {
    List<Item> listToSearch = listOfItems.stream().filter(itemInList -> itemInList.
        getName().equalsIgnoreCase(searchItem)).toList();
    return listToSearch.get(itemNumber - 1);
  }


  public double calculateTotalCostOfItems() {
    double totalcost = 0;
    for (Item item : listOfItems) {
      totalcost += item.getProductCost();
    }return totalcost;
  }


  public Iterator<Item> iterateExpiredItems(LocalDate dateToCheck) {
    return listOfItems.stream()
        .filter(item -> item.getExpirationDate().isBefore(dateToCheck)).iterator();
  }

  public double calculateCostOfExpiredItems(LocalDate dateToCheck) {
  return listOfItems.stream()
      .filter(itemInList -> itemInList.getExpirationDate().isBefore(dateToCheck))
      .mapToDouble(Item::getProductCost).sum();
  }
}

















