package com.github.rskupnik.adventofcode;

import com.google.common.io.Resources;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Extended {

    private static final Set<Integer> VISITED_ROWS = new TreeSet<>();
    private static int ACCUMULATOR = 0;
    private static int SWITCH_POS = -1;

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        List<Tuple2<String, Integer>> opcodes = input.lines().map(line -> {
            String[] s = line.split(" ");
            return new Tuple2<>(s[0], strToInt(s[1]));
        }).collect(Collectors.toList());

        Stream.ofAll(opcodes).zipWithIndex().forEach(t -> {
            Tuple2<String, Integer> opcode = t._1;
            if (opcode._1.equals("acc"))
                return;

            SWITCH_POS = t._2;
            if (traverse(opcodes, 0)) {
                System.out.println(ACCUMULATOR);
                System.exit(0);
            }

            VISITED_ROWS.clear();
            ACCUMULATOR = 0;
        });
    }

    private static int strToInt(String str) {
        String sign = str.substring(0, 1);
        String rest = str.substring(1);
        return sign.equals("-") ? Integer.parseInt(rest) * -1 : Integer.parseInt(rest);
    }

    private static boolean traverse(List<Tuple2<String, Integer>> opcodes, int pos) {
        if (pos >= opcodes.size())
            return true;

        var opcode = opcodes.get(pos);

        if (VISITED_ROWS.contains(pos))
            return false;

        VISITED_ROWS.add(pos);

        if (pos == SWITCH_POS) {
            opcode = opcode.update1(opcode._1.equals("acc") ? "acc" : (opcode._1.equals("jmp") ? "nop" : "jmp"));
        }

        int nextPos = pos + 1;
        switch (opcode._1) {
            case "acc" -> ACCUMULATOR += opcode._2;
            case "jmp" -> nextPos = pos + opcode._2;
        }

        return traverse(opcodes, nextPos);
    }
}
