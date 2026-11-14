package kz.restordering.order;

public enum OrderStatus {
    PLACED, PREPARING, READY, SERVED;

    public OrderStatus next() {
        return switch (this) {
            case PLACED -> PREPARING;
            case PREPARING -> READY;
            case READY -> SERVED;
            case SERVED -> null;
        };
    }
}
