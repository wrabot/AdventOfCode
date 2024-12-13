package aoc2024

import Day
import java.math.BigInteger
import java.math.BigInteger.ZERO

class Day13(test: Int? = null) : Day(test) {
    private val regex =
        "(?s)Button A: X\\+(\\d+), Y\\+(\\d+).*?Button B: X\\+(\\d+), Y\\+(\\d+).*?Prize: X=(\\d+), Y=(\\d+)"
    private val prizes = Regex(regex).findAll(input).map { result ->
        result.groupValues.drop(1).map { it.toBigInteger() }
    }.toList()

    override fun solvePart1() = cost()

    override fun solvePart2() = cost(10000000000000.toBigInteger())
    
    private fun cost(offset: BigInteger = ZERO) = prizes.mapNotNull {
        val (ax, ay, bx, by) = it
        val px = it[4] + offset
        val py = it[5] + offset
        val d = ay * bx - ax * by
        val a = (bx * py - by * px).divideAndRemainder(d)
        val b = (ay * px - ax * py).divideAndRemainder(d)
        if (a[1] == ZERO && b[1] == ZERO) a[0] * 3.toBigInteger() + b[0] else null
    }.reduce { acc, bigInteger -> acc + bigInteger }
}
