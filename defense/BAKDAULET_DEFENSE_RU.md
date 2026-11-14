# Бакдаулет: краткая подготовка к защите

## Моя часть: Factory Method, Decorator, Builder

**1. Factory Method - создание еды**
- Простыми словами: мы не пишем `new BasicMeal(...)` в интерфейсе заказа. Для каждого блюда есть отдельная фабрика.
- Открыть: `factory/MealFactory.java`, `PizzaFactory.java`, `BurgerFactory.java`, `SaladFactory.java`.
- Где вызов: `RestaurantFacade.placeOrder()` -> `MealFactoryProvider.forType(...).createMeal()`.
- Плюс: можно добавить новый тип блюда, сохранив общую идею создания через фабрики.
- Минус: больше классов. Не нужен, если одна простая модель и ничего не меняется.

**2. Decorator - дополнительные ингредиенты**
- Простыми словами: "оборачиваем" готовое блюдо и добавляем описание/цену.
- Открыть: `Meal`, `MealDecorator`, `CheeseDecorator`, `SauceDecorator`, `OlivesDecorator`.
- Где вызов: `RestaurantFacade.placeOrder()` -> `new CheeseDecorator(meal)` и другие.
- Пример: Pizza 2500 -> Cheese +400 -> Sauce +200 = 3100 KZT.
- Плюс: комбинации добавок без классов типа `PizzaWithCheeseAndSauce`.
- Минус: много маленьких объектов-обёрток; не нужен, когда вариантов нет.

**3. Builder - сборка заказа**
- Простыми словами: добавляем блюда по одному, потом `build()` создаёт готовый заказ.
- Открыть: `order/Order.java`, вложенный класс `Builder`.
- Где вызов: `new Order.Builder(id, customer)`, `builder.addMeal(meal)`, `builder.build()`.
- Плюс: удобная сборка нескольких блюд и проверка заказа на пустоту.
- Минус: лишний класс для объекта с 1-2 параметрами.

## Моя цепочка
`MealType -> Factory -> Meal -> Decorators -> Order.Builder -> Order`

## Что показать на защите
`run.bat --demo`; первый заказ: Pizza+Cheese+Sauce = 3100, Burger+Olives = 2300, subtotal = 5400. Объясните, почему Decorator реализует `Meal`, а Builder возвращает один `Order`.

## Вопросы
**Factory Method vs simple factory?** Factory Method - `MealFactory` с переопределяемым `createMeal()`; concrete factories решают, какой `Meal` создать. Provider только выбирает нужную concrete factory.

**Decorator vs inheritance?** С наследованием нужно много комбинаций подклассов; Decorator складывает эффекты объектов во время выполнения.

**Builder vs Factory?** Factory создаёт отдельное блюдо; Builder пошагово собирает целый заказ.
