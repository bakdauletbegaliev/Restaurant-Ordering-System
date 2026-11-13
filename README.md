# Restaurant Ordering System

**Software Design Patterns — Final Project**  
**Authors:** Bakdaulet Begaliev and Dias Sabit  
**Group:** SE-2526  
**GitHub target:** https://github.com/bakdauletbegaliev/Restaurant-Ordering-System  
**Language:** Java 17+, no external dependencies

## Project Overview

Restaurant Ordering System is a Java console application for creating and managing restaurant orders. Users can select meals, add extra ingredients, choose a discount, view the final price, and update order statuses. The kitchen and customer receive notifications when an order status changes.

## Main Features

- Create orders with pizza, burgers, and salads.
- Customize meals with cheese, sauce, and olives.
- Add multiple meals to one order.
- Apply no discount, a student discount, or a loyalty discount.
- Calculate the order total and display an order summary.
- Track orders through PLACED, PREPARING, READY, and SERVED.
- Notify the kitchen and customer about status changes.

## Design Patterns

| Pattern | Purpose | Main Classes |
| --- | --- | --- |
| Factory Method | Creates different types of meals | `MealFactory`, `PizzaFactory`, `BurgerFactory`, `SaladFactory` |
| Decorator | Adds optional ingredients to meals | `MealDecorator`, `CheeseDecorator`, `SauceDecorator`, `OlivesDecorator` |
| Builder | Creates orders containing several meals | `Order.Builder` |
| Observer | Sends notifications when an order status changes | `OrderTracker`, `KitchenObserver`, `CustomerObserver` |
| Strategy | Applies different discount calculations | `DiscountStrategy`, `StudentDiscount`, `LoyaltyDiscount`, `NoDiscount` |
| Facade | Connects the ordering process through a simple interface | `RestaurantFacade` |

The patterns are integrated into one ordering workflow, from meal creation to payment calculation and status notifications.

## How to Run

Install JDK 17 or newer and open a terminal in the project root.

**Windows — interactive menu:**

`scripts\run.bat`

**Windows — automatic demonstration:**

`scripts\run.bat --demo`

**macOS / Linux:**

`bash scripts/run.sh`

`bash scripts/run.sh --demo`

## How to Run Tests

**Windows:**

`scripts\test.bat`

**macOS / Linux:**

`bash scripts/test.sh`

The project includes eight automated tests.

## Project Structure

- `src/main/java/` — application code and design patterns.
- `src/test/java/` — automated tests.
- `scripts/` — scripts for running the application and tests.
- `docs/` — UML class and sequence diagrams.
- `screenshots/` — application output and test screenshots.
- `report/` — final PDF project report.
- `defense/` — preparation notes for the project defense.
- `TEAM_CONTRIBUTIONS.md` — team responsibility summary.

## Future Improvements

Possible extensions include database storage, online payments, customer accounts, and a graphical or web interface.
