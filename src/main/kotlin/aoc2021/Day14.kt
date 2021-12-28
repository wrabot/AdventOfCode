package aoc2021

import tools.Day

class Day14(test: Int? = null) : Day(2021, 14, test) {
    override fun getPart1() = part1
    override fun getPart2() = part2

    private val part1: Long
    private val part2: Long

    init {
        val template = lines[0]
        val rules = lines.drop(2).map {
            it.split(" -> ").let { (pattern, insert) -> pattern.zipWithNext().first() to insert[0] }
        }.toMap()

        var generation = template.zipWithNext().groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        val differences = (1..40).map { count ->
            generation = generation.flatMap {
                val insert = rules[it.key]
                if (insert == null) listOf(it.key to it.value) else listOf(
                    (it.key.first to insert) to it.value,
                    (insert to it.key.second) to it.value
                )
            }.cumulate()
            difference(generation, template)
        }
        part1 = differences[9]
        part2 = differences[39]
    }

    private fun <T> List<Pair<T, Long>>.cumulate() =
        groupBy { it.first }.mapValues { entry -> entry.value.sumOf { it.second } }

    private fun difference(generation: Map<Pair<Char, Char>, Long>, template: String) =
        (generation.map { it.key.first to it.value } + (template.last() to 1L))
            .cumulate().values.sorted().let { it.last() - it.first() }
}
