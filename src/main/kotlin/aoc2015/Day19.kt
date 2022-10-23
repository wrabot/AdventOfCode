package aoc2015

import tools.Day

class Day19(test: Int? = null) : Day(2015, 19, test) {
    override fun solvePart1() = replacements.flatMap { (replacement, regex) ->
        regex.toRegex().findAll(molecule).map { molecule.replaceRange(it.range, replacement) }
    }.distinct().count()

    override fun solvePart2() = molecule.count(0)!!

    fun String.count(level: Int): Int = cache.getOrPut(this) {
        level.log()
        if (this == "e") return@getOrPut level
        val keys = keys.filter { it in this }.takeIf { it.isNotEmpty() } ?: return@getOrPut Int.MAX_VALUE
        keys.minOf { replaceFirst(it, replacements[it]!!).count(level + 1) }
    }

    private val replacements = lines.takeWhile { it != "" }
        .associate { it.split(" => ").let { (origin, replacement) -> replacement to origin } }
    private val molecule = lines.last()
    private val keys = replacements.keys.sortedByDescending { it.length }
    private val cache = mutableMapOf<String, Int>()
}
