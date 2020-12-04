package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Extended {

    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{4})");
    private static final Pattern HEIGHT_PATTERN = Pattern.compile("(\\d*)([ci][mn])");
    private static final Pattern HCL_PATTERN = Pattern.compile("#[(0-9)(a-f)]{6}");
    private static final Pattern ECL_PATTERN = Pattern.compile("amb|blu|brn|gry|grn|hzl|oth");
    private static final Pattern PID_PATTERN = Pattern.compile("\\d{9}");

    private static final Map<String, Function<String, Boolean>> VALIDATORS = Map.of(
            "byr", (s) -> {
                var matcher = YEAR_PATTERN.matcher(s);
                return matcher.matches() && Integer.parseInt(matcher.group()) >= 1920 && Integer.parseInt(matcher.group()) <= 2002;
            },
            "iyr", (s) -> {
                var matcher = YEAR_PATTERN.matcher(s);
                return matcher.matches() && Integer.parseInt(matcher.group()) >= 2010 && Integer.parseInt(matcher.group()) <= 2020;
            },
            "eyr", (s) -> {
                var matcher = YEAR_PATTERN.matcher(s);
                return matcher.matches() && Integer.parseInt(matcher.group()) >= 2020 && Integer.parseInt(matcher.group()) <= 2030;
            },
            "hgt", (s) -> {
                var matcher = HEIGHT_PATTERN.matcher(s);
                boolean matches = matcher.matches();
                if (!matches)
                    return false;
                else {
                    if (matcher.group(2).equals("cm")) {
                        return Integer.parseInt(matcher.group(1)) >= 150 && Integer.parseInt(matcher.group(1)) <= 193;
                    } else {
                        return Integer.parseInt(matcher.group(1)) >= 59 && Integer.parseInt(matcher.group(1)) <= 76;
                    }
                }
            },
            "hcl", (s) -> HCL_PATTERN.matcher(s).matches(),
            "ecl", (s) -> ECL_PATTERN.matcher(s).matches(),
            "pid", (s) -> PID_PATTERN.matcher(s).matches()
    );

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);
        List<String> cleanedInput = cleanInput(input);

        int validPassports = cleanedInput.stream()
                .map(s -> s.split(" "))
                .map(sa -> Arrays.stream(sa)
                        .map(str -> str.split(":"))
                        .filter(strAr -> {
                            return VALIDATORS.containsKey(strAr[0]) && VALIDATORS.get(strAr[0]).apply(strAr[1]);
                        })
                        .count() == VALIDATORS.keySet().size() ? 1 : 0).filter(v -> v == 1)
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
