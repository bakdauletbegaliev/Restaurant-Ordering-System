package kz.restordering.strategy;

public final class NoDiscount implements DiscountStrategy {
    @Override
    public String getName() { return "Regular (0%)"; }

    @Override
    public int calculateDiscount(int subtotal) { return 0; }
}
