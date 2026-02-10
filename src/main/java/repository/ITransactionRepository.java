package repository;

import model.Transaction;
import model.enums.TransactionType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ITransactionRepository {

    Transaction save(Transaction transaction);

    boolean deleteById(long id);

    Optional<Transaction> findById(long id);

    List<Transaction> findAll();

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByDateRange(LocalDate start, LocalDate end);

    boolean existsById(long id);

    long count();

    void deleteAll();
}