package aoc2022

import Day

class Day3(test: Int? = null) : Day(test) {
    override fun solvePart1() = rucksacks.sumOf { it.chunked(it.size / 2).commonItem() }
    override fun solvePart2() = rucksacks.chunked(3).sumOf { it.commonItem() }
    private fun List<List<Int>>.commonItem() = map { it.toSet() }.reduce { acc, items -> acc.intersect(items) }.single()
    private val rucksacks = lines.map { line -> line.map { if (it.isLowerCase()) (it - 'a' + 1) else it - 'A' + 27 } }
}
