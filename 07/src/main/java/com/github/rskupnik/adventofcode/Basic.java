package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Basic {

    private static final Pattern MAIN_PATTERN = Pattern.compile("([\\w\\s]*)s\\scontain\\s([\\d\\w\\s,]*)");

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        Map<String, List<String>> bags = sortBags(input);
        long answer = bags.entrySet().stream().map(entry -> containsBag(bags, entry.getKey(), "shiny gold bag")).filter(b -> b).count();
        System.out.println(answer);
    }

    private static Map<String, List<String>> sortBags(String input) {
        return input.lines().map(line -> {
            var matcher = MAIN_PATTERN.matcher(line);
            matcher.find();

            String bagName = matcher.group(1);
            String bagContents = matcher.group(2);
            if (bagContents.equals("no other bags"))
                return new Tuple2<String, List<String>>(bagName, Collections.emptyList());

            return new Tuple2<>(bagName, Arrays.stream(bagContents.split(", ")).map(s -> s.substring(2)).map(str -> str.endsWith("s") ? str.substring(0, str.length()-1) : str).collect(Collectors.toList()));
        }).collect(Collectors.toMap(t -> t._1, t -> t._2));
    }

    private static boolean containsBag(Map<String, List<String>> allBags, String bagKey, String searchedBag) {
        List<String> bag = allBags.get(bagKey);
        if (bag.contains("nothing"))
            return false;

        if (bag.contains(searchedBag))
            return true;

        return bag.stream().anyMatch(b -> containsBag(allBags, b, searchedBag));
    }
}
