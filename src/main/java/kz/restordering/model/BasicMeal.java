package kz.restordering.model;

public final class BasicMeal implements Meal {
    private final String description;
    private final int price;

    public BasicMeal(String description, int price) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Meal description is required");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Meal price cannot be negative");
        }
        this.description = description;
        this.price = price;
    }

    @Override
    public String getDescription() { return description; }

    @Override
    public int getPrice() { return price; }
}
