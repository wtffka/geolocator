package com.mygeolocator.utils;

import java.util.Locale;

public class Utils {

    public static String transformStringToNormalView(String stringToTransform) {
        stringToTransform = stringToTransform.toLowerCase(Locale.ROOT);
        String firstLetter = stringToTransform.substring(0, 1).toUpperCase(Locale.ROOT);
        return firstLetter + stringToTransform.substring(1);
    }
}
