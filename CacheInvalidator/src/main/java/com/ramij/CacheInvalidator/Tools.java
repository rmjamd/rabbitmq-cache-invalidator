package com.ramij.CacheInvalidator;

public class Tools {
    public static String getOrDefault(String firstString, String secondString) {
        if (!Tools.isNullOrEmpty(firstString))
            return firstString;
        return secondString;
    }

    public static boolean isNullOrEmpty(String str) {
        return !(str != null && !str.isEmpty());
    }


}
