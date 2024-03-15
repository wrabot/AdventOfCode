package aoc2017

import Day

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        val buffer = mutableListOf(0)
        var currentPosition = 0
        repeat(2017) {
            currentPosition = (currentPosition + steps) % buffer.size
            buffer.add(currentPosition++, it + 1)
        }
        return buffer[currentPosition]
    }

    override fun solvePart2(): Int {
        // zero is always at the end, so we need the first element
        var currentPosition = 0
        var size = 1
        var valueAfter0 = 0
        repeat(50_000_000) {
            currentPosition = (currentPosition + steps) % size++
            if (currentPosition++ == 0) valueAfter0 = it + 1
        }
        return valueAfter0
    }

    val steps = input.toInt()
}
