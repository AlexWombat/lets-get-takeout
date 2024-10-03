//import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("What's your name?");
    Scanner input = new Scanner(System.in);
    String customerName = input.nextLine();
    System.out.println("How much money do you have?");
    int money;
    while(true) {
      if (input.hasNextInt()) {
        money = input.nextInt();
        break;
      } else {
        System.out.println("Enter a valid integer.");
        input.next();
      }
    }

    Customer customer = new Customer(customerName, money);
    TakeoutSimulator takeoutSimulator = new TakeoutSimulator(customer, input);
    takeoutSimulator.startTakeoutSimulator();
  }
}
