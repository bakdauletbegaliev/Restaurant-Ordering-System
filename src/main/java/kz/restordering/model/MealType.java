package kz.restordering.model;

public enum MealType {
    PIZZA("Pizza", 2500),
    BURGER("Burger", 2000),
    SALAD("Salad", 1500);

    private final String label;
    private final int basePrice;

    MealType(String label, int basePrice) {
        this.label = label;
        this.basePrice = basePrice;
    }

    public String getLabel() { return label; }
    public int getBasePrice() { return basePrice; }
}
