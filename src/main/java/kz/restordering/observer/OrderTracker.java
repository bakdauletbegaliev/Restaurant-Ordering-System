package kz.restordering.observer;

import java.util.ArrayList;
import java.util.List;
import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;

public final class OrderTracker {
    private final Order order;
    private final List<OrderObserver> subscribers = new ArrayList<>();
    private OrderStatus status;

    public OrderTracker(Order order) {
        if (order == null) throw new IllegalArgumentException("Order is required");
        this.order = order;
    }

    public void subscribe(OrderObserver observer) {
        if (observer == null) throw new IllegalArgumentException("Observer is required");
        if (!subscribers.contains(observer)) subscribers.add(observer);
    }

    public void unsubscribe(OrderObserver observer) { subscribers.remove(observer); }

    public OrderStatus getStatus() { return status; }

    public void changeStatus(OrderStatus next) {
        if (next == null) throw new IllegalArgumentException("Status is required");
        OrderStatus expected = status == null ? OrderStatus.PLACED : status.next();
        if (next != expected) {
            throw new IllegalStateException("Illegal order status transition: " + status + " -> " + next);
        }
        status = next;
        for (OrderObserver observer : List.copyOf(subscribers)) {
            observer.onStatusChanged(order, status);
        }
    }
}
