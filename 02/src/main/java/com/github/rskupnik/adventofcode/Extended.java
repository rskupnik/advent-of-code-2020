package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Extended {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)\\s*(\\w):\\s*(\\w*)");

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        int result = input.lines().map(line -> {
            Tuple4<Integer, Integer, String, String> parsedInput = parse(line);

            char char1 = parsedInput._4.toCharArray()[parsedInput._1 - 1];
            char char2 = parsedInput._4.toCharArray()[parsedInput._2 - 1];
            char targetChar = parsedInput._3.toCharArray()[0];

            return (char1 != char2) && (char1 == targetChar || char2 == targetChar) ? 1 : 0;
        }).reduce(0, Integer::sum);

        System.out.println(result);
    }

    private static Tuple4<Integer, Integer, String, String> parse(String line) {
        var match = PATTERN.matcher(line);
        match.matches();
        return new Tuple4<>(Integer.valueOf(match.group(1)), Integer.valueOf(match.group(2)), match.group(3), match.group(4));
    }
}
