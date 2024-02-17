package aoc2015

import Day
import kotlin.math.sqrt

class Day20(test: Int? = null) : Day(test) {
    override fun solvePart1(): Int {
        var i = 1
        while (sigma(i) < goal / 10) i++
        return i
    }

    private val cache = mutableMapOf(1 to 1)
    fun sigma(n: Int): Int = cache.getOrPut(n) {
        for (d in 2..sqrt(n.toFloat()).toInt()) {
            if (n % d != 0) continue
            var r = n
            var p = d
            while (r % d == 0) {
                r /= d
                p *= d
            }
            return@getOrPut (p - 1) / (d - 1) * sigma(r)
        }
        n + 1
    }

    override fun solvePart2(): Int {
        val g = goal / 11
        val houses = IntArray(g)
        for (elf in 1..g) {
            repeat(50) {
                val n = elf * (it + 1)
                if (n < houses.size) houses[n] += elf
            }
        }
        return houses.indexOfFirst { it > g }
    }

    private val goal = input.toInt()
}
