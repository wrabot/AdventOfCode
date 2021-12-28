package aoc2015

import tools.Day

class Day3 : Day(2015, 3) {
    override fun getPart1(): Any {
        var x = 0
        var y = 0
        val houses = mutableSetOf(Pair(x, y))
        lines[0].forEach {
            when (it) {
                '<' -> x--
                '>' -> x++
                '^' -> y--
                'v' -> y++
                else -> error("invalid direction")
            }
            houses.add(Pair(x, y))
        }
        return houses.count()
    }

    override fun getPart2(): Any {
        val x = mutableListOf(0, 0)
        val y = mutableListOf(0, 0)
        val houses = mutableSetOf(Pair(0, 0))
        lines[0].forEachIndexed { index, c ->
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
        return houses.count()
    }
}
