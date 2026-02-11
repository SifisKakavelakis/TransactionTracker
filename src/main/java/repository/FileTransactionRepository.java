package repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Expense;
import model.Income;
import model.Transaction;
import model.enums.TransactionType;
import repository.dto.TransactionDTO;
import repository.dto.TransactionMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileTransactionRepository implements ITransactionRepository {

    private final List<Transaction> transactions;
    private long nextId;
    private static final String DATA_FILE = "transactions.json";

    public FileTransactionRepository() {
        this.transactions = new ArrayList<>();
        this.nextId = 1;
        loadFromFile();
        updateNextId();
    }

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        if (transaction.getId() == 0) {
            Transaction newTransaction = createTransactionWithId(transaction, nextId++);
            transactions.add(newTransaction);
            saveToFile();
            return newTransaction;
        } else {
            Optional<Transaction> existing = findById(transaction.getId());
            if (existing.isPresent()) {
                transactions.remove(existing.get());
                transactions.add(transaction);
                saveToFile();
                return transaction;
            } else {
                throw new IllegalArgumentException("Transaction with ID " + transaction.getId() + " not found");
            }
        }
    }

    @Override
    public boolean deleteById(long id) {
        boolean removed = transactions.removeIf(t -> t.getId() == id);
        if (removed) {
            saveToFile();
        }
        return removed;
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
        saveToFile();
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

    private void updateNextId() {
        if (transactions.isEmpty()) {
            nextId = 1;
        } else {
            long maxId = transactions.stream()
                    .mapToLong(Transaction::getId)
                    .max()
                    .orElse(0L);
            nextId = maxId + 1;
        }
    }

    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // For LocalDate support

            TransactionDTO[] dtos = mapper.readValue(file, TransactionDTO[].class);

            for (TransactionDTO dto : dtos) {
                transactions.add(TransactionMapper.fromDto(dto));
            }

        } catch (Exception e) {
            System.err.println("Failed to load transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // For LocalDate support

            List<TransactionDTO> dtos = transactions.stream()
                    .map(TransactionMapper::toDto)
                    .toList();

            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File(DATA_FILE), dtos);

        } catch (Exception e) {
            System.err.println("Failed to save transactions: " + e.getMessage());
            e.printStackTrace();
        }
    }
}