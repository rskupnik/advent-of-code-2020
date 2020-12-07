package com.github.rskupnik.adventofcode;

import com.google.common.base.CharMatcher;
import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Extended {

    public static void main(String[] args) throws IOException {
        String input = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8);

        final List<Group> groups = new ArrayList<>();
        final StringBuilder sb = new StringBuilder();
        int participants = 0;
        for (String line : input.lines().collect(Collectors.toList())) {
            participants++;
            if (line.equals("") || line.equals(" ") || line.equals("\n")) {
                groups.add(new Group(sb.toString(), participants - 1));
                sb.setLength(0);
                participants = 0;
            }
            else sb.append(line);
        }
        //groups.add(new Group(sb.toString(), participants - 1));

        long result = groups.stream().map(g -> g.contents.codePoints().mapToObj(c -> (char) c)
                .distinct()
                .map(ch -> CharMatcher.is(ch).countIn(g.contents))
                    .filter(i -> i == g.participants)
                    .count())
                .reduce(0L, Long::sum);

        System.out.println(result);
    }

    private static class Group {
        private final String contents;
        private final int participants;

        public Group(String contents, int participants) {
            this.contents = contents;
            this.participants = participants;
        }

        @Override
        public String toString() {
            return contents + ", " + participants;
        }
    }
}
