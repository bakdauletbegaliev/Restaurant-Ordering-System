package kz.restordering.strategy;

import kz.restordering.order.Order;

public final class Checkout {
    private final DiscountStrategy discountStrategy;

    public Checkout(DiscountStrategy discountStrategy) {
        if (discountStrategy == null) throw new IllegalArgumentException("Discount strategy required");
        this.discountStrategy = discountStrategy;
    }

    public PriceSummary calculate(Order order) {
        if (order == null) throw new IllegalArgumentException("Order required");
        int subtotal = order.getSubtotal();
        int discount = discountStrategy.calculateDiscount(subtotal);
        return new PriceSummary(subtotal, discount, discountStrategy.getName());
    }
}
