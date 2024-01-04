package aoc2021

import Day

class Day14(test: Int? = null) : Day(2021, 14, test) {
    override fun solvePart1(): Any {
        repeat(10) { step() }
        return difference()
    }

    override fun solvePart2(): Any {
        part1 // force part1
        repeat(40 - 10) { step() }
        return difference()
    }

    private val template = lines[0]
    private val rules = lines.drop(2).associate {
        it.split(" -> ").let { (pattern, insert) -> pattern.zipWithNext().first() to insert[0] }
    }

    private var generation = template.zipWithNext().groupingBy { it }.eachCount().mapValues { it.value.toLong() }

    private fun step() {
        generation = generation.flatMap {
            val insert = rules[it.key]
            if (insert == null) listOf(it.key to it.value) else listOf(
                (it.key.first to insert) to it.value,
                (insert to it.key.second) to it.value
            )
        }.cumulate()
    }

    private fun difference() =
        (generation.map { it.key.first to it.value } + (template.last() to 1L))
            .cumulate().values.sorted().let { it.last() - it.first() }

    private fun <T> List<Pair<T, Long>>.cumulate() =
        groupBy { it.first }.mapValues { entry -> entry.value.sumOf { it.second } }
}
