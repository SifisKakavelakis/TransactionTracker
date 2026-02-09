package model;

import model.enums.IncomeSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Income extends Transaction {

    private IncomeSource incomeSource;
    private boolean repeatable;
    private boolean taxDeductible;

    protected Income(long id, BigDecimal amount, LocalDate localDate, String description, IncomeSource incomeSource,
                     boolean repeatable, boolean taxDeductible) {
        super(id, amount, localDate, description);
        validateIncomeSource(incomeSource);

        this.incomeSource = incomeSource;
        this.repeatable = repeatable;
        this.taxDeductible = taxDeductible;
    }

    public Income(BigDecimal amount, LocalDate localDate, String description, IncomeSource incomeSource,
                  boolean repeatable, boolean taxDeductible) {
        super(amount, localDate, description);
        validateIncomeSource(incomeSource);

        this.incomeSource = incomeSource;
        this.repeatable = repeatable;
        this.taxDeductible = taxDeductible;
    }

    public static Income withId(long id, BigDecimal amount, LocalDate date,
                                String description, IncomeSource source,
                                boolean repeatable, boolean taxDeductible) {
        return new Income(id, amount, date, description, source, repeatable, taxDeductible);
    }

    private void validateIncomeSource(IncomeSource incomeSource) {
        if (incomeSource == null) {
            throw new IllegalArgumentException("Income source cannot be null");
        }
    }

    public IncomeSource getIncomeSource() {
        return incomeSource;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public boolean isTaxDeductible() {
        return taxDeductible;
    }

    @Override
    public String toString() {
        return "Income{" + super.toString() + ", incomeSource=" + incomeSource + ", repeatable=" + repeatable +
                ", taxDeductible=" + taxDeductible + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Income income)) return false;
        if (!super.equals(o)) return false;
        return repeatable == income.repeatable && taxDeductible == income.taxDeductible && incomeSource == income.incomeSource;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), incomeSource, repeatable, taxDeductible);
    }

}
