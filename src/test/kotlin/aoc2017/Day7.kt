package aoc2017

import Day

class Day7(test: Int? = null) : Day(test) {
    override fun solvePart1() = root

    override fun solvePart2() = findError(root)!!

    private fun findError(node: String): Int? = tree[node]!!.run {
        for (child in children) return@run findError(child) ?: continue
        val nodes = children.map { tree[it]!! }
        total = weight + nodes.sumOf { it.total!! }
        val (ok, ko) = nodes.groupBy { it.total!! }.values.partition { it.singleOrNull() == null }
        if (ko.isEmpty()) return@run null
        ko.first().first().run { weight - total!! } + ok.first().first().total!!
    }

    private data class Node(val weight: Int, val children: List<String>) {
        var total: Int? = null
    }

    private val tree = lines.associate {
        it.trim(')').split(" (", ") -> ", ", ").let { it[0] to Node(it[1].toInt(), it.drop(2)) }
    }
    private val root = (tree.keys - tree.values.flatMap { it.children }.toSet()).single()
}
