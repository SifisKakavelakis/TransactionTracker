package repository;

import model.Expense;
import model.Income;
import model.Transaction;
import model.enums.TransactionType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements ITransactionRepository {

    private final List<Transaction> transactions;
    private long nextId;

    public InMemoryTransactionRepository() {
        this.transactions = new ArrayList<>();
        this.nextId = 1;
    }

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (transaction.getId() == 0) {
            Transaction newTransaction = createTransactionWithId(transaction, nextId++);
            transactions.add(newTransaction);
            return newTransaction;
        } else {
            Optional<Transaction> existing = findById(transaction.getId());
            if (existing.isPresent()) {
                transactions.remove(existing.get());
                transactions.add(transaction);
                return transaction;
            } else {
                throw new IllegalArgumentException("Transaction with ID " + transaction.getId() + " not found");
            }
        }
    }

    @Override
    public boolean deleteById(long id) {
        return transactions.removeIf(t -> t.getId() == id);
    }

    @Override
    public Optional<Transaction> findById(long id) {
        return transactions.stream()
                .filter(t -> t.getId() == id)
                .findFirst();
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public List<Transaction> findByType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        return transactions.stream()
                .filter(t -> {
                    if (type == TransactionType.INCOME) {
                        return t instanceof Income;
                    } else {
                        return t instanceof Expense;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        return transactions.stream()
                .filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
                .sorted(Comparator.comparing(Transaction::getDate))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(long id) {
        return transactions.stream()
                .anyMatch(t -> t.getId() == id);
    }

    @Override
    public long count() {
        return transactions.size();
    }

    @Override
    public void deleteAll() {
        transactions.clear();
        nextId = 1;
    }

    private Transaction createTransactionWithId(Transaction transaction, long id) {
        if (transaction instanceof Income income) {
            return Income.withId(
                    id,
                    income.getAmount(),
                    income.getDate(),
                    income.getDescription(),
                    income.getIncomeSource(),
                    income.isRepeatable(),
                    income.isTaxDeductible()
            );
        } else if (transaction instanceof Expense expense) {
            return Expense.withId(
                    id,
                    expense.getAmount(),
                    expense.getDate(),
                    expense.getDescription(),
                    expense.getExpenseCategory(),
                    expense.isRepeatable(),
                    expense.getPaymentMethod(),
                    expense.isBudgeted()
            );
        } else {
            throw new IllegalArgumentException("Unknown transaction type: " + transaction.getClass().getName());
        }
    }
}