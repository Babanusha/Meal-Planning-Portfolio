package fridge;


import static settings.ApplicationSettings.DEFAULT_COST;
import static settings.ApplicationSettings.DEFAULT_EXPIRATION_DATE;

import java.time.LocalDate;


/**
 * Class for Item, which is a product that can be stored in the fridge. The class contains
 * information about the item, such as name, quantity, quantity specifier, product cost and
 * expiration date. Some parameters are optional, for easier entry. Contains three constructors, one
 * for only name, quantity, and unit, one for all fields, and one for copying an existing item.
 */
public class Item {

  private LocalDate expirationDate; // expiration date of item.
  private String name; // name of item
  private int quantity; // needs specifier (e.g. "grams", "pieces")
  private String unit; //grams or piece
  private double productCost; //cost of item


  /**
   * Constructor for Items were only name, quantity, and unit is entered. Contains: name, quantity,
   * quantity specifier. Default values are set for product cost and expiration date.
   *
   * @param name     name of item
   * @param quantity quantity of item
   * @param unit     type of quantity (e.g. grams, pieces, liters)
   */
  public Item(String name, int quantity, String unit) {
    setName(name);
    setQuantity(quantity);
    setUnit(unit);
    setProductCost(DEFAULT_COST);
    setExpirationDate(DEFAULT_EXPIRATION_DATE);
  }

  /**
   * Constructor for Items with all fields. Contains: name, quantity, quantity specifier, product
   * cost and expiration date.
   *
   * @param name           name of item
   * @param quantity       quantity of item
   * @param unit           type of quantity (e.g. grams, pieces, liters)
   * @param productCost    price of item
   * @param expirationDate expiration date of item
   */

  public Item(String name, int quantity, String unit, double productCost, LocalDate
      expirationDate) {
    setName(name);
    setQuantity(quantity);
    setUnit(unit);
    setProductCost(productCost);
    setExpirationDate(expirationDate);
  }

  /**
   * Copy constructor for Items.
   *
   * @param item to copyIntoNewOne
   */
  public Item(Item item) {
    setName(item.getName());
    setQuantity(item.getQuantity());
    setUnit(item.getUnit());
    setProductCost(item.getProductCost());
    setExpirationDate(item.getExpirationDate());
  }


  /**
   * Get name of item.
   *
   * @return name of item
   */
  public String getName() {
    return name;
  }

  /**
   * Set name of item. Package private because only fridge needs setter access.
   *
   * @param name , name of item
   */
  void setName(String name) {
    this.name = name;
  }

  /**
   * Get quantity of item.
   *
   * @return quantity of item
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * Set quantity of item. Package private because only fridge needs setter access.
   *
   * @param quantity , quantity of item
   */
  void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  /**
   * Get quantity specifier of item.
   *
   * @return quantity specifier of item
   */
  public String getUnit() {
    return unit;
  }

  /**
   * Set quantity specifier of item. Package private because only fridge needs setter access.
   *
   * @param unit , quantity specifier of item
   */
  void setUnit(String unit) {
    this.unit = unit;
  }

  /**
   * Get product cost of item.
   *
   * @return product cost of item
   */
  public double getProductCost() {
    return productCost;
  }

  /**
   * Set product cost of item. Package private because only fridge needs setter access.
   *
   * @param productCost , product cost of item
   */
  void setProductCost(double productCost) {
    this.productCost = productCost;
  }

  /**
   * Get expiration date of item.
   *
   * @return expiration date of item
   */
  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  /**
   * Set expiration date of item. Package private because only fridge needs setter access.
   *
   * @param expirationDate , expiration date of item
   */
  void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

}