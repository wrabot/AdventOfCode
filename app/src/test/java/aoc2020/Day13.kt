package aoc2020

import forEachInput
import tools.log
import java.math.BigInteger

object Day13 {
    fun solve() = forEachInput(2020, 13, 2) { lines ->
        log("part 1: ")
        part1(lines)
        log("part 2: ")
        part2(lines)
    }

    private fun part1(lines: List<String>) {
        val min = lines[0].toInt()
        lines[1].split(",").filter { it != "x" }.map { it.toInt() }.map { it to (it - min % it) % it }
            .minByOrNull { it.second }!!.let { it.first * it.second }.log()
    }

    private fun part2(lines: List<String>) {
        val buses = lines[1].split(",")
            .mapIndexedNotNull { index, s -> if (s == "x") null else s.toBigInteger() to index.toBigInteger() }
        val timestamp = buses.reduce { acc, v ->
            val f = (1 until v.first.toInt()).map { it.toBigInteger() } // is always small
                .first { (it * acc.first) % v.first == BigInteger.ONE }
            val a = acc.first * v.first
            val b = acc.second - acc.first * f * (v.second + acc.second)
            a to (b % a + a) % a
        }.second.log()
        buses.all { (timestamp + it.second) % it.first == BigInteger.ZERO }.log()
    }
}
