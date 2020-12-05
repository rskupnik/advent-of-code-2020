package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Extended {

    private static final Pattern PATTERN = Pattern.compile("([FB]{7})([LR]{3})");

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);
        input.lines().map(line -> {
            var matcher = PATTERN.matcher(line);
            if (!matcher.matches())
                return 0;

            String firstPart = matcher.group(1);
            String secondPart = matcher.group(2);
            int row = applyBinaryPartitioning(firstPart, "F", 0, 127);
            int column = applyBinaryPartitioning(secondPart, "L", 0, 7);
            return (row * 8) + column;
        }).mapToInt(v -> v)
                .sorted()
                .reduce(0, (i1, i2) -> {
                    if (i1 != i2 - 1)
                        System.out.println(i1 + 1);
                    return i2;
                });
    }

    private static int applyBinaryPartitioning(String input, String frontChar, int min, int max) {
        if (input == null || input.equals("") || input.equals(" "))
            return min;

        String firstChar = input.substring(0, 1);
        String rest = input.substring(1);
        return applyBinaryPartitioning(
                rest,
                frontChar,
                firstChar.equals(frontChar) ? min : max - ((max - min) / 2),
                firstChar.equals(frontChar) ? max - ((max - min) / 2) - 1 : max
        );
    }
}
