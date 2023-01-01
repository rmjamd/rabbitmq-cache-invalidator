package com.ramij.CacheInvalidator;

public class Tools {
    public static String getOrDefault(String firstString ,String secondString){
        if(Tools.isNullorEmpty(firstString))
            return firstString;
        return secondString;
    }
    public static boolean isNullorEmpty(String str){
        return (str!=null && !str.isEmpty());
    }


}
