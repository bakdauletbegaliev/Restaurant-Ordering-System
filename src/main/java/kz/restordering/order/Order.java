package kz.restordering.order;

import kz.restordering.model.Meal;
import java.util.ArrayList;
import java.util.List;

public final class Order {
    private final int id;
    private final String customerName;
    private final List<Meal> meals;

    private Order(Builder builder) {
        this.id = builder.id;
        this.customerName = builder.customerName;
        this.meals = List.copyOf(builder.meals);
    }

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public List<Meal> getMeals() { return meals; }
    public int getSubtotal() {
        return meals.stream().mapToInt(Meal::getPrice).sum();
    }

    public static final class Builder {
        private final int id;
        private final String customerName;
        private final List<Meal> meals = new ArrayList<>();

        public Builder(int id, String customerName) {
            if (id <= 0) throw new IllegalArgumentException("Order ID must be positive");
            if (customerName == null || customerName.isBlank()) {
                throw new IllegalArgumentException("Customer name is required");
            }
            this.id = id;
            this.customerName = customerName.trim();
        }

        public Builder addMeal(Meal meal) {
            if (meal == null) throw new IllegalArgumentException("Meal cannot be null");
            meals.add(meal);
            return this;
        }

        public Order build() {
            if (meals.isEmpty()) throw new IllegalStateException("Order needs at least one meal");
            return new Order(this);
        }
    }
}
