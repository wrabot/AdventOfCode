package aoc2015

import Day

class Day10(test: Int? = null) : Day(2015, 10, test) {
    override fun solvePart1(): Any {
        sequence = input.map { it.toString().toInt() }
        repeat(40) { next() }
        return sequence.size
    }

    override fun solvePart2(): Any {
        part1 // force part1
        repeat(50 - 40) { next() }
        return sequence.size
    }

    private fun next() {
        val output = mutableListOf<Int>()
        while (sequence.isNotEmpty()) {
            val digit = sequence.first()
            val length = sequence.indexOfFirst { it != digit }.takeIf { it != -1 } ?: sequence.size
            output.add(length)
            output.add(digit)
            sequence = sequence.subList(length, sequence.size)
        }
        sequence = output
    }

    private lateinit var sequence : List<Int>
}
