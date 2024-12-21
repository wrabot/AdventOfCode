package aoc2024

import Day
import tools.XY
import tools.board.Board
import tools.board.Direction4
import tools.board.toBoard

class Day21(test: Int? = null) : Day(test) {
    private val keypad = """
        789
        456
        123
         0A
    """.trimIndent().lines().toBoard { it }

    private val botPad = """
         ^A
        <v>
    """.trimIndent().lines().toBoard { it }

    private data class PadPaths(val pad: Board<Char>) {
        private val unitSequence = mutableMapOf<Pair<Char, Char>, List<String>>()

        init {
            val xy = pad.xy.filter { pad[it] != ' ' }
            xy.forEach { s -> xy.forEach { e -> unitSequence[pad[s] to pad[e]] = findUnitSequence(s, e, "") } }
        }

        fun shortestSequences(s: String) = shortestSequences(s, 'A', emptyList())

        private fun shortestSequences(s: String, current: Char, prefix: List<String>): List<List<String>> =
            if (s.isEmpty()) listOf(prefix) else unitSequence[current to s.first()]!!.flatMap {
                shortestSequences(s.drop(1), s.first(), prefix + it)
            }

        private fun findUnitSequence(s: XY, e: XY, prefix: String): List<String> {
            if (s == e) return listOf(prefix + 'A')
            val distance = (e - s).manhattan()
            return Direction4.entries.filter {
                val n = s + it.xy
                pad.getOrNull(n) != null && pad[n] != ' ' && (e - n).manhattan() < distance
            }.flatMap { findUnitSequence(s + it.xy, e, prefix + it.c) }
        }
    }

    private val keypadPaths = PadPaths(keypad)
    private val botPadPaths = PadPaths(botPad)

    private val shortestBotSequenceSize = mutableMapOf<Pair<String, Int>, Long>()
    private fun shortestBotSequenceSize(s: String, level: Int): Long = if (level == 0) s.length.toLong() else
        shortestBotSequenceSize.getOrPut(s to level) { botPadPaths.shortestSequenceSize(s, level - 1) }

    private fun PadPaths.shortestSequenceSize(s: String, level: Int) =
        shortestSequences(s).minOf { unitSequence -> unitSequence.sumOf { shortestBotSequenceSize(it, level) } }

    private fun solve(code: String, level: Int) =
        code.filter { it.isDigit() }.toLong() * keypadPaths.shortestSequenceSize(code, level)

    override fun solvePart1() = lines.sumOf { solve(it, 2) }
    override fun solvePart2() = lines.sumOf { solve(it, 25) }
}
