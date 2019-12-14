package com.xxx.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class httpProtcolAssit {
    public static Map<String, String> textToMap(String text, String lineDelimiter, String keyDelimiter) {
        return Stream.of(text.split(lineDelimiter))
                .filter(line -> !line.trim().isEmpty())
                .map(line -> {
                    int index = line.lastIndexOf(keyDelimiter); // avoid header like ":authority: xxxx"

                    return new HashMap.SimpleEntry<>(line.substring(0, index).trim(),
                            line.substring(index + 1, line.length()).trim());
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue, (x, y) -> x));
    }

    public static Map<String, String> headerTextToMap(String text) {
        return textToMap(text, "\n", ":");
    }

    public static Map<String, String> cookieTextToMap(String text) {
        return textToMap(text, ";", "=");
    }
}
