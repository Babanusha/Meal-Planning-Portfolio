package module;

import static module.ApplicationSettings.STRING_KILOGRAMS;
import static module.ApplicationSettings.STRING_LITERS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Fridge {


  private ArrayList<Item> listOfItems;
  private Item item;


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
    item = new Item(name, quantity, unit, productCost, expirationDate);
    addItemToFridge(item);
  }

  public Iterator<Item> iterateOverFridge() {
    return listOfItems.iterator();
  }

  public void addItemToFridge(Item item) { //TODO: make private or remove?
    listOfItems.add(item);
  }


  public void removeItem(Item item) {
    listOfItems.remove(item);
  }


  public Iterator<Item> searchForItem(String searchItem) {
      return listOfItems.stream()
        .filter(filteredListOfItems -> filteredListOfItems.getName()
            .equalsIgnoreCase(searchItem)).iterator();
  }



  public int getSizeOfFridge() {
    return listOfItems.size();
  }
  private ArrayList<Item> duplicateFridge() {
    return new ArrayList<>(listOfItems);
  }


  private List<Item> fridgeSortedByExpirationDate() {
    ArrayList<Item> arrayListToSort = duplicateFridge();
    arrayListToSort.sort((firstItem, secondItem) -> firstItem.getExpirationDate()
        .compareTo(secondItem.getExpirationDate()));
    return arrayListToSort;
  }

  private List<Item> fridgeSortedByQuantity() {
    ArrayList<Item> arrayListToSort = duplicateFridge();
    arrayListToSort.sort((firstItem, secondItem) -> firstItem.getQuantity() - secondItem.getQuantity());
    return arrayListToSort;
  }

  private List<Item> fridgeSortedByAlphabeticalOrder() { //TODO: change name of variable and method? ALL SORTS
    ArrayList<Item> arrayListToSort = duplicateFridge();
    arrayListToSort.sort((firstItem, secondItem) -> firstItem.getName().compareTo(secondItem.getName()));
    return arrayListToSort;
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
      if (itemToCount.getName().equalsIgnoreCase(searchItem)) {
        count++;
      }
    }
    return count;
  }


  public Item retrieveNthOccurenceOfItem(int itemNumber, String searchItem) {
    List<Item> listToSearch = listOfItems.stream().filter(itemInList -> itemInList.getName()
        .equalsIgnoreCase(searchItem)).toList();
    return listToSearch.get(itemNumber - 1);
  }

  public boolean isFridgeEmpty() {
    return listOfItems.isEmpty();
  }

  public double calculateTotalCostOfItems() {
    double totalcost = 0;
    for (Item item : listOfItems) {
      totalcost += item.getProductCost();
    }return totalcost;
  }


}

















