package aoc2020

import tools.Day
import java.math.BigInteger

class Day13(test: Int? = null) : Day(2020, 13, test) {
    override fun getPart1(): Any {
        val min = lines[0].toInt()
        return lines[1].split(",").filter { it != "x" }.map { it.toInt() }.map { it to (it - min % it) % it }
            .minByOrNull { it.second }!!.let { it.first * it.second }
    }

    override fun getPart2(): Any {
        val buses = lines[1].split(",")
            .mapIndexedNotNull { index, s -> if (s == "x") null else s.toBigInteger() to index.toBigInteger() }
        return buses.reduce { acc, v ->
            val f = (1 until v.first.toInt()).map { it.toBigInteger() } // is always small
                .first { (it * acc.first) % v.first == BigInteger.ONE }
            val a = acc.first * v.first
            val b = acc.second - acc.first * f * (v.second + acc.second)
            a to (b % a + a) % a
        }.second
    }
}
