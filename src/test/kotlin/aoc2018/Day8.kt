package aoc2018

import Day

class Day8(test: Int? = null) : Day(test) {
    override fun solvePart1() = root.sum()

    fun Node.sum(): Int = metadata.sum() + children.sumOf { it.sum() }

    override fun solvePart2() = root.value()

    fun Node.value(): Int =
        if (children.isEmpty()) metadata.sum() else metadata.sumOf { children.getOrNull(it - 1)?.value() ?: 0 }

    val root = input.split(" ").map { it.toInt() }.parse(0).first

    private fun List<Int>.parse(offset: Int): Pair<Node, Int> {
        var index = offset
        val childCount = get(index++)
        val dataCount = get(index++)
        val children = mutableListOf<Node>()
        repeat(childCount) {
            parse(index).run {
                children.add(first)
                index = second
            }
        }
        return Node(subList(index, index + dataCount), children) to index + dataCount
    }

    data class Node(val metadata: List<Int>, val children: List<Node>)
}
