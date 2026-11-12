package kz.restordering.observer;

import java.io.PrintStream;
import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;

public final class KitchenObserver implements OrderObserver {
    private final PrintStream output;

    public KitchenObserver(PrintStream output) {
        if (output == null) throw new IllegalArgumentException("Output is required");
        this.output = output;
    }

    @Override
    public void onStatusChanged(Order order, OrderStatus status) {
        output.printf("[KITCHEN] Order #%d -> %s%n", order.getId(), status);
    }
}
