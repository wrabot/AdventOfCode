package aoc2017

import Day
import tools.toWords

class Day6(test: Int? = null) : Day(test) {
    override fun solvePart1() = cache.lastIndex
    override fun solvePart2() = cache.lastIndex - cache.indexOf(cache.last())

    private val cache = mutableListOf(input.toWords().map { it.toInt() })

    init {
        var steps = 0
        val memory = cache.first().toIntArray()
        while (true) {
            steps++
            var max = memory.max()
            var index = memory.indexOf(max)
            memory[index++] = 0
            while (max-- > 0) memory[(index++) % memory.size]++
            cache.add(memory.toList())
            if (cache.indexOf(cache.last()) < cache.lastIndex) break
        }
    }
}
