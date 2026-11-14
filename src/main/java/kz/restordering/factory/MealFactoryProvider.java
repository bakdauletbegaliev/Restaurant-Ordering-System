package kz.restordering.factory;

import kz.restordering.model.MealType;

public final class MealFactoryProvider {
    private MealFactoryProvider() { }

    public static MealFactory forType(MealType type) {
        if (type == null) throw new IllegalArgumentException("Meal type is required");
        return switch (type) {
            case PIZZA -> new PizzaFactory();
            case BURGER -> new BurgerFactory();
            case SALAD -> new SaladFactory();
        };
    }
}
