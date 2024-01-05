package aoc2015

import Day

class Day11(test: Int? = null) : Day(2015, 11, test) {
    override fun solvePart1() = next()

    override fun solvePart2(): Any {
        part1 // force part1
        return next()
    }

    private val digits = ('a'..'z') - setOf('i', 'l', 'o')
    private var password = input.toList()

    private fun next(): String {
        do {
            password = password.toLongPassword().inc().toListPassword()
        } while (!password.hasStraight() || !password.hasDoublePair())
        return password.joinToString("")
    }

    private fun List<Char>.toLongPassword() =
        joinToString("") { digits.indexOf(it).toString(digits.size) }.toLong(digits.size)

    private fun Long.toListPassword() =
        toString(digits.size).padStart(8, '0').map { digits[it.toString().toInt(digits.size)] }

    private fun List<Char>.hasStraight() =
        windowed(3, 1).any { it == (it.first()..it.first() + 2).toList() }

    private fun List<Char>.hasDoublePair() =
                windowed(2, 1).withIndex().filter { it.value.first() == it.value.last() }
                    .takeIf { it.isNotEmpty() }?.run { last().index - first().index >= 2 } ?: false
}
