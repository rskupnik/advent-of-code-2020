package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class Extended {

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);
        int[][] inputArray = inputToArray(input);
        int lineCount = (int) input.lines().count();
        int charCount = input.lines().collect(Collectors.toList()).get(0).length();

        long result = Stream.of(new Tuple2<>(1, 1), new Tuple2<>(3, 1), new Tuple2<>(5, 1), new Tuple2<>(7, 1), new Tuple2<>(1, 2))
                .map(t -> countTrees(inputArray, lineCount, charCount, t._1, t._2))
                .reduce((i1, i2) -> i1 * i2);
                //.forEach(i -> System.out.println(i));
        System.out.println(result);
    }

    private static long countTrees(int[][] inputArray, int lineCount, int charCount, int xInc, int yInc) {
        int trees = 0;
        int x = 0;
        int y = 0;
        while (y < lineCount) {
            trees += inputArray[y][x] == 1 ? 1 : 0;

            x += xInc;
            if (x > charCount)
                x = x - charCount;
            y += yInc;
        }
        return trees;
    }

    private static int[][] inputToArray(String input) {
        final int[][] output = new int[400][32];
        Stream.of(input.split("\n"))
                .zipWithIndex()
                .forEach(t -> {
                    Stream.of(t._1.codePoints().mapToObj(c -> (char) c).toArray())
                            .zipWithIndex()
                            .forEach(tt -> {
                                char ch = (char) tt._1;
                                output[t._2][tt._2] = ch == '.' ? 0 : 1;
                            });
                });
        return output;
    }
}
