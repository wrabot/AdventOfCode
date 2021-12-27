package aoc2015

import forEachInput
import tools.log

object Day3 {
    fun solve() = forEachInput(2015, 3, 1) { lines ->
        log("part 1: ")
        lines[0].day3part1()

        log("part 2: ")
        lines[0].day3part2()
    }

    private fun String.day3part1() {
        var x = 0
        var y = 0
        val houses = mutableSetOf(Pair(x, y))
        forEach {
            when (it) {
                '<' -> x--
                '>' -> x++
                '^' -> y--
                'v' -> y++
                else -> error("invalid direction")
            }
            houses.add(Pair(x, y))
        }
        houses.count().log()
    }

    private fun String.day3part2() {
        val x = mutableListOf(0, 0)
        val y = mutableListOf(0, 0)
        val houses = mutableSetOf(Pair(0, 0))
        forEachIndexed { index, c ->
            val i = index % 2
            when (c) {
                '<' -> x[i]--
                '>' -> x[i]++
                '^' -> y[i]--
                'v' -> y[i]++
                else -> error("invalid direction")
            }
            houses.add(Pair(x[i], y[i]))
        }
        houses.count().log()
    }
}
