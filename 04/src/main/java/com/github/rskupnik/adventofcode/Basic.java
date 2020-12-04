package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Basic {

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);
        List<String> cleanedInput = cleanInput(input);
        List<String> requiredKeys = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        int validPassports = cleanedInput.stream()
                .map(s -> s.split(" "))
                .map(sa -> Arrays.stream(sa)
                        .map(str -> str.split(":"))
                        .map(strAr -> strAr[0])
                        .filter(requiredKeys::contains)
                        .count() == requiredKeys.size() ? 1 : 0).filter(v -> v == 1)
                .reduce(0, Integer::sum);
        System.out.println(validPassports);
    }

    private static List<String> cleanInput(String input) {
        List<String> output = new ArrayList<>();
        final StringBuilder sb = new StringBuilder();
        input.lines().forEach(line -> {
            if (line.equals(" ") || line.equals("") || line.equals("\n")) {
                output.add(sb.toString().trim());
                sb.setLength(0);
                return;
            }

            sb.append(" ");
            sb.append(line);
        });
        return output;
    }
}
