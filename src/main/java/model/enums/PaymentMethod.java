package model.enums;

public enum PaymentMethod {

    CASH("Cash"),
    CREDIT("Credit Card"),
    DEBIT("Debit Card"),
    BANK_TRANSFER("Bank Transfer"),
    DIGITAL_WALLET("Digital Wallet");

    private final String displayName;

    PaymentMethod(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDisplayName()
    {
        return displayName;
    }
}
