package kz.restordering.decorator;

import kz.restordering.model.Meal;

public final class OlivesDecorator extends MealDecorator {
    public OlivesDecorator(Meal wrapped) { super(wrapped); }

    @Override
    public String getDescription() { return wrapped.getDescription() + " + Olives"; }

    @Override
    public int getPrice() { return wrapped.getPrice() + 300; }
}
