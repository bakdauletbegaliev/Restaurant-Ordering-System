package kz.restordering.facade;

import kz.restordering.model.MealType;

public final class MealSelection {
    private final MealType type;
    private final boolean extraCheese;
    private final boolean extraSauce;
    private final boolean extraOlives;

    public MealSelection(MealType type, boolean extraCheese, boolean extraSauce, boolean extraOlives) {
        if (type == null) throw new IllegalArgumentException("Meal type required");
        this.type = type;
        this.extraCheese = extraCheese;
        this.extraSauce = extraSauce;
        this.extraOlives = extraOlives;
    }

    public MealType getType() { return type; }
    public boolean hasCheese() { return extraCheese; }
    public boolean hasSauce() { return extraSauce; }
    public boolean hasOlives() { return extraOlives; }
}
