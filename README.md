# Restaurant Ordering System - Java Design Patterns Final Project

**Authors:** Bakdaulet Begaliev and Dias Sabit  
**Group:** SE-2526  
**GitHub target:** https://github.com/bakdauletbegaliev/Restaurant-Ordering-System  
**Language:** Java 17+, no external dependencies

A working console application where a user creates food orders, customizes meals, selects a discount, sees a printed receipt and moves an order from `PLACED` to `PREPARING`, `READY`, and `SERVED`. Both kitchen and customer receive notifications each time an order changes status.

## 30-second start

1. Install **JDK 17 or newer** (not only a JRE). Verify `java -version` and `javac -version`.
2. Extract the ZIP and open a terminal inside the project root.
3. On **Windows PowerShell** run `scripts\run.bat --demo`, or `scripts\run.bat` to use the menu.
4. On **macOS/Linux** run `bash scripts/run.sh --demo`, or `bash scripts/run.sh`.
5. To run **8 tests**: Windows `scripts\test.bat`; macOS/Linux `bash scripts/test.sh`.

This project deliberately uses no GUI, database, frameworks, external JARs or paid services. It can be demonstrated on a classroom computer with Java installed. In interactive mode, the orders exist only while the application is running.

## Six distinct patterns and individual ownership

| Developer | Pattern | Classes | Example |
|---|---|---|---|
| **Bakdaulet** | Factory Method | `MealFactory`, `PizzaFactory`, `BurgerFactory`, `SaladFactory` | `createMeal()` |
| **Bakdaulet** | Decorator | `MealDecorator`, `CheeseDecorator`, `SauceDecorator`, `OlivesDecorator` | `new CheeseDecorator(pizza)` |
| **Bakdaulet** | Builder | `Order.Builder` | `new Order.Builder(...).addMeal(...).build()` |
| **Dias** | Observer | `OrderTracker`, `OrderObserver`, `KitchenObserver`, `CustomerObserver` | status change broadcasts to subscribers |
| **Dias** | Strategy | `DiscountStrategy`, 3 discounts, `Checkout` | switch discount without rewriting checkout |
| **Dias** | Facade | `RestaurantFacade` | `placeOrder(...)`, `advanceOrder(...)` |

**Integration:** `RestaurantFacade` calls Factory Method to create meals, Decorator to customize them, Builder to assemble the order, Strategy to calculate totals, and Observer to notify the kitchen and customer. All patterns work in the same end-to-end flow.

## Live demo script

Use the automatic demonstration:

```powershell
scripts\run.bat --demo
```

Expected first-order calculation:
- Pizza 2,500 + Cheese 400 + Sauce 200 = **3,100 KZT**
- Burger 2,000 + Olives 300 = **2,300 KZT**
- Subtotal **5,400 KZT**; Student discount 10% = **540 KZT**
- **Total 4,860 KZT**

Observe two lines per status change (`[KITCHEN]` and `[CUSTOMER]`).

## Interactive use

Choose `1` to make an order, provide a name, select one or more dishes, type extras such as `1,3`, then select a discount. Option `2` lists orders; option `3` moves the requested order to its next allowed status; option `0` exits. Entering an invalid number repeats the prompt; invalid order IDs and already-served orders print an error.

## Project contents

- `src/main/java/` - all Java source code.
- `src/test/java/` - dependency-free automated tests.
- `scripts/` - Windows and Linux/macOS run and test scripts.
- `docs/` - class and sequence UML diagrams (PNG, SVG, DOT).
- `screenshots/` - output captured from the *real running application*.
- `report/` - PDF project report for Moodle.
- `defense/` - individual explanations and defense questions in Russian.
- `Участники/Бакдаулет/` and `Участники/Диас/` - explicit per-person file responsibility maps.
- `TEAM_CONTRIBUTIONS.md` - division of responsibilities.

## What to submit

Moodle: upload `report/Restaurant_Ordering_System_Final_Report.pdf` **as PDF** and the complete project **as ZIP** (if the LMS has two different upload slots, use them separately). The lecturer may also ask you to show the repository and defend the code live. This ZIP does not automatically upload or push anything to GitHub.

## GitHub upload of the extracted project

```powershell
git clone https://github.com/bakdauletbegaliev/Restaurant-Ordering-System.git
# Copy the contents of this extracted project into that cloned folder.
git add .
git commit -m "Add complete restaurant ordering system with six patterns"
git push origin main
```

For a clearer contribution history, see `GITHUB_STEPS_RU.md` with sample logical commits and the note about real authorship.

## Future work

Database persistence, actual customer accounts, order cancellation rules, online payments, REST API, and web/mobile UI. None of these are claimed to exist in this educational console version.
