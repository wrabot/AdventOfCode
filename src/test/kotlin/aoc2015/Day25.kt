package aoc2015

import Day
import tools.math.Modular
import tools.math.bi

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1() = Modular(33554393.bi).run {
        val index = (row + column - 2) * (row + column - 1) / 2 + column - 1
        20151125.bi * 252533.bi.pow(index.bi)
    }

    override fun solvePart2() = Unit

    val row = 2947
    val column = 3029
}


