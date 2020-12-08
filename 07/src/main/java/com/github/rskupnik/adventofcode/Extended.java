package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Extended {

    private static final Pattern MAIN_PATTERN = Pattern.compile("([\\w\\s]*)s\\scontain\\s([\\d\\w\\s,]*)");

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        Map<String, List<Tuple2<String, Integer>>> bags = sortBags(input);
        int answer = countUniqueBagsInside(bags, "shiny gold bag");
        System.out.println(answer);
    }

    private static Map<String, List<Tuple2<String, Integer>>> sortBags(String input) {
        return input.lines().map(line -> {
            var matcher = MAIN_PATTERN.matcher(line);
            matcher.find();

            String bagName = matcher.group(1);
            String bagContents = matcher.group(2);
            if (bagContents.equals("no other bags"))
                return new Tuple2<String, List<Tuple2<String, Integer>>>(bagName, Collections.emptyList());

            return new Tuple2<>(bagName, Arrays.stream(bagContents.split(", ")).map(e -> {
                Integer amount = Integer.valueOf(e.substring(0, 1));
                String str = e.substring(2);
                str = str.endsWith("s") ? str.substring(0, str.length() - 1) : str;
                return new Tuple2<>(str, amount);
            }).collect(Collectors.toList()));
        }).collect(Collectors.toMap(t -> t._1, t -> t._2));
    }

    private static int countUniqueBagsInside(Map<String, List<Tuple2<String, Integer>>> allBags, String bagKey) {
        var bag = allBags.get(bagKey);

        if (bag.isEmpty())
            return 0;

        return bag.stream().map(b -> b._2 + countUniqueBagsInside(allBags, b._1) * b._2).reduce(0, Integer::sum);
    }
}
