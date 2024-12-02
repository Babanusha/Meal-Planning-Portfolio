package view;

import static module.ApplicationSettings.INT_EXIT;
import static module.ApplicationSettings.STRING_GRAMS;
import static module.ApplicationSettings.STRING_KILOGRAMS;
import static module.ApplicationSettings.STRING_LITERS;
import static module.ApplicationSettings.STRING_MILLIGRAMS;
import static module.ApplicationSettings.STRING_MILLILITERS;

public class Printer {


  public void printString(String message) {
    System.out.println(message);
  }


  public void homeMenu() {
    printString("Fridge is open, choose an action:");
    printString("1. Add item to fridge");
    printString("2. Edit item from fridge");
    printString("3. List items in fridge & calculate cost(CALCULATE COST OF ITEMS)");
    blankLine();
    printString("4. Edit item/item list");
    printString(INT_EXIT+". Exit");
  }

  public void editMenu() {
    printString("Edit menu:");
    printString("1. Edit item");
    printString("2. Remove item");
    printString("3. Sort items by name");
    printString("4. Sort items by quantity");
    printString("5. Sort items by expiration date");

    printString(INT_EXIT +". Back to main menu");
  }
  public void itemEditMenu(){
    printString("Edit item:");
    printString("1. Edit name");
    printString("2. Edit quantity");
    printString("3. Edit unit");
    printString("4. Edit cost");
    printString("5. Edit expiration date");

    printString(INT_EXIT+". Back to edit menu");
  }


public void blankLine() {
    printString("");
  }

  public void toExit() {
    printString("To exit: either type 'exit' or '" + INT_EXIT + "'");
  }






  public void printError(String stringToPrint) {
    System.err.println(stringToPrint);
  }



  public void listQuantityUnitsTable() {

    printString("1. " + STRING_KILOGRAMS);
    printString("2. " + STRING_GRAMS);
    printString("3. " + STRING_MILLIGRAMS);
    printString("4. " + STRING_LITERS);
    printString("5. " + STRING_MILLILITERS);
  }


  public void itemTableFrame() {
    printString(String.format("%-5s\t%-13s\t%-11s\t%-10s\t%-10s\t%-16s",
       "Nr", "Name", "Quantity", "Unit", "Cost", "Expiration Date"));
  }
}
