package com.azure.android.communication.common;

class StringUtils {

    /**
     * Validate string
     * @param value a value to be validated
     * @param paramName Parameter name for exceptionMessage
     * @return value
     *
     * @throws IllegalArgumentException when value is null or Empty.
     */
    public static String validateNotNullOrEmpty(String value, String paramName) {
        if (value == null || value.trim().isEmpty()) {
            String message = "The initialization parameter [" + paramName + "] cannot be null or empty.";
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
