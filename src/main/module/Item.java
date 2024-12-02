package module;



import java.time.LocalDate;


/**
 * Class for Item, which is a product that can be stored in the fridge. The class contains
 * information about the item, such as name, quantity, quantity specifier, product cost and
 * expiration date. Some parameters are optional, for easier entry.
 */
public class Item {

  private LocalDate expirationDate;
  private String name;
  private int quantity; // needs specifier (e.g. "grams", "pieces")
  private String unit; //grams or piece
  private double productCost; // TODO:price per quantity -> max 2 decimals?

  /**
   * Constructor for Item with only mandatory fields.
   *
   * @param name              , name of item
   * @param quantity          , quantity of item
   * @param unit , type of quantity (e.g. grams, pieces, liters)
   */
  public Item(String name, int quantity, String unit) {
    this(name, quantity, unit, -1, null);
  }

  /**
   * Overloaded constructor for Items with all fields. Used when all fields are known. Contains:
   * name, quantity, quantity specifier, product cost and expiration date.
   *
   * @param name              , name of item
   * @param quantity          , quantity of item
   * @param unit , type of quantity (e.g. grams, pieces, liters)
   * @param productCost       , price of item
   * @param expirationDate    , expiration date of item
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
   * Get name of item.
   *
   * @return name of item
   */
  public String getName() {
    return name;
  }

  /**
   * Set name of item.
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
   * Set quantity of item.
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
   * Set quantity specifier of item.
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
   * Set product cost of item.
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
   * Set expiration date of item.
   *
   * @param expirationDate , expiration date of item
   */
  void setExpirationDate(LocalDate expirationDate) {
    this.expirationDate = expirationDate;
  }

}