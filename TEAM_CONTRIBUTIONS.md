# Team responsibilities - SE-2526

**Bakdaulet Begaliev**
- Factory Method: creates Pizza, Burger and Salad via concrete factories (`factory/`).
- Decorator: adds Cheese, Sauce and Olives at runtime (`decorator/`).
- Builder: creates complete multi-meal orders and validates them (`order/Order.java`).
- Defense: describe when a meal is created, wrapped, and added to an order.

**Dias Sabit**
- Observer: `OrderTracker`, `KitchenObserver`, `CustomerObserver` (`observer/`).
- Strategy: `Checkout`, three interchangeable discount algorithms (`strategy/`).
- Facade: `RestaurantFacade` coordinates the whole checkout and status flow (`facade/RestaurantFacade.java`).
- Defense: describe discount choice and who receives status updates.

**Shared integration:** console interface, integration tests, documentation, diagrams and PDF report. These folders describe a suggested division of code responsibility for the defense; they are not evidence of historical git authorship or the students' actual independent implementation.

**Six patterns (not six classes):** Factory Method, Decorator, Builder, Observer, Strategy, Facade.

**Required individual minimum:** 2 patterns per student; this project assigns 3 per student.
