package module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemStorage {

  private ArrayList<Item> itemContainer;
  private Item item;



  public ItemStorage() {
    init();
  }




  public void init() {
    itemContainer = new ArrayList<>();
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



  }















