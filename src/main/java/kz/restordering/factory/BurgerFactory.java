package kz.restordering.factory;

import kz.restordering.model.BasicMeal;
import kz.restordering.model.Meal;

public final class BurgerFactory extends MealFactory {
    @Override
    public Meal createMeal() {
        return new BasicMeal("Burger", 2000);
    }
}
