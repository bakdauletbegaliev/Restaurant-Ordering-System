package kz.restordering.observer;

import java.io.PrintStream;
import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;

public final class CustomerObserver implements OrderObserver {
    private final PrintStream output;

    public CustomerObserver(PrintStream output) {
        if (output == null) throw new IllegalArgumentException("Output is required");
        this.output = output;
    }

    @Override
    public void onStatusChanged(Order order, OrderStatus status) {
        output.printf("[CUSTOMER] %s: Order #%d is %s%n", order.getCustomerName(), order.getId(), status);
    }
}
