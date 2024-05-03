package aoc2016

import Day

class Day7(test: Int? = null) : Day(test) {
    override fun solvePart1() = lines.count {
        it.split('[', ']').mapIndexedNotNull { index, s ->
            when {
                index % 2 == 0 && s.abba() -> true
                index % 2 != 0 && s.abba() -> false
                else -> null
            }
        }.run { isNotEmpty() && all { it } }
    }

    private fun String.abba() = windowed(4).any { it[0] != it[1] && it[0] == it[3] && it[1] == it[2] }

    override fun solvePart2() = lines.count {
        val even = mutableSetOf<String>()
        val odd = mutableSetOf<String>()
        it.split('[', ']').forEachIndexed { index, s ->
            val aba = s.aba()
            if (index % 2 == 0) even.addAll(aba) else odd.addAll(aba.map { it.reversed() })
        }
        (even intersect odd).isNotEmpty()
    }

    private fun String.aba() = windowed(3).filter { it[0] != it[1] && it[0] == it[2] }.map { it.take(2) }
}
