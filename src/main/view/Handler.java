package view;
import java.util.Scanner;

public class Handler {

  Scanner scanner;
  boolean inputAccepted;
  Printer printer;





  public Handler() {
    scanner = new Scanner(System.in);
    printer = new Printer();
  }


  public String getStringInput() {
    inputAccepted = false;
    String input = "";
    while (!inputAccepted) {
      try {
        input = scanner.nextLine();
      } catch (Exception e) {
        printer.invalidInput();
      }
    } return input;
  }

  public int getIntInput() {
    int input = 0;
    inputAccepted = false;
    while (!inputAccepted) {
      try {
        input = scanner.nextInt();
        inputAccepted = true;
      } catch (Exception e) {
        printer.invalidInput();
      }
    } return input;
  }
}
