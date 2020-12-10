package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple;
import io.vavr.Tuple2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Basic {

    private static final Set<Integer> VISITED_ROWS = new TreeSet<>();
    private static int ACCUMULATOR = 0;

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        List<Tuple2<String, Integer>> opcodes = input.lines().map(line -> {
            String[] s = line.split(" ");
            return new Tuple2<>(s[0], strToInt(s[1]));
        }).collect(Collectors.toList());

        visitUntilLoop(opcodes, 0);
        System.out.println(ACCUMULATOR);
    }

    private static int strToInt(String str) {
        String sign = str.substring(0, 1);
        String rest = str.substring(1);
        return sign.equals("-") ? Integer.parseInt(rest) * -1 : Integer.parseInt(rest);
    }

    private static void visitUntilLoop(List<Tuple2<String, Integer>> opcodes, int pos) {
        var opcode = opcodes.get(pos);

        if (VISITED_ROWS.contains(pos))
            return;

        VISITED_ROWS.add(pos);

        int nextPos = pos + 1;
        switch (opcode._1) {
            case "acc" -> ACCUMULATOR += opcode._2;
            case "jmp" -> nextPos = pos + opcode._2;
        }

        visitUntilLoop(opcodes, nextPos);
    }
}
