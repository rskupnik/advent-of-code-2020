package com.github.rskupnik.adventofcode

import com.google.common.io.Resources
import java.nio.charset.StandardCharsets

fun main() {
    val lines = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8).lines().toList()
    val sortedLines = lines.map { it.toInt() }.toSortedSet()
    val (oneJoltDiff, threeJoltDiff) = findDiffs(sortedLines)
    println("$oneJoltDiff | $threeJoltDiff")
    println(oneJoltDiff * (threeJoltDiff + 1))
}

fun findDiffs(lines: Set<Int>): Pair<Int, Int> {
    var oneJoltDiffSum = 0
    var threeJoltDiffSum = 0
    var previous = 0
    for (v in lines) {
        if (v - previous == 1)
            oneJoltDiffSum++
        if (v - previous == 3)
            threeJoltDiffSum++

        previous = v
    }

    return Pair(oneJoltDiffSum, threeJoltDiffSum)
}