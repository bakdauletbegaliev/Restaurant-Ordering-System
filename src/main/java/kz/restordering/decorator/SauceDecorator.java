package kz.restordering.decorator;

import kz.restordering.model.Meal;

public final class SauceDecorator extends MealDecorator {
    public SauceDecorator(Meal wrapped) { super(wrapped); }

    @Override
    public String getDescription() { return wrapped.getDescription() + " + Sauce"; }

    @Override
    public int getPrice() { return wrapped.getPrice() + 200; }
}
