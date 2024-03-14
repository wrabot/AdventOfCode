package aoc2017

import Day
import tools.toWords

class Day15(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        var a = initA
        var b = initB
        return (1..40_000_000).count {
            a = a.nextA()
            b = b.nextB()
            test(a, b)
        }
    }

    override fun solvePart2(): Int {
        var a = initA
        var b = initB
        return (1..5_000_000).count {
            do a = a.nextA() while (a and 3 != 0L)
            do b = b.nextB() while (b and 7 != 0L)
            test(a, b)
        }
    }

    private val initA = lines[0].toWords().last().toLong()
    private val initB = lines[1].toWords().last().toLong()
    private val m = Int.MAX_VALUE.toLong()
    private fun Long.nextA() = (this * 16807).mod(m)
    private fun Long.nextB() = (this * 48271).mod(m)
    private fun test(a: Long, b: Long) = a and 65535 == b and 65535
}
