package kz.restordering.factory;

import kz.restordering.model.BasicMeal;
import kz.restordering.model.Meal;

public final class PizzaFactory extends MealFactory {
    @Override
    public Meal createMeal() {
        return new BasicMeal("Pizza", 2500);
    }
}
