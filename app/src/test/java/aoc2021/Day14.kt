package aoc2021

import forEachInput
import log

object Day14 {
    fun solve() = forEachInput(2021, 14, 1, 2) { lines ->
        val template = lines[0]
        val rules = lines.drop(2).map {
            it.split(" -> ").let { (pattern, insert) -> pattern.zipWithNext().first() to insert[0] }
        }.toMap()

        var generation = template.zipWithNext().groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        repeat(40) { count ->
            generation = generation.flatMap {
                val insert = rules[it.key]
                if (insert == null) listOf(it.key to it.value) else listOf((it.key.first to insert) to it.value, (insert to it.key.second) to it.value)
            }.cumulate()
            if (count == 9) {
                log("part 1: ")
                difference(generation, template).log()
            }
        }
        log("part 2: ")
        difference(generation, template).log()
    }

    private fun <T> List<Pair<T, Long>>.cumulate() = groupBy { it.first }.mapValues { entry -> entry.value.sumOf { it.second } }

    private fun difference(generation: Map<Pair<Char, Char>, Long>, template: String) = (generation.map { it.key.first to it.value } + (template.last() to 1L))
        .cumulate().values.sorted().let { it.last() - it.first() }
}
