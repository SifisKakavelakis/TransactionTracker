package service;

import model.Expense;
import model.Income;
import model.Transaction;
import model.enums.TransactionType;
import repository.ITransactionRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TransactionService implements ITransactionService {

    private final ITransactionRepository repository;

    public TransactionService(ITransactionRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.repository = repository;
    }

    @Override
    public Transaction addIncome(Income income) {
        if (income == null) {
            throw new IllegalArgumentException("Income cannot be null");
        }
        return repository.save(income);
    }

    @Override
    public Transaction addExpense(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("Expense cannot be null");
        }
        return repository.save(expense);
    }

    @Override
    public Optional<Transaction> getTransactionById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        return repository.findByType(type);
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates cannot be null");
        }
        return repository.findByDateRange(start, end);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (!repository.existsById(transaction.getId())) {
            throw new IllegalArgumentException("Transaction with ID " + transaction.getId() + " does not exist");
        }
        return repository.save(transaction);
    }

    @Override
    public boolean deleteTransaction(long id) {
        return repository.deleteById(id);
    }

    @Override
    public void deleteAllTransactions() {
        repository.deleteAll();
    }

    @Override
    public BigDecimal getTotalIncome() {
        return repository.findByType(TransactionType.INCOME).stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getTotalExpenses() {
        return repository.findByType(TransactionType.EXPENSE).stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getBalance() {
        return getTotalIncome().subtract(getTotalExpenses());
    }

    @Override
    public long getTransactionCount() {
        return repository.count();
    }

    @Override
    public boolean transactionExists(long id) {
        return repository.existsById(id);
    }
}