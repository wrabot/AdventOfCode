package aoc2021

import tools.Day

class Day6(test: Int? = null) : Day(2021, 6, test) {
    override fun getPart1() = part1
    override fun getPart2() = part2

    private val part1: Long
    private val part2: Long

    init {
        var generation = LongArray(9) { 0 }
        lines[0].split(",").map { generation[it.toInt()]++ }

        var part1 = 0L
        repeat(256) {
            generation = LongArray(9) { timer ->
                when (timer) {
                    8 -> generation[0]
                    6 -> generation[0] + generation[7]
                    else -> generation[timer + 1]
                }
            }
            if (it == 79) {
                part1 = generation.sum()
            }
        }

        this.part1 = part1
        part2 = generation.sum()
    }
}
