package kz.restordering.factory;

import kz.restordering.model.BasicMeal;
import kz.restordering.model.Meal;

public final class SaladFactory extends MealFactory {
    @Override
    public Meal createMeal() {
        return new BasicMeal("Salad", 1500);
    }
}
