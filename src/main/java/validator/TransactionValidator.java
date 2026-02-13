package validator;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionValidator {

    public static <T extends Enum<T>> boolean isValidEnum(Class<T> enumClass, String value) {
        if (value != null && !value.isBlank()) {
            try {
                Enum.valueOf(enumClass, value.toUpperCase());
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isValidAmount(BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        return false;
    }

    public static boolean isValidDate(String dateInput) {
        if (dateInput != null && !dateInput.isBlank()) {
            try {
                LocalDate.parse(dateInput);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isValidBoolean(String value) {
        if (value != null && !value.isBlank()) {
            String val = value.trim().toLowerCase();
            if (val.equals("true") || val.equals("false")) {
                return true;
            }
        }
        return false;
    }
}




