package aoc2018

import Day

class Day21(test: Int? = null) : Day(test) {
    override fun solvePart1() = cache.first()
    override fun solvePart2() = cache.last()
    
    private val cache = mutableListOf<Int>()

    init {
        WristDevice(input).run {
            if (it[2] != 28) return@run false
            if (it[1] in cache) return@run true
            cache.add(it[1])
            false
        }
    }
}
