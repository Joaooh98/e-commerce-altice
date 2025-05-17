package com.altice.domain.utils;

import java.text.MessageFormat;
import java.util.List;

public final class StringUtils {
    
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }


    public static boolean isNotNullOrEmpty(String s) {
        return !isNullOrEmpty(s);
    }

    public static String stringPatternFormat(String pattern, Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }


    public static boolean stringsIsNullOrEmpty(List<String> strings) {
        return strings.stream()
                    .anyMatch(StringUtils::isNullOrEmpty);
    }
}
