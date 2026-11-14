package kz.restordering.decorator;

import kz.restordering.model.Meal;

public final class CheeseDecorator extends MealDecorator {
    public CheeseDecorator(Meal wrapped) { super(wrapped); }

    @Override
    public String getDescription() { return wrapped.getDescription() + " + Cheese"; }

    @Override
    public int getPrice() { return wrapped.getPrice() + 400; }
}
