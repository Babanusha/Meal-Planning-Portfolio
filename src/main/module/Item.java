package module;

import java.util.Calendar;
import java.util.Date;

public class Item {

  private final Date expirationDate;
  private String name;
  private int quantity; // needs specifier (e.g. "grams", "pieces")
  private String quantitySpecifier; //grams or piece
  private double price; // price per quantity -> max 2 decimals?

  /**
   * Constructor for Item
   *
   * @param name
   * @param quantity
   * @param quantityType
   * optional param: price
   * optional param: expirationDate
   */

  public Item(String name, int quantity, String quantityType) {
    setName(name);
    setQuantity(quantity);
    setQuantityType(quantityType);
    this.price = -1; // default value
    this.expirationDate = null; // default value
  }


  //SETTERS

  private void setName(String name) {
    this.name = name;
  }
  private void setQuantity(int quantity) {
    this.quantity = quantity;
  }
  private void setQuantityType(String quantityType) {
    this.quantitySpecifier = quantityType;
  }

  //GETTERS

  public String getName() {
    return name;
  }
  public int getQuantity() {
    return quantity;
  }
  public double getPrice() {
    return price;
  }
  public String getUnit() {
    return quantitySpecifier;
  }
}
