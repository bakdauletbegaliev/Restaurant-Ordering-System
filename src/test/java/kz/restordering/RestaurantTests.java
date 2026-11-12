package kz.restordering;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import kz.restordering.decorator.CheeseDecorator;
import kz.restordering.decorator.OlivesDecorator;
import kz.restordering.decorator.SauceDecorator;
import kz.restordering.facade.MealSelection;
import kz.restordering.facade.OrderSummary;
import kz.restordering.facade.RestaurantFacade;
import kz.restordering.factory.BurgerFactory;
import kz.restordering.factory.MealFactory;
import kz.restordering.factory.PizzaFactory;
import kz.restordering.model.Meal;
import kz.restordering.model.MealType;
import kz.restordering.observer.OrderObserver;
import kz.restordering.observer.OrderTracker;
import kz.restordering.order.Order;
import kz.restordering.order.OrderStatus;
import kz.restordering.strategy.Checkout;
import kz.restordering.strategy.DiscountType;

public final class RestaurantTests {
    private static int passed;

    public static void main(String[] args) {
        run("Factory Method", RestaurantTests::testFactory);
        run("Decorator stacking", RestaurantTests::testDecorator);
        run("Builder creates immutable multi-meal order", RestaurantTests::testBuilder);
        run("Strategy changes total", RestaurantTests::testStrategy);
        run("Observer receives and stops notifications", RestaurantTests::testObserver);
        run("Facade integrates six patterns", RestaurantTests::testFacade);
        run("Status guard", RestaurantTests::testInvalidTransition);
        run("Invalid order rejected", RestaurantTests::testEmptyOrder);
        System.out.println("ALL " + passed + " TESTS PASSED");
    }

    private static void testFactory() {
        MealFactory pizzaFactory = new PizzaFactory();
        MealFactory burgerFactory = new BurgerFactory();
        eq("Pizza", pizzaFactory.createMeal().getDescription());
        eq(2500, pizzaFactory.createMeal().getPrice());
        eq(2000, burgerFactory.createMeal().getPrice());
    }

    private static void testDecorator() {
        Meal meal = new OlivesDecorator(new SauceDecorator(new CheeseDecorator(new PizzaFactory().createMeal())));
        eq("Pizza + Cheese + Sauce + Olives", meal.getDescription());
        eq(3400, meal.getPrice());
    }

    private static void testBuilder() {
        Order order = new Order.Builder(1, " Test ").addMeal(new PizzaFactory().createMeal())
                .addMeal(new BurgerFactory().createMeal()).build();
        eq(4500, order.getSubtotal());
        eq("Test", order.getCustomerName());
        expect(UnsupportedOperationException.class,
                () -> order.getMeals().add(new PizzaFactory().createMeal()));
    }

    private static void testStrategy() {
        Order order = new Order.Builder(2, "Student").addMeal(new PizzaFactory().createMeal()).build();
        eq(2500, new Checkout(DiscountType.REGULAR.createStrategy()).calculate(order).getTotal());
        eq(2250, new Checkout(DiscountType.STUDENT.createStrategy()).calculate(order).getTotal());
        eq(2125, new Checkout(DiscountType.LOYALTY.createStrategy()).calculate(order).getTotal());
    }

    private static void testObserver() {
        Order order = new Order.Builder(3, "Observer").addMeal(new PizzaFactory().createMeal()).build();
        OrderTracker tracker = new OrderTracker(order);
        List<OrderStatus> seen = new ArrayList<>();
        OrderObserver listener = (o, status) -> seen.add(status);
        tracker.subscribe(listener);
        tracker.changeStatus(OrderStatus.PLACED);
        tracker.changeStatus(OrderStatus.PREPARING);
        tracker.unsubscribe(listener);
        tracker.changeStatus(OrderStatus.READY);
        eq(List.of(OrderStatus.PLACED, OrderStatus.PREPARING), seen);
    }

    private static void testFacade() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        RestaurantFacade facade = new RestaurantFacade(new PrintStream(buffer));
        OrderSummary summary = facade.placeOrder("Aruzhan", List.of(
                new MealSelection(MealType.PIZZA, true, true, false),
                new MealSelection(MealType.BURGER, false, false, true)), DiscountType.STUDENT);

        eq(5400, summary.getPrice().getSubtotal());
        eq(540, summary.getPrice().getDiscount());
        eq(4860, summary.getPrice().getTotal());
        eq(2, summary.getMeals().size());
        eq(OrderStatus.PLACED, summary.getStatus());
        contains(buffer.toString(), "[KITCHEN]");
        contains(buffer.toString(), "[CUSTOMER]");
        facade.advanceOrder(summary.getId());
        facade.advanceOrder(summary.getId());
        eq(OrderStatus.READY, facade.getOrder(summary.getId()).getStatus());
        eq(1, facade.getOrders().size());
    }

    private static void testInvalidTransition() {
        Order order = new Order.Builder(4, "Test").addMeal(new PizzaFactory().createMeal()).build();
        OrderTracker tracker = new OrderTracker(order);
        expect(IllegalStateException.class, () -> tracker.changeStatus(OrderStatus.READY));
        tracker.changeStatus(OrderStatus.PLACED);
        expect(IllegalStateException.class, () -> tracker.changeStatus(OrderStatus.SERVED));
        tracker.changeStatus(OrderStatus.PREPARING);
        tracker.changeStatus(OrderStatus.READY);
        tracker.changeStatus(OrderStatus.SERVED);
        expect(IllegalStateException.class, () -> tracker.changeStatus(OrderStatus.SERVED));
    }

    private static void testEmptyOrder() {
        expect(IllegalStateException.class, () -> new Order.Builder(5, "Empty").build());
        RestaurantFacade facade = new RestaurantFacade(System.out);
        expect(IllegalArgumentException.class,
                () -> facade.placeOrder("Empty", List.of(), DiscountType.REGULAR));
    }

    private static void run(String name, Runnable test) {
        try {
            test.run();
            passed++;
            System.out.println("[PASS] " + name);
        } catch (Throwable cause) {
            System.err.println("[FAIL] " + name + ": " + cause);
            throw cause;
        }
    }

    private static void eq(Object expected, Object actual) {
        if (!expected.equals(actual)) throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void contains(String text, String fragment) {
        if (!text.contains(fragment)) throw new AssertionError("Expected to find " + fragment);
    }

    private static void expect(Class<? extends Throwable> expected, Runnable action) {
        try { action.run(); } catch (Throwable ex) {
            if (expected.isInstance(ex)) return;
            throw new AssertionError("Expected " + expected.getName() + ", got " + ex, ex);
        }
        throw new AssertionError("Expected " + expected.getName() + " but no exception was thrown");
    }
}
