package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Extended {

    private static boolean end = false;

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        // Put in a TreeSet to sort the numbers for easy lookup
        Set<Integer> inputSet = input.lines().map(Integer::valueOf).collect(Collectors.toCollection(TreeSet::new));

        // Fuck it, bruteforce
        inputSet.stream().parallel().forEach(i -> {
            inputSet.stream().parallel().forEach(j -> {
                int sum = i + j;
                if (sum >= 2020) {
                    return;
                }

                inputSet.stream().parallel().filter(k -> 2020 - k == sum).findFirst().ifPresent(k -> {
                    System.out.println("Answer is: " + (i * j * k));
                    end = true;
                });

                if (end)
                    System.exit(0);
            });
        });
    }
}
