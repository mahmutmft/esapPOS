<p align="center">
  <img src="docs/assets/esap-banner.png" alt="ESAP" width="100%">
</p>

# ESAP

[![Java 21](https://img.shields.io/badge/Java-21-E11F21?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Maven](https://img.shields.io/badge/build-Maven-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
![Tests](https://img.shields.io/badge/tests-46%20passing-brightgreen)
![Status](https://img.shields.io/badge/status-early%20development-orange)

**ESAP** is an open-source point-of-sale application being built for restaurants in North Macedonia.

The project started as a Java command-line prototype and is growing into a complete restaurant management application for owners, administrators, and waiters. ESAP will bring ordering, tables, stock, sales, and reporting together in one system designed around local restaurant workflows.

> ESAP is currently in early development. The core business logic is working and PostgreSQL persistence is being introduced, while the graphical application and production features are still planned.

## Current Features

- Item creation with automatic IDs and price validation
- Restaurant orders with multiple items and quantities
- Duplicate order items merged by item ID
- Order totals and single-confirmation protection
- Restaurant tables with waiter assignment and availability status
- Stock deliveries with weighted average purchase prices
- Stock sales, adjustments, movement history, and low-stock reporting
- Total inventory value calculation
- Sales history preserved independently from active orders
- Sales reports by waiter, month, and year
- PostgreSQL schema for the core restaurant entities
- JDBC item persistence foundation using prepared statements
- Command-line simulations for the main business workflows
- Unit and edge-case coverage with JUnit 5

## Product Vision

### Administration

Administrators will be able to:

- Track daily, monthly, and yearly sales
- Review turnover by waiter and time period
- Add new stock deliveries and monitor inventory value
- View stock movement history and low-stock warnings
- Create and manage menu items
- Create and manage waiter accounts
- Review restaurant activity from a central dashboard

### Waiter Workflow

Waiters will be able to:

- Sign in with their own account
- Open a visual restaurant floor map
- Select and manage an assigned table
- Create and update table orders
- Add items and quantities to orders
- Review table totals
- Complete payment and release the table

### Languages

The final application is planned to support:

| Language | Planned support |
| --- | --- |
| Macedonian | Full interface |
| Albanian | Full interface |
| English | Full interface |

## Roadmap

- [x] Core item, order, table, stock, waiter, and sale models
- [x] Stock movement tracking and sales reporting logic
- [x] Unit and edge-case test suite
- [x] PostgreSQL schema and item persistence foundation
- [ ] Complete PostgreSQL repositories for all entities
- [ ] Administrator authentication and dashboard
- [ ] Waiter accounts and permissions
- [ ] Visual restaurant floor and table map
- [ ] Full order and payment workflow
- [ ] Menu and inventory management screens
- [ ] Macedonian, Albanian, and English localization
- [ ] Receipt generation and printing
- [ ] Production-ready desktop or web interface

## Quick Start

### Requirements

- Java 21
- Maven 3.9 or newer, or the Maven integration bundled with IntelliJ IDEA
- PostgreSQL for database-backed development

### Clone the project

```bash
git clone https://github.com/mahmutmft/esapPOS.git
cd esapPOS
```

### Run the tests

```bash
mvn test
```

### Build the project

```bash
mvn package
```

### Run a simulation

Until the graphical application is introduced, individual workflows can be explored through the simulation classes:

```bash
java -cp target/classes com.mahmutmft.pos.simulation.OrderStockSimulation
```

Other available simulations cover orders, sales, stock, stock movements, tables, and waiter accounts.

### Database foundation

The current JDBC connection expects a local PostgreSQL database named `esap`, the user `postgres`, and the password in the `ESAP_DB_PASSWORD` environment variable. The database tables are defined in `database/schema.sql`.

In PowerShell, set the password for the current terminal session with:

```powershell
$env:ESAP_DB_PASSWORD = "your-local-password"
```

Never commit the local `.env` file or a real database password.

## Project Structure

```text
src/
├── main/java/com/mahmutmft/pos/
│   ├── item/          Item catalog and validation
│   ├── order/         Orders and order processing
│   ├── sale/          Sales history and reporting
│   ├── stock/         Inventory and stock movements
│   ├── table/         Restaurant tables and payments
│   ├── waiter/        Waiter accounts
│   ├── repository/    JDBC persistence repositories
│   └── simulation/    Executable workflow examples
├── main/java/database/
│   └── Database connection and executable JDBC examples
└── test/java/com/mahmutmft/pos/
    ├── item/
    ├── order/
    ├── sale/
    ├── stock/
    └── table/
database/
└── schema.sql          PostgreSQL database schema
```

## Testing

The test suite currently contains 46 unit and edge-case tests. It covers normal behavior as well as invalid prices, invalid quantities, insufficient stock, repeated order confirmation, missing waiter assignments, sale-history isolation, and JDBC item repository behavior.

Run a specific test class with:

```bash
mvn -Dtest=ItemEdgeCaseTest test
```

## Contributing

ESAP is intended to become a community-driven open-source project. Contributions involving restaurant workflows, Java architecture, user-interface development, testing, documentation, and localization are welcome.

Read [CONTRIBUTING.md](CONTRIBUTING.md) before submitting a change. All contributors must follow the [Code of Conduct](CODE_OF_CONDUCT.md). Please report suspected vulnerabilities privately according to the [Security Policy](SECURITY.md).

## Project Status

ESAP is not ready for use in a production restaurant. The repository currently represents the tested application core and the foundation for the future user-facing POS system.

## License

ESAP is open-source software licensed under the [MIT License](LICENSE).
