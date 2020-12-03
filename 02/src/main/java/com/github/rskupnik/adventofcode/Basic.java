package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class Basic {

    private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+)\\s*(\\w):\\s*(\\w*)");

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        int result = input.lines().map(line -> {
            Tuple4<Integer, Integer, String, String> parsedInput = parse(line);

            int amount = (int) parsedInput._4.codePoints()
                    .mapToObj(c -> String.valueOf((char) c))
                    .filter(s -> s.equals(parsedInput._3))
                    .count();

            return amount >= parsedInput._1 && amount <= parsedInput._2 ? 1 : 0;
        }).reduce(0, Integer::sum);

        System.out.println(result);
    }

    private static Tuple4<Integer, Integer, String, String> parse(String line) {
        var match = PATTERN.matcher(line);
        match.matches();
        return new Tuple4<>(Integer.valueOf(match.group(1)), Integer.valueOf(match.group(2)), match.group(3), match.group(4));
    }
}
