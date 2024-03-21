package aoc2018

import Day

class Day5(test: Int? = null) : Day(test) {
    override fun solvePart1() = input.polymerLength()
    override fun solvePart2() = ('a'..'z').minOf { input.replace(it.toString(), "", true).polymerLength() }

    private fun String.polymerLength() = fold("") { acc, c ->
        val l = acc.lastOrNull() ?: return@fold acc + c
        if (l.lowercase() == c.lowercase() && (l.isLowerCase() xor c.isLowerCase())) return@fold acc.dropLast(1)
        acc + c
    }.length
}
