package controller;

import static module.ApplicationSettings.DEFAULT_COST;
import static module.ApplicationSettings.DEFAULT_EXPIRATION_DATE;
import static module.ApplicationSettings.SWITCH_CASE_LIMIT;

import module.Item;
import module.Fridge;
import view.Reader;
import view.Printer;
import view.UI_Handler;
import java.time.LocalDate;


public class Controller {


  private Fridge fridge;
  private UI_Handler ui_handler;
  private Reader reader;
  private Printer printer;
  Validator validator;


  /**
   * Constructor for the Controller class Initializes the ItemStorage, Handler, Printer, and
   * UI_Handler Starts the application
   */
  public Controller() {
    init();

    start();
  }


  public void init() {
    fridge = new Fridge();
    validator = new Validator();

    reader = new Reader();
    printer = new Printer();

    ui_handler = new UI_Handler(reader, printer, validator);
  }


  private void start() {
    boolean isApplicationOnline = true; //If time, make persistent storage

    printer.homeMenu();
    mainMenuSwitchCase(isApplicationOnline);
  }

  private void exit() {
    printer.printExitMessage();

    //TODO: Make it save to a file if time + persistent storage?
  }

  private void mainMenuSwitchCase(boolean programStatus) {
    while (programStatus) {
      try {
        switch (ui_handler.intHandler(SWITCH_CASE_LIMIT)) {

          case 1 -> createNewItem();
          // case 2 -> itemStorage.removeItem(findItem());
          //case 3 -> itemStorage.listItems();
          case 9 -> programStatus = false;

          default -> printer.invalidInputError(); //UI_HANDLER?
        }
      } catch (Exception allExceptions) {
        printer.printError("Invalid input" + allExceptions); //TODO: CLEAN UP
      }
    }
    exit();
  }


  private void createNewItem() {

    String name;
    int quantity;
    String quantityUnit;
    int cost = DEFAULT_COST;
    LocalDate expirationDate = DEFAULT_EXPIRATION_DATE;
    name = ui_handler.promtForItemName();
    quantity = ui_handler.promtForItemQuantity();
    quantityUnit = ui_handler.promtForQuantityUnit();
    if (ui_handler.yesOrNo("Do you want to add cost of item?")) {
      cost = ui_handler.promtForCostOfItem();
    }
    if (ui_handler.yesOrNo("Do you want to add expiration date?")) {
      expirationDate = ui_handler.promtForExpirationDate();
    }
    fridge.addItemToFridge(new Item(name, quantity,
        quantityUnit, cost, expirationDate));

    fridge.testPrint();              //TO BE REMOVED

  }

}

