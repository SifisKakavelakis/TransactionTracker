package repository.dto;

import java.math.BigDecimal;

public class TransactionDTO {

    public Long id;
    public String type;  // "INCOME" or "EXPENSE"
    public BigDecimal amount;
    public String date;  // ISO format: "2024-02-06"
    public String description;
    public Boolean repeatable;

    public String incomeSource;
    public Boolean taxDeductible;

    public String expenseCategory;
    public String paymentMethod;
    public Boolean budgeted;

    public TransactionDTO() {
    }
}