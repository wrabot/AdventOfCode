package aoc2019

import tools.Day

class Day6 : Day(2019, 6) {
    override fun solvePart1() = orbits.keys.sumOf { it.suborbital().size }

    override fun solvePart2(): Int {
        val you = "YOU".suborbital()
        val san = "SAN".suborbital()
        return you.size + san.size - 2 * you.intersect(san).size
    }

    private val cache = mutableMapOf<String, Set<String>>()
    private fun String.suborbital(): Set<String> = cache.getOrPut(this) {
        val next = orbits[this] ?: return@getOrPut emptySet()
        next.suborbital() + next
    }

    private val orbits = lines.associate { line -> line.split(")").let { it[1] to it[0] } }
}
