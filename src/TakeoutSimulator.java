import java.util.Scanner;

public class TakeoutSimulator {
  private Customer customer;
  private FoodMenu menu;
  private Scanner input;

  public TakeoutSimulator(Customer customer, Scanner input) {
    this.customer = customer;
    this.menu = new FoodMenu();
    this.input = input;
  }


  private <T> T getOutputOnIntInput(String userInputPrompt, IntUserInputRetriever<T> intUserInputRetriever) {
    while (true) {
      System.out.println(userInputPrompt);
      if (this.input.hasNextInt()) {
        try {
          return intUserInputRetriever.produceOutputOnIntUserInput(this.input.nextInt());
        } catch (IllegalArgumentException e) {
          System.out.println("Your int input is not valid.");
        }
      } else {
        System.out.println("Input needs to be an `int` type.");
        this.input.next();
      }
    }
  }

  public boolean shouldSimulate () {
    String userPrompt = "Enter 1 to CONTINUE simulation or 0 to EXIT program:";
    IntUserInputRetriever<Boolean> intUserInputRetriever = selection -> {
      if (selection == 1 & customer.getMoney()>=menu.getLowestCostFood().getPrice()) {
        return true;
      } else if (selection == 0) {
        System.out.println("Ending simulation.");
        return false; 
      } else if (customer.getMoney()<menu.getLowestCostFood().getPrice()) {
        System.out.println("You're broke. Ending simulation.");
        return false;
      } else {
        throw new IllegalArgumentException();
      }
    };
    return getOutputOnIntInput(userPrompt, intUserInputRetriever);
  }

  public Food getMenuSelection() {
    String userPrompt = "\nMenu:\n"+this.menu+"\nType the number of the desired menu item.";
    IntUserInputRetriever<Food> intUserInputRetriever = (selection) -> {
      if (this.menu.getFood(selection)!=null) {
        return this.menu.getFood(selection);
      } else {
        throw new IllegalArgumentException();
      }
    };
    return getOutputOnIntInput(userPrompt, intUserInputRetriever);
  }

  public boolean isStillOrderingFood() {
    String userPrompt = "Print 1 to continue shopping or 0 to checkout.";
    IntUserInputRetriever<Boolean> intUserInputRetriever = (selection) -> {
      if (selection == 1) {
        return true;
      } else if (selection == 0) {
        return false;
      } else {
        throw new IllegalArgumentException();
      }
    };
    return getOutputOnIntInput(userPrompt, intUserInputRetriever);
  }

  public void checkoutCustomer(ShoppingBag<Food> shoppingBag) {
    System.out.println("\nPayment is processing.");
    int remainingMoney = this.customer.getMoney() - shoppingBag.getTotalPrice();
    this.customer.setMoney(remainingMoney);
    System.out.printf("Your remaining money: %d.\nThank you!\n", remainingMoney);
  }

  public void takeOutPrompt() {
    ShoppingBag<Food> shoppingBag = new ShoppingBag<>();
    int customerMoneyLeft = this.customer.getMoney();
    boolean isStillOrdering = true;
    while (isStillOrdering) {
      System.out.printf("\nYou have %d left to spend.\n", customerMoneyLeft);
      Food selectedFood = getMenuSelection();
      if (customerMoneyLeft>=selectedFood.getPrice()) {
        customerMoneyLeft -= selectedFood.getPrice();
        shoppingBag.addItem(selectedFood);
      } else {
        System.out.println("Oops. You broke. Choose other item or checkout.");
      }
      isStillOrdering = isStillOrderingFood();
    }
    checkoutCustomer(shoppingBag);
  }

  public void startTakeoutSimulator() {
    while (shouldSimulate()) {
      System.out.println("\nWelcome to my restaurant, "+customer.getName()+".");
      takeOutPrompt();
    }
  }

}
