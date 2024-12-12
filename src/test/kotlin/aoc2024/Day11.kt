package aoc2024

import Day

class Day11(test: Int? = null) : Day(test) {
    private val stones = input.split(" ").map { it.toLong() }

    override fun solvePart1(): Int {
        var s = stones
        repeat(25) {
            s = s.flatMap { it.blink() }
        }
        return s.size
    }

    override fun solvePart2() = stones.sumOf { blink(it, 75) }

    private val cache = mutableMapOf<Pair<Long, Int>, Long>()
    private fun blink(stone: Long, repeat: Int): Long = if (repeat == 0) 1L else
        cache.getOrPut(stone to repeat) {
            stone.blink().sumOf { blink(it, repeat - 1) }
        }

    private fun Long.blink(): List<Long> {
        if (this == 0L) return listOf(1L)
        val s = toString()
        return if (s.length % 2 == 0)
            listOf(s.substring(0, s.length / 2).toLong(), s.substring(s.length / 2).toLong())
        else
            listOf(this * 2024L)
    }
}
