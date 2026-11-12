package kz.restordering.strategy;

public interface DiscountStrategy {
    String getName();
    int calculateDiscount(int subtotal);
}
