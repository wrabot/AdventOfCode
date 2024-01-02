package aoc2020

import tools.Day
import tools.math.times

class Day13(test: Int? = null) : Day(2020, 13, test) {
    override fun solvePart1(): Any {
        val min = lines[0].toInt()
        return lines[1].split(",").mapNotNull { it.toIntOrNull() }
            .map { it to it - min % it }.minByOrNull { it.second }!!.let { it.first * it.second }
    }

    override fun solvePart2() = lines[1].split(",")
        .mapIndexedNotNull { index, s -> s.toLongOrNull()?.to(index.toLong()) }
        .reduce { acc, v ->
            val a = acc.first * v.first
            var f = acc.first
            while (f % v.first != 1L) f += acc.first
            a to a + acc.second - f.times(v.second + acc.second, a)
        }.second
}
