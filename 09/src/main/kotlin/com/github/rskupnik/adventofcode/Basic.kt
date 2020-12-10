package com.github.rskupnik.adventofcode

import com.google.common.io.Resources
import java.nio.charset.StandardCharsets

fun main() {
    val lines = Resources.toString(Resources.getResource("input.txt"), StandardCharsets.UTF_8).lines().toList()

    val preamble = 25
    for (i in preamble + 1 until lines.size) {
        if (!numberValid(lines, i)) {
            println("Found invalid number: ${lines[i]}")
            break
        }
    }
}

fun numberValid(lines: List<String>, index: Int): Boolean {
    val currentNumber = lines[index].toLong()
    for (i in index - 26 until index) {
        for (j in index - 26 until index) {
            if (lines[i].toLong() + lines[j].toLong() == currentNumber)
                return true
        }
    }
    return false
}