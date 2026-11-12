package kz.restordering.strategy;

public enum DiscountType {
    REGULAR("Regular (0%)"),
    STUDENT("Student (10%)"),
    LOYALTY("Loyalty (15%)");

    private final String label;

    DiscountType(String label) { this.label = label; }

    public String getLabel() { return label; }

    public DiscountStrategy createStrategy() {
        return switch (this) {
            case REGULAR -> new NoDiscount();
            case STUDENT -> new StudentDiscount();
            case LOYALTY -> new LoyaltyDiscount();
        };
    }
}
