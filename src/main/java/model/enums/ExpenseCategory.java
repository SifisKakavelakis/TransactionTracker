package model.enums;

public enum ExpenseCategory {

    FOOD("Food"),
    RENT("Rent"),
    TRANSPORT("Transport"),
    ENTERTAINMENT("Entertainment"),
    HEALTHCARE("Healthcare"),
    UTILITIES("Utilities"),
    OTHER("Other");

    private final String displayName;

    ExpenseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }
}
