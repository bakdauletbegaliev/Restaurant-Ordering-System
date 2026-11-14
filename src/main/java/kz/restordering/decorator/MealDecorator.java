package kz.restordering.decorator;

import kz.restordering.model.Meal;

public abstract class MealDecorator implements Meal {
    protected final Meal wrapped;

    protected MealDecorator(Meal wrapped) {
        if (wrapped == null) throw new IllegalArgumentException("Meal is required");
        this.wrapped = wrapped;
    }
}
