package src;

public class Validator {

    public static void validateStringNullOrBlank(String paramString) {
        if (paramString == null || paramString.isBlank()) {
            throw new IllegalArgumentException("String of parameter is null or empty");
        }
    }

    public static void validateNumberIsPositive(Number paramNumber) {
        if (paramNumber.doubleValue() < 0.0) {
            throw new IllegalArgumentException("Number is negative");
        }
    }
}
