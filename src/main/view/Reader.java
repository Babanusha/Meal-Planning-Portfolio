package view;
import controller.Validator;
import java.util.Scanner;

public class Reader {

  Scanner scanner;
  boolean inputAccepted;


  public Reader() {
    scanner = new Scanner(System.in);
  }


  public String readString() throws Exception {
    inputAccepted = false;
    String input = "";
    while (!inputAccepted) {
      try {
        input = scanner.nextLine();
          inputAccepted = true;
      } catch (Exception ignored) {
        throw new Exception("Invalid input");
      }
    } return input;
  }

  public int readInt() throws Exception {
    int input = 0;
    inputAccepted = false;

    while (!inputAccepted) {
      try {
        input = scanner.nextInt();
        scanner.nextLine(); //Consume newline
        inputAccepted = true;

      } catch (Exception e) {
        scanner.nextLine(); //Consume invalid input
        throw new Exception("Invalid input");
      }
    } return input;
  }


}
