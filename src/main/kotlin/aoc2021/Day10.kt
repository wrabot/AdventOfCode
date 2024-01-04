package aoc2021

import Day

class Day10(test: Int? = null) : Day(2021, 10, test) {
    override fun solvePart1() = simplifiedChunks.sumOf { chunk ->
        when (chunk.find { it in listOf(')', ']', '}', '>') }) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0  // not corrupted
        }.toInt()
    }

    override fun solvePart2(): Any {
        val scores = simplifiedChunks.mapNotNull { chunk ->
            runCatching {
                chunk.reversed().fold(0L) { acc, c ->
                    acc * 5 + when (c) {
                        '(' -> 1
                        '[' -> 2
                        '{' -> 3
                        '<' -> 4
                        else -> throw Error("Corrupted")
                    }
                }
            }.getOrNull()
        }.sorted()
        return scores[scores.size / 2]
    }

    private val simplifiedChunks = lines.map { line ->
        var chunk = line
        while (true) {
            val length = chunk.length
            chunk = chunk.replace("()", "")
                .replace("[]", "")
                .replace("{}", "")
                .replace("<>", "")
            if (length == chunk.length) break
        }
        chunk.toList()
    }
}
