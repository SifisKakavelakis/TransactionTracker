package ui;

import handler.TransactionInputHandler;
import handler.TransactionOutputHandler;
import model.Expense;
import model.Income;
import model.Transaction;
import model.enums.ExpenseCategory;
import model.enums.IncomeSource;
import model.enums.PaymentMethod;
import service.ITransactionService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for the Transaction Tracker.
 */
public class ConsoleMenu {

    private final ITransactionService service;
    private final TransactionInputHandler inputHandler;
    private final TransactionOutputHandler outputHandler;
    private final Scanner scanner;
    private boolean running;

    public ConsoleMenu(ITransactionService service) {
        if (service == null) {
            throw new IllegalArgumentException("Service cannot be null");
        }
        this.service = service;
        this.inputHandler = new TransactionInputHandler();
        this.outputHandler = new TransactionOutputHandler();
        this.scanner = new Scanner(System.in);
        this.running = false;
    }

    /**
     * Starts the menu loop.
     */
    public void start() {
        running = true;
        System.out.println("=== Transaction Tracker ===\n");

        while (running) {
            displayMenu();
            int choice = readMenuChoice();
            handleChoice(choice);
        }

        System.out.println("\nGoodbye!");
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add Income");
        System.out.println("2. Add Expense");
        System.out.println("3. View All Transactions");
        System.out.println("4. View Balance & Statistics");
        System.out.println("5. Delete Transaction");
        System.out.println("6. Exit");
        System.out.print("\nEnter choice: ");
    }

    private int readMenuChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void handleChoice(int choice) {
        System.out.println();

        switch (choice) {
            case 1 -> addIncome();
            case 2 -> addExpense();
            case 3 -> viewAllTransactions();
            case 4 -> viewStatistics();
            case 5 -> deleteTransaction();
            case 6 -> exit();
            default -> System.out.println("Invalid choice. Please enter 1-6.");
        }
    }

    private void addIncome() {
        System.out.println("--- Add Income ---");

        try {
            BigDecimal amount = inputHandler.askAmount();
            LocalDate date = inputHandler.askDate();
            String description = inputHandler.askDescription();
            IncomeSource source = inputHandler.askIncomeSource();
            boolean repeatable = inputHandler.askBoolean("Is this repeatable? (true/false): ");
            boolean taxDeductible = inputHandler.askBoolean("Is this tax deductible? (true/false): ");

            Income income = new Income(amount, date, description, source, repeatable, taxDeductible);
            Transaction saved = service.addIncome(income);

            outputHandler.showTransactionAdded(saved);

        } catch (Exception e) {
            outputHandler.showError("Failed to add income: " + e.getMessage());
        }
    }

    private void addExpense() {
        System.out.println("--- Add Expense ---");

        try {
            BigDecimal amount = inputHandler.askAmount();
            LocalDate date = inputHandler.askDate();
            String description = inputHandler.askDescription();
            ExpenseCategory category = inputHandler.askExpenseCategory();
            boolean repeatable = inputHandler.askBoolean("Is this repeatable? (true/false): ");
            PaymentMethod paymentMethod = inputHandler.askPaymentMethod();
            boolean budgeted = inputHandler.askBoolean("Is this budgeted? (true/false): ");

            Expense expense = new Expense(amount, date, description, category, repeatable, paymentMethod, budgeted);
            Transaction saved = service.addExpense(expense);

            outputHandler.showTransactionAdded(saved);

        } catch (Exception e) {
            outputHandler.showError("Failed to add expense: " + e.getMessage());
        }
    }

    private void viewAllTransactions() {
        System.out.println("--- All Transactions ---");

        List<Transaction> transactions = service.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("\nTotal: " + transactions.size() + " transaction(s)\n");
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    private void viewStatistics() {
        System.out.println("--- Balance & Statistics ---");

        BigDecimal totalIncome = service.getTotalIncome();
        BigDecimal totalExpenses = service.getTotalExpenses();
        BigDecimal balance = service.getBalance();
        long count = service.getTransactionCount();

        System.out.println("Total Transactions: " + count);
        System.out.println("Total Income:       €" + totalIncome);
        System.out.println("Total Expenses:     €" + totalExpenses);
        System.out.println("Balance:            €" + balance);
    }

    private void deleteTransaction() {
        System.out.println("--- Delete Transaction ---");

        System.out.print("Enter transaction ID to delete: ");
        try {
            long id = Long.parseLong(scanner.nextLine().trim());

            if (service.deleteTransaction(id)) {
                System.out.println("Transaction deleted successfully.");
            } else {
                System.out.println("Transaction with ID " + id + " not found.");
            }

        } catch (NumberFormatException e) {
            outputHandler.showError("Invalid ID format.");
        }
    }

    private void exit() {
        System.out.println("Exiting...");
        running = false;
    }
}