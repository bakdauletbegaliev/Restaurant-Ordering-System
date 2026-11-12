package kz.restordering.app;

import java.util.List;
import java.util.Scanner;
import kz.restordering.facade.MealSelection;
import kz.restordering.facade.OrderSummary;
import kz.restordering.facade.RestaurantFacade;
import kz.restordering.model.MealType;
import kz.restordering.strategy.DiscountType;


public final class Main {
    private Main() { }

    public static void main(String[] args) {
        if (args.length > 0 && "--demo".equals(args[0])) {
            runDemo();
        } else if (args.length > 0 && "--help".equals(args[0])) {
            System.out.println("Run without arguments for menu, or --demo for automatic defense demonstration.");
        } else {
            new ConsoleApp(new Scanner(System.in), System.out).run();
        }
    }

    private static void runDemo() {
        System.out.println("=== RESTAURANT ORDERING SYSTEM: AUTOMATIC DEMO ===");
        RestaurantFacade restaurant = new RestaurantFacade(System.out);
        OrderSummary first = restaurant.placeOrder("Aruzhan", List.of(
                new MealSelection(MealType.PIZZA, true, true, false),
                new MealSelection(MealType.BURGER, false, false, true)), DiscountType.STUDENT);
        show(first);
        restaurant.advanceOrder(first.getId());
        restaurant.advanceOrder(first.getId());
        restaurant.advanceOrder(first.getId());
        System.out.println();
        OrderSummary second = restaurant.placeOrder("Dias", List.of(
                new MealSelection(MealType.SALAD, false, true, false)), DiscountType.LOYALTY);
        show(second);
        System.out.println("\nFINAL ORDER LIST:");
        for (OrderSummary summary : restaurant.getOrders()) {
            System.out.printf("#%d | %s | %s | %d KZT%n", summary.getId(),
                    summary.getCustomer(), summary.getStatus(), summary.getPrice().getTotal());
        }
        System.out.println("=== DEMO FINISHED SUCCESSFULLY ===");
    }

    private static void show(OrderSummary summary) {
        System.out.printf("RECEIPT #%d (%s)%n", summary.getId(), summary.getCustomer());
        summary.getMeals().forEach(meal -> System.out.printf("  %s: %d KZT%n", meal.getDescription(), meal.getPrice()));
        System.out.printf("  Subtotal: %d KZT | Discount: %d KZT | Total: %d KZT%n",
                summary.getPrice().getSubtotal(), summary.getPrice().getDiscount(), summary.getPrice().getTotal());
    }
}
