package module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Fridge {

  private ArrayList<Item> itemContainer;
  private Item item;


  public Fridge() {
    init();
  }

  private void init() {
    itemContainer = new ArrayList<>();
  }




  public void addItemToFridge(Item item) {
    itemContainer.add(item);
  }

  public void createNewItem(String name, int amount, String unit) {
    item = new Item(name, amount, unit);
    addItemToFridge(item);
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


  public Iterator<Item> iterateFridge() {
    return itemContainer.iterator();
  }
}

















