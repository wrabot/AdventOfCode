package aoc2018

import Day
import kotlin.math.abs

class Day25(test: Int? = null) : Day(test) {
    override fun solvePart1() = lines.map { line -> line.split(',').map { it.toInt() } }
        .fold(emptyList<List<List<Int>>>()) { acc, point ->
            val (i, o) = acc.partition { it.any { point.zip(it) { a, b -> abs(a - b) }.sum() <= 3 } }
            o.plusElement(i.flatten().plusElement(point))
        }.size

    override fun solvePart2() = Unit
}
