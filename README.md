# TransactionTracker

A command-line personal finance management application built with Java 17. Track your income and expenses, categorize transactions, and monitor your financial balance — all stored locally in JSON format.

## Features

- **Add & manage transactions** — Log income and expenses with full details
- **Categorized expenses** — Food, Rent, Transport, Entertainment, Healthcare, Utilities, and more
- **Income sources** — Salary, Freelance, Investment, Business, Gift, and more
- **Payment methods** — Cash, Credit Card, Debit Card, Bank Transfer, Digital Wallet
- **Repeatable transactions** — Flag recurring income or expenses
- **Tax-deductible tracking** — Mark income entries as tax-deductible
- **Budget tracking** — Flag expenses as budgeted or unbudgeted
- **Date range filtering** — Query transactions between any two dates
- **Balance overview** — View total income, total expenses, and net balance
- **JSON persistence** — Data is saved locally to `transactions.json` and survives restarts

## Tech Stack

- **Java 17**
- **Gradle** — Build automation
- **Jackson** — JSON serialization/deserialization (`jackson-databind`, `jackson-datatype-jsr310`)

## Project Structure

```
src/main/java/
├── app/
│   └── Main.java                        # Entry point
├── model/
│   ├── Transaction.java                 # Abstract base class
│   ├── Income.java                      # Income transaction
│   ├── Expense.java                     # Expense transaction
│   └── enums/
│       ├── TransactionType.java
│       ├── ExpenseCategory.java
│       ├── IncomeSource.java
│       └── PaymentMethod.java
├── repository/
│   ├── ITransactionRepository.java      # Repository interface
│   ├── InMemoryTransactionRepository.java
│   ├── FileTransactionRepository.java   # JSON-backed persistence
│   └── dto/
│       ├── TransactionDTO.java
│       └── TransactionMapper.java
├── service/
│   ├── ITransactionService.java         # Service interface
│   └── TransactionService.java
├── handler/
│   ├── TransactionInputHandler.java
│   └── TransactionOutputHandler.java
├── validator/
│   └── TransactionValidator.java
└── ui/
    └── ConsoleMenu.java                 # CLI menu
```

## Architecture

The project follows a **layered architecture** with clear separation of concerns:

- **Model** — Domain objects (`Transaction`, `Income`, `Expense`) with built-in validation
- **Repository** — Data access layer with an interface and two implementations (in-memory and file-based)
- **Service** — Business logic layer that orchestrates repository operations
- **Handler** — Input/output handling for the console interface
- **UI** — Console menu that ties everything together

Dependency injection is used in `Main.java`, making it easy to swap the repository implementation (e.g. switch to `InMemoryTransactionRepository` for testing).

## Getting Started

### Prerequisites

- Java 17+
- Gradle (or use the included `gradlew` wrapper)

### Run the application

```bash
./gradlew run
```

### Switch to in-memory storage (for testing)

In `Main.java`, replace:
```java
ITransactionRepository repository = new FileTransactionRepository();
```
with:
```java
ITransactionRepository repository = new InMemoryTransactionRepository();
```

## Data Storage

Transactions are persisted to `transactions.json` in the project root. The file is created automatically on first save and updated after every add, update, or delete operation.
