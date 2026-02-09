package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Transaction {

    protected long id;
    protected BigDecimal amount;
    protected LocalDate date;
    protected String description;

    protected Transaction(long id, BigDecimal amount, LocalDate date, String description) {
        validateAmount(amount);
        validateDate(date);
        validateDescription(description);

        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Transaction(BigDecimal amount, LocalDate date, String description) {
        this(0, amount, date, description);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive, got: " + amount);
        }
    }

    private void validateDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date cannot be in the future");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (description.length() > 500) {
            throw new IllegalArgumentException("Description too long (max 500 characters)");
        }
    }

    public long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", localDate=" + date +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Transaction that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
