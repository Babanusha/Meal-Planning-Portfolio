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


  /**
   * Constructor for Fridge class. Initiates fridge with an empty list of items.
   */
  public Fridge() {
    init();
    testInit(); //TODO: REMOVE.
  }

  private void testInit() { //TODO: Remove before completion
    listOfItems.add(new Item("Milk", 1, STRING_LITERS, 20.03034, LocalDate.now().plusDays(2)));
    listOfItems.add(new Item("Milk", 3, STRING_LITERS, 40.3402, LocalDate.now().plusDays(7)));
    listOfItems.add(new Item("Bacon", 2, STRING_KILOGRAMS, 20.03032, LocalDate.now().plusDays(3)));
    listOfItems.add(new Item("Kenguru", 1, STRING_LITERS, 30, LocalDate.now().plusDays(7)));

  }


  /**
   * Initiates the fridge with an empty list of items.
   */
  private void init() {
    listOfItems = new ArrayList<>();
  }

  /**
   * Creates an item and adds it to the fridge.
   *
   * @param name           of item
   * @param quantity       of item
   * @param unit           of quantity
   * @param productCost    of item
   * @param expirationDate of item
   */
  public void createItemAndAddToFridge(String name, int quantity, String unit,
      double productCost, LocalDate expirationDate) {
    Item item = new Item(name, quantity, unit, productCost, expirationDate);
    addItemToFridge(item);
  }

  /**
   * Iterates over the list of items in the fridge.
   *
   * @return iterator of items in fridge
   */
  public Iterator<Item> iterateOverFridge() {
    return listOfItems.iterator();
  }

  /**
   * Adds an item to the fridge.
   *
   * @param item to add to fridge
   */
  private void addItemToFridge(Item item) {
    listOfItems.add(item);
  }


  /**
   * Removes an item from list.
   *
   * @param item to remove from fridge
   * @return true if item was removed.
   */

  public boolean removeItem(Item item) {
    return listOfItems.remove(item);
  }


  /**
   * Searches for an item in the fridge.
   *
   * @param searchItem to search for
   * @return iterator of items found
   */
  public Iterator<Item> searchForItem(String searchItem) {
    return listOfItems.stream()
        .filter(filteredListOfItems -> filteredListOfItems.getName()
            .equalsIgnoreCase(searchItem)).iterator();
  }


  /**
   * Sorts the list of items by expiration date.
   */
  public void sortItemsByExpirationDate() {
    listOfItems.sort(Comparator.comparing(Item::getExpirationDate));
  }

  /**
   * Sorts the list of items by quantity left.
   */
  public void sortItemsByQuantityLeft() {
    listOfItems.sort(Comparator.comparingInt(Item::getQuantity));
  }

  /**
   * Sorts the list of items alphabetically.
   */
  public void sortItemsAlphabetically() {
    listOfItems.sort(Comparator.comparing(Item::getName));
  }

  /**
   * Edits name of an item in the fridge.
   *
   * @param item    to edit.
   * @param newName of item.
   * @return true (always)
   */
  public boolean editItemName(Item item, String newName) {
    item.setName(newName);
    return true;
  }

  /**
   * Edits quantity of an item in the fridge.
   *
   * @param item        to edit.
   * @param newQuantity of item.
   * @return true (always)
   */
  public boolean editItemQuantity(Item item, int newQuantity) {
    item.setQuantity(newQuantity);
    return true;
  }

  /**
   * Edits unit of an item in the fridge.
   *
   * @param item    to edit.
   * @param newUnit of item.
   * @return true (always)
   */
  public boolean editItemUnit(Item item, String newUnit) {
    item.setUnit(newUnit);
    return true;
  }

  /**
   * Edits cost of an item in the fridge.
   *
   * @param item    to edit.
   * @param newCost of item.
   * @return true (always)
   */
  public boolean editItemCost(Item item, double newCost) {
    item.setProductCost(newCost);
    return true;
  }

  /**
   * Edits expiration date of an item in the fridge.
   *
   * @param item    to edit.
   * @param newDate of item.
   * @return true (always)
   */
  public boolean editItemExpirationDate(Item item, LocalDate newDate) {
    item.setExpirationDate(newDate);
    return true;
  }


  /**
   * Counts the number of a specific item in the fridge.
   *
   * @param searchItem to search for.
   * @return number of items found.
   */

  public int specificItemInFridgeCount(String searchItem) {
    int count = 0;
    for (Item itemToCount : listOfItems) {
      if (itemToCount.getName().equalsIgnoreCase(
          searchItem)) { // case in-sensitive. and includes names not fully written.
        count++;
      }
    }
    return count;
  }


  /**
   * Retrieves the nth occurrence of a specific item in the fridge.
   *
   * @param itemNumber to retrieve.
   * @param searchItem name of the wanted item
   * @return the nth occurrence of the item.
   */

  public Item retrieveNthOccurenceOfItem(int itemNumber, String searchItem) {
    List<Item> listToSearch = listOfItems.stream().filter(itemInList -> itemInList.
        getName().equalsIgnoreCase(searchItem)).toList();
    return listToSearch.get(itemNumber - 1);
  }


  /**
   * Calculates the total cost of all items in the fridge.
   *
   * @return total cost of all items.
   */

  public double calculateTotalCostOfItems() {
    double totalcost = 0;
    for (Item item : listOfItems) {
      totalcost += item.getProductCost();
    }
    return totalcost;
  }


  /**
   * Iterates over the list of items in the fridge that have expired.
   *
   * @param dateToCheck for expiration.
   * @return iterator of expired items.
   */
  public Iterator<Item> iterateExpiredItems(LocalDate dateToCheck) {
    return listOfItems.stream()
        .filter(item -> item.getExpirationDate().isBefore(dateToCheck)).iterator();
  }

  /**
   * Calculates the total cost of all expired items in the fridge.
   *
   * @param dateToCheck for expiration.
   * @return total cost of all expired items.
   */
  public double calculateCostOfExpiredItems(LocalDate dateToCheck) {
    return listOfItems.stream()
        .filter(itemInList -> itemInList.getExpirationDate().isBefore(dateToCheck))
        .mapToDouble(Item::getProductCost).sum();
  }
}

















