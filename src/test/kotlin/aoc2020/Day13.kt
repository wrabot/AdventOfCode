package aoc2020

import Day
import tools.math.Modular
import tools.math.bi

class Day13(test: Int? = null) : Day(test) {
    override fun solvePart1(): Any {
        val min = lines[0].toInt()
        return lines[1].split(",").mapNotNull { it.toIntOrNull() }
            .map { it to it - min % it }.minByOrNull { it.second }!!.let { it.first * it.second }
    }

    override fun solvePart2() = lines[1].split(",")
        .mapIndexedNotNull { index, s -> s.toBigIntegerOrNull()?.to(index.bi) }
        .reduce { acc, v ->
            val a = acc.first * v.first
            var f = acc.first
            while (f % v.first != 1.bi) f += acc.first
            Modular(a).run { a to a + acc.second - f * (v.second + acc.second) }
        }.second
}
