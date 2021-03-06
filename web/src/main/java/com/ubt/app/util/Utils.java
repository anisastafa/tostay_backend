package com.ubt.app.util;

public class Utils {

    private final String errorMessage;

    public Utils(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public static boolean isNumberic(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
