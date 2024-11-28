package edu.ntnu.iir.bidata.controller;

import edu.ntnu.iir.bidata.module.Item;
import edu.ntnu.iir.bidata.module.ItemStorage;
import edu.ntnu.iir.bidata.view.Handler;
import edu.ntnu.iir.bidata.view.Printer;


public class Controller {

  ItemStorage itemStorage;
  Handler handler;
  Printer printer;




  public Controller() {
    init();
    start();
  }


  public void init() {
    itemStorage = new ItemStorage();
    handler = new Handler();
    printer = new Printer();
  }


  private void start() {
    boolean isApplicationOnline = true; //If time, make persistent storage
    printer.homeMenu();
    handler.getStringInput();
    mainMenuSwitchCase();
  }

  private void mainMenuSwitchCase() {
    boolean caseFlag = true;
    while (caseFlag) {
      //Add item
      try {
        switch (handler.getIntInput()) {

          case 1 -> itemStorage.addItem(createItem());
          // case 2 -> itemStorage.removeItem(findItem());
          //case 3 -> itemStorage.listItems();
          default -> printer.invalidInput();

        }
      } catch (Exception e) {
        printer.invalidInput();
      }
    }
  }

  private Item createItem() {
   String name;
    int quantity;
    String unit;

    printer.printString("Enter item name:");
      name = handler.getStringInput();

    printer.printString("Enter quantity:");
      quantity = handler.getIntInput();

    printer.printString("Enter unit:");
      unit = handler.getStringInput();

      //TODO: OPTIONAL ITEMS?
      return new Item(name, quantity, unit);
  }




}

