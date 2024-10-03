import java.util.ArrayList;
import java.util.List;

public class FoodMenu {
  private List<Food> menu;

  public FoodMenu() {
    this.menu = new ArrayList<>();
    this.menu.add(new Food("Borgir", "Juicy borgir", 8));
    this.menu.add(new Food("3 oysters", "Slimy oysters", 10));
    this.menu.add(new Food("Beef Wellington", "Fancy filet mignon", 30));
  }

  public String toString() {
    String outputString = "";
    for (int i=0; i < this.menu.size(); i++) {
      //String.format("%n. %s: %s   Cost: %n", i, menu{i}.getName, menu{i});
      outputString = outputString.concat((i+1)+". "+menu.get(i).toString()+"\n");
    }
    return outputString;
  }

  public Food getFood(int index) {
    try {
      return this.menu.get(index-1);
    } catch (IndexOutOfBoundsException e) {
      return null;
    }
  }

  public Food getLowestCostFood() {
    Food cheapest = this.menu.get(0);
    for (int i = 0; i < this.menu.size(); i++) {
      if (menu.get(i).getPrice()<cheapest.getPrice()) {
        cheapest = menu.get(i);
      }
    }
    return cheapest;
  }
}
