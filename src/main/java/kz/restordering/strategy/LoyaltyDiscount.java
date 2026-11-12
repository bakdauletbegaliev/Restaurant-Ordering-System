package kz.restordering.strategy;

public final class LoyaltyDiscount implements DiscountStrategy {
    @Override
    public String getName() { return "Loyalty (15%)"; }

    @Override
    public int calculateDiscount(int subtotal) { return subtotal * 15 / 100; }
}
