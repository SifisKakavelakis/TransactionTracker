package model;

import model.enums.ExpenseCategory;
import model.enums.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Expense extends Transaction{

    private ExpenseCategory expenseCategory;
    private boolean repeatable;
    private PaymentMethod paymentMethod;
    private boolean budgeted;

    protected Expense(long id, BigDecimal amount, LocalDate localDate, String description, ExpenseCategory expenseCategory,
                      boolean repeatable, PaymentMethod paymentMethod, boolean budgeted) {
        super(id, amount, localDate, description);
        validateExpenseCategory(expenseCategory);
        validatePaymentMethod(paymentMethod);

        this.expenseCategory = expenseCategory;
        this.repeatable = repeatable;
        this.paymentMethod = paymentMethod;
        this.budgeted = budgeted;
    }

    public Expense(BigDecimal amount, LocalDate localDate, String description, ExpenseCategory expenseCategory,
                   boolean repeatable, PaymentMethod paymentMethod, boolean budgeted) {
        super(amount, localDate, description);
        validateExpenseCategory(expenseCategory);
        validatePaymentMethod(paymentMethod);

        this.expenseCategory = expenseCategory;
        this.repeatable = repeatable;
        this.paymentMethod = paymentMethod;
        this.budgeted = budgeted;
    }

    public static Expense withId(long id, BigDecimal amount, LocalDate date,
                                 String description, ExpenseCategory category,
                                 boolean repeatable, PaymentMethod paymentMethod,
                                 boolean budgeted) {
        return new Expense(id, amount, date, description, category, repeatable, paymentMethod, budgeted);
    }

    private void validateExpenseCategory(ExpenseCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("Expense category cannot be null");
        }
    }

    private void validatePaymentMethod(PaymentMethod method) {
        if (method == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
    }

    public ExpenseCategory getExpenseCategory() {
        return expenseCategory;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public boolean isBudgeted() {
        return budgeted;
    }

    @Override
    public String toString() {
        return "Expense{" + super.toString() + ", expenseCategory=" + expenseCategory + ", repeatable=" +
                repeatable + ", paymentMethod=" + paymentMethod + ", budgeted=" + budgeted + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expense expense)) return false;
        if (!super.equals(o)) return false;
        return budgeted == expense.budgeted && expenseCategory == expense.expenseCategory
                && repeatable == expense.repeatable && paymentMethod == expense.paymentMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), expenseCategory, repeatable, paymentMethod, budgeted);
    }
}
