# Диас: краткая подготовка к защите

## Моя часть: Observer, Strategy, Facade

**1. Observer - уведомление о статусе**
- Простыми словами: один заказ меняет статус, подписчики получают сообщение.
- Открыть: `observer/OrderObserver.java`, `OrderTracker.java`, `KitchenObserver.java`, `CustomerObserver.java`.
- Где вызов: `RestaurantFacade.placeOrder()` подписывает кухню и клиента; `advanceOrder()` вызывает `tracker.changeStatus(...)`.
- Метод `changeStatus()` вызывает `observer.onStatusChanged(order, status)` для каждого подписчика.
- Плюс: можно добавить нового получателя без переписывания OrderTracker.
- Минус: сложнее отслеживать события при большом числе подписчиков.

**2. Strategy - скидки**
- Простыми словами: разные способы расчёта скидки используют один интерфейс.
- Открыть: `DiscountStrategy`, `NoDiscount`, `StudentDiscount`, `LoyaltyDiscount`, `Checkout`.
- Где вызов: `new Checkout(discountType.createStrategy()).calculate(order)`.
- Пример: subtotal 5400, Student 10% = 540, total = 4860 KZT.
- Плюс: новую скидку можно добавить отдельно от `Checkout`.
- Минус: больше отдельных классов, чем простой `if` для двух случаев.

**3. Facade - единая точка входа**
- Простыми словами: клиенту не нужно знать пять других паттернов; он вызывает два метода.
- Открыть: `facade/RestaurantFacade.java`.
- Главные методы: `placeOrder()` и `advanceOrder()`.
- `placeOrder()` создаёт блюдо, добавляет ингредиенты, строит заказ, считает сумму, подписывает наблюдателей, отправляет статус PLACED.
- `advanceOrder()` меняет статус и запускает новые сообщения.
- Плюс: простой интерфейс и связанная работа всех паттернов.
- Минус: Facade может разрастаться, если делать в нём всё подряд.

## Моя цепочка
`RestaurantFacade -> Checkout (Strategy) -> OrderTracker (Observer) -> Kitchen + Customer`

## Что показать
Запустите `scripts\run.bat --demo`: при PLACED, PREPARING, READY, SERVED печатаются сообщения `[KITCHEN]` и `[CUSTOMER]`. Покажите `Checkout`, где скидка рассчитывается через интерфейс `DiscountStrategy`.

## Вопросы
**Observer vs Strategy?** Observer рассылает событие многим подписчикам; Strategy выбирает один алгоритм расчёта.

**Facade vs Adapter?** Facade упрощает взаимодействие с несколькими частями; Adapter переводит один несовместимый интерфейс в другой.

**Почему статус нельзя сразу перевести в SERVED?** `OrderTracker.changeStatus()` проверяет последовательность PLACED -> PREPARING -> READY -> SERVED.
