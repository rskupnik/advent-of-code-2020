package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Basic {

    private static boolean end = false;

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        // Put in a TreeSet to sort the numbers for easy lookup
        Set<Integer> inputSet = input.lines().map(Integer::valueOf).collect(Collectors.toCollection(TreeSet::new));

        // For each number, subtract it from 2020 and look for a match in the set. Start from the end because it's more probable to find it there.
        for (int num : inputSet) {
            inputSet.stream().parallel().filter(i -> i == 2020 - num).findFirst().ifPresent(i -> {
                System.out.println("Answer is: " + (i * num));
                end = true;
            });

            if (end)
                break;
        }
    }
}
