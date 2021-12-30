package aoc2020

import tools.Day

class Day10(test: Int? = null) : Day(2020, 10, test) {
    override fun solvePart1(): Any {
        val list = mutableListOf(links.first())
        while (list.last() != 0) {
            list.add(groups.getValue(list.last()).first())
        }
        val diff = list.zipWithNext { a, b -> a - b }
        return diff.count { it == 1 } * (diff.count { it == 3 } + 1)
    }

    override fun solvePart2(): Any {
        val counts = mutableMapOf(0 to 1L)
        adapters.reversed().forEach {
            counts[it] = groups.getValue(it).fold(0L) { acc, i -> acc + counts[i]!! }
        }
        return counts[adapters.first()]!!
    }

    private val adapters = lines.map { it.toInt() }.sortedDescending()
    private val links = adapters + 0
    private val groups = links.groupBy({ it }) { v -> links.filter { it in v - 3 until v } }
        .mapValues { it.value.flatten() }
}
