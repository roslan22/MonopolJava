package com.monopoly.utils;

public class Utils {
    private Utils() {
    }

    public static Integer tryParseInt(String num) {
        try {
            return Integer.parseUnsignedInt(num);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
