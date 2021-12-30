package aoc2015

import tools.Day

@Suppress("SpellCheckingInspection")
class Day5 : Day(2015, 5) {
    override fun solvePart1(): Any {
        val required = List(26) { ('a' + it).toString().repeat(2) }
        val forbidden = listOf("ab", "cd", "pq", "xy")
        return lines.count { line ->
            line.count { it in "aeiou" } >= 3 &&
                    line.findAnyOf(required) != null &&
                    line.findAnyOf(forbidden) == null
        }
    }

    override fun solvePart2() = lines.count { word ->
        for (i in 0 until word.length - 1) {
            val sub = word.substring(i, i + 2)
            if (word.lastIndexOf(sub) >= i + 2) {
                for (j in 0 until word.length - 2) {
                    if (word[j] == word[j + 2]) {
                        return@count true
                    }
                }
            }
        }
        false
    }
}
