package model.enums;

public enum IncomeSource {

    SALARY("Salary"),
    FREELANCE("Freelance Work"),
    INVESTMENT("Investment"),
    BUSINESS("Business"),
    GIFT("Gift"),
    OTHER("Other");

    private final String displayName;

    IncomeSource(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }
}
