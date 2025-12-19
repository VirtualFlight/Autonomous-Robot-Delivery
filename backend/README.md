## Backend — Architecture overview and design notes

This README gives a concise, text-first overview of the `backend` module: its high-level architecture, the main design patterns used, and a walkthrough of the inferred UML (class/package) relationships based on the code layout.

## Quick summary

- Language / framework: Java (Maven).
- Entry point: `RobotDeliveryApplication` in `com.robotdelivery`.
- Main layers: `controller` (HTTP/API), `service` (business logic), `repositorys` (data access), `model` (domain objects), `factory` (entity creation), `observer` (eventing/logging), `server` (TCP server for robots), and `config` (app and security configuration).

## Design patterns used

Below are the key design patterns used in the backend and where to find them.

- Factory pattern
  - Files: `factory/EntityFactory.java`, `factory/RestaurantFactory.java`
  - Purpose: centralize creation of domain objects (e.g., restaurants, orders) and encapsulate construction logic. Use this when object creation needs configuration or variable initialization.

- Observer / Publisher-Subscriber pattern
  - Files: `observer/EventPublisher.java`, `observer/LoggerObserver.java`, `observer/ConsoleLogger.java`
  - Purpose: decouple event producers (e.g., services or `TCPServer`) from consumers (loggers, analytics). `EventPublisher` broadcasts events; `LoggerObserver` and `ConsoleLogger` react to those events.

- Repository (Data Access Object) pattern
  - Files: `repositorys/*Repository.java` (e.g., `OrderRepository`, `RobotRepository`, `SensorDataRepository`)
  - Purpose: encapsulate persistence and query logic. Controllers and services depend on repositories to fetch and update domain objects.

- Layered MVC + Service layer
  - Files: `controller/*Controller.java` (API endpoints), `service/*Service.java` (business logic)
  - Purpose: controllers handle HTTP/API concerns and delegate to services which execute business rules and coordinate repositories and factories.

- Singleton / Server pattern (practical use)
  - File: `server/TCPServer.java`
  - Purpose: the `TCPServer` represents an application-level server that should be instantiated/managed centrally. In practice it's used as an application-wide service (behaves like a singleton within the app lifecycle).

- Utility
  - File: `util/PasswordUtil.java`
  - Purpose: stateless helper utilities (password hashing, checks).

## Inferred UML walkthrough (textual)

Below is an explanatory walkthrough of an inferred UML diagram derived from the repository layout. If you plan to add a visual UML image (PNG/SVG), this section maps nodes and relationships to the code.

Top-level packages (as nodes):

- com.robotdelivery.controller
  - Classes: `CustomerController`, `DeliveryController`, `MenuController`, `OrderController`, `RobotController`
  - Responsibilities: define API endpoints, parameter validation, return HTTP responses. Each controller depends on a corresponding service.

- com.robotdelivery.service
  - Classes: `CustomerService`, `DeliveryService`, `MenuService`, `OrderService`, `RobotService`
  - Responsibilities: encapsulate business logic. They depend on repository interfaces and factories, publish events via `EventPublisher`, and coordinate with `server.TCPServer` for robot comms.

- com.robotdelivery.repositorys
  - Classes: repository interfaces/implementations: `CustomerRepository`, `DeliveryRepository`, `MenuItemRepository`, `OrderRepository`, `OrderItemRepository`, `RobotRepository`, `SensorDataRepository`
  - Responsibilities: persist/retrieve model objects. Services call repositories for CRUD and querying.

- com.robotdelivery.model
  - Classes: domain entities like `Customer`, `Delivery`, `MenuItem`, `Order`, `OrderItem`, `Robot`, `SensorData`
  - Relationships: `Order` aggregates `OrderItem`s; `Order` references `Customer` and `Delivery`; `Robot` may record `SensorData`.

- com.robotdelivery.factory
  - Classes: `EntityFactory`, `RestaurantFactory`
  - Responsibilities: create domain model instances. Services call factories when new entities need to be built with more-than-trivial initialization.

- com.robotdelivery.observer
  - Classes: `EventPublisher`, `LoggerObserver`, `ConsoleLogger`
  - Responsibilities: `EventPublisher` exposes subscribe/publish API. Observers (loggers) register and receive events. Services publish events for state changes (order created, delivery assigned, robot status updates).

- com.robotdelivery.server
  - Class: `TCPServer`
  - Responsibilities: manage connections to physical robots (or simulated robots). The server receives sensor data (-> `SensorDataRepository`) and sends commands (from `RobotService`).

- com.robotdelivery.config
  - Classes: `ObserverConfig`, `SecurityConfig`
  - Responsibilities: Spring configuration — wiring observers/publisher, security filters, CORS, authentication, etc.

Key associations (UML arrows):

- Controller -> Service: dependency (uses)
- Service -> Repository: dependency (uses) for persistence
- Service -> Factory: uses to construct complex models
- Service -> EventPublisher: publishes events
- EventPublisher -> Observer(s): one-to-many (publish/subscribe)
- TCPServer -> SensorDataRepository: saves incoming sensor data
- RobotService <-> TCPServer: RobotService sends commands to TCPServer; TCPServer reports robot events back to RobotService via EventPublisher or callback

Example class-level note (Order flow):

1. `OrderController` receives API call to create an order.
2. `OrderService` validates business rules and uses `EntityFactory` (if needed) to create `Order` and `OrderItem` objects.
3. `OrderService` calls `OrderRepository.save(...)` to persist the order.
4. `OrderService` publishes an `OrderCreated` event via `EventPublisher`.
5. Observers (e.g., `LoggerObserver`) react; `DeliveryService` may subscribe and create a `Delivery` entry and schedule a `Robot`.

## Assumptions and notes

- UML: there is no image in this repo at `backend/`; the textual UML above was inferred strictly from the package and filename layout under `src/main/java/com/robotdelivery`.
- Persistence: repository interfaces are present, but concrete persistence technology (JPA, JDBC, in-memory) depends on implementation details in those files and `application.yml`.
- Event types: exact event classes and payloads are inferred (for example `OrderCreated`). If you want precise UML arrows for event classes, include the event DTOs or an image and I can update the diagram text and add a visual file.

## How to extend or document further

- Add a visual UML diagram (PlantUML, Mermaid or a PNG/SVG) under `backend/docs/` and update this file to link it.
- Document the event types published by `EventPublisher` (e.g., list of event names and payload shapes) so subscribers can be added safely.
- Add sequence diagrams for critical flows (order creation, robot assignment, sensor data ingestion).
- Add a README section describing how to run the backend (Maven goals, env vars) if you want runnable instructions here.

## Small checklist for next docs / infra improvements

- [ ] Add a `backend/docs/architecture.md` with a PlantUML or Mermaid diagram.
- [ ] Document repository implementations and required DB schema (if using SQL) or include an embedded DB configuration for local dev.
- [ ] Add developer quick start (build/test/run) using `mvn` and environment variables.