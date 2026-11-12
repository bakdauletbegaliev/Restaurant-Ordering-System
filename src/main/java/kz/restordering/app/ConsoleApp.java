package kz.restordering.app;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import kz.restordering.facade.MealSelection;
import kz.restordering.facade.OrderSummary;
import kz.restordering.facade.RestaurantFacade;
import kz.restordering.model.Meal;
import kz.restordering.model.MealType;
import kz.restordering.strategy.DiscountType;
import kz.restordering.strategy.PriceSummary;


public final class ConsoleApp {
    private final Scanner input;
    private final PrintStream out;
    private final RestaurantFacade restaurant;

    public ConsoleApp(Scanner input, PrintStream out) {
        this.input = input;
        this.out = out;
        restaurant = new RestaurantFacade(out);
    }

    public void run() {
        out.println("===================================");
        out.println("  RESTAURANT ORDERING SYSTEM");
        out.println("  Bakdaulet Begaliev & Dias Sabit");
        out.println("===================================");
        while (true) {
            out.println("\n1. Place new order");
            out.println("2. List all orders");
            out.println("3. Advance order status");
            out.println("0. Exit");
            int choice = askInt("Choose: ", 0, 3);
            if (choice == 0) { out.println("Goodbye!"); return; }
            try {
                switch (choice) {
                    case 1 -> placeOrder();
                    case 2 -> listOrders();
                    case 3 -> advanceOrder();
                    default -> throw new IllegalStateException("Unexpected menu item");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                out.println("Error: " + ex.getMessage());
            }
        }
    }

    private void placeOrder() {
        out.print("Customer name: ");
        String customer = input.nextLine().trim();
        if (customer.isEmpty()) { out.println("Name cannot be empty."); return; }

        List<MealSelection> items = new ArrayList<>();
        boolean another;
        do {
            out.println("\nMENU:");
            int index = 1;
            for (MealType type : MealType.values()) {
                out.printf("%d. %s - %d KZT%n", index++, type.getLabel(), type.getBasePrice());
            }
            MealType type = MealType.values()[askInt("Choose meal (1-3): ", 1, 3) - 1];
            out.println("Extras: 1=Cheese (+400), 2=Sauce (+200), 3=Olives (+300)");
            out.print("Extras (e.g. 1,3 or Enter for none): ");
            String extras = input.nextLine().trim();
            boolean cheese = false, sauce = false, olives = false;
            if (!extras.isEmpty()) {
                for (String part : extras.split(",")) {
                    switch (part.trim()) {
                        case "1" -> cheese = true;
                        case "2" -> sauce = true;
                        case "3" -> olives = true;
                        default -> { out.println("Invalid extras; try this meal again."); return; }
                    }
                }
            }
            items.add(new MealSelection(type, cheese, sauce, olives));
            another = askInt("Add another meal? 1=Yes, 0=No: ", 0, 1) == 1;
        } while (another);

        out.println("Discount: 1=Regular (0%), 2=Student (10%), 3=Loyalty (15%)");
        DiscountType discount = DiscountType.values()[askInt("Choose discount: ", 1, 3) - 1];
        OrderSummary order = restaurant.placeOrder(customer, items, discount);
        printReceipt(order);
    }

    private void listOrders() {
        List<OrderSummary> orders = restaurant.getOrders();
        if (orders.isEmpty()) { out.println("No orders yet."); return; }
        for (OrderSummary order : orders) {
            out.printf("#%d | %s | %s | %d KZT%n", order.getId(), order.getCustomer(),
                    order.getStatus(), order.getPrice().getTotal());
        }
    }

    private void advanceOrder() {
        int id = askInt("Order ID: ", 1, Integer.MAX_VALUE);
        OrderSummary updated = restaurant.advanceOrder(id);
        out.printf("Order #%d updated to %s%n", id, updated.getStatus());
    }

    public void printReceipt(OrderSummary order) {
        PriceSummary price = order.getPrice();
        out.println("\n----------- RECEIPT -----------");
        out.printf("Order #%d | Customer: %s%n", order.getId(), order.getCustomer());
        for (Meal item : order.getMeals()) {
            out.printf("  %-28s %5d KZT%n", item.getDescription(), item.getPrice());
        }
        out.printf("Subtotal: %d KZT%n", price.getSubtotal());
        out.printf("Discount: %s / -%d KZT%n", price.getDiscountName(), price.getDiscount());
        out.printf("TOTAL: %d KZT%n", price.getTotal());
        out.printf("Status: %s%n", order.getStatus());
        out.println("-------------------------------");
    }

    private int askInt(String prompt, int min, int max) {
        while (true) {
            out.print(prompt);
            String raw = input.nextLine().trim();
            try {
                int n = Integer.parseInt(raw);
                if (n >= min && n <= max) return n;
            } catch (NumberFormatException ignored) { }
            out.printf("Please enter a number from %d to %d.%n", min, max);
        }
    }
}
