package service;

import model.Expense;
import model.Income;
import model.Transaction;
import model.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {

    Transaction addIncome(Income income);
    Transaction addExpense(Expense expense);

    Optional<Transaction> getTransactionById(long id);
    List<Transaction> getAllTransactions();
    List<Transaction> getTransactionsByType(TransactionType type);
    List<Transaction> getTransactionsByDateRange(LocalDate start, LocalDate end);

    Transaction updateTransaction(Transaction transaction);

    boolean deleteTransaction(long id);
    void deleteAllTransactions();

    BigDecimal getTotalIncome();
    BigDecimal getTotalExpenses();
    BigDecimal getBalance();
    long getTransactionCount();

    boolean transactionExists(long id);
}