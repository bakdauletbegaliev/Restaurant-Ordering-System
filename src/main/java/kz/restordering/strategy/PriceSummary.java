package kz.restordering.strategy;

public final class PriceSummary {
    private final int subtotal;
    private final int discount;
    private final int total;
    private final String discountName;

    public PriceSummary(int subtotal, int discount, String discountName) {
        if (subtotal < 0 || discount < 0 || discount > subtotal) {
            throw new IllegalArgumentException("Invalid prices or discount");
        }
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = subtotal - discount;
        this.discountName = discountName;
    }

    public int getSubtotal() { return subtotal; }
    public int getDiscount() { return discount; }
    public int getTotal() { return total; }
    public String getDiscountName() { return discountName; }
}
