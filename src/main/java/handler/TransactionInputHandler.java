package handler;

import model.enums.ExpenseCategory;
import model.enums.IncomeSource;
import model.enums.PaymentMethod;
import model.enums.TransactionType;
import validator.TransactionValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class TransactionInputHandler {

    private final Scanner scanner;

    public TransactionInputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public boolean askBoolean(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true")) return true;
            if (input.equals("false")) return false;
            System.out.println("Invalid input. Please enter true or false.");
        }
    }

    public TransactionType askTransactionType() {
        while (true) {
            System.out.print("Transaction type (INCOME / EXPENSE): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (TransactionValidator.isValidEnum(TransactionType.class, input)) {
                return TransactionType.valueOf(input);
            }
            System.out.println("Invalid transaction type.");
        }
    }

    public IncomeSource askIncomeSource() {
        while (true) {
            System.out.print("Income source (SALARY / FREELANCE / INVESTMENT / OTHER): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (TransactionValidator.isValidEnum(IncomeSource.class, input)) {
                return IncomeSource.valueOf(input);
            }
            System.out.println("Invalid income source.");
        }
    }

    public ExpenseCategory askExpenseCategory() {
        while (true) {
            System.out.print("Expense category (FOOD / RENT / TRANSPORT / ENTERTAINMENT / OTHER): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (TransactionValidator.isValidEnum(ExpenseCategory.class, input)) {
                return ExpenseCategory.valueOf(input);
            }
            System.out.println("Invalid expense category.");
        }
    }

    public PaymentMethod askPaymentMethod() {
        while (true) {
            System.out.print("Payment method (CASH / CARD / CREDIT): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (TransactionValidator.isValidEnum(PaymentMethod.class, input)) {
                return PaymentMethod.valueOf(input);
            }
            System.out.println("Invalid payment method.");
        }
    }

    public BigDecimal askAmount() {
        BigDecimal amount = null;
        while (!TransactionValidator.isValidAmount(amount)) {
            System.out.print("Amount: ");
            try {
                amount = new BigDecimal(scanner.nextLine().trim());
                if (!TransactionValidator.isValidAmount(amount)) {
                    System.out.println("Amount must be positive.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number.");
                amount = null;
            }
        }
        return amount;
    }

    public LocalDate askDate() {
        LocalDate date = null;
        while (date == null) {
            System.out.print("Date (YYYY-MM-DD): ");
            String input = scanner.nextLine().trim();
            if (TransactionValidator.isValidDate(input)) {
                date = LocalDate.parse(input);
            } else {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }
        return date;
    }

    public String askDescription() {
        System.out.print("Description (optional): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        return input;

    }

}

