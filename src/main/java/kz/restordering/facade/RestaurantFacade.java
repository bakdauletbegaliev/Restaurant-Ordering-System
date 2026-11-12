package kz.restordering.facade;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kz.restordering.decorator.CheeseDecorator;
import kz.restordering.decorator.OlivesDecorator;
import kz.restordering.decorator.SauceDecorator;
import kz.restordering.factory.MealFactoryProvider;
import kz.restordering.model.Meal;
import kz.restordering.observer.CustomerObserver;
import kz.restordering.observer.KitchenObserver;
import kz.restordering.observer.OrderTracker;
import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;
import kz.restordering.strategy.Checkout;
import kz.restordering.strategy.DiscountType;
import kz.restordering.strategy.PriceSummary;

public final class RestaurantFacade {
    private final Map<Integer, Session> orders = new LinkedHashMap<>();
    private final PrintStream output;
    private int nextOrderId = 1;

    public RestaurantFacade(PrintStream output) {
        if (output == null) throw new IllegalArgumentException("Output is required");
        this.output = output;
    }

    public OrderSummary placeOrder(String customer, List<MealSelection> selections, DiscountType discountType) {
        if (selections == null || selections.isEmpty()) {
            throw new IllegalArgumentException("Add at least one meal");
        }
        if (discountType == null) throw new IllegalArgumentException("Discount type required");
        // Builder + Factory Method + Decorator (Bakdaulet)
        Order.Builder builder = new Order.Builder(nextOrderId, customer);
        for (MealSelection selection : selections) {
            if (selection == null) throw new IllegalArgumentException("Meal selection cannot be null");
            Meal meal = MealFactoryProvider.forType(selection.getType()).createMeal();
            if (selection.hasCheese()) meal = new CheeseDecorator(meal);
            if (selection.hasSauce()) meal = new SauceDecorator(meal);
            if (selection.hasOlives()) meal = new OlivesDecorator(meal);
            builder.addMeal(meal);
        }
        Order order = builder.build();
        // Strategy (Dias)
        PriceSummary price = new Checkout(discountType.createStrategy()).calculate(order);
        // Observer (Dias)
        OrderTracker tracker = new OrderTracker(order);
        tracker.subscribe(new KitchenObserver(output));
        tracker.subscribe(new CustomerObserver(output));
        tracker.changeStatus(OrderStatus.PLACED);
        orders.put(order.getId(), new Session(order, price, tracker));
        nextOrderId++;
        return snapshot(orders.get(order.getId()));
    }

    public OrderSummary advanceOrder(int orderId) {
        Session session = requiredSession(orderId);
        OrderStatus next = session.tracker.getStatus().next();
        if (next == null) throw new IllegalStateException("Order has already been served");
        session.tracker.changeStatus(next);
        return snapshot(session);
    }

    public OrderSummary getOrder(int id) { return snapshot(requiredSession(id)); }

    public List<OrderSummary> getOrders() {
        List<OrderSummary> result = new ArrayList<>();
        for (Session session : orders.values()) result.add(snapshot(session));
        return List.copyOf(result);
    }

    private Session requiredSession(int id) {
        Session session = orders.get(id);
        if (session == null) throw new IllegalArgumentException("Order #" + id + " not found");
        return session;
    }

    private static OrderSummary snapshot(Session session) {
        return new OrderSummary(session.order, session.price, session.tracker.getStatus());
    }

    private static final class Session {
        private final Order order;
        private final PriceSummary price;
        private final OrderTracker tracker;

        private Session(Order order, PriceSummary price, OrderTracker tracker) {
            this.order = order;
            this.price = price;
            this.tracker = tracker;
        }
    }
}
