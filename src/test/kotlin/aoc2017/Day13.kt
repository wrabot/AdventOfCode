package aoc2017

import Day

class Day13(test: Int? = null) : Day(test) {
    override fun solvePart1() = scanners.map { if (it.key % ((it.value - 1) * 2) == 0) it.key * it.value else 0 }.sum()

    override fun solvePart2(): Int {
        var delay = 0
        val cycles = scanners.mapValues { (it.value - 1) * 2 }
        while (cycles.any { (it.key + delay) % it.value == 0 }) delay++
        return delay
    }
    
    private val scanners = lines.associate { line ->
        val (k, v) = line.split(": ")
        k.toInt() to v.toInt()
    }
}
