package aoc2017

import Day
import tools.toWords

class Day4(test: Int? = null) : Day(test) {
    override fun solvePart1() = passphrases.count { it.distinct().size == it.size }
    override fun solvePart2() =
        passphrases.map { p -> p.map { it.toList().sorted() } }.count { it.distinct().size == it.size }

    val passphrases = lines.map { it.toWords() }
}
