package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Basic {

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        final StringBuilder sb = new StringBuilder();
        input.lines().forEach(line -> {
            if (line.equals("") || line.equals(" ") || line.equals("\n"))
                sb.append(" ");
            else sb.append(line);
        });

        String oneLine = sb.toString();
        System.out.println(oneLine);

        long sum = Arrays.stream(oneLine.split(" "))
                .map(s -> s.codePoints().mapToObj(c -> (char) c).toArray())
                .map(chars -> Arrays.stream(chars).distinct().count())
                .reduce(0L, Long::sum);

        System.out.println(sum);
    }
}
