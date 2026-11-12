package kz.restordering.strategy;

public final class StudentDiscount implements DiscountStrategy {
    @Override
    public String getName() { return "Student (10%)"; }

    @Override
    public int calculateDiscount(int subtotal) { return subtotal * 10 / 100; }
}
