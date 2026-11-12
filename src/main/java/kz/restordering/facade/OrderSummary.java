package kz.restordering.facade;

import java.util.List;
import kz.restordering.model.Meal;
import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;
import kz.restordering.strategy.PriceSummary;

public final class OrderSummary {
    private final Order order;
    private final PriceSummary price;
    private final OrderStatus status;

    public OrderSummary(Order order, PriceSummary price, OrderStatus status) {
        this.order = order;
        this.price = price;
        this.status = status;
    }

    public int getId() { return order.getId(); }
    public String getCustomer() { return order.getCustomerName(); }
    public List<Meal> getMeals() { return order.getMeals(); }
    public PriceSummary getPrice() { return price; }
    public OrderStatus getStatus() { return status; }
}
