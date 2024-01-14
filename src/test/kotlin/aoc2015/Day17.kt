package aoc2015

import Day

class Day17(test: Int? = null) : Day(test) {
    override fun solvePart1() = containers.combinations(150)
    override fun solvePart2() = containers.combinations(150, 0)!!.first

    private val containers = lines.map { it.toInt() }

    // part 1
    private fun List<Int>.combinations(capacity: Int): Int = mapIndexed { index, container ->
        when {
            container > capacity -> 0
            container == capacity -> 1
            else -> subList(index + 1, size).combinations(capacity - container)
        }
    }.sum()

    // part 2
    private fun List<Int>.combinations(capacity: Int, numberOfContainer: Int): Pair<Int, Int>? =
        mapIndexedNotNull { index, container ->
            when {
                container > capacity -> null
                container == capacity -> 1 to numberOfContainer
                else -> subList(index + 1, size).combinations(capacity - container, numberOfContainer + 1)
            }
        }.takeIf { it.isNotEmpty() }?.run {
            val minNumberOfContainer = minOf { it.second }
            filter { it.second == minNumberOfContainer }.sumOf { it.first } to minNumberOfContainer
        }
}
