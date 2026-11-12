package kz.restordering.observer;

import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;

public interface OrderObserver {
    void onStatusChanged(Order order, OrderStatus status);
}
