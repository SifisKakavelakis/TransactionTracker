package repository.dto;

import model.Expense;
import model.Income;
import model.Transaction;
import model.enums.ExpenseCategory;
import model.enums.IncomeSource;
import model.enums.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionMapper {

    public static TransactionDTO toDto(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();

        dto.id = transaction.getId();
        dto.amount = transaction.getAmount();
        dto.date = transaction.getDate().toString(); // ISO format
        dto.description = transaction.getDescription();

        if (transaction instanceof Income income) {
            dto.type = "INCOME";
            dto.incomeSource = income.getIncomeSource().name();
            dto.repeatable = income.isRepeatable();
            dto.taxDeductible = income.isTaxDeductible();
        } else if (transaction instanceof Expense expense) {
            dto.type = "EXPENSE";
            dto.expenseCategory = expense.getExpenseCategory().name();
            dto.paymentMethod = expense.getPaymentMethod().name();
            dto.repeatable = expense.isRepeatable();
            dto.budgeted = expense.isBudgeted();
        }

        return dto;
    }

    public static Transaction fromDto(TransactionDTO dto) {
        if (dto.type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }

        BigDecimal amount = dto.amount;
        LocalDate date = LocalDate.parse(dto.date);
        String description = dto.description;

        if (dto.type.equals("INCOME")) {
            IncomeSource source = IncomeSource.valueOf(dto.incomeSource);
            boolean repeatable = dto.repeatable != null ? dto.repeatable : false;
            boolean taxDeductible = dto.taxDeductible != null ? dto.taxDeductible : false;

            return Income.withId(dto.id, amount, date, description, source, repeatable, taxDeductible);

        } else if (dto.type.equals("EXPENSE")) {
            ExpenseCategory category = ExpenseCategory.valueOf(dto.expenseCategory);
            PaymentMethod paymentMethod = PaymentMethod.valueOf(dto.paymentMethod);
            boolean repeatable = dto.repeatable != null ? dto.repeatable : false;
            boolean budgeted = dto.budgeted != null ? dto.budgeted : false;

            return Expense.withId(dto.id, amount, date, description, category, repeatable, paymentMethod, budgeted);

        } else {
            throw new IllegalArgumentException("Unknown transaction type: " + dto.type);
        }
    }
}