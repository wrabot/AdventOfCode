package aoc2021

import forEachInput
import log

object Day10 {
    fun solve() = forEachInput(2021, 10, 1, 2) { lines ->
        val simplifiedChunks = lines.map { line ->
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

        log("part 1: ")
        simplifiedChunks.sumOf { chunk ->
            when (chunk.find { it in listOf(')', ']', '}', '>') }) {
                ')' -> 3
                ']' -> 57
                '}' -> 1197
                '>' -> 25137
                else -> 0  // not corrupted
            }.toInt()
        }.log()

        log("part 2: ")
        val scores = simplifiedChunks.mapNotNull { chunk ->
            runCatching {
                chunk.reversed().fold(0) { acc, c ->
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
        scores[scores.size / 2].log()
    }
}
