package aoc2019

import Day
import tools.log

class Day14(test: Int? = null) : Day(test) {
    override fun solvePart1(): Long {
        val remaining = mutableMapOf("ORE" to Long.MAX_VALUE)
        val todo = mutableListOf(Chemical("FUEL", 1))
        solve(remaining, todo)
        return Long.MAX_VALUE - remaining["ORE"]!!
    }

    override fun solvePart2(): Long {
        var fuel = 0L
        val oreFor1fuel = part1.toString().toLong()
        val remaining = mutableMapOf("ORE" to 1000_000_000_000L)
        while (true) {
            val min = (remaining["ORE"]!! / oreFor1fuel).coerceAtLeast(1)
            val todo = mutableListOf(Chemical("FUEL", min))
            solve(remaining, todo)
            if (remaining.values.any { it < 0L }) break
            fuel += min
        }
        return fuel
    }

    private fun solve(remaining: MutableMap<String, Long>, todo: MutableList<Chemical>) {
        while (true) {
            val chemical = todo.removeFirstOrNull() ?: break
            var r = (remaining[chemical.name] ?: 0) - chemical.quantity
            if (r < 0) {
                val rule = rules[chemical.name]
                if (rule != null) {
                    val factor = (rule.output.quantity - 1 - r) / rule.output.quantity
                    r += factor * rule.output.quantity
                    todo.addAll(rule.input.map { Chemical(it.name, it.quantity * factor) })
                }
            }
            remaining[chemical.name] = r
        }
    }

    data class Chemical(val name: String, val quantity: Long)
    data class Reaction(val input: List<Chemical>, val output: Chemical)

    private val rules = lines.associate { line ->
        val (input, output) = line.split(" => ")
        val chemical = output.toChemical()
        chemical.name to Reaction(input.split(", ").map { it.toChemical() }, chemical)
    }

    private fun String.toChemical(): Chemical {
        val (q, n) = split(" ")
        return Chemical(n, q.toLong())
    }
}
